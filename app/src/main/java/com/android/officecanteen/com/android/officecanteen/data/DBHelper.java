package com.android.officecanteen.com.android.officecanteen.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by inrsharm04 on 3/3/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int READ_ONLY = 0;
    public static final int READ_WRITE = 1;
    private static final String TAG = DBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "smartschooldb.db";
    private static final int VERSION = 1;
    private final Context context;
    private static SQLiteDatabase DB_READ_ONLY;
    private static SQLiteDatabase DB_READ_WRITE;
    private static String DB_PATH = null;
    private static DBHelper dbInstance;
    public static final HashMap<String, HashSet<Uri>> tables = new HashMap<String, HashSet<Uri>>();

    public static DBHelper getInstance(Context context, Object callerObject) {
//throws HelperClass.NotAllowedException
        if (!(callerObject instanceof DataProvider)) {
            return null;
//            throw new HelperClass.NotAllowedException(callerObject.getClass() + " is not allowed to create instance of this class");
        }
        Log.d(TAG, "getInstance");
        if (dbInstance != null)
            return dbInstance;
        dbInstance = new DBHelper(context);
        Log.d(TAG, "getInstance NEW");
        return dbInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
//        DB_PATH = context.getDatabasePath(DATABASE_NAME).getPath();
        DB_PATH = context.getFilesDir().getPath() + File.separator + DATABASE_NAME;
        Log.d(TAG, "DBHelper:Constructor");
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            SQLiteDatabase _db = this.getWritableDatabase();
            _db.close();
        } else {
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the
     * application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            //database does't exist yet.
            System.out.print("SQLiteException   " + e.toString());
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate : START");
    }

    public static SQLiteDatabase openDataBase(int MODE) throws NullPointerException {
        Log.d(TAG, "openDataBase : MODE : " + MODE);
        try {
            switch (MODE) {
            case READ_ONLY:
                if (DB_READ_ONLY == null || !DB_READ_ONLY.isOpen()) {
                    if (DB_READ_ONLY == null)
                        DB_READ_ONLY = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
                    else
                        synchronized (DB_READ_ONLY) {
                            DB_READ_ONLY = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
                        }
                }
                Log.d(TAG, "openDataBase : SUCCESS : " + MODE);
                return DB_READ_ONLY;
            case READ_WRITE:
                if (DB_READ_WRITE == null || !DB_READ_WRITE.isOpen()) {
                    if (DB_READ_WRITE == null) {
                        DB_READ_WRITE = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
                    } else
                        synchronized (DB_READ_WRITE) {
                            DB_READ_WRITE = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
                        }
                }
                Log.d(TAG, "openDataBase : SUCCESS : " + MODE);
                return DB_READ_WRITE;
            default:
                return MODE == READ_WRITE ? DB_READ_WRITE : DB_READ_ONLY;
            }
        } catch (SQLiteException sqliteException) {
            Log.e(TAG, "SQLiteException : " + sqliteException.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getClass() + " : " + e.getMessage());
        }
        Log.d(TAG, "openDataBase : END : NULL");
        return null;
    }

    public static void closeDatabase() {
        try {
            Log.d(TAG, "closeDatabase : START");
            if (DB_READ_WRITE != null && DB_READ_WRITE.isOpen()) {
                synchronized (DB_READ_WRITE) {
                    DB_READ_WRITE.close();
                }
            }
            if (DB_READ_ONLY != null && DB_READ_ONLY.isOpen()) {
                synchronized (DB_READ_ONLY) {
                    DB_READ_ONLY.close();
                }
            }
            DB_READ_WRITE = null;
            DB_READ_ONLY = null;
            Log.d(TAG, "closeDatabase : END");
        } catch (SQLiteException sqliteException) {
            Log.e(TAG, "SQLiteException : " + sqliteException.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.getClass() + " : " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
