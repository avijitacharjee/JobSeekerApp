package com.avijit.jobseeker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avijit.jobseeker.R;

/**
 * Created by Avijit on 12/1/2019.
 */

public class QuizListAdapater extends BaseAdapter {
    private Context context;
    private String[] options;

    public QuizListAdapater(Context context, String[] options) {
        this.context = context;
        this.options = options;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return options.length;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final View result;
        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_options_list,parent,false);
            viewHolder.textViewOptionNumber = convertView.findViewById(R.id.option_number);
            viewHolder.textViewOptionText = convertView.findViewById(R.id.option_text);

            result = convertView;
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder =(ViewHolder) convertView.getTag();
            result = convertView;
        }

        switch (position)
        {
            case 0: viewHolder.textViewOptionNumber.setText("A");
                    break;
            case 1: viewHolder.textViewOptionNumber.setText("B");
                    break;
            case 2: viewHolder.textViewOptionNumber.setText("C");
                    break;
            case 3: viewHolder.textViewOptionNumber.setText("D");
                    break;
        }
        viewHolder.textViewOptionText.setText(options[position]);

        return convertView;
    }
    public static class ViewHolder {
        public TextView textViewOptionNumber;
        public TextView textViewOptionText;
    }
}
