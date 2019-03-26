package com.catrionacrowe.earthquakeupdates;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper
{
    private static final String DBNAME = "mpd.db";
    private static final int DBVERSION = 1;
    private static final String TABLE1 = "earthquake";

    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStmt;
    private static final String INSERT = "insert into "
            + TABLE1 + "(name) values (?)";

    public DBHelper(Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStmt = this.db.compileStatement(INSERT);
    }

    public long insert(String name)
    {
        this.insertStmt.bindString(1, name);
        return this.insertStmt.executeInsert();
    }

    public void deleteAll()
    {
        db.delete(TABLE1, null, null);
    }

    public List<String> selectAll()
    {
        List<String> list = new ArrayList<String>();
        Cursor cursor = this.db.query(TABLE1, new String[] { "name" },
                null, null, null, null, "name desc");
        if (cursor.moveToFirst())
        {
            do
            {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return list;
    }

    private static class OpenHelper extends SQLiteOpenHelper
    {

        OpenHelper(Context context)
        {
            super(context, DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE1 + "(id INTEGER PRIMARY KEY, name TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w("Example", "Upgrading database - this will drop existing tables and recreate them.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
            onCreate(db);
        }
    } //End of OpenHelper
}// End of DataHelper