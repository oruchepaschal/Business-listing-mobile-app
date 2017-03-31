package com.ecoach.cosapp.ListViewAdapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecoach.cosapp.Activites.CompanyDetailsTabbedActivities.Details;
import com.ecoach.cosapp.Models.DetailsItem;
import com.ecoach.cosapp.R;
import com.ecoach.cosapp.Utilities.TextAwesome;

import java.util.List;

/**
 * Created by apple on 3/30/17.
 */

public class DetailsListViewAdapter extends ArrayAdapter<DetailsItem> {

    public DetailsListViewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public DetailsListViewAdapter(Context context, int resource, List<DetailsItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.itemsdetailslistcell, null);
        }

        DetailsItem p = getItem(position);

        if (p != null) {
            TextView titleTxt = (TextView) v.findViewById(R.id.titleTxt);
            titleTxt.setText(p.getTitle());

            TextAwesome imageView = (TextAwesome)v.findViewById(R.id.icon);
            imageView.setText(p.getDrawable());
            imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        }

        return v;
    }

}