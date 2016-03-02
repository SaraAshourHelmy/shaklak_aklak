package media_sci.com.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.models.Ingredients;
import media_sci.com.models.UserCalculation;
import media_sci.com.models.UserFavourite;
import media_sci.com.models.UserMeal;
import media_sci.com.shaklak_aklak.MainActivity;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;


public class CustomItemDetailsFragment extends Fragment implements View.OnClickListener {

    private static View view;

    private View actionbar;
    private double pkr_float_value = 0, serving_size = 1;
    private double pkr_integer_value = 1;

    // for serving size dialog
    private LinearLayout lnr_picker;
    private NumberPicker pkr_integer, pkr_float;
    private TextView tv_servingNo_done;
    private LinearLayout lnr_servingNo;
    private TextView tv_item_name, tv_servingSize, tv_servingSize_value;
    private TextView tv_servingNo, tv_servingNo_value, tv_percent;
    private TextView tv_calories, tv_calories_value, tv_caloriesFat, tv_caloriesFat_value;
    private TextView tv_dailyValue, tv_totalFat, tv_totalFat_value, tv_totalFat_percent;
    private TextView tv_satFat, tv_satFat_value, tv_satFat_percent;
    private TextView tv_cholest, tv_cholest_value, tv_cholest_percent;
    private TextView tv_sodium, tv_sodium_value, tv_sodium_percent;
    private TextView tv_potassium, tv_potassium_value, tv_potassium_percent;
    private TextView tv_totalCarb, tv_totalCarb_value, tv_totalCarb_percent;
    private TextView tv_dietaryFiber, tv_dietaryFiber_value, tv_dietaryFiber_percent;
    private TextView tv_sugars, tv_sugars_value, tv_sugars_percent;
    private TextView tv_protein, tv_protein_value, tv_protein_percent;
    private TextView tv_vitaminA, tv_vitaminA_value, tv_vitaminA_percent;
    private TextView tv_vitaminC, tv_vitaminC_value, tv_vitaminC_percent;
    private TextView tv_calcium, tv_calcium_value, tv_calcium_percent;
    private TextView tv_iron, tv_iron_value, tv_iron_percent;
    private Button btn_addMeal, btn_addFavourite;
    private ImageView img_arrow;

    private String sec_img_url = "";
    private String item_id = "";
    private Ingredients ingredients = null;
    private double details_calories, details_caloriesFat, details_totalFat,
            details_satFat, details_cholest, details_sodium,
            details_potassium, details_carbo, details_fiber, details_sugars,
            details_protein, details_vitaminA, details_vitaminC,
            details_calcium, details_iron;

    // daily value
    private double daily_totalFat, daily_satFat, daily_cholest, daily_sodium,
            daily_potassium, daily_carbo, daily_fiber, daily_sugars, daily_protein,
            daily_vitaminA, daily_vitaminC, daily_calcium, daily_iron;


    // needs value
    private double need_totalFat, need_satFat, need_cholest, need_sodium,
            need_potassium, need_carbo, need_fiber, need_sugars, need_protein,
            need_vitaminA, need_vitaminC, need_calcium, need_iron;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        // SetDialog();
        try {
            view = inflater.inflate(R.layout.fragment_item_details, container, false);
            SetupTools();
        } catch (InflateException e) {
            Log.e("itemDetails_error", "" + e);

        }

        return view;
    }

    private void SetupTools() {

        StaticVarClass.gram = getString(R.string.gram);
        StaticVarClass.milli_gram = getString(R.string.milli_gram);

        actionbar = (View) view.findViewById(R.id.actionbar_itemDetails);
        img_arrow = (ImageView) view.findViewById(R.id.img_serving_size_arrow);
        img_arrow.setVisibility(View.INVISIBLE);


        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("item_id")) {

            sec_img_url = bundle.getString("sec_img");
            item_id = bundle.getString("item_id");
            Log.e("item_id", item_id + "");
            ingredients = Ingredients.GetCustomIngredient(getActivity(), item_id);
        }

        // set image of custom item
        Utility.ActionBarSetting(actionbar, getString(R.string.your_food), 2,
                sec_img_url, getActivity());

        lnr_servingNo = (LinearLayout) view.findViewById(R.id.lnr_servingNo);
        tv_item_name = (TextView) view.findViewById(R.id.tv_details_itemName);
        tv_servingSize = (TextView) view.findViewById(R.id.tv_servingSize);
        tv_servingSize_value = (TextView) view.findViewById(R.id.tv_servingSize_value);


        // set serving size
        Utility.TextDirection(getActivity(), tv_servingSize_value,
                StaticVarClass.TextView_Type);

        String unit_name = (ingredients.getType() == StaticVarClass.Food) ?
                getString(R.string.gram) : getString(R.string.liter);

        tv_servingSize_value.setText(ingredients.getUnit_value() + " " +
                unit_name);

        tv_servingNo = (TextView) view.findViewById(R.id.tv_servingNo);
        tv_servingNo_value = (TextView) view.findViewById(R.id.tv_servingNo_value);
        tv_servingNo_value.setText("1");
        tv_percent = (TextView) view.findViewById(R.id.tv_serving_percent);

        // table definition
        tv_calories = (TextView) view.findViewById(R.id.tv_details_calories);
        tv_calories_value = (TextView) view.findViewById(R.id.tv_details_calories_value);
        tv_caloriesFat = (TextView) view.findViewById(R.id.tv_details_caloriesFat);
        tv_caloriesFat_value = (TextView) view.findViewById
                (R.id.tv_details_caloriesFat_value);

        tv_dailyValue = (TextView) view.findViewById(R.id.tv_details_dailyValue);
        tv_totalFat = (TextView) view.findViewById(R.id.tv_details_totalFat);
        tv_totalFat_value = (TextView) view.findViewById(R.id.tv_details_totalFat_value);
        tv_totalFat_percent = (TextView) view.findViewById(R.id.tv_details_totalFat_percent);

        tv_satFat = (TextView) view.findViewById(R.id.tv_details_satFat);
        tv_satFat_value = (TextView) view.findViewById(R.id.tv_details_satFat_value);
        tv_satFat_percent = (TextView) view.findViewById(R.id.tv_details_satFat_percent);

        tv_cholest = (TextView) view.findViewById(R.id.tv_details_cholest);
        tv_cholest_value = (TextView) view.findViewById(R.id.tv_details_cholest_value);
        tv_cholest_percent = (TextView) view.findViewById(R.id.tv_details_cholest_percent);

        tv_sodium = (TextView) view.findViewById(R.id.tv_details_sodium);
        tv_sodium_value = (TextView) view.findViewById(R.id.tv_details_sodium_value);
        tv_sodium_percent = (TextView) view.findViewById(R.id.tv_details_sodium_percent);

        tv_potassium = (TextView) view.findViewById(R.id.tv_details_potassium);
        tv_potassium_value = (TextView) view.findViewById(R.id.tv_details_potassium_value);
        tv_potassium_percent = (TextView) view.findViewById(R.id.tv_details_potassium_percent);

        tv_totalCarb = (TextView) view.findViewById(R.id.tv_details_totalCarbo);
        tv_totalCarb_value = (TextView) view.findViewById(R.id.tv_details_totalCarbo_value);
        tv_totalCarb_percent = (TextView) view.findViewById(R.id.tv_details_totalCarbo_percent);

        tv_dietaryFiber = (TextView) view.findViewById(R.id.tv_details_dietaryFiber);
        tv_dietaryFiber_value = (TextView) view.findViewById(R.id.tv_details_dietaryFiber_value);
        tv_dietaryFiber_percent = (TextView) view.findViewById(R.id.tv_details_dietaryFiber_percent);

        tv_sugars = (TextView) view.findViewById(R.id.tv_details_sugars);
        tv_sugars_value = (TextView) view.findViewById(R.id.tv_details_sugars_value);
        tv_sugars_percent = (TextView) view.findViewById(R.id.tv_details_sugars_percent);

        tv_protein = (TextView) view.findViewById(R.id.tv_details_protein);
        tv_protein_value = (TextView) view.findViewById(R.id.tv_details_protein_value);
        tv_protein_percent = (TextView) view.findViewById(R.id.tv_details_protein_percent);

        tv_vitaminA = (TextView) view.findViewById(R.id.tv_details_VitaminA);
        tv_vitaminA_value = (TextView) view.findViewById(R.id.tv_details_vitaminA_value);
        tv_vitaminA_percent = (TextView) view.findViewById(R.id.tv_details_VitaminA_percent);

        tv_vitaminC = (TextView) view.findViewById(R.id.tv_details_VitaminC);
        tv_vitaminC_value = (TextView) view.findViewById(R.id.tv_details_vitaminC_value);
        tv_vitaminC_percent = (TextView) view.findViewById(R.id.tv_details_VitaminC_percent);

        tv_calcium = (TextView) view.findViewById(R.id.tv_details_calcium);
        tv_calcium_value = (TextView) view.findViewById(R.id.tv_details_calcium_value);
        tv_calcium_percent = (TextView) view.findViewById(R.id.tv_details_calcium_percent);

        tv_iron = (TextView) view.findViewById(R.id.tv_details_iron);
        tv_iron_value = (TextView) view.findViewById(R.id.tv_details_iron_value);
        tv_iron_percent = (TextView) view.findViewById(R.id.tv_details_iron_percent);

        btn_addMeal = (Button) view.findViewById(R.id.btn_addToMeal);
        btn_addFavourite = (Button) view.findViewById(R.id.btn_addToFavourite);

        btn_addFavourite.setOnClickListener(this);
        btn_addMeal.setOnClickListener(this);
        lnr_servingNo.setOnClickListener(this);

        SetFont();
        //SetDialog();
        GetUserNeeds();
        SetIngredientDefault();
        SetViewValues();
    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(getActivity());

        tv_item_name.setTypeface(typeface);
        tv_servingSize.setTypeface(typeface);
        tv_servingSize_value.setTypeface(typeface);
        tv_servingNo.setTypeface(typeface);
        tv_servingNo_value.setTypeface(typeface);
        tv_percent.setTypeface(typeface);
        tv_calories.setTypeface(typeface);
        tv_calories_value.setTypeface(typeface);
        tv_caloriesFat.setTypeface(typeface);
        tv_caloriesFat_value.setTypeface(typeface);
        tv_dailyValue.setTypeface(typeface);
        tv_totalFat.setTypeface(typeface);
        tv_totalFat_value.setTypeface(typeface);
        tv_totalFat_percent.setTypeface(typeface);
        tv_satFat.setTypeface(typeface);
        tv_satFat_value.setTypeface(typeface);
        tv_satFat_percent.setTypeface(typeface);
        tv_cholest.setTypeface(typeface);
        tv_cholest_value.setTypeface(typeface);
        tv_cholest_percent.setTypeface(typeface);
        tv_sodium.setTypeface(typeface);
        tv_sodium_value.setTypeface(typeface);
        tv_sodium_percent.setTypeface(typeface);
        tv_potassium.setTypeface(typeface);
        tv_potassium_value.setTypeface(typeface);
        tv_potassium_percent.setTypeface(typeface);
        tv_totalCarb.setTypeface(typeface);
        tv_totalCarb_value.setTypeface(typeface);
        tv_totalCarb_percent.setTypeface(typeface);
        tv_dietaryFiber.setTypeface(typeface);
        tv_dietaryFiber_value.setTypeface(typeface);
        tv_dietaryFiber_percent.setTypeface(typeface);
        tv_sugars.setTypeface(typeface);
        tv_sugars_value.setTypeface(typeface);
        tv_sugars_percent.setTypeface(typeface);
        tv_protein.setTypeface(typeface);
        tv_protein_value.setTypeface(typeface);
        tv_protein_percent.setTypeface(typeface);
        tv_vitaminA.setTypeface(typeface);
        tv_vitaminA_value.setTypeface(typeface);
        tv_vitaminA_percent.setTypeface(typeface);
        tv_vitaminC.setTypeface(typeface);
        tv_vitaminC_value.setTypeface(typeface);
        tv_vitaminC_percent.setTypeface(typeface);
        tv_calcium.setTypeface(typeface);
        tv_calcium_value.setTypeface(typeface);
        tv_calcium_percent.setTypeface(typeface);
        tv_iron.setTypeface(typeface);
        tv_iron_value.setTypeface(typeface);
        tv_iron_percent.setTypeface(typeface);
        btn_addMeal.setTypeface(typeface);
        btn_addFavourite.setTypeface(typeface);
    }

    private void GetUserNeeds() {

        UserCalculation calc = new UserCalculation(getActivity());
        need_totalFat = calc.TotalFat();
        need_satFat = calc.SatFatCalc();
        need_cholest = calc.Cholest();
        need_sodium = calc.SodiumCalc();
        need_potassium = calc.Potassium();
        need_carbo = calc.Carbohydrate();
        need_fiber = calc.Fiber();
        need_sugars = calc.SugarCalc();
        need_protein = calc.ProteinCalc();
        need_vitaminA = calc.VitaminA();
        need_vitaminC = calc.VitaminC();
        need_calcium = calc.CalciumCalc();
        need_iron = calc.IronCalc();
    }

    private void SetIngredientDefault() {

        double serving_no = pkr_integer_value + pkr_float_value;

        details_calories = ingredients.getEnergy() * serving_no;

        Log.e("cal_serving_siz", "" + ingredients.getEnergy() +
                "/" + details_calories);


        // total fat
        details_totalFat = ingredients.getFat() * serving_no;

        details_caloriesFat = (details_totalFat * 100) / 3.25;

        daily_totalFat = (details_totalFat * StaticVarClass.percent) / need_totalFat;

        // sat fat
        details_satFat = ingredients.getSat_fat() * serving_no;
        daily_satFat = (details_satFat * StaticVarClass.percent) / need_satFat;

        // cholest
        details_cholest = ingredients.getCholest()
                * serving_no;
        daily_cholest = (details_cholest * StaticVarClass.percent) / need_cholest;

        // sodium
        details_sodium = ingredients.getSodium() * serving_no;
        daily_sodium = (details_sodium * StaticVarClass.percent) / need_sodium;

        // potassium
        details_potassium = ingredients.getPotassium() * serving_no;
        daily_potassium = (details_potassium * StaticVarClass.percent) / need_potassium;

        // carbo
        details_carbo = ingredients.getCarbo_tot() * serving_no;
        daily_carbo = (details_carbo * StaticVarClass.percent) / need_carbo;

        // fiber
        details_fiber = ingredients.getCarbo_fiber() * serving_no;
        daily_fiber = (details_fiber * StaticVarClass.percent) / need_fiber;

        // sugars
        details_sugars = ingredients.getSugars() * serving_no;
        daily_sugars = (details_sugars * StaticVarClass.percent) / need_sugars;

        // protein
        details_protein = ingredients.getProtein() * serving_no;
        daily_protein = (details_protein * StaticVarClass.percent) / need_protein;

        // vitamin A
        details_vitaminA = ingredients.getVit_a() * serving_no;
        daily_vitaminA = (details_vitaminA * StaticVarClass.percent) / need_vitaminA;

        // vitamin C
        details_vitaminC = ingredients.getAscorbic_acid() * serving_no;
        daily_vitaminC = (details_vitaminC * StaticVarClass.percent) / need_vitaminC;

        // calcium
        details_calcium = ingredients.getCalcium() * serving_no;
        daily_calcium = (details_calcium * StaticVarClass.percent) / need_calcium;

        // iron
        details_iron = ingredients.getIron() * serving_no;
        daily_iron = (details_iron * StaticVarClass.percent) / need_iron;

    }

    private void SetViewValues() {

        Utility.TextDirection(getActivity(), tv_item_name, StaticVarClass.TextView_Type);
        tv_item_name.setText(ingredients.getItem_name_en());

        tv_calories_value.setText(Utility.GetDecimalFormat(details_calories));
        tv_caloriesFat_value.setText(Utility.GetDecimalFormat(details_caloriesFat));

        // total fat
        tv_totalFat_value.setText(Utility.GetDecimalFormat(details_totalFat)
                + StaticVarClass.gram);
        tv_totalFat_percent.setText(Utility.GetDecimalFormat(daily_totalFat)
                + StaticVarClass.percent_sign);

        // sat fat
        tv_satFat_value.setText(Utility.GetDecimalFormat(details_satFat)
                + StaticVarClass.gram);
        tv_satFat_percent.setText(Utility.GetDecimalFormat(daily_satFat) +
                StaticVarClass.percent_sign);

        // cholest
        tv_cholest_value.setText(Utility.GetDecimalFormat(details_cholest)
                + StaticVarClass.milli_gram);
        tv_cholest_percent.setText(Utility.GetDecimalFormat(daily_cholest) +
                StaticVarClass.percent_sign);

        // sodium
        tv_sodium_value.setText(Utility.GetDecimalFormat(details_sodium)
                + StaticVarClass.milli_gram);
        tv_sodium_percent.setText(Utility.GetDecimalFormat(daily_sodium) +
                StaticVarClass.percent_sign);

        // potassium
        tv_potassium_value.setText(Utility.GetDecimalFormat(details_potassium)
                + StaticVarClass.milli_gram);
        tv_potassium_percent.setText(Utility.GetDecimalFormat(daily_potassium) +
                StaticVarClass.percent_sign);

        // carb
        tv_totalCarb_value.setText(Utility.GetDecimalFormat(details_carbo)
                + StaticVarClass.gram);
        tv_totalCarb_percent.setText(Utility.GetDecimalFormat(daily_carbo) +
                StaticVarClass.percent_sign);

        // fiber
        tv_dietaryFiber_value.setText(Utility.GetDecimalFormat(details_fiber)
                + StaticVarClass.gram);
        tv_dietaryFiber_percent.setText(Utility.GetDecimalFormat(daily_fiber) +
                StaticVarClass.percent_sign);

        // sugars
        tv_sugars_value.setText(Utility.GetDecimalFormat(details_sugars)
                + StaticVarClass.gram);
        tv_sugars_percent.setText(Utility.GetDecimalFormat(daily_sugars) +
                StaticVarClass.percent_sign);

        // protein
        tv_protein_value.setText(Utility.GetDecimalFormat(details_protein)
                + StaticVarClass.gram);
        tv_protein_percent.setText(Utility.GetDecimalFormat(daily_protein) +
                StaticVarClass.percent_sign);

        // vitamin a
        tv_vitaminA_value.setText(Utility.GetDecimalFormat(details_vitaminA)
                + StaticVarClass.IU);
        tv_vitaminA_percent.setText(Utility.GetDecimalFormat(daily_vitaminA) +
                StaticVarClass.percent_sign);

        // vitamin c
        tv_vitaminC_value.setText(Utility.GetDecimalFormat(details_vitaminC)
                + StaticVarClass.milli_gram);
        tv_vitaminC_percent.setText(Utility.GetDecimalFormat(daily_vitaminC) +
                StaticVarClass.percent_sign);

        // calcium
        tv_calcium_value.setText(Utility.GetDecimalFormat(details_calcium)
                + StaticVarClass.milli_gram);
        tv_calcium_percent.setText(Utility.GetDecimalFormat(daily_calcium) +
                StaticVarClass.percent_sign);

        // iron
        tv_iron_value.setText(Utility.GetDecimalFormat(details_iron)
                + StaticVarClass.milli_gram);
        tv_iron_percent.setText(Utility.GetDecimalFormat(daily_iron) +
                StaticVarClass.percent_sign);

    }

    public void onClick(View v) {

        if (v == btn_addMeal) {

            AddToMeal();

        } else if (v == btn_addFavourite) {

            Log.e("item_id", item_id + "");
            if (!item_id.equals("")) {

                AddFavourite();
            }
        } else if (v == lnr_servingNo) {
            SetServingNumber();
        } else if (v == tv_servingNo_done) {
            HideServingNumber();
        }

    }

    private void AddToMeal() {

        String date = Utility.GetStringDateNow();
        int count = UserMeal.GetMaxUserMeal(getActivity()) + 1;
        String macAddress = Utility.GetMacAddress(getActivity());
        String ID = count + macAddress;


        UserMeal userMeal = new UserMeal();
        userMeal.setId(ID);
        userMeal.setIngredient_id(ingredients.getCustomID());
        userMeal.setDate(date);
        userMeal.setIs_custom(ingredients.getIs_custom());
        userMeal.setCounter_id(count);

        // serving value calculation
        double serving_no = pkr_integer_value + pkr_float_value;
        Log.e("serving_no", serving_no + "");
        userMeal.setServing_no(serving_no);
        userMeal.setServing_size(serving_size);


        ArrayList<UserMeal> lst_userMeal = new ArrayList<>();
        lst_userMeal.add(userMeal);
        UserMeal.InsertUserMeal(lst_userMeal, getActivity());

        StaticVarClass.lst_meals.add(userMeal);

        // call user meal webservice
        FavAndMeal favAndMeal = new FavAndMeal(getActivity(), 2);

    }

    private void AddFavourite() {

        //Log.e("fav_item", "" + item_id);
        StaticVarClass.CheckFavouriteID(item_id);

        Ingredients.SetFavourite(getActivity(), item_id,
                ingredients.getIs_custom());
        UserFavourite userFavourite = new UserFavourite();
        userFavourite.setMeal_id(item_id);
        userFavourite.setIs_custom(ingredients.getIs_custom());

        StaticVarClass.lst_favourites_user.add(userFavourite);
        Log.e("size", StaticVarClass.lst_favourites_user.size() + "");

        FavAndMeal favAndMeal = new FavAndMeal(getActivity(), 1);

    }

    private void SetServingNumber() {

        MainActivity.mTabHost.getTabWidget().setEnabled(false);
        MainActivity.screenNo = 2;

        Animation bottomUp = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);

        ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.rltv_main);
        lnr_picker = (LinearLayout) viewGroup.findViewById(R.id.lnr_picker);
        pkr_integer = (NumberPicker) viewGroup.findViewById(R.id.pkr_integer);
        pkr_float = (NumberPicker) viewGroup.findViewById(R.id.pkr_float);
        tv_servingNo_done =
                (TextView) viewGroup.findViewById(R.id.tv_servingNo_done);
        tv_servingNo_done.setOnClickListener(this);

        // set animation when open view
        lnr_picker.startAnimation(bottomUp);
        lnr_picker.setVisibility(View.VISIBLE);

        pkr_integer.setMinValue(1);
        pkr_integer.setMaxValue(10);
        pkr_integer.setWrapSelectorWheel(false);

        // this line to remove keyboard and editable
        pkr_integer.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        pkr_integer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                pkr_integer_value = newVal;
                tv_servingNo_value.setText(pkr_integer_value + "");
                SetIngredientDefault();
                SetViewValues();
                Log.e("pkr_integer", "" + newVal);
            }
        });


        pkr_float.setMaxValue(StaticVarClass.GetServingNoPicker().length - 1);
        pkr_float.setMinValue(0);
        pkr_float.setWrapSelectorWheel(false); //this line remove max from top
        pkr_float.setDisplayedValues(StaticVarClass.GetServingNoPicker());

        // this line to remove keyboard and editable
        pkr_float.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        pkr_float.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                pkr_float_value = StaticVarClass.float_Serving_no[newVal];
                // Log.e("pkr_float", "" + pkr_float_value);
                Log.e("pkr_float", "" + newVal);
                if (newVal == 0)
                    tv_percent.setText("");
                else
                    tv_percent.setText(StaticVarClass.GetServingNoPicker()[newVal]);
                SetIngredientDefault();
                SetViewValues();
            }
        });

    }

    private void HideServingNumber() {
        MainActivity.mTabHost.getTabWidget().setEnabled(true);
        Animation bottom_down = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);
        lnr_picker.startAnimation(bottom_down);
        lnr_picker.setVisibility(View.GONE);
    }
}

