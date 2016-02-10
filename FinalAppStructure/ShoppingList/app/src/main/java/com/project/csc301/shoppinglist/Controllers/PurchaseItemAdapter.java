package com.project.csc301.shoppinglist.Controllers;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.csc301.shoppinglist.Controllers.IntraComponentListeners.ListItemsListener;
import com.project.csc301.shoppinglist.Models.ItemModel;
import com.project.csc301.shoppinglist.Models.ListItem;
import com.project.csc301.shoppinglist.Models.ListItemType;
import com.project.csc301.shoppinglist.Models.PurchaseHistory;
import com.project.csc301.shoppinglist.Models.Separator;
import com.project.csc301.shoppinglist.R;

import java.util.ArrayList;
import java.util.List;

class PurchaseItemAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PurchaseHistory> previousPurchases;
    private IntraComponentListeners.DeleteListener listener;
    public PurchaseItemAdapter(Context context,
                               List<PurchaseHistory> previousPurchases,
                               IntraComponentListeners.DeleteListener listener) {

        this.previousPurchases = previousPurchases;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return previousPurchases.size();
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Object getItem(int position) {
        return previousPurchases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        protected TextView store;
        protected TextView price;
        protected TextView date;
        protected Button delete;
        protected TextView separator;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.purchase_history_element, null);
            viewHolder = createPurchaseHistoryViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PurchaseHistory ph = (PurchaseHistory) getItem(position);

        viewHolder.store.setText(ph.getStore());

        if (ph.getPrice() != null)
            viewHolder.price.setText(String.format("$ %s / %s", ph.getPrice(), ph.getUnit_type()));
        if (ph.getTime() != null){
            String time = ph.getTime();
            if(time.length() > 10){
                time = time.substring(0, 10);
            }
            viewHolder.date.setText("Date: " + time);

            if(position == 0){
                viewHolder.price.setTextColor(Color.parseColor("#7DD46C"));
            }
            if(position == previousPurchases.size() - 1 && position > 0){
                viewHolder.price.setTextColor(Color.parseColor("#DE5B62"));
            }

        }

        int show_more_things = ph.getPrice() != null ? View.VISIBLE : View.GONE;
        viewHolder.price.setVisibility(show_more_things);
        viewHolder.delete.setVisibility(show_more_things);
        viewHolder.date.setVisibility(show_more_things);
        viewHolder.separator.setVisibility(show_more_things);

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteItem(position);
            }
        });

        return convertView;
    }


    private ViewHolder createPurchaseHistoryViewHolder(View convertView) {
        ViewHolder vh = new ViewHolder();
        vh.store = (TextView) (convertView.findViewById(R.id.the_store_name));
        vh.price = (TextView) (convertView.findViewById(R.id.the_price));
        vh.date = (TextView) (convertView.findViewById(R.id.the_date));
        vh.separator = (TextView) (convertView.findViewById(R.id.the_separator));
        vh.delete = (Button) (convertView.findViewById(R.id.button1));
        return vh;
    }

}