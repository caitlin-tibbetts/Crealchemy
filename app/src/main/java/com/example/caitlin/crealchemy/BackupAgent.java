package com.example.caitlin.crealchemy;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;

public class BackupAgent extends BackupAgentHelper {
    // The name of the SharedPreferences file
    static final String DATABASE_FILENAME = "elements.db";

    // A key to uniquely identify the set of backup data
    static final String FILES_BACKUP_KEY = "backupFiles";

    // Allocate a helper and add it to the backup agent
    @Override
    public void onCreate() {
        addHelper(FILES_BACKUP_KEY, new DBFileBackupHelper(this, DATABASE_FILENAME));
    }
}