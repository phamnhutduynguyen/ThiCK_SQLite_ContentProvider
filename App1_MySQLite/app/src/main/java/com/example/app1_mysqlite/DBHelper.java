package com.example.app1_mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "phamnhutduynguyen", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table tacgia(matg text primary key, tentg text)");
        sqLiteDatabase.execSQL("create table sach(mas text primary key, tens text, matg text constraint matg references tacgia(matg) on delete cascade on update cascade)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists tacgia");
        sqLiteDatabase.execSQL("drop table if exists sach");
        onCreate(sqLiteDatabase);
    }

    public int themTG(String ma, String ten){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matg", ma);
        contentValues.put("tentg", ten);
        int kq = (int) db.insert("tacgia", null, contentValues);
        return kq;
    }
    public int themS(String ma, String ten, String maTG){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mas", ma);
        contentValues.put("tens", ten);
        contentValues.put("matg", maTG);
        int kq = (int) db.insert("sach", null, contentValues);
        return kq;
    }
    public int xoaTG(String ma){
        SQLiteDatabase db = getWritableDatabase();
        int kq = (int) db.delete("tacgia", "matg=?", new String[]{ma});
        return kq;
    }
    public int xoaS(String ma){
        SQLiteDatabase db = getWritableDatabase();
        int kq = (int) db.delete("sach", "mas=?", new String[]{ma});
        return kq;
    }
    public int suaTG(String ma, String ten){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matg", ma);
        contentValues.put("tentg", ten);
        int kq = (int) db.update("tacgia", contentValues,"matg=?", new String[]{ma});
        return kq;
    }
    public int suaS(String ma, String ten, String maTG){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mas", ma);
        contentValues.put("tens", ten);
        contentValues.put("matg", maTG);
        int kq = (int) db.update("sach", contentValues,"mas=?", new String[]{ma});
        return kq;
    }
    public ArrayList<tacgia> TimTG(String ma){
        ArrayList<tacgia> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(ma.equalsIgnoreCase("")){
            cursor = db.rawQuery("select * from tacgia", null);
        }else{
            cursor = db.rawQuery("select * from tacgia where matg=?", new String[]{ma});
        }
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(new tacgia(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        return arrayList;
    }
    public ArrayList<sach> TimS(String ma){
        ArrayList<sach> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(ma.equalsIgnoreCase("")){
            cursor = db.rawQuery("select * from sach", null);
        }else{
            cursor = db.rawQuery("select * from sach where mas=?", new String[]{ma});
        }
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(new sach(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
