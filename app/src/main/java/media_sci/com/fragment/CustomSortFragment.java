package media_sci.com.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.Utility;

public class CustomSortFragment extends Fragment implements View.OnClickListener {

    private View actionbar;
    private TextView tv_title1, tv_title2;
    private TextView tv_calories, tv_fat, tv_cholest;
    private TextView tv_sodium, tv_protein, tv_carbo;
    private Button btn_calories_high, btn_calories_low;
    private Button btn_fat_high, btn_fat_low;
    private Button btn_cholest_high, btn_cholest_low;
    private Button btn_sodium_high, btn_sodium_low;
    private Button btn_protein_high, btn_protein_low;
    private Button btn_carbo_high, btn_carbo_low;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_custom_sort, container, false);
        SetupTools(view);

        return view;
    }

    private void SetupTools(View view) {

        actionbar = (View) view.findViewById(R.id.actionbar_custom_food);
        Utility.ActionBarSetting(actionbar, getString(R.string.custom_foods),
                0, "", getActivity());

        tv_title1 = (TextView) view.findViewById(R.id.tv_customFood_title1);
        tv_title2 = (TextView) view.findViewById(R.id.tv_customFood_title2);
        // tv_footer = (TextView) view.findViewById(R.id.tv_customFood_footer);
        tv_calories = (TextView) view.findViewById(R.id.tv_customFood_calories);
        tv_fat = (TextView) view.findViewById(R.id.tv_customFood_fat);
        tv_cholest = (TextView) view.findViewById(R.id.tv_customFood_cholest);
        tv_sodium = (TextView) view.findViewById(R.id.tv_customFood_sodium);
        tv_protein = (TextView) view.findViewById(R.id.tv_customFood_protein);
        tv_carbo = (TextView) view.findViewById(R.id.tv_customFood_carbo);

        btn_calories_high = (Button) view.findViewById(R.id.btn_highest_calories);
        btn_calories_low = (Button) view.findViewById(R.id.btn_lowest_calories);
        btn_fat_high = (Button) view.findViewById(R.id.btn_highest_fat);
        btn_fat_low = (Button) view.findViewById(R.id.btn_lowest_fat);
        btn_cholest_high = (Button) view.findViewById(R.id.btn_highest_cholest);
        btn_cholest_low = (Button) view.findViewById(R.id.btn_lowest_cholest);
        btn_sodium_high = (Button) view.findViewById(R.id.btn_highest_sodium);
        btn_sodium_low = (Button) view.findViewById(R.id.btn_lowest_sodium);
        btn_protein_high = (Button) view.findViewById(R.id.btn_highest_protein);
        btn_protein_low = (Button) view.findViewById(R.id.btn_lowest_protein);
        btn_carbo_high = (Button) view.findViewById(R.id.btn_highest_carbo);
        btn_carbo_low = (Button) view.findViewById(R.id.btn_lowest_carbo);

        btn_calories_high.setOnClickListener(this);
        btn_calories_low.setOnClickListener(this);
        btn_fat_high.setOnClickListener(this);
        btn_fat_low.setOnClickListener(this);
        btn_cholest_high.setOnClickListener(this);
        btn_cholest_low.setOnClickListener(this);
        btn_sodium_high.setOnClickListener(this);
        btn_sodium_low.setOnClickListener(this);
        btn_protein_high.setOnClickListener(this);
        btn_protein_low.setOnClickListener(this);
        btn_carbo_high.setOnClickListener(this);
        btn_carbo_low.setOnClickListener(this);

        SetFont();
    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(getActivity());
        tv_title1.setTypeface(typeface, Typeface.BOLD);
        tv_title2.setTypeface(typeface);
        //tv_footer.setTypeface(typeface);
        tv_calories.setTypeface(typeface);
        tv_fat.setTypeface(typeface);
        tv_sodium.setTypeface(typeface);
        tv_protein.setTypeface(typeface);
        tv_carbo.setTypeface(typeface);
        tv_cholest.setTypeface(typeface);

        btn_calories_high.setTypeface(typeface);
        btn_calories_low.setTypeface(typeface);
        btn_fat_high.setTypeface(typeface);
        btn_fat_low.setTypeface(typeface);
        btn_sodium_high.setTypeface(typeface);
        btn_sodium_low.setTypeface(typeface);
        btn_protein_high.setTypeface(typeface);
        btn_protein_low.setTypeface(typeface);
        btn_carbo_high.setTypeface(typeface);
        btn_carbo_low.setTypeface(typeface);
        btn_cholest_high.setTypeface(typeface);
        btn_cholest_low.setTypeface(typeface);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_calories_high) {

            OpenSortFragment(1, 1);
        } else if (v == btn_calories_low) {
            OpenSortFragment(1, 0);

        } else if (v == btn_fat_high) {
            OpenSortFragment(2, 1);
        } else if (v == btn_fat_low) {
            OpenSortFragment(2, 0);
        } else if (v == btn_cholest_high) {
            OpenSortFragment(3, 1);
        } else if (v == btn_cholest_low) {
            OpenSortFragment(3, 0);
        } else if (v == btn_sodium_high) {
            OpenSortFragment(4, 1);
        } else if (v == btn_sodium_low) {
            OpenSortFragment(4, 0);
        } else if (v == btn_protein_high) {
            OpenSortFragment(5, 1);
        } else if (v == btn_protein_low) {
            OpenSortFragment(5, 0);
        } else if (v == btn_carbo_high) {
            OpenSortFragment(6, 1);
        } else if (v == btn_carbo_low) {
            OpenSortFragment(6, 0);
        }
    }

    private void OpenSortFragment(int sort_no, int sort_type) {
        Bundle bundle = new Bundle();
        bundle.putInt("sort_no", sort_no);
        bundle.putInt("sort_type", sort_type);

        Fragment sortFragment = new SortFragment(); // set constructor with parameters
        sortFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(android.R.id.tabcontent, sortFragment);

        ft.hide(CustomSortFragment.this);
        ft.addToBackStack(CustomSortFragment.class.getName());
        ft.commit();
    }
}
