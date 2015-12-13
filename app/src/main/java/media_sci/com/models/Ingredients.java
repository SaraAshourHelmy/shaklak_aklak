package media_sci.com.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import media_sci.com.utility.Utility;

/**
 * Created by Bassem on 12/7/2015.
 */
public class Ingredients {

    public int id;
    public String item_name_en;
    public String item_name_ar;
    public int cat_id;
    public int res_id;
    public int unit_id;
    public double water, energy, protein, fat, sat_fat, cholest;
    public double carbo_tot, sugars, carbo_fiber, ash, calcium, phosphorus;
    public double iron, sodium, potassium, vit_a, thiamine_b1, riboflavin_b2;
    public double niacin_b3, ascorbic_acid, ndb_no;

    public static void InsertIngredients(ArrayList<Ingredients> lst_ingredients, Context con) {
        Ingredients ingredients;
        SQLiteDatabase db = Utility.ReadDatabase(con);
        for (int i = 0; i < lst_ingredients.size(); i++) {
            ingredients = lst_ingredients.get(i);
            String query = "insert or Replace into ingredients" +
                    "(id,item_name_en,item_name_ar,cat_id,res_id,unit_id," +
                    "water,energy,protein,fat,sat_fat,cholest," +
                    "carbo_tot,sugars,carbo_fiber,ash,calcium,phosphorus," +
                    "iron,sodium,potassium,vit_a,thiamine_b1,riboflavin_b2," +
                    "niacin_b3,ascorbic_acid,ndb_no) " +
                    "values(?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            SQLiteStatement insertStmt = db.compileStatement(query);
            insertStmt.clearBindings();
            insertStmt.bindLong(1, ingredients.id);
            insertStmt.bindString(2, ingredients.item_name_en);
            insertStmt.bindString(3, ingredients.item_name_ar);
            insertStmt.bindLong(4, ingredients.cat_id);
            insertStmt.bindLong(5, ingredients.res_id);
            insertStmt.bindLong(6, ingredients.unit_id);
            insertStmt.bindDouble(7, ingredients.water);
            insertStmt.bindDouble(8, ingredients.energy);
            insertStmt.bindDouble(9, ingredients.protein);
            insertStmt.bindDouble(10, ingredients.fat);
            insertStmt.bindDouble(11, ingredients.sat_fat);
            insertStmt.bindDouble(12, ingredients.cholest);
            insertStmt.bindDouble(13, ingredients.carbo_tot);
            insertStmt.bindDouble(14, ingredients.sugars);
            insertStmt.bindDouble(15, ingredients.carbo_fiber);
            insertStmt.bindDouble(16, ingredients.ash);
            insertStmt.bindDouble(17, ingredients.calcium);
            insertStmt.bindDouble(18, ingredients.phosphorus);
            insertStmt.bindDouble(19, ingredients.iron);
            insertStmt.bindDouble(20, ingredients.sodium);
            insertStmt.bindDouble(21, ingredients.potassium);
            insertStmt.bindDouble(22, ingredients.vit_a);
            insertStmt.bindDouble(23, ingredients.thiamine_b1);
            insertStmt.bindDouble(24, ingredients.riboflavin_b2);
            insertStmt.bindDouble(25, ingredients.niacin_b3);
            insertStmt.bindDouble(26, ingredients.ascorbic_acid);
            insertStmt.bindDouble(27, ingredients.ndb_no);
            insertStmt.executeInsert();
        }
        db.close();
        Log.e("Restaurants", "inserted");
    }

    public static ArrayList<Ingredients> GetAllRestaurant(Context con) {

        SQLiteDatabase db = Utility.ReadDatabase(con);
        ArrayList<Ingredients> lst_ingredients = new ArrayList<>();
        Ingredients ingredients;
        String query = "select * from ingredients";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                ingredients = new Ingredients();
                ingredients.setId(c.getInt(c.getColumnIndex("id")));
                ingredients.setItem_name_en(c.getString(c.getColumnIndex("item_name_en")));
                ingredients.setItem_name_ar(c.getString(c.getColumnIndex("item_name_ar")));
                ingredients.setCat_id(c.getInt(c.getColumnIndex("cat_id")));
                ingredients.setId(c.getInt(c.getColumnIndex("res_id")));
                ingredients.setUnit_id(c.getInt(c.getColumnIndex("unit_id")));
                ingredients.setWater(c.getDouble(c.getColumnIndex("water")));
                ingredients.setEnergy(c.getDouble(c.getColumnIndex("energy")));
                ingredients.setProtein(c.getDouble(c.getColumnIndex("protein")));
                ingredients.setFat(c.getDouble(c.getColumnIndex("fat")));
                ingredients.setSat_fat(c.getDouble(c.getColumnIndex("sat_fat")));
                ingredients.setCholest(c.getDouble(c.getColumnIndex("cholest")));
                ingredients.setCarbo_tot(c.getDouble(c.getColumnIndex("carbo_tot")));
                ingredients.setSugars(c.getDouble(c.getColumnIndex("sugars")));
                ingredients.setCarbo_fiber(c.getDouble(c.getColumnIndex("carbo_fiber")));
                ingredients.setAsh(c.getDouble(c.getColumnIndex("ash")));
                ingredients.setCalcium(c.getDouble(c.getColumnIndex("calcium")));
                ingredients.setPhosphorus(c.getDouble(c.getColumnIndex("phosphorus")));
                ingredients.setIron(c.getDouble(c.getColumnIndex("iron")));
                ingredients.setSodium(c.getDouble(c.getColumnIndex("sodium")));
                ingredients.setPotassium(c.getDouble(c.getColumnIndex("potassium")));
                ingredients.setVit_a(c.getDouble(c.getColumnIndex("vit_a")));
                ingredients.setThiamine_b1(c.getDouble(c.getColumnIndex("thiamine_b1")));
                ingredients.setRiboflavin_b2(c.getDouble(c.getColumnIndex("riboflavin_b2")));
                ingredients.setNiacin_b3(c.getDouble(c.getColumnIndex("niacin_b3")));
                ingredients.setAscorbic_acid(c.getDouble(c.getColumnIndex("ascorbic_acid")));
                ingredients.setNdb_no(c.getDouble(c.getColumnIndex("ndb_no")));

                lst_ingredients.add(ingredients);

            } while (c.moveToNext());
        }
        db.close();
        return lst_ingredients;
    }

    public double getNiacin_b3() {
        return niacin_b3;
    }

    public void setNiacin_b3(double niacin_b3) {
        this.niacin_b3 = niacin_b3;
    }

    public double getAscorbic_acid() {
        return ascorbic_acid;
    }

    public void setAscorbic_acid(double ascorbic_acid) {
        this.ascorbic_acid = ascorbic_acid;
    }

    public double getNdb_no() {
        return ndb_no;
    }

    public void setNdb_no(double ndb_no) {
        this.ndb_no = ndb_no;
    }

    public double getThiamine_b1() {
        return thiamine_b1;
    }

    public void setThiamine_b1(double thiamine_b1) {
        this.thiamine_b1 = thiamine_b1;
    }

    public double getRiboflavin_b2() {
        return riboflavin_b2;
    }

    public void setRiboflavin_b2(double riboflavin_b2) {
        this.riboflavin_b2 = riboflavin_b2;
    }

    public double getVit_a() {
        return vit_a;
    }

    public void setVit_a(double vit_a) {
        this.vit_a = vit_a;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public double getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(double phosphorus) {
        this.phosphorus = phosphorus;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getAsh() {
        return ash;
    }

    public void setAsh(double ash) {
        this.ash = ash;
    }

    public double getCarbo_fiber() {
        return carbo_fiber;
    }

    public void setCarbo_fiber(double carbo_fiber) {
        this.carbo_fiber = carbo_fiber;
    }

    public double getSugars() {
        return sugars;
    }

    public void setSugars(double sugars) {
        this.sugars = sugars;
    }

    public double getCarbo_tot() {
        return carbo_tot;
    }

    public void setCarbo_tot(double carbo_tot) {
        this.carbo_tot = carbo_tot;
    }

    public double getCholest() {
        return cholest;
    }

    public void setCholest(double cholest) {
        this.cholest = cholest;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getSat_fat() {
        return sat_fat;
    }

    public void setSat_fat(double sat_fa) {
        this.sat_fat = sat_fa;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name_en() {
        return item_name_en;
    }

    public void setItem_name_en(String item_name_en) {
        this.item_name_en = item_name_en;
    }

    public String getItem_name_ar() {
        return item_name_ar;
    }

    public void setItem_name_ar(String item_name_ar) {
        this.item_name_ar = item_name_ar;
    }


    // read and write in db
}
