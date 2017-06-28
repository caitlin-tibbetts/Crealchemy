package com.example.caitlin.crealchemy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class AchievementActivity extends Activity {
    RelativeLayout relativeLayout;
    GridView gridViewAchievements;

    ElementList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        CurrentState senderAchievements = (CurrentState)getApplication();
        list = senderAchievements.getElementList();
        senderAchievements.setAchievementList(new AchievementList(list));
        gridViewAchievements = (GridView)findViewById(R.id.gridViewAchievements);
        ImageTextArrayAdapter achievementArrayAdapter = new ImageTextArrayAdapter(this, R.layout.vertical_image_text_list_item_0, senderAchievements.getAchievementList());
        gridViewAchievements.setAdapter(achievementArrayAdapter);
    }
}
