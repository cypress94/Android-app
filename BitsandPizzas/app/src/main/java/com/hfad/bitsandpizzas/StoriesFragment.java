package com.hfad.bitsandpizzas;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by ox on 18.07.18.
 */

public class StoriesFragment  extends ListFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.stories));
        setListAdapter(arrayAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
