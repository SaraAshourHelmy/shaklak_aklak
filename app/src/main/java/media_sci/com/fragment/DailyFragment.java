package media_sci.com.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.limc.androidcharts.diagram.PieChart;
import cn.limc.androidcharts.series.TitleValueColorEntity;
import media_sci.com.adapter.MealAdapter;
import media_sci.com.models.UserCalculation;
import media_sci.com.models.UserMeal;
import media_sci.com.shaklak_aklak.ChangeCaloriesActivity;
import media_sci.com.shaklak_aklak.R;
import media_sci.com.utility.GetDataLogin;
import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

public class DailyFragment extends Fragment implements View.OnClickListener {


    static private Calendar selected_date, updated_date;
    static private int selected_day, selected_month, selected_year;
    // static private WeekCalendar weekCalendar;
    View actionbar;

    private View view;
    private LinearLayout lnr_calories, lnr_nutrients, lnr_pieChart;
    private Button btn_calories, btn_nutrients, btn_pieChart;
    private TextView tv_selected_date;
    private ImageView img_edit;
    private SimpleDateFormat txtDate_format, date_format;
    private PieChart pieChart;

    private ImageView img_calories, img_total_fat, img_cholest;
    private ImageView img_sodium, img_potassium, img_carbo;
    private ImageView img_fiber, img_sugars, img_protein;
    private ImageView img_vitaminA, img_vitaminC, img_calcium, img_iron;

    private TextView tv_calories, tv_total_fat, tv_cholest;
    private TextView tv_sodium, tv_potassium, tv_carbo, tv_fiber;
    private TextView tv_sugars, tv_protein, tv_vitaminA, tv_vitaminC;
    private TextView tv_calcium, tv_iron;

    private TextView tv_calories_need, tv_total_fat_need, tv_cholest_need;
    private TextView tv_sodium_need, tv_potassium_need, tv_carbo_need, tv_fiber_need;
    private TextView tv_sugars_need, tv_protein_need, tv_vitaminA_need, tv_vitaminC_need;
    private TextView tv_calcium_need, tv_iron_need;

    private TextView tv_calories_taken, tv_total_fat_taken, tv_cholest_taken;
    private TextView tv_sodium_taken, tv_potassium_taken, tv_carbo_taken, tv_fiber_taken;
    private TextView tv_sugars_taken, tv_protein_taken, tv_vitaminA_taken, tv_vitaminC_taken;
    private TextView tv_calcium_taken, tv_iron_taken;

    private ListView lstv_userMeals;
    private MaterialCalendarView cal_meals;
    private Button btn_calendar_ok;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        try {
            view = inflater.inflate(R.layout.fragment_daily, container, false);
            SetupTools();
        } catch (InflateException e) {
            Log.e("itemDetails_error", "" + e);

        }
        // View v = inflater.inflate(R.layout.fragment_daily, container, false);
        // SetupTools(v);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        NutrientOperation();
    }

    private void NutrientOperation() {

        ResetData();

        UserMeal.GetUserConsuming(getActivity());

        String gram = getString(R.string.gram);
        String milli_gram = getString(R.string.milli_gram);
        String kcal = getString(R.string.kcal);

        UserCalculation userCalculation = new UserCalculation(getActivity());

        // set need

        tv_calories_need.setText(Utility.GetDecimalFormat(userCalculation.CaloriesCalc()));

        tv_total_fat_need.setText(Utility.GetDecimalFormat(userCalculation.TotalFat())
                + gram);

        tv_cholest_need.setText(String.valueOf(userCalculation.Cholest())
                + milli_gram);

        tv_sodium_need.setText(userCalculation.SodiumCalc() + milli_gram);
        tv_potassium_need.setText(userCalculation.Potassium() + milli_gram);
        tv_carbo_need.setText(userCalculation.Carbohydrate() + gram);
        tv_fiber_need.setText(Utility.GetDecimalFormat(userCalculation.Fiber())
                + gram);
        tv_sugars_need.setText(Utility.GetDecimalFormat(userCalculation.SugarCalc())
                + gram);
        tv_protein_need.setText(Utility.GetDecimalFormat(userCalculation.ProteinCalc())
                + gram);
        tv_vitaminA_need.setText(userCalculation.VitaminA() + "IU");
        tv_vitaminC_need.setText(userCalculation.VitaminC() + milli_gram);
        tv_calcium_need.setText(userCalculation.CalciumCalc() + milli_gram);
        tv_iron_need.setText(userCalculation.IronCalc() + milli_gram);

        // set taken
        tv_calories_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Calories)
                + " " + kcal);
        tv_total_fat_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Total_Fat)
                + " " + gram);
        tv_cholest_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Cholest)
                + " " + milli_gram);
        tv_sodium_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Sodium)
                + " " + milli_gram);
        tv_potassium_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Potassium)
                + " " + milli_gram);
        tv_carbo_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Carbo)
                + " " + gram);
        tv_fiber_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Fiber)
                + " " + gram);
        tv_sugars_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Sugars)
                + " " + gram);
        tv_protein_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Protein)
                + " " + gram);
        tv_vitaminA_taken.setText(Utility.GetDecimalFormat(StaticVarClass.VitaminA)
                + " " + milli_gram);
        tv_vitaminC_taken.setText(Utility.GetDecimalFormat(StaticVarClass.VitaminC)
                + " " + milli_gram);
        tv_calcium_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Calcium)
                + " " + gram);
        tv_iron_taken.setText(Utility.GetDecimalFormat(StaticVarClass.Iron)
                + " " + gram);

        // set Image

        // Calories
        if (StaticVarClass.Calories == userCalculation.CaloriesCalc()) {

            // set wink image green face
            img_calories.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Calories < userCalculation.CaloriesCalc()) {

            // set gray face
            img_calories.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_calories.setImageResource(R.drawable.nutriat_more);
        }

        // Total Fat
        if (StaticVarClass.Total_Fat == userCalculation.TotalFat()) {

            // set wink image green face
            img_total_fat.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Total_Fat < userCalculation.TotalFat()) {

            // set gray face
            img_total_fat.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_total_fat.setImageResource(R.drawable.nutriat_more);
        }

        // Cholest
        if (StaticVarClass.Cholest == userCalculation.Cholest()) {

            // set wink image green face
            img_cholest.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Cholest < userCalculation.Cholest()) {

            // set gray face
            img_cholest.setImageResource(R.drawable.nutriat_less);

        } else {
            // set pink face
            img_cholest.setImageResource(R.drawable.nutriat_more);
        }

        // Sodium
        if (StaticVarClass.Sodium == userCalculation.SodiumCalc()) {

            // set wink image green face
            img_sodium.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Sodium < userCalculation.SodiumCalc()) {

            // set gray face
            img_sodium.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_sodium.setImageResource(R.drawable.nutriat_more);
        }

        // Potassium
        if (StaticVarClass.Potassium == userCalculation.Potassium()) {

            // set wink image green face
            img_potassium.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Potassium < userCalculation.Potassium()) {

            // set gray face
            img_potassium.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_potassium.setImageResource(R.drawable.nutriat_more);
        }

        // Carbo
        if (StaticVarClass.Carbo == userCalculation.Carbohydrate()) {

            // set wink image green face
            img_carbo.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Carbo < userCalculation.Carbohydrate()) {

            // set gray face
            img_carbo.setImageResource(R.drawable.nutriat_less);

        } else {
            // set pink face
            img_carbo.setImageResource(R.drawable.nutriat_more);
        }

        // Fiber
        if (StaticVarClass.Fiber <= userCalculation.Fiber()) {

            // set wink image green face
            img_fiber.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Fiber < userCalculation.Fiber()) {
            // set gray face
            img_fiber.setImageResource(R.drawable.nutriat_less);

        } else {
            // set pink face
            img_fiber.setImageResource(R.drawable.nutriat_more);
        }

        // Sugars
        if (StaticVarClass.Sugars == userCalculation.SugarCalc()) {
            // set wink image green face
            img_sugars.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Sugars < userCalculation.SugarCalc()) {
            // set gray face
            img_sugars.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_sugars.setImageResource(R.drawable.nutriat_more);
        }

        // Protein
        if (StaticVarClass.Protein == userCalculation.ProteinCalc()) {
            // set wink image green face
            img_protein.setImageResource(R.drawable.nutriat_equal);
        } else if (StaticVarClass.Protein < userCalculation.ProteinCalc()) {
            // set gray face
            img_protein.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_protein.setImageResource(R.drawable.nutriat_more);
        }

        // Vitamin A
        if (StaticVarClass.VitaminA <= userCalculation.VitaminA()) {
            // set wink image green face
            img_vitaminA.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.VitaminA < userCalculation.VitaminA()) {
            // set gray face
            img_vitaminA.setImageResource(R.drawable.nutriat_less);

        } else {
            // set pink face
            img_vitaminA.setImageResource(R.drawable.nutriat_more);
        }

        // Vitamin C
        if (StaticVarClass.VitaminC <= userCalculation.VitaminC()) {

            // set wink image green face
            img_vitaminC.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.VitaminC < userCalculation.VitaminC()) {
            // set gray face
            img_vitaminC.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_vitaminC.setImageResource(R.drawable.nutriat_more);
        }

        // Calcium
        if (StaticVarClass.Calcium == userCalculation.CalciumCalc()) {
            // set wink image green face
            img_calcium.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Calcium < userCalculation.CalciumCalc()) {
            // set gray face
            img_calcium.setImageResource(R.drawable.nutriat_less);
        } else {
            // set pink face
            img_calcium.setImageResource(R.drawable.nutriat_more);
        }

        // Iron
        if (StaticVarClass.Iron == userCalculation.IronCalc()) {

            // set wink image green face
            img_iron.setImageResource(R.drawable.nutriat_equal);

        } else if (StaticVarClass.Iron < userCalculation.IronCalc()) {

            // set gray face
            img_iron.setImageResource(R.drawable.nutriat_less);

        } else {
            // set pink face
            img_iron.setImageResource(R.drawable.nutriat_more);
        }

    }

    private void ResetData() {

        //String date = Utility.GetStringDate();
        //Log.e("date today", date);
        // String old_date = UserMeal.GetDate(this);
        // if (!old_date.equals(date)) {

        // UserMeal.SetDate(this, date);
        StaticVarClass.Calories = 0;
        StaticVarClass.Total_Fat = 0;
        StaticVarClass.Cholest = 0;
        StaticVarClass.Sodium = 0;
        StaticVarClass.Potassium = 0;
        StaticVarClass.Carbo = 0;
        StaticVarClass.Fiber = 0;
        StaticVarClass.Sugars = 0;
        StaticVarClass.Protein = 0;
        StaticVarClass.VitaminA = 0;
        StaticVarClass.VitaminC = 0;
        StaticVarClass.Calcium = 0;
        StaticVarClass.Iron = 0;

        //   } else {
        Log.e("date", "we are same day");
        //  }
    }

    private void SetupTools() {

        SetupToolsNutrient();
        actionbar = (View) view.findViewById(R.id.actionbar_daily);
        Utility.ActionBarSetting(actionbar, getString(R.string.daily), 3, "", getActivity());
        //if (weekCalendar == null)
        //  weekCalendar = (WeekCalendar) view.findViewById(R.id.weekCalendar);
        lnr_calories = (LinearLayout) view.findViewById(R.id.lnr_daily_calories);
        lnr_nutrients = (LinearLayout) view.findViewById(R.id.lnr_daily_nutrients);
        lnr_pieChart = (LinearLayout) view.findViewById(R.id.lnr_daily_pieChart);
        btn_calories = (Button) view.findViewById(R.id.btn_daily_calories);
        btn_nutrients = (Button) view.findViewById(R.id.btn_daily_nutrients);
        btn_pieChart = (Button) view.findViewById(R.id.btn_daily_pieChart);
        img_edit = (ImageView) actionbar.findViewById(R.id.img_action_icon);
        pieChart = (PieChart) view.findViewById(cn.limc.androidcharts.R.id.piechart);
        tv_selected_date = (TextView) view.findViewById(R.id.tv_selected_date);
        lstv_userMeals = (ListView) view.findViewById(R.id.lst_userMeals);


        btn_calories.setOnClickListener(this);
        btn_nutrients.setOnClickListener(this);
        btn_pieChart.setOnClickListener(this);
        img_edit.setOnClickListener(this);

        SetFont();

        SetupCalendar();
        SetupPieChart();

        SetVisibleView(lnr_calories, lnr_nutrients, lnr_pieChart, btn_calories, btn_nutrients, btn_pieChart);
        // weekCalendar.invalidate();
        // weekCalendar.requestFocus();

    }

    private void SetupToolsNutrient() {

        // img
        img_calories = (ImageView) view.findViewById(R.id.img_daily_calories);
        img_total_fat = (ImageView) view.findViewById(R.id.img_daily_totalFat);
        img_cholest = (ImageView) view.findViewById(R.id.img_daily_cholest);
        img_sodium = (ImageView) view.findViewById(R.id.img_daily_sodium);
        img_potassium = (ImageView) view.findViewById(R.id.img_daily_potassium);
        img_carbo = (ImageView) view.findViewById(R.id.img_daily_carbo);
        img_fiber = (ImageView) view.findViewById(R.id.img_daily_fiber);
        img_sugars = (ImageView) view.findViewById(R.id.img_daily_sugars);
        img_protein = (ImageView) view.findViewById(R.id.img_daily_protein);
        img_vitaminA = (ImageView) view.findViewById(R.id.img_daily_vitaminA);
        img_vitaminC = (ImageView) view.findViewById(R.id.img_daily_vitaminC);
        img_calcium = (ImageView) view.findViewById(R.id.img_daily_calcium);
        img_iron = (ImageView) view.findViewById(R.id.img_daily_iron);

        // textView
        tv_calories = (TextView) view.findViewById(R.id.tv_daily_calories);
        tv_total_fat = (TextView) view.findViewById(R.id.tv_daily_totalFat);
        tv_cholest = (TextView) view.findViewById(R.id.tv_daily_cholest);
        tv_sodium = (TextView) view.findViewById(R.id.tv_daily_sodium);
        tv_potassium = (TextView) view.findViewById(R.id.tv_daily_potassium);
        tv_carbo = (TextView) view.findViewById(R.id.tv_daily_carbo);
        tv_fiber = (TextView) view.findViewById(R.id.tv_daily_fiber);
        tv_sugars = (TextView) view.findViewById(R.id.tv_daily_sugars);
        tv_protein = (TextView) view.findViewById(R.id.tv_daily_protein);
        tv_vitaminA = (TextView) view.findViewById(R.id.tv_daily_vitaminA);
        tv_vitaminC = (TextView) view.findViewById(R.id.tv_daily_vitaminC);
        tv_calcium = (TextView) view.findViewById(R.id.tv_daily_calcium);
        tv_iron = (TextView) view.findViewById(R.id.tv_daily_iron);

        // text need
        tv_calories_need = (TextView) view.findViewById(R.id.tv_daily_caloriesNeeded);
        tv_total_fat_need = (TextView) view.findViewById(R.id.tv_daily_totalFatNeeded);
        tv_cholest_need = (TextView) view.findViewById(R.id.tv_daily_cholestNeeded);
        tv_sodium_need = (TextView) view.findViewById(R.id.tv_daily_sodiumNeeded);
        tv_potassium_need = (TextView) view.findViewById(R.id.tv_daily_potassiumNeeded);
        tv_carbo_need = (TextView) view.findViewById(R.id.tv_daily_carboNeeded);
        tv_fiber_need = (TextView) view.findViewById(R.id.tv_daily_fiberNeeded);
        tv_sugars_need = (TextView) view.findViewById(R.id.tv_daily_sugarsNeeded);
        tv_protein_need = (TextView) view.findViewById(R.id.tv_daily_proteinNeeded);
        tv_vitaminA_need = (TextView) view.findViewById(R.id.tv_daily_vitaminA_Needed);
        tv_vitaminC_need = (TextView) view.findViewById(R.id.tv_daily_vitaminC_Needed);
        tv_calcium_need = (TextView) view.findViewById(R.id.tv_daily_calciumNeeded);
        tv_iron_need = (TextView) view.findViewById(R.id.tv_daily_ironNeeded);

        // text taken
        tv_calories_taken = (TextView) view.findViewById(R.id.tv_daily_caloriesTaken);
        tv_total_fat_taken = (TextView) view.findViewById(R.id.tv_daily_totalFatTaken);
        tv_cholest_taken = (TextView) view.findViewById(R.id.tv_daily_cholestTaken);
        tv_sodium_taken = (TextView) view.findViewById(R.id.tv_daily_sodiumTaken);
        tv_potassium_taken = (TextView) view.findViewById(R.id.tv_daily_potassiumTaken);
        tv_carbo_taken = (TextView) view.findViewById(R.id.tv_daily_carboTaken);
        tv_fiber_taken = (TextView) view.findViewById(R.id.tv_daily_fiberTaken);
        tv_sugars_taken = (TextView) view.findViewById(R.id.tv_daily_sugarsTaken);
        tv_protein_taken = (TextView) view.findViewById(R.id.tv_daily_proteinTaken);
        tv_vitaminA_taken = (TextView) view.findViewById(R.id.tv_daily_vitaminA_Taken);
        tv_vitaminC_taken = (TextView) view.findViewById(R.id.tv_daily_vitaminC_Taken);
        tv_calcium_taken = (TextView) view.findViewById(R.id.tv_daily_calciumTaken);
        tv_iron_taken = (TextView) view.findViewById(R.id.tv_daily_ironTaken);

        SetFontNutrient();
        NutrientOperation();
    }

    private void SetFont() {
        Typeface typeface = Utility.GetFont(getActivity());
        btn_calories.setTypeface(typeface);
        btn_nutrients.setTypeface(typeface);
        btn_pieChart.setTypeface(typeface);
        tv_selected_date.setTypeface(typeface, Typeface.BOLD);

    }

    private void SetupCalendar() {

        selected_date = Calendar.getInstance();
        updated_date = Calendar.getInstance();
        txtDate_format = new SimpleDateFormat("EEEE MMMM dd, yyyy");
        date_format = new SimpleDateFormat("yyyy-MM-dd");

        // set current date
        // Note calendar start month index from 0 so month number will be -1
        Calendar c = Calendar.getInstance();
        selected_year = c.get(Calendar.YEAR);
        selected_month = c.get(Calendar.MONTH);
        selected_day = c.get(Calendar.DAY_OF_MONTH);

        // set current date in Textview
        String folderName = txtDate_format.format(c.getTime());
        tv_selected_date.setText(folderName);
        tv_selected_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShowCalendar();
                ShowCalendarDialog();
            }
        });

        // when click on swipe calendar
        /*
        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {

                selected_date.set(Calendar.YEAR, dateTime.getYear());
                selected_date.set(Calendar.MONTH, dateTime.getMonthOfYear());
                selected_date.set(Calendar.DAY_OF_MONTH, dateTime.getDayOfMonth());

                selected_year = dateTime.getYear();
                selected_month = dateTime.getMonthOfYear() - 1;
                selected_day = dateTime.getDayOfMonth();


                try {
                    Date folderName1 = date_format.parse(dateTime.toString());
                    // get from db
                    SetMealData(StaticVarClass.dash_format.format(folderName1));
                    Log.e("selected_date", selected_date + "");


                } catch (Exception e) {
                    Log.e("date error", "" + e);
                }
            }

        });*/

        String date = StaticVarClass.dash_format.format(updated_date.getTime());
        tv_selected_date.setText(txtDate_format.format(updated_date.getTime()));
        SetMealData(date);
    }

    private void SetupPieChart() {

        final List<TitleValueColorEntity> data3 = new ArrayList<TitleValueColorEntity>();
        data3.add(new TitleValueColorEntity("Protein", 10,
                getResources().getColor(R.color.colorPrimaryDark)));
        data3.add(new TitleValueColorEntity("Total Carb", 3,
                getResources().getColor(R.color.pink)));
        data3.add(new TitleValueColorEntity("Total Fat", 3,
                getResources().getColor(R.color.dialog_color)));
        pieChart.setBorderWidth(0);
        pieChart.setData(data3);

    }

    private void SetFontNutrient() {
        Typeface typeface = Utility.GetFont(getActivity());
        tv_calories.setTypeface(typeface);
        tv_calories_need.setTypeface(typeface);
        tv_calories_taken.setTypeface(typeface, Typeface.BOLD);

        tv_total_fat.setTypeface(typeface);
        tv_total_fat_need.setTypeface(typeface);
        tv_total_fat_taken.setTypeface(typeface, Typeface.BOLD);

        tv_cholest.setTypeface(typeface);
        tv_cholest_need.setTypeface(typeface);
        tv_cholest_taken.setTypeface(typeface, Typeface.BOLD);

        tv_sodium.setTypeface(typeface);
        tv_sodium_need.setTypeface(typeface);
        tv_sodium_taken.setTypeface(typeface, Typeface.BOLD);

        tv_potassium.setTypeface(typeface);
        tv_potassium_need.setTypeface(typeface);
        tv_potassium_taken.setTypeface(typeface);

        tv_carbo.setTypeface(typeface);
        tv_carbo_need.setTypeface(typeface);
        tv_carbo_taken.setTypeface(typeface, Typeface.BOLD);

        tv_fiber.setTypeface(typeface);
        tv_fiber_need.setTypeface(typeface);
        tv_fiber_taken.setTypeface(typeface, Typeface.BOLD);

        tv_sugars.setTypeface(typeface);
        tv_sugars_need.setTypeface(typeface);
        tv_sugars_taken.setTypeface(typeface, Typeface.BOLD);

        tv_protein.setTypeface(typeface);
        tv_protein_need.setTypeface(typeface);
        tv_protein_taken.setTypeface(typeface, Typeface.BOLD);

        tv_vitaminA.setTypeface(typeface);
        tv_vitaminA_need.setTypeface(typeface);
        tv_vitaminA_taken.setTypeface(typeface, Typeface.BOLD);

        tv_vitaminC.setTypeface(typeface);
        tv_vitaminC_need.setTypeface(typeface);
        tv_vitaminC_taken.setTypeface(typeface, Typeface.BOLD);

        tv_calcium.setTypeface(typeface);
        tv_calcium_need.setTypeface(typeface);
        tv_calcium_taken.setTypeface(typeface, Typeface.BOLD);

        tv_iron.setTypeface(typeface);
        tv_iron_need.setTypeface(typeface);
        tv_iron_taken.setTypeface(typeface, Typeface.BOLD);

    }

    private void ShowCalendarDialog() {
        final Dialog calDialog =
                new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        calDialog.setContentView(R.layout.dialog_calendar);

        cal_meals = (MaterialCalendarView) calDialog.findViewById(R.id.cal_meals);
        cal_meals.setDateSelected(updated_date, true);
        cal_meals.setCurrentDate(updated_date);
        cal_meals.setBackgroundColor(getResources().getColor(R.color.white));

        btn_calendar_ok = (Button) calDialog.findViewById(R.id.btn_calendar_ok);

        btn_calendar_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updated_date = cal_meals.getSelectedDate().getCalendar();
                String date = StaticVarClass.dash_format.format(updated_date.getTime());
                tv_selected_date.setText(txtDate_format.format(updated_date.getTime()));
                Log.e("calendar_select", date);
                calDialog.dismiss();
                SetMealData(date);
                // cal_meals.getSelectedDate();
            }
        });


        calDialog.show();
    }

    private void ShowCalendar() {


        // save last date in selected date
        selected_date.set(selected_year, selected_month, selected_day);

        DatePickerDialog dateDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        updated_date.set(year, monthOfYear, dayOfMonth);

                        selected_year = year;
                        selected_month = monthOfYear;
                        selected_day = dayOfMonth;

                        Log.e("selected_month", monthOfYear + "");
                        CalculateDate();
                    }
                }, selected_year, selected_month, selected_day);

        dateDialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable
                        (Color.TRANSPARENT));
        dateDialog.getDatePicker().setCalendarViewShown(true);
        dateDialog.getDatePicker().setSpinnersShown(false);
        dateDialog.show();
    }

    private void CalculateDate() {

        long diff = 0;
        boolean flag_after = false;

        //Log.e("update_date", updated_date.getTime().toString());
        //Log.e("selected_date", selected_date.getTime().toString());


        if (updated_date.getTime().compareTo(selected_date.getTime()) == 1) {
            flag_after = true;
            diff = updated_date.getTimeInMillis() -
                    selected_date.getTimeInMillis(); //result in millis
            Log.e("diff_next", diff + "");
        } else {
            diff = selected_date.getTimeInMillis() -
                    updated_date.getTimeInMillis(); //result in millis
            Log.e("diff_prev", diff + "");
        }

        long days = diff / (24 * 60 * 60 * 1000);

        Log.e("days : ", days + "");


        for (int i = 0; i < days; i++) {

            // if (flag_after)
            // weekCalendar.moveToNext();
            // else
            // weekCalendar.moveToPrevious();
        }


        String txtDate = txtDate_format.format(updated_date.getTime());
        tv_selected_date.setText(txtDate);

        // update from db
        String date = StaticVarClass.dash_format.format(updated_date.getTime());
        SetMealData(date);

    }

    private void SetMealData(String date) {

        //String date = StaticVarClass.dash_format.format(updated_date.getTime());
        Log.e("date", date);
        ArrayList<UserMeal> userMeals = UserMeal.GetUSerMeals(getActivity(),
                date);
        Log.e("userMeals_size", userMeals.size() + "");
        if (userMeals.size() == 0) {

            // call webservice with certain date
            GetDataLogin getMyMeal = new GetDataLogin(getActivity(), date, 1, 2);
            userMeals = UserMeal.GetUSerMeals(getActivity(),
                    date);

        }

        SortMeals(userMeals);
        MealAdapter mealAdapter = new MealAdapter(getActivity(),
                R.layout.adapter_meal, userMeals);
        lstv_userMeals.setAdapter(mealAdapter);
    }

    private void SortMeals(ArrayList<UserMeal> lst_userMeals) {


        Collections.sort(lst_userMeals, new Comparator<UserMeal>() {


            @Override
            public int compare(UserMeal meal1, UserMeal meal2) {

                return meal1.getItemName().compareToIgnoreCase(
                        meal2.getItemName()
                );

            }
        });

    }

    private void SetVisibleView(LinearLayout lnr_first, LinearLayout lnr_second
            , LinearLayout lnr_third, Button btn_first, Button btn_second
            , Button btn_third) {

        lnr_first.setVisibility(View.VISIBLE);
        lnr_second.setVisibility(View.GONE);
        lnr_third.setVisibility(View.GONE);

        btn_first.setBackground(getResources().getDrawable
                (R.drawable.rectangle_border_pressed));
        btn_second.setBackground(getResources().getDrawable(R.drawable.rectangle_border));
        btn_third.setBackground(getResources().getDrawable(R.drawable.rectangle_border));


    }


    @Override
    public void onClick(View v) {

        if (v == btn_calories) {

            SetVisibleView(lnr_calories, lnr_nutrients, lnr_pieChart,
                    btn_calories, btn_nutrients, btn_pieChart);
        } else if (v == btn_nutrients) {

            SetVisibleView(lnr_nutrients, lnr_calories, lnr_pieChart,
                    btn_nutrients, btn_calories, btn_pieChart);
        } else if (v == btn_pieChart) {

            SetVisibleView(lnr_pieChart, lnr_nutrients, lnr_calories,
                    btn_pieChart, btn_nutrients, btn_calories);
        } else if (v == img_edit) {

            Intent intent = new Intent(getActivity(), ChangeCaloriesActivity.class);
            startActivity(intent);
        }

    }


}
