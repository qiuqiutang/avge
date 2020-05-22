package com.example.lyfuelgas.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class MyListPopAdapter extends ArrayAdapter {
    public MyListPopAdapter(Context context, int resource) {
        super(context, resource);
    }

    public MyListPopAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public MyListPopAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);

    }

    public MyListPopAdapter(Context context, int resource, int textViewResourceId, Object[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MyListPopAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public MyListPopAdapter(Context context, int resource, int textViewResourceId, List objects) {
        super(context, resource, textViewResourceId, objects);
    }



}
