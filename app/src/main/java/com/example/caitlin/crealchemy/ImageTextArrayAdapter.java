package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.ArrayList;

public class ImageTextArrayAdapter extends ArrayAdapter {

    int resource;
    int textViewResourceId;
    Context context;
    ArrayList objects;
    private Bitmap bmp;

    static class ViewHolder{
        ImageView icon;
        TextView text;
        AsyncImageSetter imageLoader;

    }

    public ImageTextArrayAdapter(Context context, int resource, int textViewResourceId, ArrayList objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
    }

    public ImageTextArrayAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        View row = view;
        ViewHolder holder;
        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(resource, viewGroup, false);
            holder = new ViewHolder();
            holder.icon = (ImageView)view.findViewById(R.id.listImageView);
            holder.text = (TextView)view.findViewById(R.id.listTextView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
            holder.imageLoader.cancel();
        }
        if(objects.get(position) instanceof Achievement){
            Achievement achievement = (Achievement) objects.get(position);
            holder.text.setText(achievement.getText());
            if(getDrawable(context, achievement.getIcon_id()) == 0){
                holder.imageLoader = new AsyncImageSetter(context, holder.icon,
                        getDrawable(context, "placeholder"), bmp, holder.text);
                holder.imageLoader.execute();
            } else {
                holder.imageLoader = new AsyncImageSetter(context, holder.icon,
                        getDrawable(context, achievement.getIcon_id()), bmp, holder.text);
                holder.imageLoader.execute();
            }
        } else if(objects.get(position) instanceof Element){
            Element element = (Element) objects.get(position);
            holder.text.setText(element.getName());
            if(getDrawable(context, element.getName()) == 0){
                holder.imageLoader = new AsyncImageSetter(context, holder.icon,
                        getDrawable(context, "placeholder"), bmp, holder.text);
                holder.imageLoader.execute();
            } else {
                holder.imageLoader = new AsyncImageSetter(context, holder.icon,
                        getDrawable(context, element.getName()), bmp, holder.text);
                holder.imageLoader.execute();
            }
        }
        return view;
    }

    public static int getDrawable(Context context, String name) {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);
        return context.getResources().getIdentifier(name.toLowerCase().replaceAll(" ", ""),
                "drawable", context.getPackageName());
    }
}
