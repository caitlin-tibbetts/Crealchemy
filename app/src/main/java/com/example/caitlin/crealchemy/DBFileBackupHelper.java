package com.example.caitlin.crealchemy;

import android.app.backup.FileBackupHelper;
import android.content.Context;

public class DBFileBackupHelper extends FileBackupHelper {
    public DBFileBackupHelper(Context context, String dbName) {
        super(context, context.getDatabasePath(dbName).getAbsolutePath());
    }
}