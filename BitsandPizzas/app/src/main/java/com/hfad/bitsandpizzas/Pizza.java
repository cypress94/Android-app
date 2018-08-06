package com.hfad.bitsandpizzas;

/**
 * Created by ox on 30.07.18.
 */

public class Pizza {
    private String name;
    private int imageResourceId;

    public static final Pizza[] pizzas = {
            new Pizza("Diavolo", R.mipmap.diavolo),
            new Pizza("Funghi", R.mipmap.funghi)
    };

    private Pizza(String name, int imageResourceId){
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName(){
        return name;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }
}
