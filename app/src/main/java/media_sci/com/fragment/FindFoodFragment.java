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
public class FindFoodFragment extends Fragment implements
        AdapterView.OnItemClickListener, View.OnClickListener {


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
        Utility.ActionBarSetting(actionbar, getString(R.string.your_food), 1
                , "", getActivity());

        img_search = (ImageView) actionbar.findViewById(R.id.img_action_icon);
        img_search.setOnClickListener(this);


        // get category from db or webservice then set adapter
        lst_category_items = Category.GetAllCategory(getActivity());
        categoryAdapter = new CategoryAdapter(getActivity()
                , R.layout.adapter_category, lst_category_items);
        lst_category.setAdapter(categoryAdapter);
        lst_category.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String cat_img = lst_category_items.get(position).getImg_url();

        if (lst_category_items.get(position).getSection_type() == 0) {
            OpenItemFragment(lst_category_items.get(position).getId(),
                    cat_img);
        } else if (lst_category_items.get(position).getSection_type() == 1) {

            OpenResFragment(cat_img);
        } else if (lst_category_items.get(position).getSection_type() == 2) {

            // My Meal Custom
            OpenCustomMealFragment(cat_img);
        }
    }

    private void OpenItemFragment(int cat_id, String cat_img) {

        Bundle bundle = new Bundle();
        bundle.putInt("sec_id", cat_id);
        bundle.putInt("sec_type", 0);
        bundle.putString("sec_img", cat_img);

        Fragment itemsFragment = new ItemsFragment(); // set constructor with parameters
        itemsFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, itemsFragment);

        ft.hide(FindFoodFragment.this);
        ft.addToBackStack(FindFoodFragment.class.getName());
        ft.commit();
    }

    private void OpenResFragment(String img) {

        Bundle bundle = new Bundle();
        bundle.putString("sec_img", img);

        Fragment restFragment = new RestFragment(); // set constructor with parameters
        restFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, restFragment);

        ft.hide(FindFoodFragment.this);
        ft.addToBackStack(FindFoodFragment.class.getName());
        ft.commit();
    }

    private void OpenCustomMealFragment(String img) {

        Bundle bundle = new Bundle();
        bundle.putString("sec_img", img);

        Fragment customMealFragment = new ViewCustomFoodFragment(); // set constructor with parameters

        customMealFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, customMealFragment);

        ft.hide(FindFoodFragment.this);
        ft.addToBackStack(FindFoodFragment.class.getName());
        ft.commit();
    }

    @Override
    public void onClick(View v) {

        if (v == img_search) {
            Intent intent = new Intent(getActivity(), IndexedSearchActivity.class);
            startActivity(intent);
        }
    }
}
