package com.hfad.beeradviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.List;


public class FindBeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
    }

    public void onClickFindBeer(View view){
        TextView brands = (TextView) findViewById(R.id.brands);
        Spinner color = (Spinner)findViewById(R.id.color);


        String beerType = String.valueOf(color.getSelectedItem());

        BeerExpert expert = new BeerExpert();
        List<String> brands_list = expert.getBrands(beerType);
        StringBuilder brands_string = new StringBuilder();

        for(String name : brands_list){
            brands_string.append(name).append("\n");
        }

        brands.setText(brands_string);
    }
}

