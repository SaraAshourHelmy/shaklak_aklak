package media_sci.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import media_sci.com.adapter.ItemAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 11/24/2015.
 */
public class ItemsFragment extends Fragment implements View.OnClickListener {

    private ListView lst_items;
    private ImageView img_cancel;
    private View actionbar;
    private int cat_id;
    private String cat_name, cat_img;
    private ArrayList<Ingredients> lst_items_content;
    private ItemAdapter itemAdapter;
    private EditText et_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_items, container, false);
        SetupTools(v);
        return v;
    }

    private void SetupTools(View view) {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("cat_id")) {
            cat_id = bundle.getInt("cat_id");
            cat_name = bundle.getString("cat_name");
            cat_img = bundle.getString("cat_img");
        }

        lst_items = (ListView) view.findViewById(R.id.lst_items);
        actionbar = (View) view.findViewById(R.id.actionbar_items);
        et_search = (EditText) view.findViewById(R.id.et_item_search);
        img_cancel = (ImageView) view.findViewById(R.id.img_searchItem_cancel);
        img_cancel.setOnClickListener(this);

        Utility.ActionBarSetting(actionbar, cat_name, 2, cat_img); //
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

                SearchItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void SetupList() {
        lst_items_content = Ingredients.GetAllIngredientsCat(getActivity(), cat_id);
        itemAdapter = new ItemAdapter(getActivity(), R.layout.adapter_item, lst_items_content);
        lst_items.setAdapter(itemAdapter);
    }

    private void SearchItems(String txt) {

        ArrayList<Ingredients> searchList = new ArrayList<>();

        int searchListLength = searchList.size();
        for (int i = 0; i < lst_items_content.size(); i++) {
            if (lst_items_content.get(i).getItem_name_en().contains(txt)) {
                searchList.add(lst_items_content.get(i));
            }

        }
        itemAdapter = new ItemAdapter(getActivity(), R.layout.adapter_item, searchList);
        lst_items.setAdapter(itemAdapter);

    }

    @Override
    public void onClick(View v) {

        if (v == img_cancel) {
            et_search.setText("");
            SetupList();
        }
    }
}
