package media_sci.com.models;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import media_sci.com.utility.StaticVarClass;
import media_sci.com.utility.Utility;

public class UserMeal {

    private String id;
    private String ItemName_en;
    private String ItemName_ar;
    private String ingredient_id;
    private String date;
    private int is_custom;
    private double serving_size;
    private double serving_no;
    private int counter_id;

    public static String GetDate(Context context) {
        SharedPreferences meal_shared = context.getSharedPreferences("meal_shared",
                Context.MODE_PRIVATE);
        String date = meal_shared.getString("date", "");
        return date;
    }

    public static String SetDate(Context context, String date) {
        SharedPreferences meal_shared = context.getSharedPreferences("meal_shared",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = meal_shared.edit();
        editor.putString("date", date);
        editor.commit();

        return date;
    }

    public static void ClearUserMeals(Context context) {
        try {
            SQLiteDatabase db = Utility.ReadDatabase(context);
            String query = "delete from user_meal";
            db.execSQL(query);
            db.close();
            Log.e("user_meal", "Cleared");
        } catch (Exception e) {
            Log.e("user_meal_error", "" + e);
        }
    }

    public static void InsertUserMeal(ArrayList<UserMeal> lst_userMeal, Context con) {

        UserMeal userMeal;
        SQLiteDatabase db = Utility.ReadDatabase(con);
        try {
            Log.e("myMeal_size", lst_userMeal.size() + "");
            for (int i = 0; i < lst_userMeal.size(); i++) {

                userMeal = lst_userMeal.get(i);

                String query = "insert or Replace into user_meal(meal_id,date,is_custom," +
                        "serving_size,serving_no,id,counter_id) values(?,?,?,?,?,?,?)";
                SQLiteStatement insert_userMeal = db.compileStatement(query);
                insert_userMeal.clearBindings();
                insert_userMeal.bindString(1, userMeal.ingredient_id);
                insert_userMeal.bindString(2, userMeal.date);
                insert_userMeal.bindLong(3, userMeal.is_custom);
                insert_userMeal.bindDouble(4, userMeal.serving_size);
                insert_userMeal.bindDouble(5, userMeal.serving_no);
                insert_userMeal.bindString(6, userMeal.id);
                insert_userMeal.bindLong(7, userMeal.counter_id);

                // set id to prevent duplicate data from webservice
                //insert_userMeal.bindLong(6, userMeal.id);
                insert_userMeal.executeInsert();
            }

            Log.e("user_meal", "inserted");
        } catch (Exception e) {
            Log.e("userMeal_db_error", "" + e);
        }

        String d = "select * from user_meal";
        Cursor cursor = db.rawQuery(d, null);
        Log.e("inserted_count", cursor.getCount() + "");
        db.close();

    }

    public static ArrayList<UserMeal> GetUSerMeals(Context con, String date) {

        SQLiteDatabase db = Utility.ReadDatabase(con);
        ArrayList<UserMeal> lst_userMeals = new ArrayList<>();
        UserMeal userMeal;
        try {

            // get normal meals
            String query = "select x.id as ID , x.meal_id as MealID,x.serving_size as Serving_Size" +
                    ",x.serving_no as Serving_No , x.counter_id " +
                    ", y.item_name_en as ItemName , y.item_name_ar as Name_ar" +
                    " from user_meal as x inner join ingredients as y on " +
                    "x.meal_id=y.id where x.is_custom=0 and x.date='"
                    + date + "'";

            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    userMeal = new UserMeal();
                    userMeal.setId(c.getString(c.getColumnIndex("ID")));
                    userMeal.setIngredient_id(c.getString(c.getColumnIndex("MealID")));
                    userMeal.setServing_size(c.getDouble(c.getColumnIndex("Serving_Size")));
                    userMeal.setServing_no(c.getDouble(c.getColumnIndex("Serving_No")));
                    userMeal.setItemName_en(c.getString(c.getColumnIndex("ItemName")));
                    userMeal.setItemName_ar(c.getString(c.getColumnIndex("Name_ar")));
                    userMeal.setCounter_id(c.getInt(c.getColumnIndex("counter_id")));
                    userMeal.setDate(date);
                    userMeal.setIs_custom(0);
                    lst_userMeals.add(userMeal);

                } while (c.moveToNext());

                // Log.e("normal_size", c.getCount() + "");
            }

            //------------------------------------------------------

            // get custom meals
            query = "select x.meal_id as MealID,x.serving_size as Serving_Size" +
                    ",x.serving_no as Serving_No , y.item_name as ItemName " +
                    " from user_meal as x inner join custom_ingredients as y on " +
                    "x.meal_id=y.id where x.is_custom=1 and x.date='"
                    + date + "'";

            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    userMeal = new UserMeal();
                    userMeal.setIngredient_id(c.getString(c.getColumnIndex("MealID")));
                    userMeal.setServing_size(c.getDouble(c.getColumnIndex("Serving_Size")));
                    userMeal.setServing_no(c.getDouble(c.getColumnIndex("Serving_No")));
                    userMeal.setItemName_en(c.getString(c.getColumnIndex("ItemName")));
                    userMeal.setItemName_ar(c.getString(c.getColumnIndex("ItemName")));
                    userMeal.setDate(date);
                    userMeal.setIs_custom(1);
                    lst_userMeals.add(userMeal);

                } while (c.moveToNext());
                // Log.e("normal+custom", lst_userMeals.size() + "");
            }
        } catch (Exception e) {
            Log.e("GetMeals_db_error", "" + e);
        }

        return lst_userMeals;
    }

    public static void GetUserConsuming(Context context) {

        SQLiteDatabase db = Utility.ReadDatabase(context);

        try {

            String date = Utility.GetStringDateNow();

            // get sum of normal

            String sum_normal_query = "select x.meal_id,y.item_name_en, (y.energy * x.serving_size * x.serving_no) as Calories ," +
                    " (y.fat * x.serving_size * x.serving_no) as TotalFat ," +
                    "(y.cholest * x.serving_size * x.serving_no) as Cholest ,(y.sodium * x.serving_size * x.serving_no) " +
                    " as Sodium ,(y.potassium * x.serving_size * x.serving_no) as Potassium " +
                    ", (y.carbo_tot * x.serving_size * x.serving_no) as Carbo, (y.carbo_fiber * x.serving_size * x.serving_no) as Fiber," +
                    " (y.sugars * x.serving_size * x.serving_no) as Sugars ," +
                    "(y.protein * x.serving_size * x.serving_no) as Protein ,(y.vit_a * x.serving_size * x.serving_no) as Vitamin_A ," +
                    "(y.ascorbic_acid * x.serving_size * x.serving_no) as Vitamin_C , " +
                    "(y.calcium * x.serving_size * x.serving_no) as Calcium ,(y.iron * x.serving_size * x.serving_no) as Iron " +
                    "from user_meal as x inner join ingredients as y on x.meal_id = y.id " +
                    " where x.is_custom=0 and x.date='"
                    + date + "'";

            Cursor c = db.rawQuery(sum_normal_query, null);

            if (c.moveToFirst()) {

                do {
                    StaticVarClass.Calories += c.getDouble(c.getColumnIndex("Calories"));
                    //----
                    Log.e("meal_name", c.getString(c.getColumnIndex("item_name_en")));
                    Log.e("calories_calc", StaticVarClass.Calories + "");
                    //-------
                    StaticVarClass.Total_Fat += c.getDouble(c.getColumnIndex("TotalFat"));
                    StaticVarClass.Cholest += c.getDouble(c.getColumnIndex("Cholest"));
                    StaticVarClass.Sodium += c.getDouble(c.getColumnIndex("Sodium"));
                    StaticVarClass.Potassium += c.getDouble(c.getColumnIndex("Potassium"));
                    StaticVarClass.Carbo += c.getDouble(c.getColumnIndex("Carbo"));
                    StaticVarClass.Fiber += c.getDouble(c.getColumnIndex("Fiber"));
                    StaticVarClass.Sugars += c.getDouble(c.getColumnIndex("Sugars"));
                    StaticVarClass.Protein += c.getDouble(c.getColumnIndex("Protein"));
                    StaticVarClass.VitaminA += c.getDouble(c.getColumnIndex("Vitamin_A"));
                    StaticVarClass.VitaminC += c.getDouble(c.getColumnIndex("Vitamin_C"));
                    StaticVarClass.Calcium += c.getDouble(c.getColumnIndex("Calcium"));
                    StaticVarClass.Iron += c.getDouble(c.getColumnIndex("Iron"));
                } while (c.moveToNext());
            }


            // get sum of custom

            String sum_custom_query = "select x.meal_id, (y.energy * x.serving_no) as Calories , " +
                    "(y.fat * x.serving_no) as TotalFat ," +
                    "(y.cholest * x.serving_no) as Cholest ,(y.sodium * x.serving_no) as Sodium " +
                    ",(y.potassium * x.serving_no) as Potassium " +
                    ",(y.carbo_tot * x.serving_no) as Carbo,(y.carbo_fiber * x.serving_no) as Fiber," +
                    " (y.sugars * x.serving_no) as Sugars ," +
                    "(y.protein * x.serving_no) as Protein ,(y.vit_a * x.serving_no) as Vitamin_A " +
                    ",(y.ascorbic_acid * x.serving_no) as Vitamin_C ,(y.calcium * x.serving_no) as Calcium ," +
                    "(y.iron * x.serving_no) as Iron from user_meal as x" +
                    " inner join custom_ingredients as y on x.meal_id=y.id " +
                    " where x.is_custom=1 and x.date='"
                    + date + "'";

            c = db.rawQuery(sum_custom_query, null);
            if (c.moveToFirst()) {

                do {

                    StaticVarClass.Calories += c.getDouble(c.getColumnIndex("Calories"));
                    StaticVarClass.Total_Fat += c.getDouble(c.getColumnIndex("TotalFat"));
                    StaticVarClass.Cholest += c.getDouble(c.getColumnIndex("Cholest"));
                    StaticVarClass.Sodium += c.getDouble(c.getColumnIndex("Sodium"));
                    StaticVarClass.Potassium += c.getDouble(c.getColumnIndex("Potassium"));
                    StaticVarClass.Carbo += c.getDouble(c.getColumnIndex("Carbo"));
                    StaticVarClass.Fiber += c.getDouble(c.getColumnIndex("Fiber"));
                    StaticVarClass.Sugars += c.getDouble(c.getColumnIndex("Sugars"));
                    StaticVarClass.Protein += c.getDouble(c.getColumnIndex("Protein"));
                    StaticVarClass.VitaminA += c.getDouble(c.getColumnIndex("Vitamin_A"));
                    StaticVarClass.VitaminC += c.getDouble(c.getColumnIndex("Vitamin_C"));
                    StaticVarClass.Calcium += c.getDouble(c.getColumnIndex("Calcium"));
                    StaticVarClass.Iron += c.getDouble(c.getColumnIndex("Iron"));

                } while (c.moveToNext());
            }


        } catch (Exception e) {
            Log.e("user_consume_db_error", e + "");
        }
        db.close();
    }

    public static int GetMaxUserMeal(Context context) {

        int count = 0;

        try {
            SQLiteDatabase db = Utility.ReadDatabase(context);
            String query = "select max(counter_id) as Counter_id from user_meal";
            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                count = c.getInt(c.getColumnIndex("Counter_id"));
                Log.e("counter_id", count + "");
            }
            c.close();
            db.close();

        } catch (Exception e) {
            Log.e("Count_error", "" + e);
        }
        return count;
    }

    public int getCounter_id() {
        return counter_id;
    }

    public void setCounter_id(int counter_id) {
        this.counter_id = counter_id;
    }

    //---------------------- properties ----------------------------------


    public String getItemName_ar() {
        return ItemName_ar;
    }

    public void setItemName_ar(String itemName_ar) {
        ItemName_ar = itemName_ar;
    }

    public String getItemName_en() {
        return ItemName_en;
    }

    public void setItemName_en(String itemName_en) {
        ItemName_en = itemName_en;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(String ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getServing_no() {
        return serving_no;
    }

    public void setServing_no(double serving_no) {
        this.serving_no = serving_no;
    }

    public double getServing_size() {
        return serving_size;
    }

    public void setServing_size(double serving_size) {
        this.serving_size = serving_size;
    }

    public int getIs_custom() {
        return is_custom;
    }

    public void setIs_custom(int is_custom) {
        this.is_custom = is_custom;
    }
}
