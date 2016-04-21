package com.example.chaos.project;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add carts
     * @param cart
     */
    public void add(Cart cart) {
        db.beginTransaction();  //开始事务
        try {
            db.execSQL("INSERT INTO cart VALUES(null, ?, ?, ?)", new Object[]{cart.item_name, cart.count, cart.price});
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * update carts
     * @param cart
     */
    public void update(Cart cart) {
        ContentValues cv = new ContentValues();
        cv.put("count", cart.count);
        cv.put("price", cart.price);
        db.update("cart", cv, "item_name = ?", new String[]{cart.item_name});
    }


    /**
     * query all carts, return list
     * @return List<Cart>
     */
    public List<Cart> query() {
        ArrayList<Cart> carts = new ArrayList<Cart>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Cart cart = new Cart();
            cart._id = c.getInt(c.getColumnIndex("_id"));
            cart.item_name = c.getString(c.getColumnIndex("item_name"));
            cart.count = c.getInt(c.getColumnIndex("count"));
            cart.price = c.getInt(c.getColumnIndex("price"));
            carts.add(cart);
        }
        c.close();
        return carts;
    }

    /**
     * query all carts, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM cart", null);
        return c;
    }

    /**
     * query one cart, return cursor
     * @return  Cursor
     */
    public Cursor queryOneCursor(String name) {
        String[] str= new String[]{name};
        Cursor c = db.rawQuery("SELECT * FROM cart where item_name = ?", str);
        return c;
    }

    /**
     * delete carts
     * @param db
     */
    public void delete(SQLiteDatabase db) {
        helper.onDelete(db);
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}