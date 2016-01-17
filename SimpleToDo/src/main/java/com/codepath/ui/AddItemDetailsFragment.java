package com.codepath.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import codepath.ui.R;


public class AddItemDetailsFragment extends DialogFragment{
    private TextView textAddedTextView;
    private Button addItemDetailsButton;
    private Button addDueDateButton;


    private String itemAdded;

    private static SimpleDateFormat dueDate;
    private static String dueDateString;



    public AddItemDetailsFragment() {
        // Required empty public constructor
    }

    public static AddItemDetailsFragment newInstance(String addedItem) {
        AddItemDetailsFragment fragment = new AddItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TEXT, addedItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        itemAdded = getArguments().getString(Constants.ADDED_FRAGMENT_ITEM_ARG);
        return inflater.inflate(R.layout.fragment_add_item_details, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        textAddedTextView = (TextView)view.findViewById(R.id.txtAdded);
        addItemDetailsButton = (Button)view.findViewById(R.id.addDetailsButton);
        addDueDateButton = (Button)view.findViewById(R.id.addDueDateButton);

        if(itemAdded!=null) {
            textAddedTextView.setText(itemAdded);
            textAddedTextView.setTextColor(Color.BLACK);
        }
        getDialog().setTitle("Add Item Details: ");

        final Spinner priorityDropdown = (Spinner)view.findViewById(R.id.itemPriorityAddSpinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.priority_array,
                        R.layout.spinner_item);
        staticAdapter
                .setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        priorityDropdown.setAdapter(staticAdapter);

        addItemDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.APP_TAG,"Priority: "+ String.valueOf(priorityDropdown.getSelectedItem()));
                OnItemDetailsAddedListener listener = (OnItemDetailsAddedListener) getActivity();
                listener.onItemDetailsAdded(String.valueOf(priorityDropdown.getSelectedItem()),itemAdded,dueDateString);
                dismiss();
            }
        });

        addDueDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
    }

    // Container Activity must implement this interface
    public interface OnItemDetailsAddedListener {
        void onItemDetailsAdded(String priority, String itemText,String dueDateString);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            updateDisplay(year,month,day);
        }
    }

    public static void updateDisplay(int year,int month, int day) {

        GregorianCalendar cal = new GregorianCalendar(year, month, day);
        cal.set(year,month,day);

        dueDate = new SimpleDateFormat("dd MMMM yyyy");

        dueDateString = dueDate.format(cal.getTime());

        Log.d(Constants.APP_TAG,"Due Date: "+ dueDate);


    }// updateDisplay

}
