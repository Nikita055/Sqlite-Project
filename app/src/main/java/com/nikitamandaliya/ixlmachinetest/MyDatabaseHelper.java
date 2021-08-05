package com.nikitamandaliya.ixlmachinetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
   private Context context;
   private static final String DATABASE_NAME = "Info.db";
   private static final int DATABASE_VERSION = 1;

   private static final String TABLE_NAME = "Information_table";
    public static final String COLUMN_ID = "_id";
   private static final String COLUMN_PI_NAME = "name";
   private static final String COLUMN_PI_lNAME = "last_name";
   private static final String COLUMN_PI_PHONE_NO = "Phone_number";
   private static final String COLUMN_PI_GENDER = "Gender";
   private static final String COLUMN_PI_DATE = "Date";

    private SQLiteDatabase db;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_PI_NAME + " TEXT, " +
                COLUMN_PI_lNAME + " TEXT, " +
                COLUMN_PI_PHONE_NO + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Get All SQLite Data
    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return cur;
    }

    //Get 1 Data By ID
    public Cursor oneData(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id, null);
        return cur;
    }

    //Insert Data to Database
    public void insertData(ContentValues values){
        db.insert(TABLE_NAME, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(TABLE_NAME, values, COLUMN_ID + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(TABLE_NAME, COLUMN_ID + "=" + id, null);
    }

    public void addPersonalInfoData(String name, String Lname, String PhoneNo, int Gender)
    {
       SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PI_NAME,name);
        cv.put(COLUMN_PI_lNAME,Lname);
        cv.put(COLUMN_PI_PHONE_NO,PhoneNo);
        cv.put(COLUMN_PI_GENDER,Gender);
      /*  cv.put(COLUMN_PI_DATE,Date);*/
        long result = db.insert(TABLE_NAME, null,cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

   public void addEmployessInfoData(){

   }

}
