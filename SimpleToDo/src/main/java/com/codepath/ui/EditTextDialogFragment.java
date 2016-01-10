package com.codepath.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import codepath.ui.R;
import com.codepath.utils.Constants;

/**
 * A fragment to support editing of items
 */
//TODO: make the textview visible onAddItem :)
public class EditTextDialogFragment extends DialogFragment
        implements TextView.OnEditorActionListener {
    private EditText mEditText;
    private TextView textAddedTextView;

    private int position;
    private String fragmentTitle;

    private String itemAdded;

    public EditTextDialogFragment() {
        // Required empty public constructor
    }

    public static EditTextDialogFragment newInstance(String editedText, int position) {
        EditTextDialogFragment fragment = new EditTextDialogFragment();
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
        fragmentTitle = getArguments().getString(Constants.EDIT_FRAGMENT_ARG);
        itemAdded = getArguments().getString(Constants.EDIT_FRAGMENT_ITEM_ARG);
        return inflater.inflate(R.layout.fragment_edit_name_dialog, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);

        textAddedTextView = (TextView)view.findViewById(R.id.txtAdded);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString(Constants.TEXT, null);
        position = getArguments().getInt(Constants.POS);
        if(fragmentTitle!=null && itemAdded!=null) {
            getDialog().setTitle(fragmentTitle);
            mEditText.setVisibility(View.INVISIBLE);
            textAddedTextView.setVisibility(View.VISIBLE);
            textAddedTextView.setText(itemAdded);
        }
        else {
            getDialog().setTitle("Edit item below: ");
            mEditText.setText(title);
            mEditText.setVisibility(View.VISIBLE);
            textAddedTextView.setVisibility(View.INVISIBLE);
        }

        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        mEditText.setFocusable(Boolean.TRUE);
        mEditText.setSelection(mEditText.getText().length());
        mEditText.setOnEditorActionListener(this);

        Spinner priorityDropdown = (Spinner)view.findViewById(R.id.itemPriorityEditSpinner);

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

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString(), position);
            dismiss();
            return true;
        }
        return false;
    }


    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText, int position);
    }
}
