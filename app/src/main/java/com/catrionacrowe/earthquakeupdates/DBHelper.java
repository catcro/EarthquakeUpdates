package com.catrionacrowe.earthquakeupdates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper
{
    private static final String DBNAME = "mpd-db";
    private static final int DBVERSION = 1;
    private static final String TABLE1 = "earthquakes";

    private Context context;
    private SQLiteDatabase db;

    public DBHelper(Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
    }

    public boolean insertStatement(String eqLoc, String eqMag, String eqDep, String eqLink, String eqDate, String eqLat, String eqLong) {
        SQLiteDatabase db = this.db;
        ContentValues contentValues = new ContentValues();
        if(eqLoc !=null) {
            contentValues.put("Location", eqLoc);
            contentValues.put("Magnitude", eqMag);
            contentValues.put("Depth", eqDep);
            contentValues.put("Link", eqLink);
            contentValues.put("Date", eqDate.substring(5,16));
            contentValues.put("GeoLat", eqLat);
            contentValues.put("GeoLong", eqLong);
//            Log.e("Entry ", "- Location - " + eqLoc + "\n - Date - " + eqDate.substring(5,16) + "\n - Magnitude - " + eqMag + "\n - Depth - " + eqDep + "\n - Link - " + eqLink + "\n - Geo Lat - " + eqLat + "\n- Geo Lng - " + eqLong + "\n\n");
            db.insert("earthquakes", null, contentValues);
        }
        return true;
    }

    public void deleteStatement()
    {
        db.delete(TABLE1, null, null);

    }

    public List<String> selectStatementA()
    {
        List<String> list1 = new ArrayList<String>();
        Cursor c1 = this.db.query(TABLE1, new String[] { "Location", "GeoLat", "GeoLong", "Date" },
                null, null, null, null,  "Location desc");
        if (c1.moveToFirst())
        {
            do
            {
                list1.add(c1.getString(0));
                list1.add(c1.getString(1));
                list1.add(c1.getString(2));
                c1.getString(3);
                list1.add(c1.getString(3));
            } while (c1.moveToNext());
        }
        if (c1 != null && !c1.isClosed())
        {
            c1.close();
        }
        return list1;
    }

    public ArrayList<String> selectStatementB(String dateSelect) {
        ArrayList<String> list2 = new ArrayList<String>();
        Cursor c2 = db.rawQuery("SELECT * FROM " + TABLE1 + " WHERE Date == " + "'"+dateSelect+"';",null);
        c2.moveToFirst();
        while(c2.isAfterLast() == false){
            list2.add(c2.getString(0));
            list2.add(c2.getString(1));
            list2.add(c2.getString(2));
            list2.add(c2.getString(3));
            list2.add(c2.getString(4));
            list2.add(c2.getString(5));
            list2.add(c2.getString(6));
            list2.add(c2.getString(7));
            c2.moveToNext();
        }
        Log.e("TRIAL", String.valueOf(list2));
        return list2;
    }

    private static class OpenHelper extends SQLiteOpenHelper
    {

        OpenHelper(Context context)
        {
            super(context, (String) DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE1 + "(id INTEGER PRIMARY KEY, Location TEXT, Magnitude TEXT, Depth TEXT, Link TEXT, Date TEXT, GeoLat DOUBLE, GeoLong DOUBLE)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w("Example", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
            onCreate(db);
        }
    } //End of OpenHelper
}// End of DataHelper