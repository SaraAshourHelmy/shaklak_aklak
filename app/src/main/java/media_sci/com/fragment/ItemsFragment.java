package media_sci.com.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.adapter.ItemAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.models.UserData;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/24/2015.
 */
public class ItemsFragment extends Fragment implements View.OnClickListener
        , AdapterView.OnItemClickListener, View.OnTouchListener {

    private ListView lst_items;
    // private ImageView img_cancel;
    private TextView tv_search_cancel;
    private View actionbar;
    private int section_id = -1, section_type = -1;
    private String section_img;
    private ArrayList<Ingredients> lst_items_content;
    private ItemAdapter itemAdapter;
    private EditText et_search;
    private ArrayList<Ingredients> searchList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_items, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {

        // check keyboard
        Utility.CheckKeyboardVisible(view);
        searchList.clear();

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("sec_id")) {
            section_id = bundle.getInt("sec_id");
            section_img = bundle.getString("sec_img");

            // type mean category or restaurant
            section_type = bundle.getInt("sec_type");
        }

        lst_items = (ListView) view.findViewById(R.id.lst_items);
        actionbar = (View) view.findViewById(R.id.actionbar_items);
        et_search = (EditText) view.findViewById(R.id.et_item_search);
        tv_search_cancel = (TextView) view.findViewById(R.id.tv_search_cancel);
        tv_search_cancel.setOnClickListener(this);
        lst_items.setOnItemClickListener(this);

        SetFont();
        Utility.ActionBarSetting(actionbar, getString(R.string.your_food), 2
                , section_img, getActivity()); //
        SetupList();

        // set touch focus in editText
        et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_search.setFocusableInTouchMode(true);
                return false;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0)
                    tv_search_cancel.setVisibility(View.VISIBLE);
                else
                    tv_search_cancel.setVisibility(View.GONE);


                SearchItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        view.setOnTouchListener(this);
    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(getActivity());
        et_search.setTypeface(typeface);
        tv_search_cancel.setTypeface(typeface);

    }

    private void SetupList() {
        if (section_type == 0) {
            // search item by category
            lst_items_content = Ingredients.GetAllIngredientsCat(getActivity()
                    , section_id);
        } else if (section_type == 1) {
            // search item by restaurant
            lst_items_content = Ingredients.GetAllIngredientsRest(getActivity()
                    , section_id);
        }
        itemAdapter = new ItemAdapter(getActivity(), R.layout.adapter_item, lst_items_content);
        lst_items.setAdapter(itemAdapter);
    }

    private void SearchItems(String txt) {

        UserData userData = new UserData(getActivity());

        //ArrayList<Ingredients> searchList = new ArrayList<>();

        searchList.clear();
        //int searchListLength = searchList.size();
        for (int i = 0; i < lst_items_content.size(); i++) {

            if (userData.GetLanguage() == StaticVarClass.English) {
                String item_txt = lst_items_content.get(i).getItem_name_en().toLowerCase();
                if (item_txt.contains(txt.toLowerCase())) {
                    searchList.add(lst_items_content.get(i));
                }
            } else {
                String item_txt = lst_items_content.get(i).getItem_name_ar().toLowerCase();
                if (item_txt.contains(txt.toLowerCase())) {
                    searchList.add(lst_items_content.get(i));
                }
            }

        }

        itemAdapter = new ItemAdapter(getActivity(), R.layout.adapter_item, searchList);
        lst_items.setAdapter(itemAdapter);

    }

    @Override
    public void onClick(View v) {

        if (v == tv_search_cancel) {
            et_search.setText("");
            Utility.HideKeyboard(getActivity(), et_search);
            //SetupList();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Utility.HideKeyboard(getActivity(), et_search);
        if (searchList.size() > 0) {
            OpenItemDetailsFragment(searchList.get(position).getId());
        } else {
            OpenItemDetailsFragment(lst_items_content.get(position).getId());
        }

    }

    private void OpenItemDetailsFragment(int item_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("item_id", item_id);
        bundle.putString("sec_img", section_img);

        Fragment itemsFragment = new ItemDetailsFragment(); // set constructor with parameters
        itemsFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, itemsFragment);

        ft.hide(ItemsFragment.this);
        ft.addToBackStack(ItemsFragment.class.getName());
        ft.commit();

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v != et_search)
            Utility.HideKeyboard(getActivity(), et_search);
        return true;
    }
}
