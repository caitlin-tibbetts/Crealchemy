package com.example.caitlin.crealchemy;

import android.app.IntentService;
import android.content.Intent;
import android.app.backup.BackupManager;

public class DeleteElementIntentService extends IntentService {
    private static final String TAG = "DeleteElementIntentService";

    public DeleteElementIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        CurrentState sender = (CurrentState)getApplication();
        ElementDBHelper helper = new ElementDBHelper(getApplicationContext());
        for(Element e : sender.getToDelete()){
            helper.deleteElement(e);
        }
        helper.deleteElement(sender.getElement());
        requestBackup();
    }

    public void requestBackup() {
        BackupManager bm = new BackupManager(this);
        bm.dataChanged();
    }
}
