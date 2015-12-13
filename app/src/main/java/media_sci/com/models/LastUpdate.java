package media_sci.com.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import media_sci.com.utility.Utility;

/**
 * Created by sara on 3/19/2015.
 */
public class LastUpdate {

    public int lastUpdate_id;
    public String name;
    public String last_update;

    public LastUpdate(int lastUpdate_id, String name, String last_update) {

        this.lastUpdate_id = lastUpdate_id;
        this.name = name;
        this.last_update = last_update;
    }

    public static void InsertLastUpdate(LastUpdate lastUpdate, Context con) {

        SQLiteDatabase db = Utility.ReadDatabase(con);
        String query = "insert or Replace into lastupdate " +
                "(lastupdate_id,name,lastupdate) values (?,?,?)";
        SQLiteStatement insertStmt = db.compileStatement(query);
        insertStmt.clearBindings();
        insertStmt.bindLong(1, lastUpdate.lastUpdate_id);
        insertStmt.bindString(2, lastUpdate.name);
        insertStmt.bindString(3, lastUpdate.last_update);
        insertStmt.executeInsert();
        db.execSQL(query);
    }

    public static LastUpdate GetLastUpdate(Context con, String name) {

        LastUpdate lastUpdate = null;
        SQLiteDatabase db = Utility.ReadDatabase(con);
        String query = "select * from lastupdate where  name='"
                + name + "'";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex("lastupdate_id"));
                String last_update = c.getString(c.getColumnIndex("lastupdate"));
                lastUpdate = new LastUpdate(id, name, last_update);
            } while (c.moveToNext());
        }
        db.close();
        return lastUpdate;
    }
}
