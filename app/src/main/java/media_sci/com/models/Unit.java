package media_sci.com.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import media_sci.com.utility.Utility;

public class Unit {

    private int id;
    private String unit_en;
    private String unit_ar;

    public static void InsertUnit(ArrayList<Unit> lst_unit, Context con) {
        Unit unit;
        SQLiteDatabase db = Utility.ReadDatabase(con);
        try {

            for (int i = 0; i < lst_unit.size(); i++) {
                unit = lst_unit.get(i);
                String query = "insert or Replace into unit(id,unit_en,unit_ar) " +
                        "values(?,?,?)";
                SQLiteStatement insertStmt = db.compileStatement(query);
                insertStmt.clearBindings();
                insertStmt.bindLong(1, unit.id);
                insertStmt.bindString(2, unit.unit_en);
                insertStmt.bindString(3, unit.unit_ar);
                insertStmt.executeInsert();
            }
            db.close();
            Log.e("units", "inserted");
        } catch (Exception e) {
            Log.e("units db error", "" + e);
        }
    }

    public static void DeleteUnit(ArrayList<Integer> lst_deleteUnit, Context con) {
        SQLiteDatabase db = Utility.ReadDatabase(con);
        try {

            String deleteUnit = "(";
            for (int i = 0; i < lst_deleteUnit.size(); i++) {
                deleteUnit += lst_deleteUnit.get(i);
                if (i < lst_deleteUnit.size() - 1) {
                    deleteUnit += ",";
                }

            }
            deleteUnit += ")";
            String query = "delete from unit where id in" + deleteUnit;
            db.execSQL(query);

            Log.e("unit_delete", "done");

        } catch (Exception e) {
            Log.e("delete_unit_error", "" + e);
        }
        db.close();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit_en() {
        return unit_en;
    }

    public void setUnit_en(String unit_en) {
        this.unit_en = unit_en;
    }

    public String getUnit_ar() {
        return unit_ar;
    }

    public void setUnit_ar(String unit_ar) {
        this.unit_ar = unit_ar;
    }
}
