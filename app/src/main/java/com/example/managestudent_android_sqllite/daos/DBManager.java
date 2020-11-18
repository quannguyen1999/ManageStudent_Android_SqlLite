package com.example.managestudent_android_sqllite.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.proto.ProtoOutputStream;

import androidx.annotation.Nullable;

import com.example.managestudent_android_sqllite.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="manageSomething";

    private static final String TABLE_NAME ="product";
    private static final String MA ="ma";
    private static final String NAMEPRODUCT ="nameProduct";
    private static final String SOLUONG = "SOLUONG";

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME,null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                MA+" String primary key, "+
                NAMEPRODUCT + " TEXT, "+
                SOLUONG + " TEXT )";
        db.execSQL(sqlQuery);
    }

    public void addProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MA, product.getMa());
        values.put(NAMEPRODUCT, product.getNameProduct());
        values.put(SOLUONG, product.getSoLuong());
        //Neu de null thi khi value bang null thi loi

        db.insert(TABLE_NAME,null,values);

        db.close();
    }

    public List<Product> getAllProduct() {
        List<Product> listStudent = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();

                product.setMa(cursor.getString(0));
                product.setNameProduct(cursor.getString(1));
                product.setSoLuong(cursor.getInt(2));

                listStudent.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listStudent;
    }

    public Product findById(String ma){
        Product listStudent = new Product();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +" where MA='"+ma+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();

                product.setMa(cursor.getString(0));
                product.setNameProduct(cursor.getString(1));
                product.setSoLuong(cursor.getInt(2));

                listStudent = product;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listStudent;
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, MA + " = ?",
                new String[] { String.valueOf(product.getMa()) });
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int updateProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAMEPRODUCT,product.getNameProduct());

        values.put(SOLUONG,product.getSoLuong());

        return db.update(TABLE_NAME,values,MA +"=?",new String[] { String.valueOf(product.getMa())});


    }
}
