package com.example.caitlin.crealchemy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ElementDBHelper extends SQLiteOpenHelper{
    //Logcat tag
    private static final String LOG = "ElementDBHelper";

    //Database name
    private static final String DATABASE_NAME = "elements.db";

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Table names
    private static final String TABLE_NAMES = "names";
    private static final String TABLE_PARENTS = "parents";

    //Column names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GROUP = "groupName";
    private static final String COLUMN_NAMES_ISFOUND = "isFound";
    private static final String COLUMN_NAMES_WASCREATED = "wasCreated";
    private static final String COLUMN_PARENTS_PARENT1 = "parent1";
    private static final String COLUMN_PARENTS_PARENT2 = "parent2";

    //Create statements
    private static final String SQL_CREATE_ENTRIES_NAMES =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAMES + " (" +
                    COLUMN_ID +
                    " INTEGER, " +
                    COLUMN_NAME +
                    " TEXT, " +
                    COLUMN_GROUP +
                    " TEXT, " +
                    COLUMN_NAMES_ISFOUND +
                    " INTEGER, " +
                    COLUMN_NAMES_WASCREATED +
                    " INTEGER, PRIMARY KEY (" + COLUMN_NAME + "))";
    private static final String SQL_CREATE_ENTRIES_PARENTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_PARENTS + " (" +
                    COLUMN_ID +
                    " INTEGER, " +
                    COLUMN_NAME +
                    " TEXT," +
                    COLUMN_PARENTS_PARENT1 +
                    " TEXT," +
                    COLUMN_PARENTS_PARENT2 +
                    " TEXT, PRIMARY KEY (" + COLUMN_ID + "))";
    //Methods
    public ElementDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_NAMES);
        db.execSQL(SQL_CREATE_ENTRIES_PARENTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARENTS);
        onCreate(db);
    }

    public void insertElement(Element e){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("REPLACE INTO " + TABLE_NAMES + " (" + COLUMN_NAME + ", " + COLUMN_GROUP + ", " + COLUMN_NAMES_ISFOUND + ", " + COLUMN_NAMES_WASCREATED + ") VALUES ('" + e.getName() + "', '" + e.getGroup().toString() + "', " + e.getIsFound() + ", " + e.getWasCreated() + ")");
        if(e.getAllParents() != null){
            for(int k = 0; k<e.getAllParents().size(); k++){
                db.execSQL("INSERT INTO " + TABLE_PARENTS + " (" + COLUMN_NAME + ", " + COLUMN_PARENTS_PARENT1 + ", " + COLUMN_PARENTS_PARENT2 + ") VALUES ('" + e.getName() + "', '" + e.getParents(k)[0].getName() + "', '" + e.getParents(k)[1].getName() + "')");
            }
        }
        db.close();
    }

    public void deleteElement(Element e){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAMES + " WHERE " + COLUMN_NAME + " = '" + e.getName() + "'");
        db.execSQL("DELETE FROM " + TABLE_PARENTS + " WHERE " + COLUMN_NAME + " = '" + e.getName() + "'");
        db.close();
    }

    public ElementList getElementList(){
        ElementList ret = new ElementList();
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT * FROM " + TABLE_NAMES;
        Log.e(LOG, query1);
        try (Cursor cursor1 = db.rawQuery(query1, null)) {
            if (cursor1.moveToFirst()) {
                do {
                    ret.add(new Element(cursor1.getString(cursor1.getColumnIndex(COLUMN_NAME)), GroupType.getGroup(cursor1.getString(cursor1.getColumnIndex(COLUMN_GROUP))), cursor1.getInt(cursor1.getColumnIndex(COLUMN_NAMES_ISFOUND)), cursor1.getInt(cursor1.getColumnIndex(COLUMN_NAMES_WASCREATED))));
                } while (cursor1.moveToNext());
            }
        }
        String query2 = "SELECT * FROM " + TABLE_PARENTS;
        Log.e(LOG, query2);
        try (Cursor cursor2 = db.rawQuery(query2, null)) {
            if (cursor2.moveToFirst()) {
                do {
                    int index = ret.indexOf(cursor2.getString(cursor2.getColumnIndex(COLUMN_NAME)));
                    ret.get(index).addParents(ret.get(ret.indexOf(cursor2.getString(cursor2.getColumnIndex(COLUMN_PARENTS_PARENT1)))), ret.get(ret.indexOf(cursor2.getString(cursor2.getColumnIndex(COLUMN_PARENTS_PARENT2)))));
                } while (cursor2.moveToNext());
            }
        }
        ret.sort();
        db.close();
        return ret;
    }
}


