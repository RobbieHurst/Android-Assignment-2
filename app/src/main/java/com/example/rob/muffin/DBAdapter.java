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
    static final String KEY_TEAM_NAME = "teamName";
    static final String KEY_POSITION = "position";
    static final String KEY_POINTS = "points";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "Soccer";
    static final String DATABASE_TABLE = "tbl_teams";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
            "create table tbl_teams (_id integer primary key autoincrement, "
                    + "teamName text not null, position text not null, points text not null );";

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
            db.execSQL("DROP TABLE IF EXISTS tbl_teams");
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

    public long insertTeam(String name, String points, String position) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TEAM_NAME, name);
        initialValues.put(KEY_POINTS, points);
        initialValues.put(KEY_POSITION, position);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteTeam(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllTeams()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TEAM_NAME,
                KEY_POSITION,KEY_POINTS}, null, null, null, null, null);
    }

    public Cursor getTeam(long rowId) throws SQLException
    {
        Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                        KEY_TEAM_NAME, KEY_POSITION,KEY_POINTS}, KEY_ROWID + "="+rowId,
                null, null, null, null, null);
        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public boolean updateTeam(long rowId, String name, String pos, String points)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_TEAM_NAME,name);
        args.put(KEY_POINTS,points);
        args.put(KEY_POSITION,pos);
        return db.update(DATABASE_TABLE, args,KEY_ROWID + "=" + rowId, null) > 0;
    }



}
