package com.hfad.starbuzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.content.ContentValues;


public class DrinkActivity extends Activity {

    public final static String EXTRA_DRINKNO = "drink_number";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        // загрузка данных напитка из БД в фоновом режиме
        new UploadInfoOfDrinkTask().execute(drinkNo);
      //  Drink drink = Drink.drinks[drinkNo];
      /*  try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();

            Cursor cursor = db.query("drink",
                    new String[]{"name", "description", "image_resource_id",
                    "favorite"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkNo)},
                    null, null, null);
            if (cursor.moveToFirst()){
                // получение данных напитка
                String nameText = cursor.getString(0);
                String descText = cursor.getString(1);
                Integer photoId = cursor.getInt(2);
                Boolean isFavorite = ( cursor.getInt(3) == 1 );
                // установка данных напитка на макет
                // добавление изображения
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                // добавление имени и описания кофе
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);
                TextView desc = (TextView)findViewById(R.id.description);
                desc.setText(descText);
                // добавить флажок любимого напитка
                CheckBox checkBox = (CheckBox)findViewById(R.id.favorite);
                checkBox.setChecked(isFavorite);
            }
            cursor.close();
            db.close();

        }catch(SQLiteException ex){

            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    }

    // Реакция клика на CheckBox (обновление поля Favorite в БД)
    public void onFavoriteClicked(View view){
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        // запускаем фоновый поток для обновления БД
        new UpdateDrinkTask().execute(drinkNo);
    }

    /**Класс для работы потока в фоновом режиме.
     * Делает обновление любимых напитков в БД*/
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean>{

        ContentValues drinkValues;
        // Метод выполняется до doInBackground
        // Метод вызывается в основном потоке событий, поэтому имеет доступ к интефейсу.
        // Информацию о любимом напитке берется из CheckBox
        protected void onPreExecute(){
            // узнаём установлен ли флаг
            CheckBox checkBox = (CheckBox)findViewById(R.id.favorite);
            Boolean favorite = checkBox.isChecked();
            // обновление favorite в БД
            drinkValues = new ContentValues();
            drinkValues.put("favorite", favorite);
        }
        //  Код задачи фонового потока
        protected Boolean doInBackground(Integer ... drinks){

            Integer drinkNo = drinks[0];
            // Загрузка БД и ее обновление через помощника
            try{
                SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update("drink", drinkValues,
                        "_id = ?",
                        new String[]{Integer.toString(drinkNo)});
                db.close();
                return true;
            }catch(SQLiteException ex){
                return false;
            }
        }
        // Метод выполняется после doInBackground и принимает его возвр-мое значение
        // Метод вызывается в основном потоке событий, поэтому имеет доступ к интефейсу.
        // Оповещение об ошибки чтения БД
        protected void onPostExecute(Boolean success){
            if (!success) {
                Toast toast = Toast.makeText(DrinkActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    /**Класс для работы потока в фоновом режиме.
     * Загружает требуемый напиток из БД и отображает его на макете*/
    private class UploadInfoOfDrinkTask extends AsyncTask<Integer, Void, Boolean> {
        // Данные напитка из БД
        String nameText;
        String descText;
        Integer photoId;
        Boolean isFavorite;

        protected Boolean doInBackground(Integer... drinks) {
            Integer drinkNo = drinks[0];
            try {
                SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
                SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();

                Cursor cursor = db.query("drink",
                        new String[]{"name", "description", "image_resource_id",
                                "favorite"},
                        "_id = ?",
                        new String[]{Integer.toString(drinkNo)},
                        null, null, null);
                if (cursor.moveToFirst()) {
                    // получение данных напитка
                    nameText = cursor.getString(0);
                    descText = cursor.getString(1);
                    photoId = cursor.getInt(2);
                    isFavorite = (cursor.getInt(3) == 1);
                }
                cursor.close();
                db.close();
                return true;
            } catch (SQLiteException ex) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success){
            if (success){
                // установка данных напитка на макет
                // добавление изображения
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                // добавление имени и описания кофе
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);
                TextView desc = (TextView)findViewById(R.id.description);
                desc.setText(descText);
                // добавить флажок любимого напитка
                CheckBox checkBox = (CheckBox)findViewById(R.id.favorite);
                checkBox.setChecked(isFavorite);
            }else{
                Toast toast = Toast.makeText(DrinkActivity.this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}
