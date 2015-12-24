package com.codepath.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import codepath.ui.R;
import com.codepath.utils.Constants;

/**
 * Activity for editing items -
 * Not used any more because we're using the dialog fragment
 */
public class EditItemActivity extends Activity {
    private EditText editText;
    private Button mSaveButton;
    private int itemPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editText = (EditText) findViewById(R.id.editText);
        mSaveButton = (Button) findViewById(R.id.saveButton);

        setupSaveListener();

        Intent i = getIntent();
        if (i != null) {
            String itemText = i.getStringExtra(Constants.TEXT);
            itemPos = i.getIntExtra(Constants.POS, -1);
            if (itemText != null) {
                editText.setText(itemText);
                editText.setCursorVisible(Boolean.TRUE);
                editText.setFocusable(Boolean.TRUE);
                editText.setSelection(editText.getText().length());
            }
        }
    }

    private void setupSaveListener() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent data = new Intent();
                data.putExtra("EDITED", editText.getText().toString());
                data.putExtra("code", Constants.REQUEST_CODE);
                data.putExtra(Constants.POS, itemPos);
                setResult(MainActivity.RESULT_OK, data);
                finish();
            }
        });
    }

}
