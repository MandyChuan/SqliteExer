package com.example.lsx.sqliteexer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lsx.sqliteexer.database.MyDatebaseHelper;

public class MainActivity extends AppCompatActivity {
    private Button mCreateDbButton;
    private Button mInsertButton;
    private Button mUpdateButton;
    private Button mDeleteButton;
    private Button mQueryButton;
    private MyDatebaseHelper mDbHelper;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new MyDatebaseHelper(this,"bookstore.db",null,3);
        mCreateDbButton = (Button)findViewById(R.id.activity_main_create_db_button);
        mCreateDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDbHelper.getReadableDatabase();
            }
        });
        mInsertButton = (Button)findViewById(R.id.activity_main_insert_db_button);
        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("name","The Da Vinci Code");
                cv.put("author","Dan Brown");
                cv.put("pages",454);
                cv.put("price",16.99);
                db.insert("book",null,cv);

                cv.clear();
                cv.put("name","Test Book");
                cv.put("author","Lin Bao Chuan");
                cv.put("pages",500);
                cv.put("price",10);
                db.insert("book",null,cv);
            }
        });
        mUpdateButton = (Button)findViewById(R.id.activity_main_update_db_button);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("price",10);
                db.update("book",cv,"name=?",new String[]{"The Da Vinci Code"});
            }
        });

        mDeleteButton = (Button)findViewById(R.id.activity_main_delete_db_button);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.delete("book","pages>=?",new String[]{"500"});
            }
        });

        mQueryButton = (Button)findViewById(R.id.activity_main_query_db_button);
        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Cursor cursor = db.query("book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String  author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, "book name is "+name);
                        Log.d(TAG, "book author is "+author);
                        Log.d(TAG, "book pages is "+pages);
                        Log.d(TAG, "book price is "+price);
                    } while (cursor.moveToNext());

                }
                cursor.close();

            }
        });


    }
}
