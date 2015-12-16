package com.codepath.models;

import android.util.Log;

import com.activeandroid.query.Select;
import com.codepath.ui.MainActivity;

import java.util.List;

public class DbHelper {

    public static List<Item> getAll(){
        // This is how you execute a query
        return new Select()
                .from(Item.class)
                .orderBy("Id ASC")
                .execute();
    }

    public static Item addItem(String text){
        Item i = new Item(text);
        i.save();
        return i;
    }

    public static void removeItem(Item toRemoveItem){
        Log.d(MainActivity.APP_TAG,"removing item: "+toRemoveItem.name + " ID: "+toRemoveItem.getId());
        Item i = Item.load(Item.class,toRemoveItem.getId());
        i.delete();
    }

    public static Item editItem(Item itemToEdit,String text){
        Log.d(MainActivity.APP_TAG,"editing item: "+itemToEdit.name + " ID: "+itemToEdit.getId());
        Item i = Item.load(Item.class,itemToEdit.getId());
        i.setName(text);
        i.save();
        return i;
    }
}
