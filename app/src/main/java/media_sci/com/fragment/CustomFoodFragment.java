package media_sci.com.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import media_sci.com.adapter.ItemAdapter;
import media_sci.com.models.Ingredients;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.FavAndMeal;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

public class CustomFoodFragment extends Fragment implements
        View.OnTouchListener, View.OnClickListener {

    private String[] units_arr;
    private ImageView img_close, img_add_food;
    private TextView tv_action_title, tv_servingSize;
    private EditText et_item_name, et_servingSize_value;
    private RelativeLayout rltv_servingSize;

    // item contents
    private TextView tv_calories, tv_totalFat, tv_satFat;
    private EditText et_calories_value, et_totalFat_value, et_satFat_value;
    private TextView tv_cholest, tv_sodium, tv_potassium, tv_protein;
    private EditText et_cholest_value, et_sodium_value, et_potassium_value, et_protein_value;
    private TextView tv_carbo, tv_fiber, tv_sugars, tv_vitaminA, tv_vitaminC;
    private EditText et_carbo_value, et_fiber_value, et_sugars_value,
            et_vitaminA_value, et_vitaminC_value;

    private TextView tv_calcium, tv_iron;
    private EditText et_calcium_value, et_iron_value;
    private Spinner spnr_units;
    private double calories, total_fat, sat_fat, cholest, sodium, potassium,
            protein, carbo, fiber, sugars, vitaminA, vitaminC, calcium, iron;

    private String item_name = "", unit_name = "gram";
    private int unit_value = 0;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_custom_food, container, false);
        SetupTools(view);
        return view;
    }

    private void SetupTools(View view) {

        // Utility.PushLayoutKeyboard(getActivity());

        units_arr = getResources().getStringArray(R.array.arr_units);

        ArrayList<Ingredients> lst_test = Ingredients.GetCustomIngredients(getActivity());
        Log.e("count_list", lst_test.size() + "");

        img_close = (ImageView) view.findViewById(R.id.img_close_customFood);
        img_add_food = (ImageView) view.findViewById(R.id.img_add_customFood);
        tv_action_title = (TextView) view.findViewById(R.id.tv_customFood_title);
        tv_servingSize = (TextView) view.findViewById(R.id.tv_custom_servingSize);
        et_servingSize_value = (EditText) view.findViewById(R.id.et_custom_servingSize_value);
        et_item_name = (EditText) view.findViewById(R.id.et_custom_itemName);
        rltv_servingSize = (RelativeLayout) view.findViewById(R.id.rltv_custom_servingSize);
        spnr_units = (Spinner) view.findViewById(R.id.spnr_units);

        // item contents
        tv_calories = (TextView) view.findViewById(R.id.tv_custom_calories);
        tv_totalFat = (TextView) view.findViewById(R.id.tv_custom_totalFat);
        tv_satFat = (TextView) view.findViewById(R.id.tv_custom_satFat);
        tv_cholest = (TextView) view.findViewById(R.id.tv_custom_cholest);
        tv_sodium = (TextView) view.findViewById(R.id.tv_custom_sodium);
        tv_potassium = (TextView) view.findViewById(R.id.tv_custom_potassium);
        tv_protein = (TextView) view.findViewById(R.id.tv_custom_protein);
        tv_carbo = (TextView) view.findViewById(R.id.tv_custom_totalCarbo);
        tv_fiber = (TextView) view.findViewById(R.id.tv_custom_dietaryFiber);
        tv_sugars = (TextView) view.findViewById(R.id.tv_custom_sugars);
        tv_vitaminA = (TextView) view.findViewById(R.id.tv_custom_VitaminA);
        tv_vitaminC = (TextView) view.findViewById(R.id.tv_custom_VitaminC);
        tv_calcium = (TextView) view.findViewById(R.id.tv_custom_calcium);
        tv_iron = (TextView) view.findViewById(R.id.tv_custom_iron);

        et_calories_value = (EditText) view.findViewById(R.id.et_custom_calories_value);
        et_totalFat_value = (EditText) view.findViewById(R.id.et_custom_totalFat_value);
        et_satFat_value = (EditText) view.findViewById(R.id.et_custom_satFat_value);
        et_cholest_value = (EditText) view.findViewById(R.id.et_custom_cholest_value);
        et_sodium_value = (EditText) view.findViewById(R.id.et_custom_sodium_value);
        et_potassium_value = (EditText) view.findViewById(R.id.et_custom_potassium_value);
        et_protein_value = (EditText) view.findViewById(R.id.et_custom_protein_value);
        et_carbo_value = (EditText) view.findViewById(R.id.et_custom_totalCarbo_value);
        et_fiber_value = (EditText) view.findViewById(R.id.et_custom_dietaryFiber_value);
        et_sugars_value = (EditText) view.findViewById(R.id.et_custom_sugars_value);
        et_vitaminA_value = (EditText) view.findViewById(R.id.et_custom_vitaminA_value);
        et_vitaminC_value = (EditText) view.findViewById(R.id.et_custom_vitaminC_value);
        et_calcium_value = (EditText) view.findViewById(R.id.et_custom_calcium_value);
        et_iron_value = (EditText) view.findViewById(R.id.et_custom_iron_value);

        et_item_name.setOnTouchListener(this);
        et_calories_value.setOnTouchListener(this);
        et_totalFat_value.setOnTouchListener(this);
        et_satFat_value.setOnTouchListener(this);
        et_cholest_value.setOnTouchListener(this);
        et_sodium_value.setOnTouchListener(this);
        et_potassium_value.setOnTouchListener(this);
        et_protein_value.setOnTouchListener(this);
        et_carbo_value.setOnTouchListener(this);
        et_fiber_value.setOnTouchListener(this);
        et_sugars_value.setOnTouchListener(this);
        et_vitaminA_value.setOnTouchListener(this);
        et_vitaminC_value.setOnTouchListener(this);
        et_calcium_value.setOnTouchListener(this);
        et_iron_value.setOnTouchListener(this);

        SetSpnrUnit();
        SetFont();

        img_add_food.setOnClickListener(this);
        img_close.setOnClickListener(this);
        rltv_servingSize.setOnClickListener(this);

        Utility.CheckKeyboardVisible(view);

    }

    private void SetSpnrUnit() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, units_arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_units.setAdapter(adapter);
        spnr_units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (position == 0)
                    unit_name = "gram";
                else
                    unit_name = "liter";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void SetFont() {

        Typeface typeface = Utility.GetFont(getActivity());
        tv_action_title.setTypeface(typeface, Typeface.BOLD);
        tv_servingSize.setTypeface(typeface);
        et_servingSize_value.setTypeface(typeface);
        et_item_name.setTypeface(typeface);
        tv_calories.setTypeface(typeface);
        tv_totalFat.setTypeface(typeface);
        tv_satFat.setTypeface(typeface);
        et_calories_value.setTypeface(typeface);
        et_totalFat_value.setTypeface(typeface);
        et_satFat_value.setTypeface(typeface);
        tv_cholest.setTypeface(typeface);
        tv_sodium.setTypeface(typeface);
        tv_potassium.setTypeface(typeface);
        tv_protein.setTypeface(typeface);
        et_cholest_value.setTypeface(typeface);
        et_sodium_value.setTypeface(typeface);
        et_potassium_value.setTypeface(typeface);
        et_protein_value.setTypeface(typeface);
        tv_carbo.setTypeface(typeface);
        tv_fiber.setTypeface(typeface);
        tv_sugars.setTypeface(typeface);
        tv_vitaminA.setTypeface(typeface);
        tv_vitaminC.setTypeface(typeface);
        et_carbo_value.setTypeface(typeface);
        et_fiber_value.setTypeface(typeface);
        et_sugars_value.setTypeface(typeface);
        et_vitaminA_value.setTypeface(typeface);
        et_vitaminC_value.setTypeface(typeface);
        tv_calcium.setTypeface(typeface);
        tv_iron.setTypeface(typeface);
        et_calcium_value.setTypeface(typeface);
        et_iron_value.setTypeface(typeface);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        v.setFocusableInTouchMode(true);
        return false;
    }


    @Override
    public void onClick(View v) {

        Utility.HideKeyboard(getActivity(), getActivity().getCurrentFocus());

        if (v == img_add_food) {
            CheckDataValidation();

        } else if (v == img_close) {
            getActivity().onBackPressed();

        } else if (v == rltv_servingSize) {

        }
    }

    private void CheckDataValidation() {
        GetCustomData();
        if (item_name.length() > 0 && unit_name.length() > 0 && unit_value > 0) {

            AddCustomFood();

        } else {


            if (et_servingSize_value.getText().length() == 0) {
                et_servingSize_value.setError("Enter Unit Value");
                et_servingSize_value.setFocusable(true);
                et_servingSize_value.requestFocus();
            }
            if (item_name.length() == 0) {
                et_item_name.setError("Enter Food Name");
                et_item_name.setHintTextColor(getResources().getColor(R.color.red));
                et_item_name.setFocusable(true);
                et_item_name.requestFocus();
            }
        }
    }

    private void GetCustomData() {

        item_name = et_item_name.getText().toString();

        calories = (et_calories_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_calories_value.getText().toString()) : 0;

        total_fat = (et_totalFat_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_totalFat_value.getText().toString()) : 0;

        sat_fat = (et_satFat_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_satFat_value.getText().toString()) : 0;

        cholest = (et_cholest_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_cholest_value.getText().toString()) : 0;

        sodium = (et_sodium_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_sodium_value.getText().toString()) : 0;

        potassium = (et_potassium_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_potassium_value.getText().toString()) : 0;

        protein = (et_protein_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_protein_value.getText().toString()) : 0;

        carbo = (et_carbo_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_carbo_value.getText().toString()) : 0;

        fiber = (et_fiber_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_fiber_value.getText().toString()) : 0;

        sugars = (et_sugars_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_sugars_value.getText().toString()) : 0;

        vitaminA = (et_vitaminA_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_vitaminA_value.getText().toString()) : 0;

        vitaminC = (et_vitaminC_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_vitaminC_value.getText().toString()) : 0;

        calcium = (et_calcium_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_calcium_value.getText().toString()) : 0;

        iron = (et_iron_value.getText().toString().length() > 0) ?
                Double.parseDouble(et_iron_value.getText().toString()) : 0;

        // unit value
        unit_value = (et_servingSize_value.getText().toString().length() > 0) ?
                Integer.parseInt(et_servingSize_value.getText().toString()) : 0;


    }

    private void AddCustomFood() {

        int count = Ingredients.GetMaxCounter(getActivity()) + 1;
        String macAddress = Utility.GetMacAddress(getActivity());
        String ID = count + macAddress;

        Log.e("custom_id", ID);

        Ingredients ingredients = new Ingredients();
        ingredients.setCustomID(ID);
        ingredients.setCounter_id(count);
        ingredients.setItem_name_en(item_name);
        ingredients.setEnergy(calories);
        ingredients.setFat(total_fat);
        ingredients.setSat_fat(sat_fat);
        ingredients.setCholest(cholest);
        ingredients.setSodium(sodium);
        ingredients.setPotassium(potassium);
        ingredients.setProtein(protein);
        ingredients.setCarbo_tot(carbo);
        ingredients.setCarbo_fiber(fiber);
        ingredients.setSugars(sugars);
        ingredients.setVit_a(vitaminA);
        ingredients.setAscorbic_acid(vitaminC);
        ingredients.setCalcium(calcium);
        ingredients.setIron(iron);
        ingredients.setUnit_name(unit_name);
        ingredients.setUnit_value(unit_value);

        ArrayList<Ingredients> lst_customMeal = new ArrayList<>();
        lst_customMeal.add(ingredients);
        Ingredients.InsertCustomIngredient(lst_customMeal, getActivity());

        StaticVarClass.lst_custom_ingredients.add(Ingredients.GetLastCustomIngredient(getActivity()));

        // refresh adapter
        ViewCustomFoodFragment.lst_myMeals = Ingredients.GetCustomIngredients(getActivity());
        ViewCustomFoodFragment.CustomAdapter = new ItemAdapter(getActivity(),
                R.layout.adapter_item, ViewCustomFoodFragment.lst_myMeals);
        ViewCustomFoodFragment.lst_custom_food.setAdapter
                (ViewCustomFoodFragment.CustomAdapter);

        FavAndMeal favAndMeal = new FavAndMeal(getActivity(), 4);
        getActivity().onBackPressed();


    }
}

