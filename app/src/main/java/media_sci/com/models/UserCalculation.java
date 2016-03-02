package media_sci.com.models;

import android.content.Context;

/**
 * Created by Bassem on 12/29/2015.
 */
public class UserCalculation {

    private double weight;
    private double height;
    private int gender;
    private int age;
    private UserData userData;
    private int exercise;
    private Context context;

    public UserCalculation(Context context) {

        userData = new UserData(context);
        weight = userData.GetWeight();
        height = userData.GetHeight();
        gender = userData.GetGender();
        age = userData.GetAge();
        exercise = userData.GetExercise();
        this.context = context;
    }

    public double TotalFat() {
        double fat = CaloriesCalc() * (3.25 / 100);
        return fat;
    }

    public double CaloriesCalc() {

        UserData userData = new UserData(context);

        double calories = userData.GetCalories();
/*
        if (StaticVarClass.UserCalories != -1) {
            calories = StaticVarClass.UserCalories;
        } else {
            if (exercise == 0) {
                // no exercise
                calories = BMRCalc() * 1.2;
            } else if (exercise == 1) {
                // 1-3  exercise days per week
                calories = BMRCalc() * 1.375;
            } else if (exercise == 2) {
                // 3-5  exercise days per week
                calories = BMRCalc() * 1.55;
            } else if (exercise == 3) {
                // 6-7  exercise days per week
                calories = BMRCalc() * 1.725;
            } else if (exercise == 4) {
                //  very hard exercise/sports
                // & physical job or 2x training
                calories = BMRCalc() * 1.9;
            }

        }*/
        return calories;
    }

    public double BMRCalc() {
        double BMR = 0;

        if (gender == 0) {
            BMR = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        } else if (gender == 1) {
            BMR = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }
        return BMR;
    }

    public double SatFatCalc() {
        double sat_fat = CaloriesCalc() * (.01);
        return sat_fat;
    }

    public double Cholest() {
        return 300 ;
    }

    public double SodiumCalc() {
        return 2400;
    }

    public double SugarCalc() {
        double sugar = (4.5 / 100) * CaloriesCalc();
        return sugar;
    }

    public double ProteinCalc() {
        double protein = (2.5 / 100) * CaloriesCalc();
        return protein;
    }


    public double VitaminC() {
        return 60;
    }

    public double IronCalc() {
        return 18;
    }

    public double Carbohydrate() {

        double carbo = (.15) * CaloriesCalc();
        return carbo;
    }

    public double CalciumCalc() {
        return 1000;
    }

    public double VitaminA() {

        return 5000;
    }

    public double Fiber() {

        double fiber = (1.25 / 100) * CaloriesCalc();
        return fiber;
    }

    public double Potassium() {
        return 3500;
    }
}
