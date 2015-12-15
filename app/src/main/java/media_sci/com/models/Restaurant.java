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
public class Restaurant {

    private int id;
    private String name_en;
    private String name_ar;
    private String img_url;

    public Restaurant() {
    }

    public Restaurant(int id, String name_en, String name_ar, String img_url) {
        this.id = id;
        this.name_en = name_en;
        this.name_ar = name_ar;
        this.img_url = img_url;

    }

    public static void InsertRestaurant(ArrayList<Restaurant> lst_rest, Context con) {
        Restaurant restaurant;
        SQLiteDatabase db = Utility.ReadDatabase(con);
        try {


            for (int i = 0; i < lst_rest.size(); i++) {
                restaurant = lst_rest.get(i);
                String query = "insert or Replace into restaurant(id,name_en,name_ar,img_url) " +
                        "values(?,?,?,?)";
                SQLiteStatement insertStmt = db.compileStatement(query);
                insertStmt.clearBindings();
                insertStmt.bindLong(1, restaurant.id);
                insertStmt.bindString(2, restaurant.name_en);
                insertStmt.bindString(3, restaurant.name_ar);
                insertStmt.bindString(4, restaurant.img_url);
                insertStmt.executeInsert();
            }
            db.close();
            Log.e("Restaurants", "inserted");
        } catch (Exception e) {
            Log.e("restaurant db error", "" + e);
        }
    }

    public static ArrayList<Restaurant> GetAllRestaurant(Context con) {

        SQLiteDatabase db = Utility.ReadDatabase(con);
        ArrayList<Restaurant> lst_rest = new ArrayList<>();
        try {


            Restaurant restaurant;
            String query = "select * from restaurant";
            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    int RestId = c.getInt(c.getColumnIndex("id"));
                    String Name_en = c.getString(c.getColumnIndex("name_en"));
                    String Name_ar = c.getString(c.getColumnIndex("name_ar"));
                    String ImgURL = c.getString(c.getColumnIndex("img_url"));

                    restaurant = new Restaurant(RestId, Name_en, Name_ar, ImgURL);
                    lst_rest.add(restaurant);

                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.e("restaurant db error", "" + e);
        }
        db.close();
        return lst_rest;
    }

    public static void DeleteRestaurant(ArrayList<Integer> lst_deleteRestaurant, Context con) {
        SQLiteDatabase db = Utility.ReadDatabase(con);
        try {

            String deleteRestaurant = "(";
            for (int i = 0; i < lst_deleteRestaurant.size(); i++) {
                deleteRestaurant += lst_deleteRestaurant.get(i);
                if (i < lst_deleteRestaurant.size() - 1) {
                    deleteRestaurant += ",";
                }

            }
            deleteRestaurant += ")";
            String query = "delete from restaurant where id in" + deleteRestaurant;
            db.execSQL(query);

            Log.e("restaurant_delete", "done");

        } catch (Exception e) {
            Log.e("delete restaurant error", "" + e);
        }
        db.close();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_ar() {
        return name_ar;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
