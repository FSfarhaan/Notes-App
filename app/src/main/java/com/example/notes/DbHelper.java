package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NotesDB";
    public static final String TABLE_NAME = "Notes";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_CREATED_ON = "createdOn";
    public static final String KEY_MODIFIED_ON = "modifiedOn";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE TABLE_NAME (
//            KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
//            KEY_TITLE TEXT,
//            KEY_CONTENT TEXT,
//            KEY_CREATED_ON TEXT,
//            KEY_MODIFIED_ON TEXT)

        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TITLE + " TEXT, "
                + KEY_CONTENT + " TEXT, "
                + KEY_CREATED_ON + " TEXT, "
                + KEY_MODIFIED_ON + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertNotes(String title, String content, String todayDate, long modifiedOn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_CONTENT, content);
        values.put(KEY_CREATED_ON, todayDate);
        values.put(KEY_MODIFIED_ON, modifiedOn);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    ArrayList<NotesModel> getNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<NotesModel> notesList = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * from " + TABLE_NAME, null);

            if (cursor.moveToFirst()) {
                do {
                    NotesModel notesModel = new NotesModel();
                    notesModel.setId(cursor.getInt(0));
                    notesModel.setTitle(cursor.getString(1));
                    notesModel.setContent(cursor.getString(2));
                    notesModel.setCreatedOn(cursor.getString(3));
                    notesModel.setModifiedOn(cursor.getLong(4));
                    notesList.add(notesModel);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        notesList.sort(new NotesModelTimestampComparator());
        return notesList;
    }

    public int updateNoteRow(String oldTitle, String newTitle, String content, long modifiedOn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, newTitle);
        values.put(KEY_CONTENT, content);
        values.put(KEY_MODIFIED_ON, modifiedOn);
        int rowsAffected = db.update(TABLE_NAME, values, KEY_TITLE + " = ?" , new String[] {oldTitle});
        db.close();
        return rowsAffected;
    }
    public void deleteNote(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_TITLE + " = ?", new String[] {title});
        db.close();
    }
    public boolean isTableEmpty() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        int count = 0;
        try {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } finally {
            cursor.close();
        }
        return count == 0;
    }
}
