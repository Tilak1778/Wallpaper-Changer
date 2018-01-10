package com.example.tilakrajchordiya.databasexa;

import java.lang.ref.SoftReference;

/**
 * Created by Tilak Raj Chordiya on 21/12/2017.
 */

public class DataProvider{
    private int id;
    private String name;

    public DataProvider(int id,String name){
        this.id=id;
        this.name=name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
