package com.example.uas.models;

public class SensorModel {
    private int id;
    private String settingName;
    private int dieHight;
    private int buckling;
    private int pass;
    private int missFeed;
    private int bodySensor;

    public SensorModel(int id, String settingName, int dieHight, int buckling, int pass,
                       int missFeed, int bodySensor) {
        this.id = id;
        this.settingName = settingName;
        this.dieHight = dieHight;
        this.buckling = buckling;
        this.pass = pass;
        this.missFeed = missFeed;
        this.bodySensor = bodySensor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public void setDieHight(int dieHight) {
        this.dieHight = dieHight;
    }

    public void setBuckling(int buckling) {
        this.buckling = buckling;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public void setMissFeed(int missFeed) {
        this.missFeed = missFeed;
    }

    public void setBodySensor(int bodySensor) {
        this.bodySensor = bodySensor;
    }

    public long getId() { return id; }

    public String getSettingName() {
        return settingName;
    }

    public int getDieHight() {
        return dieHight;
    }

    public int getBuckling() {
        return buckling;
    }

    public int getPass() {
        return pass;
    }

    public int getMissFeed() {
        return missFeed;
    }

    public int getBodySensor() {
        return bodySensor;
    }

}
