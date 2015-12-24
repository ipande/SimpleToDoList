package com.codepath.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import codepath.ui.R;
import com.codepath.utils.Constants;

/**
 * A fragment to support editing of items
 */
public class EditNameDialog extends DialogFragment
        implements TextView.OnEditorActionListener {
    private EditText mEditText;

    private int position;

    public EditNameDialog() {
        // Required empty public constructor
    }

    public static EditNameDialog newInstance(String editedText, int position) {
        EditNameDialog fragment = new EditNameDialog();
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
        return inflater.inflate(R.layout.fragment_edit_name_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString(Constants.TEXT, null);
        position = getArguments().getInt(Constants.POS);
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
