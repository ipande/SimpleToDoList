package com.codepath.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


/**
 * a class to model a ToDo item in the list view
 * using ActiveAndroid as the ORM abstraction above SQLite
 */
@Table(name = "Items")
public class Item extends Model {
    @Column(name = "Name")
    public String name;

    // Make sure to have a default constructor for every ActiveAndroid model
    public Item() {
        super();
    }

    public Item(String text) {
        super();

        this.name = text;

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
