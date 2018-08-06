package com.hfad.workout;

import android.app.ListFragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class WorkoutListFragment extends ListFragment {
    static interface WorkoutListListener{
        void itemClicked(long id);
    };
    WorkoutListListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        String[] names = new String[Workout.workouts.length];
        for (int i=0; i < Workout.workouts.length; i++){
            names[i] = Workout.workouts[i].getName();
        }

        ArrayAdapter<String> listArray = new ArrayAdapter<String>(inflater.getContext(),
                android.R.layout.simple_list_item_1, names);
        setListAdapter(listArray);

        //  предоставляет макет по умолчанию
        return super.onCreateView(inflater, container, savedInstanceState);
    }
// вызывается при присоединении фрагмента к активности
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener = (WorkoutListListener) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener !=  null)
            listener.itemClicked(id);
    }
}
