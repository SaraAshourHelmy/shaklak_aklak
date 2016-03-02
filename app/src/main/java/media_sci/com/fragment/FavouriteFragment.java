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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.adapter.FavouriteAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.models.UserData;
import media_sci.com.models.UserFavourite;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.Sorting;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/18/2015.
 */
public class FavouriteFragment extends Fragment
        implements AdapterView.OnItemLongClickListener, View.OnClickListener,
        AdapterView.OnItemClickListener {


    private View actionbar;
    private ListView lst_favourite;
    private ArrayList<Ingredients> lst_favourite_items;
    private ArrayList<String> lst_favouriteName = new ArrayList<>();
    private FavouriteAdapter adapter;
    private TextView tv_msg;
    //private int item_type;
    private Button btn_yes, btn_no;
    private ImageView img_action_icon;
    private TextView tv_delete, tv_done, tv_clear_all;
    private int type_mode = StaticVarClass.No_Delete_Mode;
    private Dialog delete_dialog;

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
        SetupAction();

        lst_favourite = (ListView) view.findViewById(R.id.lst_favourite);
        lst_favourite_items = Ingredients.GetFavourites(getActivity());
        lst_favourite_items.addAll(Ingredients.GetFavouritesCustom(getActivity()));
        lst_favourite_items = Sorting.SortIngredients(getActivity(), lst_favourite_items);

        Log.e("favourite_count", lst_favourite_items.size() + "");

        UserData userData = new UserData(getActivity());
        int lang = userData.GetLanguage();

        for (int i = 0; i < lst_favourite_items.size(); i++) {
            if (lang == StaticVarClass.English)
                lst_favouriteName.add(lst_favourite_items.get(i).getItem_name_en());
            else if (lang == StaticVarClass.Arabic)
                lst_favouriteName.add(lst_favourite_items.get(i).getItem_name_ar());
        }


        SetupFavouriteList(StaticVarClass.No_Delete_Mode);
        lst_favourite.setOnItemLongClickListener(this);
        lst_favourite.setOnItemClickListener(this);

    }

    private void SetupAction() {

        img_action_icon = (ImageView) actionbar.findViewById(R.id.img_action_icon);
        tv_delete = (TextView) actionbar.findViewById(R.id.tv_delete_item);
        tv_done = (TextView) actionbar.findViewById(R.id.tv_done_action);
        tv_clear_all = (TextView) actionbar.findViewById(R.id.tv_clearAll);

        tv_done.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_clear_all.setOnClickListener(this);

        img_action_icon.setVisibility(View.GONE);
        tv_delete.setVisibility(View.VISIBLE);
        tv_done.setVisibility(View.GONE);
        tv_clear_all.setVisibility(View.VISIBLE);
    }

    private void SetupFavouriteList(int type) {

        adapter = new FavouriteAdapter(getActivity(), R.layout.adapter_simple
                , lst_favouriteName, type);
        lst_favourite.setAdapter(adapter);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        SetupDialog(position);
        Log.e("long click item", position + "");
        return true;
    }

    private void SetupDialog(final int position) {
        //final Dialog

        delete_dialog = new Dialog(getActivity(),
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

                UserFavourite userFavourite =
                        GetUserFavourite(lst_favourite_items.get(position));

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
        tv_clear_all.setTypeface(typeface);
        tv_done.setTypeface(typeface);
        tv_delete.setTypeface(typeface);
    }

    private UserFavourite GetUserFavourite(Ingredients ingredients) {

        UserFavourite userFavourite = new UserFavourite();
        userFavourite.setIs_custom(
                ingredients.getIs_custom());

        Log.e("is_custom_item", ingredients.getIs_custom() + "");
        if (ingredients.getIs_custom() ==
                StaticVarClass.NotCustom) {

            StaticVarClass.CheckFavouriteID(
                    String.valueOf(ingredients.getId()));

            userFavourite.setMeal_id(
                    String.valueOf(ingredients.getId()));

            // remove from db
            Ingredients.RemoveFavourite(getActivity()
                    , String.valueOf(ingredients.getId()),
                    ingredients.getIs_custom());
        } else {

            Log.e("remove_item_id",
                    ingredients.getCustomID());
            StaticVarClass.CheckFavouriteID(
                    ingredients.getCustomID());

            userFavourite.setMeal_id(
                    String.valueOf(ingredients.getCustomID()));

            Ingredients.RemoveFavourite(getActivity()
                    , ingredients.getCustomID(),
                    ingredients.getIs_custom());
        }

        return userFavourite;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (type_mode == StaticVarClass.Delete_Mode)
            SetupDialog(position);

    }

    public void SetupClearAllDialog() {
        delete_dialog = new Dialog(getActivity(),
                android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);

        delete_dialog.setContentView(R.layout.dialog_confirm_action);

        tv_msg = (TextView) delete_dialog.findViewById(R.id.tv_dialog_msg);
        btn_yes = (Button) delete_dialog.findViewById(R.id.btn_dialog_yes);
        btn_no = (Button) delete_dialog.findViewById(R.id.btn_dialog_no);
        tv_msg.setText(getString(R.string.clear_msg));
        SetFont();

        delete_dialog.show();
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClearAllFavourite();

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

    private void PressDone() {

        img_action_icon.setVisibility(View.GONE);
        tv_delete.setVisibility(View.VISIBLE);
        tv_done.setVisibility(View.GONE);

        // invisible delete icon
        SetupFavouriteList(StaticVarClass.No_Delete_Mode);
        type_mode = StaticVarClass.No_Delete_Mode;
    }

    private void ClearAllFavourite() {

        for (int i = 0; i < lst_favourite_items.size(); i++) {

            StaticVarClass.lst_removed_favourite.add(GetUserFavourite
                    (lst_favourite_items.get(i)));
        }

        lst_favouriteName.clear();
        lst_favourite_items.clear();
        adapter.notifyDataSetChanged();
        FavAndMeal favAndMeal = new FavAndMeal(getActivity(), 3);
    }

    private void PressDelete() {
        img_action_icon.setVisibility(View.GONE);
        tv_delete.setVisibility(View.GONE);
        tv_done.setVisibility(View.VISIBLE);

        // view delete icon
        SetupFavouriteList(StaticVarClass.Delete_Mode);
        type_mode = StaticVarClass.Delete_Mode;

    }


    @Override
    public void onClick(View v) {

        if (v == tv_done) {
            PressDone();
        } else if (v == tv_delete) {
            PressDelete();
        } else if (v == tv_clear_all) {

            SetupClearAllDialog();
        }
    }


}

