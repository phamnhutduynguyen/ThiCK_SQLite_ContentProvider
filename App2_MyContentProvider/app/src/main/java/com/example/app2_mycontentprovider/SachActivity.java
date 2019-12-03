package com.example.app2_mycontentprovider;

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

public class SachActivity extends AppCompatActivity {

    private Button btnThem, btnXoa, btnSua, btnTim, btnThoat;
    private EditText etMa, etTen, etMaTG;
    private GridView gridView;
    private ArrayList<String> arrayList;
    private ArrayList<sach> arr;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);

        btnSua = (Button)findViewById(R.id.buttonSua);
        btnThem = (Button)findViewById(R.id.buttonThem);
        btnThoat = (Button)findViewById(R.id.btnThoat);
        btnTim = (Button)findViewById(R.id.buttonTim);
        btnXoa = (Button)findViewById(R.id.buttonXoa);

        etMa = (EditText)findViewById(R.id.editTextMa);
        etTen = (EditText)findViewById(R.id.editTextTen);
        etMaTG = (EditText)findViewById(R.id.editTextMaTG);

        gridView = (GridView)findViewById(R.id.gridview);

        TimKiem("");

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("mas", etMa.getText().toString());
                contentValues.put("tens", etTen.getText().toString());
                contentValues.put("matg", etMaTG.getText().toString());
                Uri table = Uri.parse("content://com.example.app1_mysqlite/data");
                Uri insert = getContentResolver().insert(table, contentValues);
                TimKiem("");
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("mas", etMa.getText().toString());
                contentValues.put("tens", etTen.getText().toString());
                contentValues.put("matg", etMaTG.getText().toString());
                Uri table = Uri.parse("content://com.example.app1_mysqlite/data");
                if(getContentResolver().update(table, contentValues, "mas=?", new String[]{etMa.getText().toString()})>0){
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
                if(getContentResolver().delete(table, "mas=?", new String[]{etMa.getText().toString()})>0){
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
    public void TimKiem(String ma){
        arrayList = new ArrayList<>();
        Uri table = Uri.parse("content://com.example.app1_mysqlite/data");
        Cursor cursor = null;

        if(ma.equalsIgnoreCase("")){
            cursor = getContentResolver().query(table, null, null, null, "tens");
        }else{
            cursor = getContentResolver().query(table, null, "mas=?", new String[]{ma}, "tens");
        }
        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.isAfterLast()==false){
            arrayList.add(cursor.getString(0));
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            cursor.moveToNext();
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        gridView.setAdapter(arrayAdapter);
    }
}
