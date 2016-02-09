package media_sci.com.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/15/2015.
 */
public class IngredientUnit {

    private int id;
    private int ingredient_id;
    private int unit_id;
    private double unit_value;
    private String unit_Name;
    private int default_unit;

    public static ArrayList<IngredientUnit> GetIngredientUnit(Context context, int id) {

        SQLiteDatabase db = Utility.ReadDatabase(context);
        ArrayList<IngredientUnit> lst_result = new ArrayList<>();
        IngredientUnit ingredientUnit;

        try {

            String query = "select x.id as ID , x.ingredient_id as IngredientID , " +
                    "x.unit_id as UnitID , x.unit_value as UnitValue ,x.default_unit as Default_Unit" +
                    " , y.unit_en as UnitName" +
                    " from ingredient_unit as x inner join unit as y on" +
                    " x.unit_id=y.id where x.ingredient_id=" + id;

            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    ingredientUnit = new IngredientUnit();

                    ingredientUnit.setId(c.getInt(c.getColumnIndex("ID")));
                    ingredientUnit.setUnit_id(c.getInt(c.getColumnIndex("UnitID")));
                    ingredientUnit.setUnit_value(c.getDouble(c.getColumnIndex("UnitValue")));
                    ingredientUnit.setUnit_Name(c.getString(c.getColumnIndex("UnitName")));
                    ingredientUnit.setDefault_unit(c.getInt(c.getColumnIndex("Default_Unit")));
                    ingredientUnit.setIngredient_id(id);

                    lst_result.add(ingredientUnit);

                } while (c.moveToNext());
            }
        } catch (Exception e) {

            Log.e("ingredientUnit_db_error", "" + e);
        }

        return lst_result;
    }

    public static void InsertIngredientUnit(ArrayList<IngredientUnit> lst_result, Context con) {
        IngredientUnit ingredientUnit;
        SQLiteDatabase db = Utility.ReadDatabase(con);
        try {


            for (int i = 0; i < lst_result.size(); i++) {
                ingredientUnit = lst_result.get(i);

                String query = "insert or Replace into ingredient_unit" +
                        "(id,ingredient_id,unit_id,unit_value,default_unit) " +
                        "values(?,?,?,?,?)";
                SQLiteStatement insertStmt = db.compileStatement(query);
                insertStmt.clearBindings();
                insertStmt.bindLong(1, ingredientUnit.id);
                insertStmt.bindLong(2, ingredientUnit.ingredient_id);
                insertStmt.bindLong(3, ingredientUnit.unit_id);
                insertStmt.bindDouble(4, ingredientUnit.unit_value);
                insertStmt.bindLong(5, ingredientUnit.default_unit);
                insertStmt.executeInsert();
            }
            db.close();
            Log.e("ingredient_unit", "inserted");
        } catch (Exception e) {
            Log.e("ingredientUnit_db_error", "" + e);
        }
    }

    public static void DeleteIngredientUnit(String deleteIngredient, Context con) {
        SQLiteDatabase db = Utility.ReadDatabase(con);
        try {

            String query = "delete from ingredient_unit where ingredient_id in" + deleteIngredient;
            db.execSQL(query);
            Log.e("ingredient_unit_delete", "done");

        } catch (Exception e) {
            Log.e("delete_ingredUnit_error", "" + e);
        }
        db.close();

    }

    public int getDefault_unit() {
        return default_unit;
    }

    public void setDefault_unit(int default_unit) {
        this.default_unit = default_unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public double getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(double unit_value) {
        this.unit_value = unit_value;
    }

    public String getUnit_Name() {
        return unit_Name;
    }

    public void setUnit_Name(String unit_Name) {
        this.unit_Name = unit_Name;
    }
}
