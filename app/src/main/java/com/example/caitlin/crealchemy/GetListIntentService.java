package com.example.caitlin.crealchemy;

import android.app.IntentService;
import android.content.Intent;
import android.app.backup.BackupManager;

public class GetListIntentService extends IntentService{
    private static final String TAG = "GetListIntent";

    public GetListIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ElementDBHelper helper = new ElementDBHelper(getApplicationContext());
        CurrentState sender = (CurrentState)getApplication();
        if(sender.getElementList() == null){
            sender.setElementList(helper.getElementList());
        }
        sender.setElement(new Element("Name", GroupType.DEFAULT));
        requestBackup();
    }
    public void requestBackup() {
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
    }
}
