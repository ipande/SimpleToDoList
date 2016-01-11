package com.codepath.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import codepath.ui.R;
import com.codepath.models.DbHelper;
import com.codepath.models.Item;
import com.codepath.utils.Constants;
import com.codepath.utils.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the lifecycle of the app
 */
public class MainActivity extends AppCompatActivity
        implements EditTextDialogFragment.EditNameDialogListener,
        AddItemDetailsFragment.OnItemDetailsAddedListener {

    ListView lvItems;
    private List<Item> items;
    //private ArrayAdapter<Item> listAdapter;
    private ItemAdapter listAdapter;

    private String itemPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);

        retrieveItems();
        //listAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, items);
        listAdapter = new ItemAdapter(getApplicationContext(),items);
        lvItems.setAdapter(listAdapter);
        setupViewListener();

    }

    private void retrieveItems() {

        List<Item> itemList = DbHelper.getAll();
        if (itemList == null || itemList.size() == 0) {
            Log.e(Constants.APP_TAG, "Items are null!");
            items = new ArrayList<Item>();
        } else {
            items = new ArrayList<Item>();
            items.addAll(itemList);
        }

    }


    private void setupViewListener() {

        //TODO - These should ideally be separate methods
        // instead of anonymous inner method implementations
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
                    showEditDialog(items.get(position).toString(), position);

            }
        });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Correct item has been edited
//        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE) {
//            String name = data.getExtras().getString("EDITED");
//            int code = data.getExtras().getInt("code", 0);
//            int pos = data.getIntExtra(Constants.POS, -1);
//
//            // Item has been edited
//            Item editedItem = DbHelper.editItem(items.get(pos), name);
//
//            listAdapter.remove(items.get(pos));
//            listAdapter.insert(editedItem, pos);
//
//            items.set(pos, editedItem);
//
//            Log.d(Constants.APP_TAG, "Items: " + items.toString());
//
//            listAdapter.setNotifyOnChange(Boolean.TRUE);
//        }
//    }

    public void onAddItem(View v) {

        EditText etItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etItem.getText().toString();

        showItemDetailsDialog(itemText);

        etItem.setText(Constants.BLANK);
    }

    private void showItemDetailsDialog(String itemAdded){
        FragmentManager fm = getSupportFragmentManager();
        AddItemDetailsFragment itemDetailsFragment = AddItemDetailsFragment.newInstance(itemAdded);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ADDED_FRAGMENT_ITEM_ARG, itemAdded);
        itemDetailsFragment.setArguments(bundle);
        itemDetailsFragment.show(fm,Constants.ADD_DETAILS_FRAGMENT_NAME);
    }


    private void showEditDialog(String itemToEdit, int position) {
        FragmentManager fm = getSupportFragmentManager();
        EditTextDialogFragment editNameDialog = EditTextDialogFragment.newInstance(itemToEdit, position);
        editNameDialog.show(fm, Constants.EDIT_FRAGMENT_NAME);
    }

    @Override
    public void onFinishEditDialog(String inputText, int position, String itemPriority) {
        // Correct item has been edited
        Item editedItem = DbHelper.editItem(items.get(position), inputText,itemPriority);

        listAdapter.remove(items.get(position));
        listAdapter.insert(editedItem, position);

        items.set(position, editedItem);
        Log.d(Constants.APP_TAG, "Items: " + items.toString());
        listAdapter.setNotifyOnChange(Boolean.TRUE);
    }

    @Override
    public void onItemDetailsAdded(String priority, String itemText) {
        Log.d(Constants.APP_TAG,"Priority in Activity: "+priority);
        itemPriority = priority;
        // Add an item
        Item newItem = DbHelper.addItem(itemText,itemPriority);

        // Add to adapter
        listAdapter.add(newItem);

    }
}
