package com.android.officecanteen.com.android.officecanteen.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBOperations {

    public static Cursor getValue(SQLiteDatabase db, String table, String key, String selection, String[] selectionArgs) {
        if (db == null || table == null || selection == null || selectionArgs == null) {
            return null;
        }
        Cursor cursor = db.query(true, table, new String[]{key}, selection,
                selectionArgs, null, null, null, null);
        if (cursor == null) {
            return null;
        }

        return cursor;
    }

    public static long insertStudent(SQLiteDatabase db, ContentValues values) {
        if (db == null || values == null) {
            return -1;
        }
        /* User already exist check*/
        String selection = DataMember.TABLE_STUDENT.STUDENT_COLUMN_STUDENT_ID + " = ?";
        if (isExist(db, DataMember.TABLE_STUDENT.TABLE, selection,
                new String[]{values.getAsString(DataMember.TABLE_STUDENT.STUDENT_COLUMN_STUDENT_ID)})) {
            return updateStudent(db, values);
        }
        return db.insert(DataMember.TABLE_STUDENT.TABLE, null, values);
    }

    public static long updateStudent(SQLiteDatabase db, ContentValues values) {
        if (db == null || values == null) {
            return -1;
        }
        String whereClause = DataMember.TABLE_STUDENT.STUDENT_COLUMN_STUDENT_ID + " = ?";
        return db.update(DataMember.TABLE_STUDENT.TABLE, values, whereClause,
                new String[]{values.getAsString(DataMember.TABLE_STUDENT.STUDENT_COLUMN_STUDENT_ID)});
    }

    public static boolean isExist(SQLiteDatabase db, String table, String selection, String[] selectionArgs) {
        if (db == null || selection == null || selectionArgs == null) {
            return false;
        }
        Cursor cursor = db.query(false, table, new String[]{"count(*)"}, selection,
                selectionArgs, null, null, null, null);
        if (cursor == null) {
            return false;
        }
        cursor.moveToFirst();
        if (cursor.getInt(0) < 1) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
