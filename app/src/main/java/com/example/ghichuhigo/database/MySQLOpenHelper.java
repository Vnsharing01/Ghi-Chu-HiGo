package com.example.ghichuhigo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.ghichuhigo.object.Note;

public class MySQLOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dbNote";
    public static final int VERSION = 03;

    public static final String TABLE_NAME = "note";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    public MySQLOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onCreateTableNote(sqLiteDatabase);
//        insertDataDSChuong(sqLiteDatabase);
    }

    public void onCreateTableNote(SQLiteDatabase db) {
        String SqlNote = "CREATE TABLE " + TABLE_NAME + "( " +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                TITLE + " TEXT NOT NULL, " +
                CONTENT + " TEXT NOT NULL ) ";
        Log.e("tag", "onCreateTableNote: OK", null);
        db.execSQL(SqlNote);
    }

    public void insertDataNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(CONTENT, note.getContent());
        db.insert(TABLE_NAME, null, values);
    }

    public void updateDataNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID,note.getID());
        values.put(TITLE, note.getTitle());
        values.put(CONTENT, note.getContent());
        db.update(TABLE_NAME, values, ID + " = ? ", new String[]{String.valueOf(note.getID())});
        db.close();
    }

    public void deleteDataNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID + " =? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Note> selectDataNote() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sqlQuery = " select * from " + TABLE_NAME + " order by " + ID + " desc";
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                Note note = new Note(id,title, content);
                noteList.add(note);
            }while (cursor.moveToNext());
        }
        return noteList;
    }

    public Note selectItemNote(int noteId) {
        SQLiteDatabase db = getWritableDatabase();
        String sqlQuery = " select * from " + TABLE_NAME + " where " + ID + "  = " + noteId;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String content = cursor.getString(2);
        Note mNote = new Note(id,title, content);
        return mNote;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // xoá bảng cũ
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            // tạo bảng mới
            onCreate(db);
        }
    }
//    public void insertDataDSChuong(SQLiteDatabase db) {
//
//        String sqlInsert = "INSERT INTO " + TABLE_NAME +
//                "( " + TITLE + "," + CONTENT + ") VALUES(";
//
//        sqlInsert += "'Ten truyen 0001'";
//        sqlInsert += ", 'Tac gia 0001'";
//        sqlInsert += ")";
//        db.execSQL(sqlInsert);
//
//        sqlInsert = "INSERT INTO " + TABLE_NAME +
//                "( " + TITLE + "," + CONTENT + ") VALUES(";
//
//        sqlInsert += "'Ten truyen 0002'";
//        sqlInsert += ", 'Tac gia 0001'";
//        sqlInsert += ")";
//        db.execSQL(sqlInsert);
//    }
}
