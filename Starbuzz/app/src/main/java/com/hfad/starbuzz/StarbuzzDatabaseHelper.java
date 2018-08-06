package com.hfad.starbuzz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;

/**
 * Created by ox on 19.07.18.
 */

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;

    StarbuzzDatabaseHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);

    }
    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId){
        ContentValues drinkValue = new ContentValues();
        drinkValue.put("name", name);
        drinkValue.put("description", description);
        drinkValue.put("image_resource_id", resourceId);

        db.insert("drink", null, drinkValue);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){

        if (oldVersion < 1){
            db.execSQL("create table drink (_id integer primary key autoincrement," +
                    "name text," +
                    "description text," +
                    "image_resource_id integer);");
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);

        }
        if (oldVersion < 2){
            // Если у пользователя уже установлена версия 1
            // Код добавления нового столбца
            db.execSQL("alter table drink add column favorite numeric;");
        }
    }
}
