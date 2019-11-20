/*
 * This program creates a note taking application that allows you to create new notes, edit them, and delete them
 *
 * CPSC 312-01, Fall 2019
 * Programming Assignment #6
 *
 * @authors Diego Valdez:       Handled the Note object and most of the integration of the MVC data model for the entire app
 *          Patrick Seminatore: Did most of the work with setting up the layout through java without XML
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * <div>Icons made by <a href="https://www.flaticon.com/authors/mynamepong" title="mynamepong">mynamepong</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
 * @version v1.0 10/22/19
 */

package com.example.pa7real;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteOpenHelper extends SQLiteOpenHelper {
    static final String TAG = "SQLiteFunTag";

    // define some fields for our database
    static final String DATABASE_NAME = "contactsDatabase";
    static final int DATABASE_VERSION = 1;

    static final String TABLE_NOTES = "tableNotes";
    static final String ID = "_id"; // _id is for use with adapters later
    static final String TITLE = "title";
    static final String SPINNER_CHOICE = "spinnerChoice";
    static final String CONTENT = "content";
    static final String IMAGE_REP = "image_id";

    public NoteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // where we create tables in our database
        // construct a SQL statement to create a table to store contacts
        // CREATE TABLE tableContacts(_id INTEGER PRIMARY KEY AUTOINCREMENT,
        // name TEXT,
        // phoneNumber TEXT,
        // imageResource INTEGER)

        // create a string that represents our SQL statement
        // structured query language
        String sqlCreate = "CREATE TABLE " + TABLE_NOTES +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT, " +
                SPINNER_CHOICE + " TEXT, " +
                CONTENT + " INTEGER, " +
                IMAGE_REP + " INTEGER)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        // execute this sql statement
        sqLiteDatabase.execSQL(sqlCreate);
        // onCreate() only executes one time
        // and that is after the first call to getWritableDatabase()
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertContact(Note note) {
        // INSERT INTO tableContacts VALUES(null, 'Spike the Bulldog',
        // '509-509-5095', -1)
        String sqlInsert = "INSERT INTO " + TABLE_NOTES + " VALUES(null, '" +
                note.getTitle() + "', '" +
                note.getCategory() + "', '" +
                note.getContent() + "', " +
                note.getImageRep() + ")";
        Log.d(TAG, "insertNote: " + sqlInsert);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close(); // good practice to close database open for writing
    }

    public Cursor getSelectAllNotesCursor() {
        // cursor used to navigate through results from a query
        // think of the cursor like a file cursor
        // SELECT * FROM tableContacts
        String sqlSelect = "SELECT * FROM " + TABLE_NOTES;
        // * means all columns
        Log.d(TAG, "getSelectAllNotesCursor: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        // use db.rawQuery() because its returs a Cursor
        Cursor cursor = db.rawQuery(sqlSelect, null);
        // don't close the database, the cursor needs it open

        return cursor;
    }

    // for debug purposes only!!
    // for PA7 use SimpleCursorAdapter to wire up the database to the listview
    public List<Note> getSelectAllNotesList() {
        List<Note> noteList = new ArrayList<>();

        // goal: walk through each record using a cursor
        // create a Note and add it to the list
        // the cursor doesn't start at the first record
        // because there might be not be a first record
        Cursor cursor = getSelectAllNotesCursor();
        while (cursor.moveToNext()) { // returns false when there is no next record
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String category = cursor.getString(2);
            String content = cursor.getString(3);
            Note contact = new Note(id, title, category, content);
            noteList.add(contact);
        }

        return noteList;
    }

    public void updateContactById(int id, Note newNote) {
        // UPDATE tableContacts SET name='SPIKE', phoneNumber='208-208-2082' WHERE _id=1
        String sqlUpdate = "UPDATE " + TABLE_NOTES + " SET " +
                TITLE + "='" + newNote.getTitle() + "', " +
                SPINNER_CHOICE  + "='" + newNote.getCategory() + "', " +
                CONTENT + "='" + newNote.getContent() + "' WHERE " +
                ID + "=" + id;
        Log.d(TAG, "updateNoteById: " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    public void delete(int id) {
        String sqlDelete = "DELETE FROM " + TABLE_NOTES + " WHERE " + ID + " = " + id;
        Log.d(TAG, "deleteAllContacts: " + sqlDelete);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }
}
