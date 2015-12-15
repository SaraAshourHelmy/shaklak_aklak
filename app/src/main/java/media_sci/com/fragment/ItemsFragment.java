package media_sci.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import media_sci.com.adapter.ItemAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/24/2015.
 */
public class ItemsFragment extends Fragment {

    private ListView lst_items;
    private View actionbar;
    private int cat_id;
    private String cat_name;
    private ArrayList<Ingredients> lst_items_content;
    private ItemAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_items, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("cat_id"))
            cat_id = bundle.getInt("cat_id");

        lst_items = (ListView) view.findViewById(R.id.lst_items);
        actionbar = (View) view.findViewById(R.id.actionbar_items);

        Utility.ActionBarSetting(actionbar,cat_name,2); //

        lst_items_content = Ingredients.GetAllRestaurant(getActivity());
        itemAdapter = new ItemAdapter(getActivity(), R.layout.adapter_item, lst_items_content);
        lst_items.setAdapter(itemAdapter);


    }

}
