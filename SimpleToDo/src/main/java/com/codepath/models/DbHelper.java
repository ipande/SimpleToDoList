package com.codepath.models;

import android.util.Log;
import com.activeandroid.query.Select;
import com.codepath.utils.Constants;

import java.util.List;

/**
 * A class that supports add, delete and edit operations
 */
public class DbHelper {

    public static List<Item> getAll() {
        return new Select()
                .from(Item.class)
                .orderBy("Id ASC")
                .execute();
    }

    public static Item addItem(String text,String priority) {
        Item i = new Item(text,priority);
        i.save();
        return i;
    }

    public static void removeItem(Item toRemoveItem) {
        Log.d(Constants.APP_TAG, "removing item: "
                + toRemoveItem.text + " ID: " + toRemoveItem.getId());
        Item i = Item.load(Item.class, toRemoveItem.getId());
        i.delete();
    }

    public static Item editItem(Item itemToEdit, String text,String priority) {
        Log.d(Constants.APP_TAG, "editing item: "
                + itemToEdit.text + " ID: " + itemToEdit.getId());
        Item i = Item.load(Item.class, itemToEdit.getId());
        i.setText(text);
        i.setPriority(priority);
        i.save();
        return i;
    }
}
