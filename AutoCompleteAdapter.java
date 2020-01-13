package com.example.weatherapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> acCities;


    public AutoCompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        acCities = new ArrayList<>();
    }

    public void setData(List<String> list) {
        acCities.clear();
        acCities.addAll(list);
    }
    @Override
    public int getCount() {
        return acCities.size();
    }
    @Nullable
    @Override
    public String getItem(int position) {
        return acCities.get(position);
    }


    public String getObject(int position) {
        return acCities.get(position);
    }
    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = acCities;
                    filterResults.count = acCities.size();
                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;
    }

}
