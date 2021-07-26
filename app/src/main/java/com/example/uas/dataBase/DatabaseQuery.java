package com.example.uas.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.example.uas.models.MaterialModel;
import com.example.uas.models.ProdDetailModel;
import com.example.uas.models.ProdModel;
import com.example.uas.models.SensorModel;
import com.example.uas.models.UserModel;
import com.example.uas.utils.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQuery {

    private Context context;

    public DatabaseQuery(Context context){
        this.context = context;
    }

    public long insertUser (UserModel userModel) {
        long result = -1;
        Cursor cursor = null;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Config.COLUMN_USER_NAME, userModel.getFName());
        cv.put(Config.COLUMN_USER_EMAIL, userModel.getEmail());
        cv.put(Config.COLUMN_USER_PASSWORD, userModel.getPassword());
        cv.put(Config.COLUMN_USER_ROLE, userModel.getRole());

        try {
            cursor = db.query(Config.TABLE_USER, null,
                    Config.COLUMN_USER_EMAIL + " = ? ",
                    new String[]{userModel.getEmail()}, null, null, null);

            if (!cursor.moveToFirst()) {
                try {
                    result = db.insertOrThrow(Config.TABLE_USER,null, cv);
                } catch (SQLiteException e) {
                    Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    db.close();
                }
            } else {
                result = -1;
            }
        } catch (Exception e) {
            Log.d("Error Query => ", e.getMessage().toString());
            Toast.makeText(context, "Operation Failed", Toast.LENGTH_SHORT).show();
        }  finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return result;
    }

    public UserModel checkUserLogin(String email, String pass) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        UserModel userModel = null;

        try {
            cursor = db.query(Config.TABLE_USER, null,
                    Config.COLUMN_USER_EMAIL + " = ? AND " + Config.COLUMN_USER_PASSWORD + " = ? ",
                    new String[]{email, pass}, null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ID));
                String fName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_NAME));
                String emailData = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_EMAIL));
                int role = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ROLE));

                userModel = new UserModel(id, fName, emailData, null, role);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return userModel;
    }

    public UserModel getUserById(String user_id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        UserModel userModel = null;

        try {
            cursor = db.query(Config.TABLE_USER, null,
                    Config.COLUMN_USER_ID + " = ? ",
                    new String[]{user_id}, null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ID));
                String fName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_NAME));
                String emailData = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_EMAIL));
                int role = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ROLE));

                userModel = new UserModel(id, fName, emailData, null, role);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return userModel;
    }

    public long insertSensor(SensorModel sensorModel) {
        long result = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Config.COLUMN_SETTING_NAME, sensorModel.getSettingName());
        cv.put(Config.COLUMN_SENSOR_DIE_HIGHT_DETECTOR, sensorModel.getDieHight());
        cv.put(Config.COLUMN_SENSOR_BUCKLING, sensorModel.getBuckling());
        cv.put(Config.COLUMN_SENSOR_PASS, sensorModel.getPass());
        cv.put(Config.COLUMN_SENSOR_MISS_FEED, sensorModel.getMissFeed());
        cv.put(Config.COLUMN_SENSOR_BODY, sensorModel.getBodySensor());

        try {
            result = db.insertOrThrow(Config.TABLE_SENSOR,null, cv);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
        return result;
    }

    public List<SensorModel> getAllSensorSetting() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        String SELECT_QUERY = "SELECT * FROM " + Config.TABLE_SENSOR;
        cursor = db.rawQuery(SELECT_QUERY, null);

        try {
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<SensorModel> sensorList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_ID));
                        String settingName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SETTING_NAME));
                        int dieHight = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_DIE_HIGHT_DETECTOR));
                        int buckling = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_BUCKLING));
                        int pass = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_PASS));
                        int missFeed = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_MISS_FEED));
                        int bodySensor = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_BODY));

                        sensorList.add(new SensorModel(id, settingName, dieHight, buckling, pass, missFeed, bodySensor));
                    }   while (cursor.moveToNext());

                    return sensorList;
                }
        } catch (Exception e) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null) cursor.close();
            db.close();
        }

        return Collections.emptyList();
    }

    public long insertMaterial(MaterialModel materialModel) {
        long result = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Config.COLUMN_MATERIAL_WEIGHT, materialModel.getWeight());
        cv.put(Config.COLUMN_MATERIAL_TAG_NUMBER, materialModel.getTagNumber());
        cv.put(Config.COLUMN_MATERIAL_START, materialModel.getStart());
        cv.put(Config.COLUMN_MATERIAL_FINISH, materialModel.getFinish());
        cv.put(Config.COLUMN_MATERIAL_TOTAL_TIME, materialModel.getTotalTime());
        cv.put(Config.COLUMN_MATERIAL_RESULT, materialModel.getResult());

        try {
            result = db.insertOrThrow(Config.TABLE_MATERIAL,null, cv);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
        return result;
    }

    public List<MaterialModel> getAllMaterial() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        String SELECT_QUERY = "SELECT * FROM " + Config.TABLE_MATERIAL;
        cursor = db.rawQuery(SELECT_QUERY, null);

        try {
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<MaterialModel> materialList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATERIAL_ID));
                        int weight = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATERIAL_WEIGHT));
                        String tagNumber = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATERIAL_TAG_NUMBER));
                        String start = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATERIAL_START));
                        String finish = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATERIAL_FINISH));
                        int totalTime = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATERIAL_TOTAL_TIME));
                        int result = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATERIAL_RESULT));

                        materialList.add(new MaterialModel(id, weight, tagNumber, start, finish, totalTime, result));
                    }   while (cursor.moveToNext());

                    return materialList;
                }
        } catch (Exception e) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null) cursor.close();
            db.close();
        }

        return Collections.emptyList();
    }

    public long insertDailyProd(ProdModel prodModel) {
        long result = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Config.COLUMN_PRODUCTION_CUSTOMER, prodModel.getCustomer());
        cv.put(Config.COLUMN_PRODUCTION_MODEL, prodModel.getModel());
        cv.put(Config.COLUMN_PRODUCTION_MIN_TARGET, prodModel.getMinTarget());
        cv.put(Config.COLUMN_PRODUCTION_RUNNING_TIME, prodModel.getRunningTime());
        cv.put(Config.COLUMN_PRODUCTION_PART_CODE, prodModel.getPartCode());
        cv.put(Config.COLUMN_PRODUCTION_PART_NAME, prodModel.getPartName());
        cv.put(Config.COLUMN_PRODUCTION_PROCESS_NAME, prodModel.getProcessName());
        cv.put(Config.COLUMN_PRODUCTION_PLAN_QTY, prodModel.getPlanQty());
        cv.put(Config.COLUMN_PRODUCTION_DATE, prodModel.getDate());
        cv.put(Config.COLUMN_PRODUCTION_ID_USER, prodModel.getIdUser());
        cv.put(Config.COLUMN_PRODUCTION_ID_SENSOR, prodModel.getIdSensor());
        cv.put(Config.COLUMN_PRODUCTION_ID_MATERIAL, prodModel.getIdMaterial());

        try {
            result = db.insertOrThrow(Config.TABLE_PRODUCTION,null, cv);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
        return result;
    }

    public Cursor readAllDataProd() {
        String query = "SELECT * FROM " + Config.TABLE_PRODUCTION;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public long updateDailyProd(ProdModel prodModel) {
        long result = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Config.COLUMN_PRODUCTION_CUSTOMER, prodModel.getCustomer());
        cv.put(Config.COLUMN_PRODUCTION_MODEL, prodModel.getModel());
        cv.put(Config.COLUMN_PRODUCTION_MIN_TARGET, prodModel.getMinTarget());
        cv.put(Config.COLUMN_PRODUCTION_RUNNING_TIME, prodModel.getRunningTime());
        cv.put(Config.COLUMN_PRODUCTION_PART_CODE, prodModel.getPartCode());
        cv.put(Config.COLUMN_PRODUCTION_PART_NAME, prodModel.getPartName());
        cv.put(Config.COLUMN_PRODUCTION_PROCESS_NAME, prodModel.getProcessName());
        cv.put(Config.COLUMN_PRODUCTION_PLAN_QTY, prodModel.getPlanQty());
        cv.put(Config.COLUMN_PRODUCTION_DATE, prodModel.getDate());
        cv.put(Config.COLUMN_PRODUCTION_ID_USER, prodModel.getIdUser());
        cv.put(Config.COLUMN_PRODUCTION_ID_SENSOR, prodModel.getIdSensor());
        cv.put(Config.COLUMN_PRODUCTION_ID_MATERIAL, prodModel.getIdMaterial());
        Log.d("ID Prod => ", String.valueOf(prodModel.getId()));

        try {
            result = db.update(Config.TABLE_PRODUCTION, cv, "_id=?",
                    new String[]{String.valueOf(prodModel.getId())});
        } catch (SQLiteException e) {
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            db.close();
        }
        return result;
    }

    public long deleteDailyProdById(long idProd) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = db.delete(Config.TABLE_PRODUCTION,
                    Config.COLUMN_PRODUCTION_ID + " = ? ",
                    new String[]{ String.valueOf(idProd)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.close();
        }
        return deletedRowCount;
    }

    public ProdDetailModel getProdById(long idProd) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = null;
        ProdDetailModel prodDetailModel = null;
        String SELECT_QUERY = "SELECT * FROM " + Config.TABLE_PRODUCTION
                + " INNER JOIN " + Config.TABLE_SENSOR + " ON " + Config.TABLE_SENSOR + "._id="
                + Config.TABLE_PRODUCTION + "." + Config.COLUMN_PRODUCTION_ID_SENSOR + " INNER JOIN " + Config.TABLE_MATERIAL + " ON "
                + Config.TABLE_MATERIAL + "._id=" + Config.TABLE_PRODUCTION + "." + Config.COLUMN_PRODUCTION_ID_MATERIAL + " INNER JOIN "
                + Config.TABLE_USER + " ON " + Config.TABLE_USER + "._id=" +  Config.TABLE_PRODUCTION + "." + Config.COLUMN_PRODUCTION_ID_USER
                + " WHERE " + Config.TABLE_PRODUCTION + "." + Config.COLUMN_PRODUCTION_ID + "=?";

        try {
            cursor = db.rawQuery(SELECT_QUERY, new String[] {String.valueOf(idProd)});
            if(cursor.moveToFirst()) {
                long id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_ID));
                String customer = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_CUSTOMER));
                String model = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_MODEL));;
                int minTarget = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_MIN_TARGET));
                int runningTime = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_RUNNING_TIME));
                String partCode = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_PART_CODE));
                String partName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_PART_NAME));
                String processName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_PROCESS_NAME));
                int planQty = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_PLAN_QTY));
                String date = cursor.getString(cursor.getColumnIndex(Config.COLUMN_PRODUCTION_DATE));

                String settingName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SETTING_NAME));
                int dieHight = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_DIE_HIGHT_DETECTOR));
                int buckling = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_BUCKLING));
                int pass = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_PASS));
                int missFeed = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_MISS_FEED));
                int bodySensor = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_SENSOR_BODY));

                int weight = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATERIAL_WEIGHT));
                String tagNumber = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATERIAL_TAG_NUMBER));
                String start = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATERIAL_START));
                String finish = cursor.getString(cursor.getColumnIndex(Config.COLUMN_MATERIAL_FINISH));
                int totalTime = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATERIAL_TOTAL_TIME));
                int result = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_MATERIAL_RESULT));

                String fName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_NAME));
                String email = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_PASSWORD));
                String idUser = cursor.getString(cursor.getColumnIndex(Config.COLUMN_USER_ID));
                int role = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_USER_ROLE));

                prodDetailModel = new ProdDetailModel((int) id, customer, model, minTarget, runningTime, partCode, partName, processName, planQty, date,
                        settingName, dieHight, buckling, pass, missFeed, bodySensor,
                        weight, tagNumber, start, finish, totalTime, result,
                        fName, email, password, idUser, role);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null) cursor.close();
            db.close();
        }
        return prodDetailModel;
    }

}
