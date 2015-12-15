package media_sci.com.utility;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import media_sci.com.models.Category;
import media_sci.com.models.Restaurant;

/**
 * Created by Bassem on 12/13/2015.
 */
public class ParseData {


    public static void ParseCategory(JSONObject categoryJson, Context context) {

        ArrayList<Category> lst_category = new ArrayList<>();
        ArrayList<Integer> lst_deleteCategory = new ArrayList<>();

        Category category;
        try {

            JSONObject updateObject;
            JSONArray updateList = categoryJson.getJSONArray("update");
            JSONArray deleteList = categoryJson.getJSONArray("delete");
            for (int i = 0; i < updateList.length(); i++) {

                updateObject = updateList.getJSONObject(i);
                category = new Category();
                category.setId(updateObject.getInt("id"));
                category.setName_en(updateObject.getString("cat_name_en"));
                category.setName_ar(updateObject.getString("cat_name_ar"));
                category.setImg_url(updateObject.getString("image"));
                lst_category.add(category);
            }
            Category.InsertCategory(lst_category, context);
            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteCategory.add(deleteList.getInt(i));
            }
            Category.DeleteCategory(lst_deleteCategory, context);


        } catch (Exception e) {
            Log.e("category_Parse_error", "" + e);
        }

    }

    public static void ParseRestaurant(JSONObject restaurantJson, Context context) {

        ArrayList<Restaurant> lst_restaurant = new ArrayList<>();
        ArrayList<Integer> lst_deleteRestaurant = new ArrayList<>();

        Restaurant restaurant;
        try {

            JSONObject updateObject;
            JSONArray updateList = restaurantJson.getJSONArray("update");
            JSONArray deleteList = restaurantJson.getJSONArray("delete");
            for (int i = 0; i < updateList.length(); i++) {

                updateObject = updateList.getJSONObject(i);
                restaurant = new Restaurant();
                restaurant.setId(updateObject.getInt("id"));
                restaurant.setName_en(updateObject.getString("restaurant_name_en"));
                restaurant.setName_ar(updateObject.getString("restaurant_name_ar"));
                restaurant.setImg_url(updateObject.getString("image"));
                lst_restaurant.add(restaurant);
            }
            Restaurant.InsertRestaurant(lst_restaurant, context);
            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteRestaurant.add(deleteList.getInt(i));
            }
            Restaurant.DeleteRestaurant(lst_deleteRestaurant, context);


        } catch (Exception e) {
            Log.e("category_Parse_error", "" + e);
        }

    }
}
