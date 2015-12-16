package com.codepath.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.*;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import codepath.ui.R;


public class EditNameDialog extends DialogFragment implements TextView.OnEditorActionListener {
    private EditText mEditText;

    private int  position;

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText, int position);
    }



    public static EditNameDialog newInstance(String editedText, int position) {
        EditNameDialog fragment = new EditNameDialog();
        Bundle args = new Bundle();
        args.putString(MainActivity.TEXT, editedText);
        args.putInt(MainActivity.POS,position);
        fragment.setArguments(args);
        return fragment;
    }

    public EditNameDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//        mEditText.setOnEditorActionListener(this);
        return inflater.inflate(R.layout.fragment_edit_name_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString(MainActivity.TEXT, null);
        position = getArguments().getInt(MainActivity.POS);
        getDialog().setTitle("Edit item below: ");
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        mEditText.setText(title);
        mEditText.setFocusable(Boolean.TRUE);
        mEditText.setSelection(mEditText.getText().length());
        mEditText.setOnEditorActionListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);



    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString(),position);
            dismiss();
            return true;
        }
        return false;
    }
}
