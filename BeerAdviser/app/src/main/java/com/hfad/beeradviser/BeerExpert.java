package com.hfad.beeradviser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ox on 04.06.18.
 */

public class BeerExpert{

    public List<String> getBrands(String color){
        List<String> result = new ArrayList<String>();
        if (color.equals("amber")){
            result.add("Jack Amber");
            result.add("Red Moose");
        }else{
            result.add("Jail Pale Ale");
            result.add("Gout Stout");
        }
        return result;
    }
}
