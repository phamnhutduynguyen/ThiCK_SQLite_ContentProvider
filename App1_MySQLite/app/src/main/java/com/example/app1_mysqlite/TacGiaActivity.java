package com.example.app1_mysqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class TacGiaActivity extends AppCompatActivity {

    private Button btnThem, btnXoa, btnSua, btnTim, btnThoat;
    private EditText etMa, etTen;
    private GridView gridView;
    private ArrayList<String> arrayList;
    private ArrayList<tacgia> arr;
    private ArrayAdapter<String> arrayAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tac_gia);

        dbHelper = new DBHelper(this);

        btnSua = (Button)findViewById(R.id.buttonSua);
        btnThem = (Button)findViewById(R.id.buttonThem);
        btnThoat = (Button)findViewById(R.id.btnThoat);
        btnTim = (Button)findViewById(R.id.buttonTim);
        btnXoa = (Button)findViewById(R.id.buttonXoa);

        etMa = (EditText)findViewById(R.id.editTextMa);
        etTen = (EditText)findViewById(R.id.editTextTen);

        gridView = (GridView)findViewById(R.id.gridview);

        TimKiem("");


        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*        SQLite
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.themTG(etMa.getText().toString(), etTen.getText().toString())>0){
                    Toast.makeText(getBaseContext(), "them ok", Toast.LENGTH_SHORT).show();
                    TimKiem("");
                }else{
                    Toast.makeText(getBaseContext(), "them error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.suaTG(etMa.getText().toString(), etTen.getText().toString())>0){
                    Toast.makeText(getBaseContext(), "sua ok", Toast.LENGTH_SHORT).show();
                    TimKiem("");
                }else{
                    Toast.makeText(getBaseContext(), "sua error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.xoaTG(etMa.getText().toString())>0){
                    Toast.makeText(getBaseContext(), "xoa ok", Toast.LENGTH_SHORT).show();
                    TimKiem("");
                }else{
                    Toast.makeText(getBaseContext(), "xoa error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimKiem(etMa.getText().toString());
            }
        });
*/
//      ContentProvider
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("matg", etMa.getText().toString());
                contentValues.put("tentg", etTen.getText().toString());
                Uri table = Uri.parse("content://com.example.app1_mysqlite/data");
                Uri insert = getContentResolver().insert(table, contentValues);
                TimKiem("");
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("matg", etMa.getText().toString());
                contentValues.put("tentg", etTen.getText().toString());
                Uri table = Uri.parse("content://com.example.app1_mysqlite/data");
                if(getContentResolver().update(table, contentValues, "matg=?", new String[]{etMa.getText().toString()})>0){
                    Toast.makeText(getBaseContext(), "sua ok", Toast.LENGTH_SHORT).show();
                    TimKiem("");
                }else{
                    Toast.makeText(getBaseContext(), "sua error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri table = Uri.parse("content://com.example.app1_mysqlite/data");
                if(getContentResolver().delete(table, "matg=?", new String[]{etMa.getText().toString()})>0){
                    Toast.makeText(getBaseContext(), "xoa ok", Toast.LENGTH_SHORT).show();
                    TimKiem("");
                }else{
                    Toast.makeText(getBaseContext(), "xoa error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimKiem(etMa.getText().toString());
            }
        });
    }
    //Tim kiem = SQLite
//    public void TimKiem(String ma){
//        arrayList = new ArrayList<>();
//        arr = new ArrayList<>();
//        arr = dbHelper.TimTG(ma);
//        for(tacgia tg: arr){
//            arrayList.add(tg.getMaTG());
//            arrayList.add(tg.getTenTG());
//        }
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
//        gridView.setAdapter(arrayAdapter);
//    }


    //Tim kiem = provider
    public void TimKiem(String ma){
        arrayList = new ArrayList<>();
        Uri table = Uri.parse("content://com.example.app1_mysqlite/data");
        Cursor cursor = null;

        if(ma.equalsIgnoreCase("")){
            cursor = getContentResolver().query(table, null, null, null, "tentg");
        }else{
            cursor = getContentResolver().query(table, null, "matg=?", new String[]{ma}, "tentg");
        }
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(arrayAdapter);
    }
}
