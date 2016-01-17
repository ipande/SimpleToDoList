package com.codepath.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import codepath.ui.R;
import com.codepath.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A fragment to support editing of items
 */
public class EditTextDialogFragment extends DialogFragment
        implements TextView.OnEditorActionListener {
    private EditText mEditText;

    private TextView editDueDateTextView;
    private Spinner priorityDropdown;

    private Button submitEditButton;



    private String editedText = null;
    private int position;

    private static SimpleDateFormat dueDate;
    private static String dueDateString;



    private String itemAdded;
    private String editDueDate;

    public EditTextDialogFragment() {
        // Required empty public constructor
    }

    public static EditTextDialogFragment newInstance(String editedText, int position, String dueDate) {
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TEXT, editedText);
        args.putString(Constants.DUE_DATE,dueDate);
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
        itemAdded = getArguments().getString(Constants.TEXT);
        editDueDate =  getArguments().getString(Constants.DUE_DATE);
        return inflater.inflate(R.layout.fragment_edit_name_dialog, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);

        submitEditButton = (Button) view.findViewById(R.id.buttonDoneEdit);
        editDueDateTextView = (TextView)view.findViewById(R.id.editDueDateText);

        position = getArguments().getInt(Constants.POS);
        if(itemAdded!=null && editDueDate!=null) {
            getDialog().setTitle("Edit item below: ");
            mEditText.setVisibility(View.VISIBLE);
            mEditText.setText(itemAdded);
            // Show soft keyboard automatically and request focus to field
            mEditText.requestFocus();
            mEditText.setFocusable(Boolean.TRUE);
            mEditText.setSelection(mEditText.getText().length());


            mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (EditorInfo.IME_ACTION_DONE == actionId){
                        editedText = mEditText.getText().toString();
                        return true;
                    }
                        return false;
                }
            });

            editDueDateTextView.setText(editDueDate);
            editDueDateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(), "DatePicker");
                }
            });

            priorityDropdown = (Spinner)view.findViewById(R.id.itemPriorityEditSpinner);

            // Create an ArrayAdapter using the string array and a default spinner
            ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                    .createFromResource(getContext(), R.array.priority_array,
                            android.R.layout.simple_spinner_item);
            staticAdapter
                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            priorityDropdown.setAdapter(staticAdapter);

            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            submitEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Return input text to activity
                    EditNameDialogListener listener = (EditNameDialogListener) getActivity();

                    listener.onFinishEditDialog(mEditText.getText().toString(), position,
                            String.valueOf(priorityDropdown.getSelectedItem()),dueDateString);
                    dismiss();
                }
            });

        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            switch (v.getId()) {
                case R.id.txt_your_name:
                    // Return input text to activity
                    EditNameDialogListener listener = (EditNameDialogListener) getActivity();

                    listener.onFinishEditDialog(mEditText.getText().toString(), position,
                            String.valueOf(priorityDropdown.getSelectedItem()), dueDateString);
                    dismiss();
                    return true;
                case R.id.editDueDateText:
                    Log.d(Constants.APP_TAG, "Due date picker clicked!");
            }
        }
        return false;
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

        public static void updateDisplay(int year,int month, int day) {

            GregorianCalendar cal = new GregorianCalendar(year, month, day);
            cal.set(year,month,day);

            dueDate = new SimpleDateFormat("dd MMMM yyyy");

            dueDateString = dueDate.format(cal.getTime());

            Log.d(Constants.APP_TAG,"Due Date: "+ dueDate);


        }// updateDisplay
    }



    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText, int position, String itemPriority, String dueDateString);
    }
}
