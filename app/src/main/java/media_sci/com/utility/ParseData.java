package media_sci.com.utility;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import media_sci.com.models.Category;
import media_sci.com.models.IngredientUnit;
import media_sci.com.models.Ingredients;
import media_sci.com.models.Restaurant;
import media_sci.com.models.Unit;
import media_sci.com.models.UserMeal;

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
                category.setSection_type(updateObject.getInt("section_type"));
                lst_category.add(category);
            }

            if (lst_category.size() > 0)
                Category.InsertCategory(lst_category, context);
            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteCategory.add(deleteList.getInt(i));
            }
            if (lst_deleteCategory.size() > 0)
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

            if (lst_restaurant.size() > 0)
                Restaurant.InsertRestaurant(lst_restaurant, context);
            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteRestaurant.add(deleteList.getInt(i));
            }

            if (lst_deleteRestaurant.size() > 0)
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
                unit.setUnit_en(updateObject.getString("unit_name_en"));
                unit.setUnit_ar(updateObject.getString("unit_name_ar"));
                lst_unit.add(unit);
            }

            if (lst_unit.size() > 0)
                Unit.InsertUnit(lst_unit, context);

            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteUnit.add(deleteList.getInt(i));
            }

            if (lst_deleteUnit.size() > 0)
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
                ingredient.setNiacin_b3(updateObject.getDouble("niacin_b3"));
                ingredient.setAscorbic_acid(updateObject.getDouble("ascorbic_acid"));
                ingredient.setNdb_no(updateObject.getDouble("ndb_no"));
                ingredient.setIs_favourite(0);
                lst_ingredient.add(ingredient);
            }

            if (lst_ingredient.size() > 0)
                Ingredients.InsertIngredients(lst_ingredient, context);

            for (int i = 0; i < deleteList.length(); i++) {
                lst_deleteIngredient.add(deleteList.getInt(i));
            }

            if (lst_deleteIngredient.size() > 0)
                Ingredients.DeleteIngredient(lst_deleteIngredient, context);


        } catch (Exception e) {
            Log.e("ingredient_Parse_error", "" + e);
        }

    }

    public static void ParseIngredientUnit(JSONObject ingredientUnitJson
            , Context context) {

        String deleteIngredientUnit = "";
        ArrayList<IngredientUnit> lst_ingredient_unit = new ArrayList<>();

        IngredientUnit ingredient_unit;
        try {

            JSONObject updateObject;
            JSONArray updateList = ingredientUnitJson.getJSONArray("update");
            JSONArray deleteList = ingredientUnitJson.getJSONArray("delete");
            for (int i = 0; i < updateList.length(); i++) {

                updateObject = updateList.getJSONObject(i);
                ingredient_unit = new IngredientUnit();
                ingredient_unit.setId(updateObject.getInt("id"));
                ingredient_unit.setIngredient_id(updateObject.getInt("ingredient_id"));
                ingredient_unit.setUnit_id(updateObject.getInt("unit_id"));
                ingredient_unit.setUnit_value(updateObject.getDouble("unit_percent"));
                ingredient_unit.setDefault_unit(updateObject.getInt("default_unit"));

                lst_ingredient_unit.add(ingredient_unit);
            }

            if (lst_ingredient_unit.size() > 0)
                IngredientUnit.InsertIngredientUnit(lst_ingredient_unit, context);

            if (deleteList.length() > 0) {

                deleteIngredientUnit = "(";
                for (int i = 0; i < deleteList.length(); i++) {
                    deleteIngredientUnit += deleteList.getInt(i);
                    if (i < deleteList.length() - 1) {
                        deleteIngredientUnit += ",";
                    }

                }
                deleteIngredientUnit += ")";
                IngredientUnit.DeleteIngredientUnit(deleteIngredientUnit, context);
            }


        } catch (Exception e) {
            Log.e("ingUnit_Parse_error", "" + e);
        }

    }

    public static void ParseFavourite(JSONObject unitJson, Context context) {

        // reset favourite items
        Ingredients.ClearAllFavourite(context);
        try {

            JSONArray normalList = unitJson.getJSONArray("normal");
            JSONArray customList = unitJson.getJSONArray("custom");
            String normal = "";
            String custom = "";

            //------------------------ normal ---------------------

            if (normalList.length() > 0) {
                normal = "(";

                for (int i = 0; i < normalList.length(); i++) {

                    normal += normalList.getInt(i);
                    if (i < normalList.length() - 1) {
                        normal += ",";
                    }
                }
                normal += ")";
                Ingredients.SetAllFavourite(context, 1, normal);
            }


            //---------------------------------- custom -------------

            if (customList.length() > 0) {
                custom = "(";

                for (int i = 0; i < customList.length(); i++) {

                    custom += "'" + customList.getString(i) + "'";
                    if (i < customList.length() - 1) {
                        custom += ",";
                    }
                }
                custom += ")";
                Log.e("custom_favorite", custom);
                Ingredients.SetAllFavourite(context, 2, custom);
            }
        } catch (Exception e) {
            Log.e("fav_parse_error", "" + e);
        }

    }

    public static void ParseCustomMeals(JSONObject customMealJson, Context context) {

        // remove old data
        //Ingredients.RemoveCustomMeal(context);
        ArrayList<Ingredients> lst_ingredient = new ArrayList<>();

        Ingredients ingredient;
        try {

            JSONObject customMeal;
            JSONArray customMealsList = customMealJson.getJSONArray("meals");
            for (int i = 0; i < customMealsList.length(); i++) {

                customMeal = customMealsList.getJSONObject(i);
                ingredient = new Ingredients();
                Log.e("custom_id", customMeal.getString("id"));
                ingredient.setCustomID(customMeal.getString("id"));
                ingredient.setCounter_id(customMeal.getInt("counter_id"));
                ingredient.setItem_name_en(customMeal.getString("item_name"));
                ingredient.setType(customMeal.getInt("type"));
                ingredient.setWater(customMeal.getDouble("water"));
                ingredient.setEnergy(customMeal.getDouble("energy"));
                ingredient.setProtein(customMeal.getDouble("protein"));
                ingredient.setFat(customMeal.getDouble("fat"));
                ingredient.setSat_fat(customMeal.getDouble("sat_fat"));
                ingredient.setCholest(customMeal.getDouble("cholest"));
                ingredient.setCarbo_tot(customMeal.getDouble("carbo_tot"));
                ingredient.setSugars(customMeal.getDouble("sugars"));
                ingredient.setCarbo_fiber(customMeal.getDouble("carbo_fiber"));
                ingredient.setAsh(customMeal.getDouble("ash"));
                ingredient.setCalcium(customMeal.getDouble("calcium"));
                ingredient.setPhosphorus(customMeal.getDouble("phosphorus"));
                ingredient.setIron(customMeal.getDouble("iron"));
                ingredient.setSodium(customMeal.getDouble("sodium"));
                ingredient.setPotassium(customMeal.getDouble("potassium"));
                ingredient.setVit_a(customMeal.getDouble("vit_a"));
                ingredient.setThiamine_b1(customMeal.getDouble("thiamine_b1"));
                ingredient.setRiboflavin_b2(customMeal.getDouble("riboflavin_b2"));
                ingredient.setNiacin_b3(customMeal.getDouble("niacin_b3"));
                ingredient.setAscorbic_acid(customMeal.getDouble("ascorbic_acid"));
                ingredient.setNdb_no(customMeal.getDouble("ndb_no"));
                ingredient.setIs_favourite(customMeal.getInt("is_favorite"));
                ingredient.setUnit_name(customMeal.getString("unit_name"));
                ingredient.setUnit_value(customMeal.getInt("unit_value"));
                lst_ingredient.add(ingredient);
            }

            if (lst_ingredient.size() > 0)
                Ingredients.InsertCustomIngredient(lst_ingredient, context);

        } catch (Exception e) {
            Log.e("customMeal_Parse_error", "" + e);
        }

    }

    public static void ParseMyMeal(JSONObject mealJson, Context context) {

        ArrayList<UserMeal> lst_userMeal = new ArrayList<>();
        UserMeal userMeal;

        try {

            JSONObject myMealObject;
            JSONArray myMealList = mealJson.getJSONArray("result");
            for (int i = 0; i < myMealList.length(); i++) {

                myMealObject = myMealList.getJSONObject(i);
                userMeal = new UserMeal();
                userMeal.setId(myMealObject.getString("device_id"));
                userMeal.setIngredient_id(myMealObject.getString("ingredient_id"));
                userMeal.setDate(myMealObject.getString("date"));
                userMeal.setIs_custom(myMealObject.getInt("is_custom"));
                userMeal.setServing_size(myMealObject.getDouble("serving_size"));
                userMeal.setServing_no(myMealObject.getDouble("serving_number"));
                userMeal.setCounter_id(myMealObject.getInt("counter_id"));

                lst_userMeal.add(userMeal);
            }

            if (lst_userMeal.size() > 0)
                UserMeal.InsertUserMeal(lst_userMeal, context);


        } catch (Exception e) {
            Log.e("myMeal_Parse_error", "" + e);
        }

    }

}
