package com.codepath.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.sql.Date;
import java.text.SimpleDateFormat;


/**
 * a class to model a ToDo item in the list view
 * using ActiveAndroid as the ORM abstraction above SQLite
 */
@Table(name = "Items")
public class Item extends Model {
    @Column(name = "Name")
    public String text;

    @Column(name = "Priority")
    public String priority;

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Column(name = "DueDate")
    public String dueDate;

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

    public Item(String text, String priority, String dueDate) {
        super();
        this.text = text;
        this.priority = priority;
        this.dueDate = dueDate;
    }


    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

}
