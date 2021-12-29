package com.gordarg.messageforwarder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.number.FormattedNumberRange;
import android.widget.Toast;

import com.gordarg.messageforwarder.MainActivity;
import com.gordarg.messageforwarder.model.Forwarder;

import java.text.Normalizer;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String FORWARDERS_TABLE_NAME = "[forwarders]";
    public static final String FORWARDERS_COLUMN_ID = "id";
    public static final String FORWARDERS_COLUMN_FROM = "from";
    public static final String FORWARDERS_COLUMN_TO = "to";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + FORWARDERS_TABLE_NAME + " " +
                        "(id integer primary key AUTOINCREMENT, [from] text, [to] text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        //        db.execSQL("DROP TABLE IF EXISTS contacts");
        //        onCreate(db);
    }

    public Cursor getForwarder(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, FORWARDERS_TABLE_NAME);
        return numRows;
    }

    public boolean insertForwarder (String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("[" + FORWARDERS_COLUMN_FROM + "]", from);
        contentValues.put("[" + FORWARDERS_COLUMN_TO + "]", to);
        db.insert(FORWARDERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteForwarder (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FORWARDERS_TABLE_NAME,
                FORWARDERS_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Forwarder> getAllForwarders() {
        ArrayList<Forwarder> array_list = new ArrayList<Forwarder>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + FORWARDERS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            Forwarder item = new Forwarder();

            item.setFrom(res.getString(res.getColumnIndex(FORWARDERS_COLUMN_FROM)));
            item.setTo(res.getString(res.getColumnIndex(FORWARDERS_COLUMN_TO)));
            item.setId(res.getInt(res.getColumnIndex(FORWARDERS_COLUMN_ID)));

            array_list.add(item);
            res.moveToNext();
        }
        return array_list;
    }
}
