package media_sci.com.utility;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import media_sci.com.models.Category;

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
                updateObject = deleteList.getJSONObject(i);
                int category_id = updateObject.getInt("id");
                lst_deleteCategory.add(category_id);
            }
            Category.DeleteCategory(lst_deleteCategory,context);


        } catch (Exception e) {
            Log.e("category_Parse_error", "" + e);
        }

    }
}
