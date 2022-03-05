package com.kevinnt.myaddressbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.kevinnt.myaddressbook.models.EmployeeTableModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "myaddressbook.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTabelStmt = "CREATE TABLE EMPLOYEE (employeeId INTEGER PRIMARY KEY," +
                "name TEXT," +
                "city TEXT," +
                "call TEXT," +
                "email TEXT," +
                "profileUrl TEXT)";

        sqLiteDatabase.execSQL(createTabelStmt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public boolean insertEmployee(EmployeeTableModel employeeTableModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("employeeId", employeeTableModel.getEmployeeId());
        contentValues.put("name", employeeTableModel.getName());
        contentValues.put("city", employeeTableModel.getCity());
        contentValues.put("call", employeeTableModel.getCall());
        contentValues.put("email", employeeTableModel.getEmail());
        contentValues.put("profileUrl", employeeTableModel.getProfileUrl());

        long insertResult = sqLiteDatabase.insert("EMPLOYEE", null, contentValues);

        if(insertResult == -1) return false;
        return true;
    }

    public List<EmployeeTableModel> getAllEmployee(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<EmployeeTableModel> employees = new ArrayList<>();

        String query = "SELECT * FROM EMPLOYEE";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do{
                int employeeId = cursor.getInt(0);
                String name = cursor.getString(1);
                String city = cursor.getString(2);
                String call = cursor.getString(3);
                String email = cursor.getString(4);
                String profileUrl = cursor.getString(5);

                EmployeeTableModel employee = new EmployeeTableModel(employeeId,name,city,call,email,profileUrl);
                employees.add(employee);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return employees;

    }

}
