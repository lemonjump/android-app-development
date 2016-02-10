package com.project.csc301.shoppinglist.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.project.csc301.shoppinglist.Models.ItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.project.csc301.shoppinglist.Models.ListItem;
import com.project.csc301.shoppinglist.Models.PurchaseHistory;
import com.project.csc301.shoppinglist.Models.Separator;
import com.project.csc301.shoppinglist.Models.ListItemType;
import com.project.csc301.shoppinglist.R;
import com.project.csc301.shoppinglist.Controllers.IntraComponentListeners.*;


class ItemAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ListItemsListener listener;
    private List<ListItem> items_to_buy, items_acquired;
    private static final int VIEW_TYPE_COUNT = ListItemType.values().length;

    public ItemAdapter(Context context,
                       ListItemsListener listener,
                       List<ListItem> items_to_buy,
                       List<ListItem> items_acquired) {

        this.items_to_buy = items_to_buy;
        this.items_acquired = items_acquired;
        this.listener = listener;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items_acquired.size() + items_to_buy.size();
    }

    public boolean hasValidElements() {
        int i = 0;
        for (ListItem m : items_acquired) {
            if (m.getViewType() == ListItemType.PRODUCT) {
                i++;
            }
        }
        for (ListItem m : items_to_buy) {
            if (m.getViewType() == ListItemType.PRODUCT) {
                i++;
            }
        }
        return i > 0;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Object getItem(int position) {
        return getActualListItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return getActualListItem(position).getViewType().ordinal();
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == ListItemType.PRODUCT.ordinal();
    }

    /*Both: true? Ignores get_acquired, returns both lists as strings.
    * Both: false? If get_acquired, returns Acquired items list, else Pending items list.*/
    List<String> getProductsFromSreen(boolean both, boolean get_acquired) {
        List<String> products = new ArrayList<>();
        if (both || get_acquired)
            products.addAll(getProductsAsStrings(items_acquired));
        if (both || !get_acquired)
            products.addAll(getProductsAsStrings(items_to_buy));
        return products;
    }

    List<String> getProductsAsStrings(List<ListItem> itemList) {
        List<String> products = new ArrayList<>();
        for (ListItem item : itemList) {
            if (item.getViewType() == ListItemType.PRODUCT)
                products.add(((ItemModel) item).getProductName());
        }
        return products;
    }

    ListItem getActualListItem(int position) {

        int num_pending_items = items_to_buy.size();
        if (position < num_pending_items)
            return items_to_buy.get(position);
        else
            return items_acquired.get(position - num_pending_items);
    }


    public boolean isProductStringInList(String s) {
        return findProdUsingStringFromList(s, items_acquired)
                || findProdUsingStringFromList(s, items_to_buy);
    }

    public boolean findProdUsingStringFromList(String s, List<ListItem> l) {
        for (ListItem m : l)
            if (m.getViewType() == ListItemType.PRODUCT &&
                    ((ItemModel) m).getProductName().equalsIgnoreCase(s))
                return true;
        return false;
    }

    static class ViewHolder {
        protected TextView text;
        protected TextView amount;
        protected TextView purchase_details;
        protected CheckBox checkbox;
        protected Button delete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int type = getItemViewType(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            int layout;
            if (type == ListItemType.PRODUCT.ordinal()) {
                layout = R.layout.list_element;
            } else if (type == ListItemType.SEPARATOR_STORE.ordinal()) {
                layout = R.layout.separator_store;
            } else {
                layout = R.layout.separator_common;
            }
            convertView = inflater.inflate(layout, null);

            if (type == ListItemType.PRODUCT.ordinal()) {
                viewHolder = createProductViewHolder(convertView, position);
            } else {
                viewHolder = createSeparatorViewHolder(convertView, position);
            }
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (type != ListItemType.PRODUCT.ordinal()) {
            Separator current_item = (Separator) getItem(position);
            if (current_item.getTitle() != -1)
                viewHolder.text.setText(current_item.getTitle());
            else
                viewHolder.text.setText(current_item.getTextitle());

            if (current_item.getViewType() == ListItemType.SEPARATOR_ITEMS_PENDING)
                viewHolder.purchase_details.setText(current_item.getDetails());

            int show_details =
                    current_item.getViewType() == ListItemType.SEPARATOR_ITEMS_PENDING
                            && items_to_buy.size() == 1
                            ? View.VISIBLE : View.GONE;

            viewHolder.purchase_details.setVisibility(show_details);

        } else {
            ItemModel current_item = (ItemModel) getItem(position);
            //Get the product name.
            viewHolder.text.setText(current_item.getProductName());

            //Get the amount details.
            int show_amount_info = current_item.isAmountAvailable() ? View.VISIBLE : View.GONE;
            viewHolder.amount.setVisibility(show_amount_info);
            if (show_amount_info != View.GONE)
                viewHolder.amount.setText(
                        String.format("(%s %s)", current_item.getAmount(), current_item.getUnit()));

            //Check the box depending on the model's status.
            viewHolder.checkbox.setChecked(current_item.isAcquired());
            viewHolder.checkbox.setVisibility(View.VISIBLE);

            //Show delete buttons only if on delete mode.
            boolean onDeleteMode = ((ShoppingListMain) listener).isOnDeleteMode();
            int show_del_buttons = onDeleteMode ? View.VISIBLE : View.GONE;
            viewHolder.checkbox.setEnabled(!onDeleteMode);

            int which = viewHolder.checkbox.isChecked() ? R.drawable.checked : R.drawable.unchecked;
            viewHolder.checkbox.setButtonDrawable(which);

            if (!viewHolder.checkbox.isEnabled())
                viewHolder.checkbox.setAlpha(0.4f);
            else
                viewHolder.checkbox.setAlpha(1.0f);

            viewHolder.delete.setVisibility(show_del_buttons);

            //Add listeners.
            addOnClickListenerForDelete(viewHolder.delete, position);
            addOnClickListenerForCheckBox(viewHolder.checkbox, position);

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    notifyLongClick(position);
                    return true;
                }
            });
        }

        return convertView;
    }

    private void addOnClickListenerForDelete(Button button, final int position) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }

    private void addOnClickListenerForCheckBox(CheckBox checkbox, final int position) {
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel m = (ItemModel) getActualListItem(position);
                last_clicked_cb = (CheckBox) v;
                last_clicked_cb.setChecked(!last_clicked_cb.isChecked());
                notifyCheck(position, m.isAcquired());
            }
        });
    }

    private CheckBox last_clicked_cb;

    public void toggleLastItem(int position) {
        ItemModel m = (ItemModel) getActualListItem(position);
        m.toggle();
        last_clicked_cb.setChecked(m.isAcquired());
    }

    private ViewHolder createSeparatorViewHolder(View convertView, int position) {
        ViewHolder vh = new ViewHolder();
        vh.text = (TextView) (convertView.findViewById(R.id.text1));
        vh.purchase_details = (TextView) (convertView.findViewById(R.id.details));
        return vh;
    }

    private ViewHolder createProductViewHolder(View convertView, int position) {
        ViewHolder vh = new ViewHolder();
        vh.text = (TextView) (convertView.findViewById(R.id.text1));
        vh.amount = (TextView) (convertView.findViewById(R.id.amounttext));
        vh.checkbox = (CheckBox) (convertView.findViewById(R.id.radio1));
        vh.delete = (Button) (convertView.findViewById(R.id.button1));
        return vh;
    }

    private void removeItem(int pos) {
        listener.onDeleteItem(pos);
    }

    private void notifyLongClick(int pos) {
        listener.onLongClick((ItemModel) getActualListItem(pos));
    }

    private void notifyCheck(int pos, boolean hasBeenChecked) {
        listener.OnCheckAction(pos, hasBeenChecked);
    }
}