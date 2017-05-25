package com.example.rob.muffin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by roberthurst on 25/05/2017.
 */

public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_GAME_NAME = "gameName";
    static final String KEY_PUBLISHER = "publisher";
    static final String KEY_RATING = "rating";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "Games";
    static final String DATABASE_TABLE = "tbl_games";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table tbl_games (_id integer primary key autoincrement, "
                    + "gameName text not null, publisher text not null, rating text not null );";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.v(TAG, "Upgrading database from version " + oldVersion + "to"
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tbl_games");
            onCreate(db);
        }
    }

    //open the database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //close the database
    public void close() {
        DBHelper.close();
    }

    public long insertGame(String name, String publisher, String rating) {

        open();

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GAME_NAME, name);
        initialValues.put(KEY_RATING, rating);
        initialValues.put(KEY_PUBLISHER, publisher);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteTeam(long rowId)
    {
        open();

        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllGames()
    {
        open();
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_GAME_NAME,
                KEY_PUBLISHER, KEY_RATING}, null, null, null, null, null);
    }

    public boolean updateTeam(long rowId, String name, String pos, String points)
    {
        open();

        ContentValues args = new ContentValues();
        args.put(KEY_GAME_NAME,name);
        args.put(KEY_RATING,points);
        args.put(KEY_PUBLISHER,pos);
        return db.update(DATABASE_TABLE, args,KEY_ROWID + "=" + rowId, null) > 0;
    }

}
