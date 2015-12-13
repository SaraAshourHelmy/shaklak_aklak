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
public class Category {

    private int id;
    private String name_en;
    private String name_ar;
    private String img_url;


    public Category() {
    }

    public Category(int id, String name_en, String name_ar, String img_url) {
        this.id = id;
        this.name_en = name_en;
        this.name_ar = name_ar;
        this.img_url = img_url;

    }

    public static void InsertCategory(ArrayList<Category> lst_category, Context con) {
        Category category;
        SQLiteDatabase db = Utility.ReadDatabase(con);
        for (int i = 0; i < lst_category.size(); i++) {
            category = lst_category.get(i);
            String query = "insert or Replace into category(id,name_en,name_ar,img_url) " +
                    "values(?,?,?,?)";
            SQLiteStatement insertStmt = db.compileStatement(query);
            insertStmt.clearBindings();
            insertStmt.bindLong(1, category.id);
            insertStmt.bindString(2, category.name_en);
            insertStmt.bindString(3, category.name_ar);
            insertStmt.bindString(4, category.img_url);
            insertStmt.executeInsert();
        }
        db.close();
        Log.e("Categories", "inserted");
    }

    public static ArrayList<Category> GetAllCategory(Context con) {

        SQLiteDatabase db = Utility.ReadDatabase(con);
        ArrayList<Category> lst_category = new ArrayList<>();
        Category category;
        String query = "select * from category";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                int CatId = c.getInt(c.getColumnIndex("id"));
                String Name_en = c.getString(c.getColumnIndex("name_en"));
                String Name_ar = c.getString(c.getColumnIndex("name_ar"));
                String ImgURL = c.getString(c.getColumnIndex("img_url"));

                category = new Category(CatId, Name_en, Name_ar, ImgURL);
                lst_category.add(category);

            } while (c.moveToNext());
        }
        db.close();
        return lst_category;
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
