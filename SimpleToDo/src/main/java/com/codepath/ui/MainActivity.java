package com.codepath.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import codepath.ui.R;

import com.codepath.models.DbHelper;
import com.codepath.models.Item;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private List<Item> items;
    private ArrayAdapter<Item> listAdapter;
    ListView lvItems;

    public static final String ITEMS_PREF = "com.codepath.com.simpletodo.ITEMS";
    public static final String APP_TAG = "com.codepath.com.simpletodo.TAG";
    public static final String TEXT = "TEXT";
    public static final String POS = "POS";
    public static final int REQUEST_CODE = 100;
    public static final int RESULT_OK = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        retrieveSet();
        listAdapter = new ArrayAdapter<Item>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(listAdapter);
        setupViewListener();

    }

    private void retrieveSet(){

        List<Item> itemList = DbHelper.getAll();
        if(itemList == null || itemList.size() == 0) {
            Log.e(APP_TAG,"Items are null!");
            items = new ArrayList<Item>();
        }
        else {
            items = new ArrayList<Item>();
            items.addAll(itemList);
        }

    }

    private void setupViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Delete item at this position

                // 1. get the item:
                Item i = items.get(position);

                // 2. remove from DB
                DbHelper.removeItem(i);

                // 3. remove from list
                items.remove(position);

                listAdapter.notifyDataSetChanged();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editItemIntent = new Intent(MainActivity.this,EditItemActivity.class);
                editItemIntent.putExtra(TEXT,items.get(position).toString());
                editItemIntent.putExtra(POS,position);
                startActivityForResult(editItemIntent,REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("EDITED");
            int code = data.getExtras().getInt("code", 0);
            int pos = data.getIntExtra(POS, -1);

            // Item has been edited
            Item editedItem = DbHelper.editItem(items.get(pos),name);

            listAdapter.remove(items.get(pos));
            listAdapter.insert(editedItem,pos);

            items.set(pos,editedItem);

            Log.d(APP_TAG,"Items: "+items.toString());

            listAdapter.setNotifyOnChange(Boolean.TRUE);
        }
    }

    public void onAddItem(View v){

        EditText etItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etItem.getText().toString();

        // Add an item
        Item newItem = DbHelper.addItem(itemText);

        // Add to adapter
        listAdapter.add(newItem);

        etItem.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
