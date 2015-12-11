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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private List<String> items;
    private ArrayAdapter<String> listAdapter;
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
        listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(listAdapter);
        setupViewListener();



    }

    private void retrieveSet(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        String values = sharedPref.getString(ITEMS_PREF, null);
        if(values == null) {
            Log.e(APP_TAG,"Items are null!");
            items = new ArrayList<String>();
        }
        else {
            items = new ArrayList<String>();
            items.addAll((ArrayList<String>)gson.fromJson(values,new TypeToken<List<String>>(){}.getType()));
        }

    }

    private void saveList(){
        //TODO: dont save as set
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        String values = gson.toJson(items);

        editor.putString(ITEMS_PREF,values);

        editor.commit();
    }

    private void setupViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                listAdapter.notifyDataSetChanged();
                saveList();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editItemIntent = new Intent(MainActivity.this,EditItemActivity.class);
                editItemIntent.putExtra(TEXT,items.get(position));
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
            listAdapter.remove(items.get(pos));
            listAdapter.insert(name,pos);
            items.set(pos,name);
            Log.d(APP_TAG,"Items: "+items.toString());
            listAdapter.setNotifyOnChange(Boolean.TRUE);
            saveList();
        }
    }

    public void onAddItem(View v){
        EditText etItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etItem.getText().toString();
        listAdapter.add(itemText);
        etItem.setText("");
        saveList();
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
