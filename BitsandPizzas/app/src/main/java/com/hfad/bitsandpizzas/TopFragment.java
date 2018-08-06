package com.hfad.bitsandpizzas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_top, container, false);
        RecyclerView recyclerView = (RecyclerView) relativeLayout.findViewById(R.id.pizza_recycler);

        String[] pizzaNames = new String[Pizza.pizzas.length];
        int[] pizzaImages = new int[Pizza.pizzas.length];
        for (int i=0; i < pizzaNames.length; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName();
            pizzaImages[i] = Pizza.pizzas[i].getImageResourceId();
        }
        // установки тип отображение элементов в RecycleView в виде таблицы
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImages);
        recyclerView.setAdapter(adapter);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZANO, position);
                getActivity().startActivity(intent);
            }
        });

        return relativeLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
