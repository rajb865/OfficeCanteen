package com.android.officecanteen.com.android.officecanteen.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by inrsharm04 on 11/17/2015.
 */
public class DataProvider extends ContentProvider {
    String TAG = DataProvider.class.getSimpleName();

    private static final int USER_TABLE = 0;
    private static final int STUDENT_TABLE = 1;
    private static final int ATTACHMENT_TABLE = 2;
    private static final int SUBJECT_TABLE = 3;
    private static final int ATTENDANCE_TABLE = 4;
    private static final int NOTICE_TABLE = 5;
    private static final int HOLIDAY_TABLE = 6;
    private static final int REPORT_CARD_TABLE = 7;
    private static final int DATE_SHEET_TABLE = 8;
    private static final int UPDATE_MANAGER_TABLE = 9;

    public static final String AUTHORITY = "com.techroots.smartschool.data.DataProvider";
    private static final String TABLE = "table";
    private static final String BASE_PATH_TABLE_USER = DataMember.TABLE_COMMUNICATION_DATA.TABLE;
    private static final String BASE_PATH_TABLE_STUDENT = DataMember.TABLE_STUDENT.TABLE;
    private static final String BASE_PATH_TABLE_ATTACHMENT = DataMember.TABLE_ATTACHMENT.TABLE;
    private static final String BASE_PATH_TABLE_SUBJECT = DataMember.TABLE_SUBJECTS.TABLE;
    private static final String BASE_PATH_TABLE_ATTENDANCE = DataMember.TABLE_ATTENDANCE.TABLE;
    private static final String BASE_PATH_TABLE_NOTICE = DataMember.TABLE_NOTICE.TABLE;
    private static final String BASE_PATH_TABLE_HOLIDAYS = DataMember.TABLE_HOLIDAYS.TABLE;
    private static final String BASE_PATH_TABLE_REPORT_CARD = DataMember.TABLE_REPORT_CARD.TABLE;
    private static final String BASE_PATH_TABLE_DATE_SHEET = DataMember.TABLE_DATE_SHEET.TABLE;
    private static final String BASE_PATH_TABLE_UPDATE_MANAGER = DataMember.TABLE_UPDATE_MANAGER.TABLE;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
//        sURIMatcher.addURI(AUTHORITY, TABLE + "/#", USER_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_USER), USER_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_STUDENT), STUDENT_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_ATTACHMENT), ATTACHMENT_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_SUBJECT), SUBJECT_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_ATTENDANCE), ATTENDANCE_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_NOTICE), NOTICE_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_HOLIDAYS), HOLIDAY_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_REPORT_CARD), REPORT_CARD_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_DATE_SHEET), DATE_SHEET_TABLE);
        sURIMatcher.addURI(AUTHORITY, (TABLE + "/" + BASE_PATH_TABLE_UPDATE_MANAGER), UPDATE_MANAGER_TABLE);
    }

    @Override
    public boolean onCreate() {
        DBHelper db = null;
        db = DBHelper.getInstance(getContext(), this);
        try {
            db.createDataBase();
        } catch (IOException exc) {
            Log.d("", "Database creation failed");
        }
        db.close();

        return db == null ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        try {
            final String tableName = getTableName(uri);

            if (tableName != null) {
                final SQLiteDatabase db = DBHelper.openDataBase(DBHelper.READ_ONLY);

                final Cursor cursor = db.query(tableName, projection, selection,
                        selectionArgs, null, null, sortOrder);

                return cursor;
            }
        } catch (final NullPointerException e) {
            Log.e(TAG, "query is failed    due to some error");
        } catch (final SQLiteException e) {
            Log.e(TAG, "query is failed");
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] bulkValues) {
        final int numValues = bulkValues.length;
        try {
            if (numValues > 0) {
                final SQLiteDatabase db = DBHelper.openDataBase(DBHelper.READ_WRITE);

                try {
                    db.beginTransaction();

                    for (final ContentValues values : bulkValues) {
                        insert(uri, values);
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "bulkInsert is failed");
        }

        return numValues;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri newUri = null;
        try {
            final String tableName = getTableName(uri);

            if (tableName != null) {
                final SQLiteDatabase db = DBHelper.openDataBase(DBHelper.READ_WRITE);

                long id = db.insert(tableName, null, values);
                newUri = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "insert is failed");
        }

        if (newUri == null) {
            return uri;
        } else {
            return newUri;
        }
    }

    private String getTableName(final Uri uri) {
        String tableName = null;

        switch (sURIMatcher.match(uri)) {
        case USER_TABLE:
            tableName = DataMember.TABLE_COMMUNICATION_DATA.TABLE;
            break;

        case STUDENT_TABLE:
            tableName = DataMember.TABLE_STUDENT.TABLE;
            break;

        case ATTACHMENT_TABLE:
            tableName = DataMember.TABLE_ATTACHMENT.TABLE;
            break;

        case SUBJECT_TABLE:
            tableName = DataMember.TABLE_SUBJECTS.TABLE;
            break;
        case ATTENDANCE_TABLE:
            tableName = DataMember.TABLE_ATTENDANCE.TABLE;
            break;
        case NOTICE_TABLE:
            tableName = DataMember.TABLE_NOTICE.TABLE;
            break;
        case HOLIDAY_TABLE:
            tableName = DataMember.TABLE_HOLIDAYS.TABLE;
            break;
        case REPORT_CARD_TABLE:
            tableName = DataMember.TABLE_REPORT_CARD.TABLE;
            break;
        case DATE_SHEET_TABLE:
            tableName = DataMember.TABLE_DATE_SHEET.TABLE;
            break;
        case UPDATE_MANAGER_TABLE:
            tableName = DataMember.TABLE_UPDATE_MANAGER.TABLE;
            break;
        default:
            break;
        }

        return tableName;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        try {
            final String tableName = getTableName(uri);

            if (tableName != null) {
                final SQLiteDatabase db = DBHelper.openDataBase(DBHelper.READ_WRITE);
                final int id = db.delete(tableName, selection, selectionArgs);
                if (uri != null) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return id;
            }
        } catch (final SQLiteException e) {
            Log.e(TAG, "delete from DB is failed", e);
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
