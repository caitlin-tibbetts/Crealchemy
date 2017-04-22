package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import junit.framework.Assert;

public class AchievementActivity extends Activity {
    RelativeLayout relativeLayout;
    GridView gridViewAchievements;
    ImageView imageViewIcon;
    TextView textViewText;

    ElementList list;

    AsyncImageSetter imageLoader;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        CurrentState senderAchievements = (CurrentState)getApplication();
        list = senderAchievements.getElementList();
        senderAchievements.setAchievementList(new AchievementList(list));
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        gridViewAchievements = (GridView)findViewById(R.id.gridViewAchievements);
        ImageTextArrayAdapter achievementArrayAdapter = new ImageTextArrayAdapter(this, R.layout.vertical_image_text_list_item_0, senderAchievements.getAchievementList());
        gridViewAchievements.setAdapter(achievementArrayAdapter);
        imageViewIcon = (ImageView)findViewById(R.id.imageViewIcon);
        textViewText = (TextView)findViewById(R.id.textViewText);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridViewAchievements.setVisibility(View.VISIBLE);
                imageViewIcon.setVisibility(View.INVISIBLE);
                textViewText.setVisibility(View.INVISIBLE);
            }
        });
        gridViewAchievements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridViewAchievements.setVisibility(View.INVISIBLE);
                if(getDrawable(getApplicationContext(), ((Achievement)gridViewAchievements.getItemAtPosition(position)).getIcon_id()) == 0){
                    imageLoader = new AsyncImageSetter(getApplicationContext(), imageViewIcon,
                            getDrawable(getApplicationContext(), "placeholder"), bmp, textViewText);
                    imageLoader.execute();
                } else {
                    imageLoader = new AsyncImageSetter(getApplicationContext(), imageViewIcon,
                            getDrawable(getApplicationContext(), ((Achievement)gridViewAchievements.getItemAtPosition(position)).getIcon_id()), bmp, textViewText);
                    imageLoader.execute();
                }
                imageViewIcon.setVisibility(View.VISIBLE);
                textViewText.setText(((Achievement)gridViewAchievements.getItemAtPosition(position)).getText());
                textViewText.setVisibility(View.VISIBLE);
            }
        });
    }

    public static int getDrawable(Context context, String name) {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);
        return context.getResources().getIdentifier(name.toLowerCase().replaceAll(" ", ""),
                "drawable", context.getPackageName());
    }
}
