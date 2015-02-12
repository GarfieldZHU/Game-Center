package com.example.jack.showcase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jack on 2014/10/7.
 */
public class AddItemDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Context ctx = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View rootView = inflater.inflate(R.layout.dialog_item_add, null, false);
        final EditText editText1 = (EditText) rootView.findViewById(R.id.editText1);
        final EditText editText2 = (EditText) rootView.findViewById(R.id.editText2);

        return new AlertDialog.Builder(ctx)
                .setTitle("Add Item")
                .setView(rootView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText1.setError(null);

                        String title = editText1.getText().toString();
                        String desc = editText2.getText().toString();
                        if (TextUtils.isEmpty(title)) {
                            editText1.setError(getString(R.string.error_field_required));
                            return;
                        }

                        try {
                            Data data = new Data(title, desc, null, null, null);

                            ContentValues values = new ContentValues(1);
                            values.put(DataProvider.COL_CONTENT, data.toString());
                            ctx.getContentResolver().insert(DataProvider.CONTENT_URI_DATA, values);
                        } catch (SQLException sqle) {}
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

}
