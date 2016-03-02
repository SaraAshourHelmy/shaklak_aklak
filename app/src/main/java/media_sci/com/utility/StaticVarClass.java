package media_sci.com.utility;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import media_sci.com.models.Ingredients;
import media_sci.com.models.UserFavourite;
import media_sci.com.models.UserMeal;
import media_sci.com.shaklak_aklak.R;

public class StaticVarClass {

    public static int English = 0;
    public static int Arabic = 1;
    public static int TextView_Type = 0;
    public static int EditText_Type = 1;
    public static int Black_Color = 0;
    public static int Pink_Color = 1;
    public static int Device_language = 0;
    public static int Setting_language = 1;
    public static int Food = 0;
    public static int Liquid = 1;

    // old ip : http://192.168.1.227/shaklk/public

    public static String Server_API = "http://shaklakaklak.com/api";

    public static String Register_URL = Server_API + "/users/register";
    public static String loginURL = Server_API + "/users/login";
    public static String FavURL = Server_API + "/categories/setfavorites";
    public static String MealURL = Server_API + "/categories/setmeal";
    public static String CategoryURL = Server_API + "/categories/all";
    public static String RestaurantURL = Server_API + "/categories/restaurants";
    public static String UnitURL = Server_API + "/categories/units";
    public static String IngredientURL = Server_API + "/categories/ingredients";
    public static String IngredientUnitURL = Server_API + "/categories/ingredientunits";
    public static String SetCustomMeal = Server_API + "/categories/setcustommeal";
    public static String GetCustomMeal = Server_API + "/categories/mycustommeals";
    public static String GetUserFavourite = Server_API + "/categories/favorites";
    public static String LogoutURL = Server_API + "/users/logout";
    public static String GetMeals = Server_API + "/categories/meals";
    public static String VerifyUser = Server_API + "/users/verify";
    public static String ReVerifyUser = Server_API + "/users/reverify";
    public static String ForgetPassword_URL = Server_API + "/users/forgetpassword";
    public static String ChangePassword_URL = Server_API + "/users/changepassword";
    public static String ChangeCalories_URL = Server_API + "/users/updatecalories";
    public static String GetCalories_URL = Server_API + "/users/mycalories";


    public static ArrayList<Ingredients> lst_custom_ingredients =
            new ArrayList<>();

    public static ArrayList<UserFavourite> lst_favourites_user =
            new ArrayList<>();

    public static ArrayList<UserMeal> lst_meals =
            new ArrayList<>();

    public static ArrayList<UserFavourite> lst_removed_favourite =
            new ArrayList<>();
    public static SimpleDateFormat dash_format = new SimpleDateFormat("yyyy-MM-dd");
    public static double UserCalories = -1;
    // public static int verify_status = 0; // set to 0
    public static String verification_code = "";
    public static double Calories = 0, Total_Fat = 0, Cholest = 0, Sodium = 0;
    public static double Potassium = 0, Carbo = 0, Fiber = 0, Sugars = 0;
    public static double Protein = 0, VitaminA = 0, VitaminC = 0, Calcium = 0, Iron = 0;
    public static double percent = 100;
    public static String percent_sign = "%";
    public static String gram = "g";
    public static String milli_gram = "mg";
    public static String IU = "IU";
    public static String kcal = "kcal";
    public static String Liter = "L";

    public static int Delete_Mode = 1;
    public static int No_Delete_Mode = 0;

    public static int NotCustom = 0;
    public static int Custom = 1;

    public static String Female = "1";
    public static String Male = "0";

    public static double[] float_Serving_no = {0, 0.125, 0.142, 0.166, 0.2, 0.25, 0.285, 0.333
            , 0.375, 0.4, 0.428, 0.5, 0.571, 0.6, 0.625, 0.666, 0.714, 0.75, 0.8, 0.833
            , 0.875};

    public static void CheckFavouriteID(String meal_id) {

        //  if (type == 1) {
        for (int i = 0; i < lst_favourites_user.size(); i++) {
            if (lst_favourites_user.get(i).getMeal_id().equals(meal_id)) {
                lst_favourites_user.remove(i);
                Log.e("favourite", "item_delete");
                break;
            }
        }

        // } else if (type == 2) {

        for (int i = 0; i < lst_removed_favourite.size(); i++) {
            if (lst_removed_favourite.get(i).getMeal_id().equals(meal_id)) {
                lst_removed_favourite.remove(i);
                Log.e("remove_favourite", "item_delete");
                break;
            }
        }
        // }
    }

    public static String[] GetServingNoPicker() {
        String picker_serving_no[]
                = {"--",
                Utility.GetDecimalFormat(1) + "/" + Utility.GetDecimalFormat(8),
                Utility.GetDecimalFormat(1) + "/" + Utility.GetDecimalFormat(7),
                Utility.GetDecimalFormat(1) + "/" + Utility.GetDecimalFormat(6),
                Utility.GetDecimalFormat(1) + "/" + Utility.GetDecimalFormat(5),
                Utility.GetDecimalFormat(1) + "/" + Utility.GetDecimalFormat(4),
                Utility.GetDecimalFormat(2) + "/" + Utility.GetDecimalFormat(7),
                Utility.GetDecimalFormat(1) + "/" + Utility.GetDecimalFormat(3),
                Utility.GetDecimalFormat(3) + "/" + Utility.GetDecimalFormat(8),
                Utility.GetDecimalFormat(2) + "/" + Utility.GetDecimalFormat(5),
                Utility.GetDecimalFormat(3) + "/" + Utility.GetDecimalFormat(7),
                Utility.GetDecimalFormat(1) + "/" + Utility.GetDecimalFormat(2),
                Utility.GetDecimalFormat(4) + "/" + Utility.GetDecimalFormat(7),
                Utility.GetDecimalFormat(3) + "/" + Utility.GetDecimalFormat(5),
                Utility.GetDecimalFormat(5) + "/" + Utility.GetDecimalFormat(8),
                Utility.GetDecimalFormat(2) + "/" + Utility.GetDecimalFormat(3),
                Utility.GetDecimalFormat(5) + "/" + Utility.GetDecimalFormat(7),
                Utility.GetDecimalFormat(3) + "/" + Utility.GetDecimalFormat(4),
                Utility.GetDecimalFormat(4) + "/" + Utility.GetDecimalFormat(5),
                Utility.GetDecimalFormat(5) + "/" + Utility.GetDecimalFormat(6),
                Utility.GetDecimalFormat(7) + "/" + Utility.GetDecimalFormat(8)};


        return picker_serving_no;
    }

    public static String[] GetExerciseList(Context context) {
        String[] exercise = {context.getString(R.string.not_found),
                Utility.GetDecimalFormat(1) + "-" + Utility.GetDecimalFormat(3),
                Utility.GetDecimalFormat(3) + "-" + Utility.GetDecimalFormat(5),
                Utility.GetDecimalFormat(6) + "-" + Utility.GetDecimalFormat(7),
                context.getString(R.string.heavy_ex)
        };
        return exercise;
    }
}
