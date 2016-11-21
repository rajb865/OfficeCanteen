package com.android.officecanteen.com.android.officecanteen.data;

import com.android.officecanteen.com.android.officecanteen.app.OfficeCanteenApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by inrsharm04 on 12/27/2015.
 */
public class UpdateHelper {
    /**
     * Tag for logging
     */
    private static final String TAG = UpdateHelper.class
            .getSimpleName();

    /**
     * {@link UpdateHelper} instance
     */
    private static UpdateHelper authHelper;
    /**
     * {@link Context} for accessing data, which stored in database.
     */
    private final Context context;

    /**
     * Represents all set of fields
     */
    public enum AuthDataField {
        /** Session id */
        ATTACHMENT,
        /** Full name */
        ATTENDANCE,
        /** User email */
        DATE_SHEET,
        /** Mobile number */
        HOLIDAYS,
        /** User image url */
        NOTICE,
        /** School name */
        REPORT_CARD,
        /** School image url */
        STUDENT,
        /** Username */
        SUBJECT
    }

    public static synchronized UpdateHelper getInstance() {
        if (null == authHelper) {
            authHelper = new UpdateHelper(
                    OfficeCanteenApp.getAppContext());
        }
        return authHelper;
    }

    private UpdateHelper(final Context context) {
        this.context = context;
    }

    public synchronized String getValue(final AuthDataField fieldName) {
        final String fieldKey = getProperDBField(fieldName);

        Log.i(TAG, "getValue for " + fieldKey);

        String value = null;

        try {
            final Cursor c = this.context.getContentResolver().query(
                    DataMember.TABLE_UPDATE_MANAGER.CONTENT_URI,
                    new String[]{DataMember.TABLE_UPDATE_MANAGER.COLUMN.COLUMN_UPDATED_ON},
                    DataMember.TABLE_UPDATE_MANAGER.COLUMN.COLUMN_MODULE_NAME + "=?",
                    new String[]{fieldKey}, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    value = c.getString(0);
                }
                c.close();
            }

        } catch (final SQLiteException e) {
            Log.e(TAG, "Getting key " + fieldKey + " from DB "
                    + DataMember.TABLE_UPDATE_MANAGER.CONTENT_URI.toString()
                    + " is failed", e);
        }

        return value;
    }

    public synchronized void setValue(final AuthDataField fieldName,
                                      final String value) {
        final String fieldKey = getProperDBField(fieldName);

        Log.i(TAG, "setValue for " + fieldKey);

        if (value != null) {
            try {
                final ContentValues values = new ContentValues();

                values.put(DataMember.TABLE_UPDATE_MANAGER.COLUMN.COLUMN_MODULE_NAME, fieldKey);
                values.put(DataMember.TABLE_UPDATE_MANAGER.COLUMN.COLUMN_UPDATED_ON, value);
                this.context.getContentResolver().insert(
                        DataMember.TABLE_UPDATE_MANAGER.CONTENT_URI, values);
            } catch (final SQLiteException e) {
                Log.e(TAG, "Putting " + fieldKey + " to DB "
                        + DataMember.TABLE_UPDATE_MANAGER.CONTENT_URI.toString()
                        + " is failed ", e);
            }
        } else {
            this.context.getContentResolver().delete(
                    DataMember.TABLE_UPDATE_MANAGER.CONTENT_URI,
                    DataMember.TABLE_UPDATE_MANAGER.COLUMN.COLUMN_MODULE_NAME + "=?",
                    new String[]{fieldKey});
        }
    }

    private String getProperDBField(final AuthDataField fieldName) {
        String field = null;

        switch (fieldName) {
        case ATTACHMENT:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_ATTACHMENT;
            break;

        case ATTENDANCE:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_ATTENDANCE;
            break;

        case DATE_SHEET:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_DATE_SHEET;
            break;

        case HOLIDAYS:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_HOLIDAYS;
            break;

        case NOTICE:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_NOTICES;
            break;

        case REPORT_CARD:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_REPORT_CARD;
            break;

        case STUDENT:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_STUDENT;
            break;

        case SUBJECT:
            field = DataMember.TABLE_UPDATE_MANAGER.MODULE_SUBJECT;
            break;

        default:
            // nothing here
            Log.e(TAG, "There is no such updatemanager data field name");
            break;
        }

        return field;
    }
}
