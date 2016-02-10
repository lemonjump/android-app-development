package com.project.csc301.shoppinglist.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.csc301.shoppinglist.Controllers.IntraComponentListeners.SavePriceDialogListener;
import com.project.csc301.shoppinglist.Helpers.GlobalFunctions;
import com.project.csc301.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class SavePriceDialog extends DialogFragment implements TextWatcher {

    SavePriceDialogListener listener;
    boolean error;

    /*Elements*/
    Button okButton;
    EditText price_exact, price_change;
    AutoCompleteTextView store_name;
    Spinner units;
    TextView textstatus;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (SavePriceDialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.save_price_layout, null))
                .setTitle(R.string.savepricetitle)
                .setPositiveButton(R.string.savepriceok, null)
                .setIcon(R.drawable.check)
                .setNegativeButton(R.string.savepricecancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SavePriceDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog) getDialog();

        okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOkButtonAction();
            }
        });

        store_name = (AutoCompleteTextView) alertDialog.findViewById(R.id.storename);
        store_name.addTextChangedListener(this);

        List<String> stores = new ArrayList<>();
        try{
            stores.addAll(listener.getListOfAllStores());
        }catch (Exception e){
            GlobalFunctions.showInformativeDialog(this.getActivity(), "Error", e.getMessage());
        }
        ArrayAdapter<String> a = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                stores);
        store_name.setAdapter(a);

        price_exact = (EditText) alertDialog.findViewById(R.id.price_exact);
        price_exact.addTextChangedListener(this);

        price_change = (EditText) alertDialog.findViewById(R.id.price_change);
        price_change.addTextChangedListener(this);

        units = (Spinner) alertDialog.findViewById(R.id.unittype);
        units.setAdapter(new ArrayAdapter<>(this.getActivity(),
                R.layout.unit_type_layout, GlobalFunctions.arraySpinnerS));

        //Error messages
        textstatus = (TextView) alertDialog.findViewById(R.id.textstatus);
        error = false;
    }

    private void performOkButtonAction() {
        String prod_text = store_name.getText().toString().trim();
        String price_exact_text = price_exact.getText().toString().trim();
        String price_change_text = price_change.getText().toString().trim();
        String unit_type = units.getSelectedItem().toString();
        //Assume there is an error unless everything is valid.
        error = true;
        if ("".equals(prod_text)
                || "".equals(price_exact_text)) {
            textstatus.setText(R.string.invalidproddescr);
        } else {
            error = false;
            if("".equals(price_change_text))
                price_change_text = "00";
            listener.onSavePricePositiveClick(prod_text,
                    price_exact_text+"."+price_change_text,
                    unit_type);
        }
    }

    /*Text change listeners*/
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (error)
            error = false;
        this.textstatus.setText("");
    }
}