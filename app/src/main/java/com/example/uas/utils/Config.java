package com.example.uas.utils;

public class Config {

    public static final String DATABASE_NAME = "DataProduction.db";

    //column names of user table
    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_ROLE = "role";

    //column names of material
    public static final String TABLE_MATERIAL = "material";
    public static final String COLUMN_MATERIAL_ID = "_id";
    public static final String COLUMN_MATERIAL_WEIGHT = "weight";
    public static final String COLUMN_MATERIAL_TAG_NUMBER = "tag_number";
    public static final String COLUMN_MATERIAL_START = "start";
    public static final String COLUMN_MATERIAL_FINISH = "finish";
    public static final String COLUMN_MATERIAL_TOTAL_TIME = "total_time";
    public static final String COLUMN_MATERIAL_RESULT = "result";

    //column names of sensor table
    public static final String TABLE_SENSOR = "sensor";
    public static final String COLUMN_SENSOR_ID = "_id";
    public static final String COLUMN_SETTING_NAME = "sensor_setting_name";
    public static final String COLUMN_SENSOR_DIE_HIGHT_DETECTOR = "die_hight_detector";
    public static final String COLUMN_SENSOR_BUCKLING = "buckling_sensor";
    public static final String COLUMN_SENSOR_PASS = "pass_sensor";
    public static final String COLUMN_SENSOR_MISS_FEED = "miss_feed_sensor";
    public static final String COLUMN_SENSOR_BODY = "body_sensor";

    //column names of daily production
    public static final String TABLE_PRODUCTION = "daily_production";
    public static final String COLUMN_PRODUCTION_ID = "_id";
    public static final String COLUMN_PRODUCTION_CUSTOMER = "customer";
    public static final String COLUMN_PRODUCTION_MODEL = "model";
    public static final String COLUMN_PRODUCTION_MIN_TARGET = "min_target";
    public static final String COLUMN_PRODUCTION_RUNNING_TIME = "running_time";
    public static final String COLUMN_PRODUCTION_PART_CODE = "part_code";
    public static final String COLUMN_PRODUCTION_PART_NAME = "part_name";
    public static final String COLUMN_PRODUCTION_PROCESS_NAME = "process_name";
    public static final String COLUMN_PRODUCTION_PLAN_QTY = "plan_qty";
    public static final String COLUMN_PRODUCTION_DATE = "date";
    public static final String COLUMN_PRODUCTION_ID_USER = "id_user";
    public static final String COLUMN_PRODUCTION_ID_MATERIAL = "id_material";
    public static final String COLUMN_PRODUCTION_ID_SENSOR = "id_sensor";
    public static final String PROD_USR_CONSTRAINT = "production_usr_unique";
    public static final String PROD_MTR_CONSTRAINT = "production_mtr_unique";
    public static final String PROD_SENSOR_CONSTRAINT = "production_sensor_unique";
}
