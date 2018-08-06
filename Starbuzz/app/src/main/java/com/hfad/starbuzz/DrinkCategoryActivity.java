package com.hfad.starbuzz;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.widget.Toast;


public class DrinkCategoryActivity extends ListActivity {

    private Cursor cursor;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try {
            // загрузка на чтение БД через помошника
            SQLiteOpenHelper starbuzzDatabase = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabase.getReadableDatabase();
            // Создание адаптера курсора
            cursor = db.query("drink",
                    new String[]{"_id", "name"},
                    null, null, null, null, null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"name"},
                    new int[]{android.R.id.text1},
                    0);

        /* Когда не было БД
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(
                this,
                android.R.layout.simple_expandable_list_item_1,
                Drink.drinks
        );*/

            ListView listView = getListView();
            listView.setAdapter(listAdapter);
        }catch(SQLiteException ex){

            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
        startActivity(intent);
    }

   
}
