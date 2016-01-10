package com.codepath.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.sql.Date;


/**
 * a class to model a ToDo item in the list view
 * using ActiveAndroid as the ORM abstraction above SQLite
 */
@Table(name = "Items")
public class Item extends Model {
    @Column(name = "Name")
    public String text;

    //TODO: this should be an enum
    public String priority;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Make sure to have a default constructor for every ActiveAndroid model
    public Item() {
        super();
    }

    public Item(String text) {
        super();
        this.text = text;
    }


    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
