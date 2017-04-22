package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TextArrayAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private ArrayList objects;

    public TextArrayAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder;
        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource, viewGroup, false);
            holder = new ViewHolder();
            holder.text = (TextView)row.findViewById(R.id.listTextView);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }
        if(objects.get(position) instanceof Element[]){
            Element[] elements = (Element[]) objects.get(position);
            holder.text.setText(context.getString(R.string.listview_item_parents, elements[0].getName(), elements[1].getName()));
        }
        return view;
    }

    static class ViewHolder {
        TextView text;
    }
}
