package media_sci.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import media_sci.com.adapter.CategoryAdapter;
import media_sci.com.models.Category;
import media_sci.com.shaklak_aklak.R;

/**
 * Created by Bassem on 11/18/2015.
 */
public class FindFoodFragment extends Fragment implements AdapterView.OnItemClickListener {


    ArrayList<Category> lst_category_items;
    private ListView lst_category;
    private LinearLayout lnr_sideIndex;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_food, container, false);
        SetupTools(v);
        return v;
    }


    private void SetupTools(View view) {

        lst_category = (ListView) view.findViewById(R.id.lst_category);

        // get category from db or webservice then set adapter
        ArrayList<Integer> lst_test = new ArrayList<>();
        lst_test.add(1);
       // lst_test.add(3);
        Category.DeleteCategory(lst_test, getActivity());

        lst_category_items = Category.GetAllCategory(getActivity());
        categoryAdapter = new CategoryAdapter(getActivity()
                , R.layout.adapter_category, lst_category_items);
        lst_category.setAdapter(categoryAdapter);
        lst_category.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
