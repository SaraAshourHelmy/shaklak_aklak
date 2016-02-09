package media_sci.com.fragment;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.adapter.SimpleAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.models.UserFavourite;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.Sorting;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/18/2015.
 */
public class FavouriteFragment extends Fragment implements AdapterView.OnItemLongClickListener {


    private View actionbar;
    private ListView lst_favourite;
    private ArrayList<Ingredients> lst_favourite_items;
    private ArrayList<String> lst_favouriteName = new ArrayList<>();
    private SimpleAdapter adapter;
    private TextView tv_msg;
    private int item_type;
    private Button btn_yes, btn_no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {

        lst_favouriteName.clear();
        //lst_favourite_items.clear();
        actionbar = (View) view.findViewById(R.id.actionbar_favourite);
        Utility.ActionBarSetting(actionbar, getString(R.string.favourite), 0
                , "", getActivity());

        lst_favourite = (ListView) view.findViewById(R.id.lst_favourite);
        lst_favourite_items = Ingredients.GetFavourites(getActivity());
        lst_favourite_items.addAll(Ingredients.GetFavouritesCustom(getActivity()));
        lst_favourite_items = Sorting.SortIngredients(lst_favourite_items);

        Log.e("favourite_count", lst_favourite_items.size() + "");

        for (int i = 0; i < lst_favourite_items.size(); i++) {
            lst_favouriteName.add(lst_favourite_items.get(i).getItem_name_en());
        }

        adapter = new SimpleAdapter(getActivity(), R.layout.adapter_simple
                , lst_favouriteName);
        lst_favourite.setAdapter(adapter);
        lst_favourite.setOnItemLongClickListener(this);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        SetupDialog(position);
        Log.e("long click item", position + "");
        return true;
    }

    private void SetupDialog(final int position) {
        final Dialog delete_dialog = new Dialog(getActivity(),
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);

        delete_dialog.setContentView(R.layout.dialog_confirm_action);

        tv_msg = (TextView) delete_dialog.findViewById(R.id.tv_dialog_msg);
        btn_yes = (Button) delete_dialog.findViewById(R.id.btn_dialog_yes);
        btn_no = (Button) delete_dialog.findViewById(R.id.btn_dialog_no);
        tv_msg.setText(getString(R.string.delete_msg));
        SetFont();

        delete_dialog.show();
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("item_id", lst_favourite_items.get(position).getId() + "");

                UserFavourite userFavourite = new UserFavourite();
                userFavourite.setIs_custom(
                        lst_favourite_items.get(position).getIs_custom());

                Log.e("is_custom_item", lst_favourite_items.get(position).getIs_custom() + "");
                if (lst_favourite_items.get(position).getIs_custom() == 0) {

                    StaticVarClass.CheckFavouriteID(
                            String.valueOf(lst_favourite_items.get(position).getId()));

                    userFavourite.setMeal_id(
                            String.valueOf(lst_favourite_items.get(position).getId()));

                    // remove from db
                    Ingredients.RemoveFavourite(getActivity()
                            , String.valueOf(lst_favourite_items.get(position).getId()),
                            lst_favourite_items.get(position).getIs_custom());
                } else {

                    Log.e("remove_item_id",
                            lst_favourite_items.get(position).getCustomID());
                    StaticVarClass.CheckFavouriteID(
                            lst_favourite_items.get(position).getCustomID());

                    userFavourite.setMeal_id(
                            String.valueOf(lst_favourite_items.get(position).getCustomID()));

                    Ingredients.RemoveFavourite(getActivity()
                            , lst_favourite_items.get(position).getCustomID(),
                            lst_favourite_items.get(position).getIs_custom());
                }
                StaticVarClass.lst_removed_favourite.add(userFavourite);

                lst_favouriteName.remove(position);
                lst_favourite_items.remove(position);
                adapter.notifyDataSetChanged();
                FavAndMeal favAndMeal = new FavAndMeal(getActivity(), 3);

                delete_dialog.dismiss();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_dialog.dismiss();
            }
        });

    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(getActivity());
        tv_msg.setTypeface(typeface);
        btn_yes.setTypeface(typeface);
        btn_no.setTypeface(typeface);
    }

}
