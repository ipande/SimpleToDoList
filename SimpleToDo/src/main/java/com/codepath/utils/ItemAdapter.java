package com.codepath.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.models.Item;

import java.util.ArrayList;
import java.util.List;

import codepath.ui.R;

public class ItemAdapter extends ArrayAdapter<Item>{



    public ItemAdapter(Context context, List<Item> items){
        super(context,0,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.priority_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.itemText);

        // Populate the data into the template view using the data object
        tvName.setText(item.text);

        tvName.setTextColor(Color.BLACK);

        TextView itemPriorityView = (TextView) convertView.findViewById(R.id.itemPriorityText);

        Log.d(Constants.APP_TAG, "pri in adapter " + item.getPriority());

        itemPriorityView.setText(item.getPriority());
        if(item.getPriority()!=null) {
            if (item.getPriority().equalsIgnoreCase("low")) {
                itemPriorityView.setTextColor(Color.parseColor("#FFF06D2F"));
            } else if (item.getPriority().equalsIgnoreCase("high")) {
                itemPriorityView.setTextColor(Color.RED);
            } else
                itemPriorityView.setTextColor(Color.BLUE);
        }

        TextView dueDateTv = (TextView) convertView.findViewById(R.id.itemDueDateTextView);
        if(item.getDueDate()!=null) {
            String date = item.getDueDate();
            dueDateTv.setText(date);
        }

        // Return the completed view to render on screen
        return convertView;
    }

}
