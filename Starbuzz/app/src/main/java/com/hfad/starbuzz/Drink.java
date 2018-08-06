package com.hfad.starbuzz;

/**
 * Created by ox on 21.06.18.
 */

public class Drink {
    private String name;
    private String description;
    private int imageResourcesId;
    // массив с элементами drinks
    public static final Drink[] drinks = {
            new Drink("Latte", "A couple of espresso shots with steamed milk",
                    R.drawable.latte),
            new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam",
                    R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh",
                    R.drawable.filter)
    };

    private Drink(String name, String description, int imageResourceId){
        this.name = name;
        this.description = description;
        this.imageResourcesId = imageResourceId;
    }

    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }
    public int getImageResourcesId(){
        return imageResourcesId;
    }
    // В качестве строкового предствления используется название напитка
    public String toString(){
        return name;
    }

}
