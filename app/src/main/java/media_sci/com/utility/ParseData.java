package media_sci.com.utility;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import media_sci.com.models.Category;
import media_sci.com.models.Ingredients;
import media_sci.com.models.Restaurant;
import media_sci.com.models.Unit;

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

    public static void ParseUnit(JSONObject unitJson, Context context) {

        ArrayList<Unit> lst_unit = new ArrayList<>();
        ArrayList<Integer> lst_deleteUnit = new ArrayList<>();

        Unit unit;
        try {

            JSONObject updateObject;
            JSONArray updateList = unitJson.getJSONArray("update");
            JSONArray deleteList = unitJson.getJSONArray("delete");
            for (int i = 0; i < updateList.length(); i++) {

                updateObject = updateList.getJSONObject(i);
                unit = new Unit();
                unit.setId(updateObject.getInt("id"));
                unit.setUnit(updateObject.getString("unit"));

                lst_unit.add(unit);
            }
            Unit.InsertUnit(lst_unit, context);

            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteUnit.add(deleteList.getInt(i));
            }
            Unit.DeleteUnit(lst_deleteUnit, context);


        } catch (Exception e) {
            Log.e("unit_Parse_error", "" + e);
        }

    }

    public static void ParseIngredient(JSONObject ingredientJson, Context context) {

        ArrayList<Ingredients> lst_ingredient = new ArrayList<>();
        ArrayList<Integer> lst_deleteIngredient = new ArrayList<>();

        Ingredients ingredient;
        try {

            JSONObject updateObject;
            JSONArray updateList = ingredientJson.getJSONArray("update");
            JSONArray deleteList = ingredientJson.getJSONArray("delete");
            for (int i = 0; i < updateList.length(); i++) {

                updateObject = updateList.getJSONObject(i);
                ingredient = new Ingredients();
                ingredient.setId(updateObject.getInt("id"));
                ingredient.setItem_name_en(updateObject.getString("item_name_en"));
                ingredient.setItem_name_ar(updateObject.getString("item_name_ar"));

                ingredient.setCat_id(updateObject.getInt("cat_id"));
                ingredient.setRes_id(updateObject.getInt("restaurant_id"));
                ingredient.setType(updateObject.getInt("type"));

                ingredient.setWater(updateObject.getDouble("water"));
                ingredient.setEnergy(updateObject.getDouble("energy"));
                ingredient.setProtein(updateObject.getDouble("protein"));
                ingredient.setFat(updateObject.getDouble("fat"));

                ingredient.setSat_fat(updateObject.getDouble("sat_fat"));
                ingredient.setCholest(updateObject.getDouble("cholest"));
                ingredient.setCarbo_tot(updateObject.getDouble("carbo_tot"));
                ingredient.setSugars(updateObject.getDouble("sugars"));
                ingredient.setCarbo_fiber(updateObject.getDouble("carbo_fiber"));

                ingredient.setAsh(updateObject.getDouble("ash"));
                ingredient.setCalcium(updateObject.getDouble("calcium"));
                ingredient.setPhosphorus(updateObject.getDouble("phosphorus"));
                ingredient.setIron(updateObject.getDouble("iron"));

                ingredient.setSodium(updateObject.getDouble("sodium"));
                ingredient.setPotassium(updateObject.getDouble("potassium"));
                ingredient.setVit_a(updateObject.getDouble("vit_a"));
                ingredient.setThiamine_b1(updateObject.getDouble("thiamine_b1"));

                ingredient.setRiboflavin_b2(updateObject.getDouble("riboflavin_b2"));
                ingredient.setNiacin_b3(updateObject.getDouble("niacin-b3"));
                ingredient.setAscorbic_acid(updateObject.getDouble("ascorbic_acid"));
                ingredient.setNdb_no(updateObject.getDouble("ndb_no"));


                lst_ingredient.add(ingredient);
            }
            Ingredients.InsertIngredients(lst_ingredient, context);

            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteIngredient.add(deleteList.getInt(i));
            }
            Ingredients.DeleteIngredient(lst_deleteIngredient, context);


        } catch (Exception e) {
            Log.e("ingredient_Parse_error", "" + e);
        }

    }
}
