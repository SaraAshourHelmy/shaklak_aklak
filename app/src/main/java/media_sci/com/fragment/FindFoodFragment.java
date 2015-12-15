package media_sci.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import media_sci.com.adapter.CategoryAdapter;
import media_sci.com.models.Category;
import media_sci.com.shaklak_aklak.IndexedSearchActivity;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/18/2015.
 */
public class FindFoodFragment extends Fragment implements AdapterView.OnItemClickListener {


    ArrayList<Category> lst_category_items;
    private ListView lst_category;
    private View actionbar;
    private CategoryAdapter categoryAdapter;
    private ImageView img_search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_food, container, false);
        SetupTools(v);
        return v;
    }


    private void SetupTools(View view) {

        lst_category = (ListView) view.findViewById(R.id.lst_category);
        actionbar = (View) view.findViewById(R.id.actionbar_FindFood);
        Utility.ActionBarSetting(actionbar, getString(R.string.your_food), 1, "");

        img_search = (ImageView) actionbar.findViewById(R.id.img_action_icon);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IndexedSearchActivity.class);
                startActivity(intent);
            }
        });


        // get category from db or webservice then set adapter
        lst_category_items = Category.GetAllCategory(getActivity());
        categoryAdapter = new CategoryAdapter(getActivity()
                , R.layout.adapter_category, lst_category_items);
        lst_category.setAdapter(categoryAdapter);
        lst_category.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        OpenItemFragment(lst_category_items.get(position).getId(),
                lst_category_items.get(position).getName_en(),
                lst_category_items.get(position).getImg_url());
    }

    private void OpenItemFragment(int cat_id, String cat_name, String cat_img) {

        Bundle bundle = new Bundle();
        bundle.putInt("cat_id", cat_id);
        bundle.putString("cat_name", cat_name);
        bundle.putString("cat_img", cat_img);

        Fragment itemsFragment = new ItemsFragment(); // set constructor with parameters
        itemsFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, itemsFragment);

        ft.hide(FindFoodFragment.this);
        ft.addToBackStack(ArticlesFragment.class.getName());
        ft.commit();
    }
}
