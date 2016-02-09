package media_sci.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import media_sci.com.adapter.RestAdapter;
import media_sci.com.models.Restaurant;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

public class RestFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View actionbar;
    private ListView lst_rest;
    private ArrayList<Restaurant> lst_rest_items;
    private RestAdapter rest_adapter;
    private String img = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rest, container, false);
        SetupTools(view);
        return view;
    }

    private void SetupTools(View view) {

        Bundle b = getArguments();
        if (b.containsKey("sec_img")) {
            img = b.getString("sec_img");
        }

        actionbar = (View) view.findViewById(R.id.actionbar_rest);
        Utility.ActionBarSetting(actionbar, getString(R.string.restaurant)
                , 2, img, getActivity());


        lst_rest = (ListView) view.findViewById(R.id.lst_restaurant);

        lst_rest_items = Restaurant.GetAllRestaurant(getActivity());
        rest_adapter = new RestAdapter(getActivity()
                , R.layout.adapter_category, lst_rest_items);
        lst_rest.setAdapter(rest_adapter);
        lst_rest.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        OpenItemFragment(lst_rest_items.get(position).getId(),
                lst_rest_items.get(position).getImg_url());
    }

    private void OpenItemFragment(int res_id, String res_img) {

        Bundle bundle = new Bundle();
        bundle.putInt("sec_id", res_id);
        bundle.putInt("sec_type", 1);
        bundle.putString("sec_img", res_img);

        Fragment itemsFragment = new ItemsFragment();
        itemsFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, itemsFragment);

        ft.hide(RestFragment.this);
        ft.addToBackStack(RestFragment.class.getName());
        ft.commit();
    }
}
