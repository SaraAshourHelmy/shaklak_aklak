package media_sci.com.utility;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import media_sci.com.models.Ingredients;
import media_sci.com.models.UserFavourite;
import media_sci.com.models.UserMeal;

public class StaticVarClass {

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
    public static int verify_status = 0; // set to 0
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
}
