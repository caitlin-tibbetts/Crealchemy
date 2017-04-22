package com.example.caitlin.crealchemy;

import android.app.IntentService;
import android.content.Intent;
import android.app.backup.BackupManager;

public class InsertNewElementIntentService extends IntentService {
    private static final String TAG = "InsertNewElementIntent";

    public InsertNewElementIntentService(){
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        CurrentState sender = (CurrentState)getApplication();
        ElementDBHelper helper = new ElementDBHelper(getApplicationContext());
        helper.insertElement(sender.getElement());
        requestBackup();
    }
    public void requestBackup() {
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
    }
}
