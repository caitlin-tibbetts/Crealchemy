package com.example.caitlin.crealchemy;

import android.app.IntentService;
import android.content.Intent;
import android.app.backup.BackupManager;

public class UpdateElementListIntentService extends IntentService {
    private static final String TAG = "UpdateElementListIntent";
    public UpdateElementListIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CurrentState sender = (CurrentState)getApplication();
        ElementDBHelper helper = new ElementDBHelper(getApplicationContext());
        for(Element e : sender.getElementList()){
            helper.insertElement(e);
        }
        requestBackup();
    }
    public void requestBackup() {
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
    }
}
