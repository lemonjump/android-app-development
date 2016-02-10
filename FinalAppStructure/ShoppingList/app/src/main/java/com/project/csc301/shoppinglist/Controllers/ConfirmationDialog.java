package com.project.csc301.shoppinglist.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.csc301.shoppinglist.Controllers.IntraComponentListeners.*;

public class ConfirmationDialog extends DialogFragment {

    ConfirmationDialogListener listener;
    ConfirmationType current_type;

    private int title, message, ok, cancel;

    public void setArgs(int title, int message, int ok, int cancel, ConfirmationType type){
        this.title = title;
        this.message = message;
        this.ok = ok;
        this.cancel = cancel;
        this.current_type = type;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ConfirmationDialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(this.title)
                .setMessage(this.message)
                .setPositiveButton(this.ok, null)
                .setNegativeButton(this.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmationDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirmation(current_type);
            }
        });
    }

}
