package com.hfad.workout;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WorkoutDetailFragment extends Fragment{
    private long workoutId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

            if (savedInstanceState != null)
                workoutId = savedInstanceState.getLong("workoutId");
            else {//Глава 8. обновлять транзакцию только при первом создании,
                // чтобы не обнулялся секундомер в StopwatchFragment

                // Глава 8. Программное добавление фрагмента во фрейм родительского фрагмента
                // использовать диспетчер getChildFragmentManager()
                StopwatchFragment fragment = new StopwatchFragment();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.replace(R.id.stopwatch_container, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        return inflater.inflate(R.layout.fragment_workout_detail, container, false);

    }


    public void setWorkout(long workoutId) {
        this.workoutId = workoutId;
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = getView();
        if (view != null){
            TextView title = (TextView)view.findViewById(R.id.textTitle);
            Workout workout = Workout.workouts[(int)workoutId];
            title.setText(workout.getName());
            TextView description = (TextView)view.findViewById(R.id.textDescription);
            description.setText(workout.getDescription());
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("workoutId", workoutId);
    }

}