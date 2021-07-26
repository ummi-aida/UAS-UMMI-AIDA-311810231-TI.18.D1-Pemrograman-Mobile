package com.example.uas.dataBase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.uas.utils.Config;

public class DatabaseHelper extends SQLiteOpenHelper {

    @SuppressLint("StaticFieldLeak")
    private static DatabaseHelper databaseHelper;

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelper == null) databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Config.TABLE_USER + "("
                + Config.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_USER_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_USER_EMAIL + " TEXT NOT NULL, "
                + Config.COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
                + Config.COLUMN_USER_ROLE + " INTEGER NOT NULL "
                + ")";
        String CREATE_MATERIAL_TABLE = "CREATE TABLE " + Config.TABLE_MATERIAL + "("
                + Config.COLUMN_MATERIAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_MATERIAL_WEIGHT + " INTEGER NOT NULL, "
                + Config.COLUMN_MATERIAL_TAG_NUMBER + " TEXT NOT NULL, "
                + Config.COLUMN_MATERIAL_START + " TEXT NOT NULL, "
                + Config.COLUMN_MATERIAL_FINISH + " TEXT NOT NULL, "
                + Config.COLUMN_MATERIAL_TOTAL_TIME + " INTEGER NOT NULL, "
                + Config.COLUMN_MATERIAL_RESULT + " INTEGER NOT NULL "
                + ")";
        String CREATE_SENSOR_TABLE = "CREATE TABLE " + Config.TABLE_SENSOR + "("
                + Config.COLUMN_SENSOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_SETTING_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_SENSOR_DIE_HIGHT_DETECTOR + " INTEGER NOT NULL, "
                + Config.COLUMN_SENSOR_BUCKLING + " INTEGER NOT NULL, "
                + Config.COLUMN_SENSOR_PASS + " INTEGER NOT NULL, "
                + Config.COLUMN_SENSOR_MISS_FEED + " INTEGER NOT NULL, "
                + Config.COLUMN_SENSOR_BODY + " INTEGER NOT NULL "
                + ")";
        String CREATE_PRODUCTION_TABLE = "CREATE TABLE " + Config.TABLE_PRODUCTION + "("
                + Config.COLUMN_PRODUCTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PRODUCTION_CUSTOMER + " TEXT NOT NULL, "
                + Config.COLUMN_PRODUCTION_MODEL + " TEXT NOT NULL, "
                + Config.COLUMN_PRODUCTION_MIN_TARGET + " INTEGER NOT NULL, "
                + Config.COLUMN_PRODUCTION_RUNNING_TIME + " INTEGER NOT NULL, "
                + Config.COLUMN_PRODUCTION_PART_CODE + " TEXT NOT NULL, "
                + Config.COLUMN_PRODUCTION_PART_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_PRODUCTION_PROCESS_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_PRODUCTION_PLAN_QTY + " INTEGER NOT NULL, "
                + Config.COLUMN_PRODUCTION_DATE + " TEXT NOT NULL, "
                + Config.COLUMN_PRODUCTION_ID_USER + " INTEGER NOT NULL, "
                + Config.COLUMN_PRODUCTION_ID_MATERIAL + " INTEGER NOT NULL, "
                + Config.COLUMN_PRODUCTION_ID_SENSOR + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + Config.COLUMN_PRODUCTION_ID_USER + ") REFERENCES " + Config.TABLE_USER + "(" + Config.COLUMN_USER_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + Config.COLUMN_PRODUCTION_ID_MATERIAL + ") REFERENCES " + Config.TABLE_MATERIAL + "(" + Config.COLUMN_MATERIAL_ID + ") ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + Config.COLUMN_PRODUCTION_ID_SENSOR + ") REFERENCES " + Config.TABLE_SENSOR + "(" + Config.COLUMN_SENSOR_ID + ") ON UPDATE CASCADE ON DELETE CASCADE"
                + ")";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_MATERIAL_TABLE);
        db.execSQL(CREATE_SENSOR_TABLE);
        db.execSQL(CREATE_PRODUCTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_MATERIAL);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_SENSOR);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PRODUCTION);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
