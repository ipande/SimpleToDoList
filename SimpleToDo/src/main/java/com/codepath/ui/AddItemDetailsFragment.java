package com.codepath.ui;


import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.utils.Constants;

import codepath.ui.R;


public class AddItemDetailsFragment extends DialogFragment{
    private TextView textAddedTextView;

    private int position;
    private String fragmentTitle;

    private String itemAdded;


    public AddItemDetailsFragment() {
        // Required empty public constructor
    }

    public static AddItemDetailsFragment newInstance(String editedText, int position) {
        AddItemDetailsFragment fragment = new AddItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TEXT, editedText);
        args.putInt(Constants.POS, position);
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
        itemAdded = getArguments().getString(Constants.EDIT_FRAGMENT_ITEM_ARG);
        return inflater.inflate(R.layout.fragment_add_item_details, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        textAddedTextView = (TextView)view.findViewById(R.id.txtAdded);
        position = getArguments().getInt(Constants.POS);

        if(itemAdded!=null) {
            textAddedTextView.setText(itemAdded);
            textAddedTextView.setTextColor(Color.BLACK);
        }
        getDialog().setTitle("Add Item Details: ");

        Spinner priorityDropdown = (Spinner)view.findViewById(R.id.itemPriorityAddSpinner);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.priority_array,
                        R.layout.spinner_item);
        staticAdapter
                .setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        priorityDropdown.setAdapter(staticAdapter);

    }


}
