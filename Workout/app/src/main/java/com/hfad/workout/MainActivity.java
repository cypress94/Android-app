package com.hfad.workout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity
        implements WorkoutListFragment.WorkoutListListener{

    private long workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void itemClicked(long id){
        /* Проверка на версию устройства. Если в макете есть FrameLayout кот. содержит
        * фрагмент, то это версия планшета*/
        View frame = findViewById(R.id.frag_container);

        if (frame != null) {
            /*для планшета. */
            // 1 - Создание транзакции фрагмента
            WorkoutDetailFragment detail = new WorkoutDetailFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            workoutId = id;
            detail.setWorkout(id);
            // 2 - Указываем действия в транзакции
            ft.replace(R.id.frag_container, detail);
            // 3 - Определение анимации перехода
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            // 4 - Поместить транзакцию в стек
            ft.addToBackStack(null);
            // 5 - Закрепить изменения в активности
            ft.commit();
        }else{
            /*для телефона*/
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int)id);
            startActivity(intent);
        }
    }


}
