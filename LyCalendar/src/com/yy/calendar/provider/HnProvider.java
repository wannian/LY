package com.yy.calendar.provider;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class HnProvider extends ContentProvider {

    private static final String TAG = "HnProvider";
    public static final String AUTHORITY = "hn";

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_URI_STRING = "content://" + AUTHORITY;

    public static final Uri URI_PERSON = Uri.parse("content://" + AUTHORITY + "/person");

    public static final Uri URI_BUG = Uri.parse("content://" + AUTHORITY + "/bug");

    public static final Uri URI_BUG_PROGRESS = Uri.parse("content://" + AUTHORITY + "/bug_progress");

    public static final Uri URI_OPERATE = Uri.parse("content://" + AUTHORITY + "/operate");

    public static final Uri URI_OPERATE_STEP = Uri.parse("content://" + AUTHORITY + "/operate_step");

    public static final Uri URI_OPERATE_PROGRESS = Uri.parse("content://" + AUTHORITY + "/operate_progress");

    private static final int PERSON = 1;

    private static final int PERSON_ID = 2;

    private static final int BUG = 3;

    private static final int BUG_ID = 4;

    private static final int OPERATE = 5;

    private static final int OPERATE_ID = 6;

    private static final int OPERATE_STEP = 7;

    private static final int OPERATE_STEP_ID = 8;

    private static final int OPERATE_PROGRESS = 9;

    private static final int OPERATE_PROGRESS_ID = 10;

    private static final int BUG_PROGRESS = 11;

    private static final int BUG_PROGRESS_ID = 12;

    private static final String DATABASE_NAME = "hn.db";

    public static final String TABLES_PERSONS_NAME = "persons";

    public static final String TABLES_BUGS_NAME = "bugs";

    public static final String TABLES_BUGS_PROGRESS_NAME = "bugs_progress";

    public static final String TABLES_OPERATES_NAME = "operates";

    public static final String TABLES_OPERATE_STEPS_NAME = "operate_steps";

    public static final String TABLES_OPERATE_PROGRESSES_NAME = "operate_progresses";

    static {
        URI_MATCHER.addURI(AUTHORITY, "person", PERSON);
        URI_MATCHER.addURI(AUTHORITY, "person/#", PERSON_ID);

        URI_MATCHER.addURI(AUTHORITY, "bug", BUG);
        URI_MATCHER.addURI(AUTHORITY, "bug/#", BUG_ID);

        URI_MATCHER.addURI(AUTHORITY, "operate", OPERATE);
        URI_MATCHER.addURI(AUTHORITY, "operate/#", OPERATE_ID);

        URI_MATCHER.addURI(AUTHORITY, "operate_step", OPERATE_STEP);
        URI_MATCHER.addURI(AUTHORITY, "operate_step/#", OPERATE_STEP_ID);

        URI_MATCHER.addURI(AUTHORITY, "operate_progress", OPERATE_PROGRESS);
        URI_MATCHER.addURI(AUTHORITY, "operate_progress/#", OPERATE_PROGRESS_ID);

        URI_MATCHER.addURI(AUTHORITY, "bug_progress", BUG_PROGRESS);
        URI_MATCHER.addURI(AUTHORITY, "bug_progress/#", BUG_PROGRESS_ID);
    }

    final class DatabaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 36;

        public DatabaseHelper(Context context, String name) {
            super(context, name, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            Log.d(TAG, "DatabaseHelper onCreate.");
            updateDatabase(db, 0, DATABASE_VERSION);
        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            Log.d(TAG, "DatabaseHelper onUpgrade.");
            updateDatabase(db, oldVersion, newVersion);
        }

        private void updateDatabase(SQLiteDatabase db, int fromVersion, int toVersion) {
            Log.d(TAG, "DatabaseHelper updateDatabase fromVersion:" + fromVersion + ",toVersion:" + toVersion);
            if (fromVersion < toVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLES_PERSONS_NAME);
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_PERSONS_NAME + "(" + "_id INTEGER PRIMARY KEY," + "_name TEXT," + "_password TEXT,"
                        + "_group INTEGER" + ");");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('yy', 'yy', 1);");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('zz', 'zz', 16);");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('aa', 'aa', 256);");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('bb', 'bb', 4096);");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('zz1', 'zz1', 16);");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('zz2', 'zz2', 16);");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('bb1', 'bb1', 4096);");
                db.execSQL("INSERT INTO " + TABLES_PERSONS_NAME + " (_name, _password, _group) values ('bb2', 'bb2', 4096);");

                db.execSQL("DROP TABLE IF EXISTS " + TABLES_BUGS_NAME);
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_BUGS_NAME + " (" + "_id INTEGER PRIMARY KEY," + "_location_id TEXT NOT NULL,"
                        + "_device_id TEXT NOT NULL," + "_bugbill_id TEXT NOT NULL," + "_sysunit_id TEXT NOT NULL," + "_bug_level INTEGER,"
                        + "_workflow_id TEXT NOT NULL," + "_specialty INTEGER," + "_bug_style INTEGER," + "_bug_content TEXT NOT NULL,"
                        + "_bug_desc TEXT NOT NULL," + "_dep_id TEXT NOT NULL," + "_group_id TEXT NOT NULL," + "_create_time INTEGER,"
                        + "_approver_id INTEGER," + "_create_id INTEGER," + "_state INTEGER" + ");");
                db.execSQL("DROP INDEX IF EXISTS bugs_id_index");
                db.execSQL("CREATE INDEX IF NOT EXISTS bugs_id_index on " + TABLES_BUGS_NAME + "(_approver_id);");

                db.execSQL("DROP TABLE IF EXISTS " + TABLES_BUGS_PROGRESS_NAME);
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_BUGS_PROGRESS_NAME + " (" + "_id INTEGER PRIMARY KEY," + "_bug_id INTEGER,"
                        + "_bug_content TEXT NOT NULL," + "_approve_time INTEGER," + "_approver_id INTEGER" + ");");
                db.execSQL("DROP INDEX IF EXISTS bugs_progress_bug_id_index");
                db.execSQL("CREATE INDEX IF NOT EXISTS bugs_progress_bug_id_index on " + TABLES_BUGS_PROGRESS_NAME + "(_bug_id);");
                db.execSQL("DROP INDEX IF EXISTS bugs_progress_approver_id_index");
                db.execSQL("CREATE INDEX IF NOT EXISTS bugs_progress_approver_id_index on " + TABLES_BUGS_PROGRESS_NAME + "(_approver_id);");

                db.execSQL("DROP TABLE IF EXISTS " + TABLES_OPERATES_NAME);
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_OPERATES_NAME + " (" + "_id INTEGER PRIMARY KEY," + "_name TEXT," + "_creater_id INTEGER,"
                        + "_bug_id TEXT," + "_create_time INTEGER," + "_state INTEGER DEFAULT 0," + "_finish_time INTEGER" + ");");
                db.execSQL("DROP INDEX IF EXISTS operates_creater_id_index");
                db.execSQL("CREATE INDEX IF NOT EXISTS operates_creater_id_index on " + TABLES_OPERATES_NAME + "(_creater_id);");

                db.execSQL("DROP TABLE IF EXISTS " + TABLES_OPERATE_STEPS_NAME);
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_OPERATE_STEPS_NAME + " (" + "_id INTEGER PRIMARY KEY," + "_operate TEXT,"
                        + "_ticket_id INTEGER," + "_order_id INTEGER," + "_create_time INTEGER," + "_finish_time INTEGER," + "_operater_id INTEGER,"
                        + "_operate_custodyer_id INTEGER," + "_state INTEGER DEFAULT 0" + ");");
                db.execSQL("DROP INDEX IF EXISTS operate_steps_ticket_id_index");
                db.execSQL("CREATE INDEX IF NOT EXISTS operate_steps_ticket_id_index on " + TABLES_OPERATE_STEPS_NAME + "(_ticket_id);");
                db.execSQL("DROP INDEX IF EXISTS operate_steps_state_index");
                db.execSQL("CREATE INDEX IF NOT EXISTS operates_state_index on " + TABLES_OPERATE_STEPS_NAME + "(_state);");
            }
        }
    }

    private DatabaseHelper database;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = database.getWritableDatabase();
        int table = URI_MATCHER.match(uri);

        long rowId = 0;
        switch (table) {
            case PERSON:
            case PERSON_ID:
                rowId = db.insert(HnProvider.TABLES_PERSONS_NAME, null, values);
                break;
            case BUG:
            case BUG_ID:
                values.put("_create_time", System.currentTimeMillis() / 1000);
                rowId = db.insert(HnProvider.TABLES_BUGS_NAME, null, values);
                break;
            case OPERATE:
            case OPERATE_ID:
                values.put("_create_time", System.currentTimeMillis() / 1000);
                rowId = db.insert(HnProvider.TABLES_OPERATES_NAME, null, values);
                break;
            case OPERATE_STEP:
            case OPERATE_STEP_ID:
                values.put("_create_time", System.currentTimeMillis() / 1000);
                rowId = db.insert(HnProvider.TABLES_OPERATE_STEPS_NAME, null, values);
                break;
            case BUG_PROGRESS:
            case BUG_PROGRESS_ID:
                values.put("_approve_time", System.currentTimeMillis() / 1000);
                rowId = db.insert(HnProvider.TABLES_BUGS_PROGRESS_NAME, null, values);
                break;
            default:
                throw new IllegalStateException("Unknown URL: " + uri.toString());
        }

        if (rowId > 0) {
            Uri empUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(empUri, null);
            return empUri;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext(), DATABASE_NAME);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int table = URI_MATCHER.match(uri);

        SQLiteDatabase db = database.getReadableDatabase();
        String limit = uri.getQueryParameter("limit");
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        if (uri.getQueryParameter("distinct") != null) {
            qb.setDistinct(true);
        }

        switch (table) {
            case PERSON:
                qb.setTables(TABLES_PERSONS_NAME);
                break;
            case PERSON_ID:
                qb.setTables(TABLES_PERSONS_NAME);
                qb.appendWhere("_id = " + uri.getPathSegments().get(3));
                break;
            case BUG:
                qb.setTables(TABLES_BUGS_NAME);
                break;
            case BUG_ID:
                qb.setTables(TABLES_BUGS_NAME);
                qb.appendWhere("_id = " + uri.getPathSegments().get(3));
            case OPERATE:
                qb.setTables(TABLES_OPERATES_NAME);
                break;
            case OPERATE_ID:
                qb.setTables(TABLES_OPERATES_NAME);
                qb.appendWhere("_id = " + uri.getPathSegments().get(3));
                break;
            case OPERATE_STEP:
                qb.setTables(TABLES_OPERATE_STEPS_NAME);
                break;
            case OPERATE_STEP_ID:
                qb.setTables(TABLES_OPERATE_STEPS_NAME);
                qb.appendWhere("_id = " + uri.getPathSegments().get(3));
                break;
            case BUG_PROGRESS:
                qb.setTables(TABLES_BUGS_PROGRESS_NAME);
                break;
            case BUG_PROGRESS_ID:
                qb.setTables(TABLES_BUGS_PROGRESS_NAME);
                qb.appendWhere("_id = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalStateException("Unknown URL: " + uri.toString());

        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder, limit);
        if (c != null) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        String where = null;
        SQLiteDatabase db = database.getReadableDatabase();
        int table = URI_MATCHER.match(uri);
        Log.d(TAG, "update table:" + table);
        switch (table) {
            case BUG:
                where = selection;
                count = db.update(TABLES_BUGS_NAME, values, where, selectionArgs);
                break;
            case BUG_ID:
                where = " _id = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " and (" + selection + ")";
                }
                count = db.update(TABLES_BUGS_NAME, values, where, selectionArgs);
                break;
            case OPERATE_STEP:
                where = selection;
                count = db.update(TABLES_OPERATE_STEPS_NAME, values, where, selectionArgs);
                break;
            case OPERATE_STEP_ID:
                where = " _id = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " and (" + selection + ")";
                }
                count = db.update(TABLES_OPERATE_STEPS_NAME, values, where, selectionArgs);
                break;
            case OPERATE:
                where = selection;
                count = db.update(TABLES_OPERATES_NAME, values, where, selectionArgs);
                break;
            case OPERATE_ID:
                where = " _id = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " and (" + selection + ")";
                }
                count = db.update(TABLES_OPERATES_NAME, values, where, selectionArgs);
                break;
            default:
                throw new IllegalStateException("Unknown URL: " + uri.toString());
        }
        return count;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }
}