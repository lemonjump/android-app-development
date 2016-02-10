package com.project.csc301.shoppinglist.Controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.csc301.shoppinglist.Helpers.GlobalFunctions;
import com.project.csc301.shoppinglist.R;
import com.project.csc301.shoppinglist.Controllers.IntraComponentListeners.*;

import java.util.ArrayList;
import java.util.List;

public class AddNewItemDialog extends DialogFragment implements TextWatcher {

    AddItemDialogListener listener;
    boolean error;

    /*Elements*/
    Button okButton;
    Spinner units;
    CheckBox addquantity;
    EditText quantityfield;
    AutoCompleteTextView prodtextfield;
    TextView textstatus;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (AddItemDialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_dialog_layout, null))
                .setTitle(R.string.addmessage)
                .setIcon(R.drawable.add)
                .setPositiveButton(R.string.add, null)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddNewItemDialog.this.getDialog().cancel();
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

        addquantity = (CheckBox) alertDialog.findViewById(R.id.addquantitycheck);
        addquantity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                addquantity.setChecked(addquantity.isChecked());
                int visibility = addquantity.isChecked() ? View.VISIBLE : View.GONE;
                quantityfield.setVisibility(visibility);
                units.setVisibility(visibility);
                afterTextChanged(null);
            }
        });

        prodtextfield = (AutoCompleteTextView) alertDialog.findViewById(R.id.productname);
        prodtextfield.addTextChangedListener(this);

        List<String> products = new ArrayList<>();
        try{
            products.addAll(listener.getListOfAllProducts());
        }catch (Exception e){
            GlobalFunctions.showInformativeDialog(this.getActivity(), "Error", e.getMessage());
        }
        ArrayAdapter<String> a = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                products);
        prodtextfield.setAdapter(a);

        quantityfield = (EditText) alertDialog.findViewById(R.id.amounttext);
        quantityfield.addTextChangedListener(this);

        units = (Spinner) alertDialog.findViewById(R.id.unittype);

        units.setAdapter(new ArrayAdapter<>(this.getActivity(),
                R.layout.unit_type_layout, GlobalFunctions.arraySpinnerP));

        //Error messages
        textstatus = (TextView) alertDialog.findViewById(R.id.textstatus);

        //Hide amount fields by default.
        quantityfield.setVisibility(View.GONE);
        units.setVisibility(View.GONE);
        error = false;
    }

    private void performOkButtonAction() {
        String prod_text = prodtextfield.getText().toString().trim();
        String amount = quantityfield.getText().toString().trim();
        String unit = units.getSelectedItem().toString();

        boolean includeQuantity = addquantity.isChecked();

        //Assume there is an error unless everything is valid.
        error = true;
        if ("".equals(prod_text) || (!isValidNum(amount) && includeQuantity)) {
            textstatus.setText(R.string.invalidproddescr);
        } else if (foundInData()) {
            textstatus.setText(R.string.prodinlist);
        } else {
            error = false;
            if (!includeQuantity) {
                amount = null;
                unit = null;
            }
            listener.onAddDialogPositiveClick(prod_text, amount, unit);
        }
    }

    private boolean isValidNum(String amount) {
        try {
            Integer.parseInt(amount);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean foundInData() {
        String prod_text = prodtextfield.getText().toString().trim();
        prod_text = GlobalFunctions.transform(prod_text);
        ItemAdapter adapter = ((ShoppingListMain) listener).getAdapter();
        return adapter.isProductStringInList(prod_text);
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