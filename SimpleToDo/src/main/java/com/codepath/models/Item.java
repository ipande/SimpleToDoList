package com.codepath.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;



@Table(name = "Items")
public class Item extends Model{
    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Name")
    public String name;

    // Make sure to have a default constructor for every ActiveAndroid model

    public Item(){
        super();
    }
    public Item(String text) {
        super();

        this.name = text;

    }

    @Override
    public String toString() {
        return this.name;
    }

}
