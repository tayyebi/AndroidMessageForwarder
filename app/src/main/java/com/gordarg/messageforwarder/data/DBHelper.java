package com.gordarg.messageforwarder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gordarg.messageforwarder.model.AutoReply;
import com.gordarg.messageforwarder.model.Forwarder;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String FORWARDERS_TABLE_NAME = "[forwarders]";
    public static final String FORWARDERS_COLUMN_ID = "id";
    public static final String FORWARDERS_COLUMN_FROM = "from";
    public static final String FORWARDERS_COLUMN_TO = "to";
    
    public static final String AUTOREPLIES_TABLE_NAME = "[autoreplies]";
    public static final String AUTOREPLIES_COLUMN_ID = "id";
    public static final String AUTOREPLIES_COLUMN_CONDITION = "condition";
    public static final String AUTOREPLIES_COLUMN_REPLY = "reply";
    public static final String AUTOREPLIES_COLUMN_ENABLED = "enabled";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + FORWARDERS_TABLE_NAME + " " +
                        "(id integer primary key AUTOINCREMENT, [from] text, [to] text)"
        );
        
        db.execSQL(
                "create table " + AUTOREPLIES_TABLE_NAME + " " +
                        "(id integer primary key AUTOINCREMENT, [condition] text, [reply] text, [enabled] integer)"
        );
        
        seedDefaultAutoReplies(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(
                    "create table " + AUTOREPLIES_TABLE_NAME + " " +
                            "(id integer primary key AUTOINCREMENT, [condition] text, [reply] text, [enabled] integer)"
            );
            seedDefaultAutoReplies(db);
        }
    }
    
    private void seedDefaultAutoReplies(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("[" + AUTOREPLIES_COLUMN_CONDITION + "]", "لغو۱۱|لغو11|لغو 11|لغو ۱۱");
        cv.put("[" + AUTOREPLIES_COLUMN_REPLY + "]", "11");
        cv.put("[" + AUTOREPLIES_COLUMN_ENABLED + "]", 1);
        db.insert(AUTOREPLIES_TABLE_NAME, null, cv);
    }

    public boolean insertForwarder(String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("[" + FORWARDERS_COLUMN_FROM + "]", from);
        contentValues.put("[" + FORWARDERS_COLUMN_TO + "]", to);
        db.insert(FORWARDERS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteForwarder(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FORWARDERS_TABLE_NAME,
                FORWARDERS_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Forwarder> getAllForwarders() {
        ArrayList<Forwarder> array_list = new ArrayList<Forwarder>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + FORWARDERS_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Forwarder item = new Forwarder();
            item.setFrom(res.getString(res.getColumnIndex(FORWARDERS_COLUMN_FROM)));
            item.setTo(res.getString(res.getColumnIndex(FORWARDERS_COLUMN_TO)));
            item.setId(res.getInt(res.getColumnIndex(FORWARDERS_COLUMN_ID)));
            array_list.add(item);
            res.moveToNext();
        }
        return array_list;
    }
    
    public boolean insertAutoReply(String condition, String reply, boolean enabled) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("[" + AUTOREPLIES_COLUMN_CONDITION + "]", condition);
        contentValues.put("[" + AUTOREPLIES_COLUMN_REPLY + "]", reply);
        contentValues.put("[" + AUTOREPLIES_COLUMN_ENABLED + "]", enabled ? 1 : 0);
        db.insert(AUTOREPLIES_TABLE_NAME, null, contentValues);
        return true;
    }
    
    public Integer deleteAutoReply(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(AUTOREPLIES_TABLE_NAME,
                AUTOREPLIES_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }
    
    public ArrayList<AutoReply> getAllAutoReplies() {
        ArrayList<AutoReply> array_list = new ArrayList<AutoReply>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + AUTOREPLIES_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            AutoReply item = new AutoReply();
            item.setCondition(res.getString(res.getColumnIndex(AUTOREPLIES_COLUMN_CONDITION)));
            item.setReply(res.getString(res.getColumnIndex(AUTOREPLIES_COLUMN_REPLY)));
            item.setIsEnabled(res.getInt(res.getColumnIndex(AUTOREPLIES_COLUMN_ENABLED)) == 1);
            item.setId(res.getInt(res.getColumnIndex(AUTOREPLIES_COLUMN_ID)));
            array_list.add(item);
            res.moveToNext();
        }
        return array_list;
    }
}
