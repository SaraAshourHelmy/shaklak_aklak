package media_sci.com.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.adapter.ItemAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.CustomImageLoader;
import media_sci.com.utility.Utility;

public class ViewCustomFoodFragment extends Fragment implements
        AdapterView.OnItemClickListener, View.OnClickListener, View.OnTouchListener {

    public static ArrayList<Ingredients> lst_myMeals = new ArrayList<>();
    public static ListView lst_custom_food;
    public static ItemAdapter CustomAdapter;
    private TextView tv_actionTitle;
    private ImageView img_addFood, img_actionIcon;
    private EditText et_search;
    private TextView tv_search_cancel;
    private String img;
    private ArrayList<Ingredients> searchList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_custom_food, container, false);
        SetupTools(view);
        return view;
    }

    private void SetupTools(View view) {

        // check keyboard
        Utility.CheckKeyboardVisible(view);
        searchList.clear();

        view.setOnTouchListener(this);

        Bundle bundle = getArguments();
        if (bundle.containsKey("sec_img")) {

            img = bundle.getString("sec_img");
        }
        tv_actionTitle = (TextView) view.findViewById(R.id.tv_actionFood_title);
        img_addFood = (ImageView) view.findViewById(R.id.img_add_food);
        img_actionIcon = (ImageView) view.findViewById(R.id.img_actionFood_icon);
        et_search = (EditText) view.findViewById(R.id.et_customFood_search);
        tv_search_cancel = (TextView) view.findViewById(R.id.tv_customFood_cancel);

        tv_search_cancel.setOnClickListener(this);

        lst_custom_food = (ListView) view.findViewById(R.id.lst_custom_food);
        SetFont();

        lst_myMeals = Ingredients.GetCustomIngredients(getActivity());


        CustomAdapter = new ItemAdapter(getActivity(),
                R.layout.adapter_item, lst_myMeals);
        lst_custom_food.setAdapter(CustomAdapter);
        lst_custom_food.setOnItemClickListener(this);

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

        img_addFood.setOnClickListener(this);

        // set img of actionbar
        CustomImageLoader.getInstance().loadImage(img
                , img_actionIcon, null);
    }


    private void SetFont() {
        Typeface typeface = Utility.GetFont(getActivity());
        tv_actionTitle.setTypeface(typeface, Typeface.BOLD);
        et_search.setTypeface(typeface);
        tv_search_cancel.setTypeface(typeface);
    }

    private void SearchItems(String txt) {

        //ArrayList<Ingredients> searchList = new ArrayList<>();
        searchList.clear();

        int searchListLength = searchList.size();
        for (int i = 0; i < lst_myMeals.size(); i++) {

            String item_txt = lst_myMeals.get(i).getItem_name_en().toLowerCase();
            if (item_txt.contains(txt.toLowerCase())) {
                searchList.add(lst_myMeals.get(i));
            }

        }

        CustomAdapter = new ItemAdapter(getActivity(), R.layout.adapter_item, searchList);
        lst_custom_food.setAdapter(CustomAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Utility.HideKeyboard(getActivity(), getActivity().getCurrentFocus());

        if (searchList.size() > 0) {
            OpenItemDetailsFragment(searchList.get(position).getCustomID());
        } else {
            OpenItemDetailsFragment(lst_myMeals.get(position).getCustomID());
        }

    }

    private void OpenItemDetailsFragment(String item_id) {
        Bundle bundle = new Bundle();
        bundle.putString("item_id", item_id);
        bundle.putString("sec_img", img);

        Fragment itemsFragment = new CustomItemDetailsFragment(); // set constructor with parameters
        itemsFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, itemsFragment);

        ft.hide(ViewCustomFoodFragment.this);
        ft.addToBackStack(ViewCustomFoodFragment.class.getName());
        ft.commit();
    }

    @Override
    public void onClick(View v) {

        Utility.HideKeyboard(getActivity(), getActivity().getCurrentFocus());

        if (v == img_addFood) {

            OpenCustomFoodFragment();
        } else if (v == tv_search_cancel) {
            et_search.setText("");
            //SearchItems("");
        }
    }

    private void OpenCustomFoodFragment() {

        Bundle bundle = new Bundle();
        Fragment customFoodFragment = new CustomFoodFragment();
        customFoodFragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, customFoodFragment);
        ft.hide(ViewCustomFoodFragment.this);
        ft.addToBackStack(ViewCustomFoodFragment.class.getName());
        ft.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v != et_search) {

            Utility.HideKeyboard(getActivity(), getActivity().getCurrentFocus());
        }
        return false;
    }
}
