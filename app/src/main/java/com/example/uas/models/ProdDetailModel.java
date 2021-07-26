package com.example.uas.models;

public class ProdDetailModel {
    private long id;
    private String customer;
    private String model;
    private int minTarget;
    private int runningTime;
    private String partCode;
    private String partName;
    private String processName;
    private int planQty;
    private String date;

    private String settingName;
    private int dieHight;
    private int buckling;
    private int pass;
    private int missFeed;
    private int bodySensor;

    private int weight;
    private String tagNumber;
    private String start;
    private String finish;
    private int totalTime;
    private int result;

    private String fName;
    private String email;
    private String password;
    private String id_user;
    private int role;

    public ProdDetailModel(int id, String customer, String model, int minTarget, int runningTime, String partCode, String partName,
                           String processName, int planQty, String date,
                           String settingName, int dieHight, int buckling, int pass, int missFeed, int bodySensor,
                           int weight, String tagNumber, String start, String finish, int totalTime, int result,
                           String fName, String email, String password, String id_user, int role) {
        this.id = id;
        this.customer =customer;
        this.model = model;
        this.minTarget = minTarget;
        this.runningTime = runningTime;
        this.partCode = partCode;
        this.partName =partName;
        this.processName = processName;
        this.planQty = planQty;
        this.date = date;

        this.settingName = settingName;
        this.dieHight = dieHight;
        this.buckling = buckling;
        this.pass = pass;
        this.missFeed = missFeed;
        this.bodySensor = bodySensor;

        this.weight = weight;
        this.tagNumber = tagNumber;
        this.start = start;
        this.finish = finish;
        this.totalTime = totalTime;
        this.result = result;

        this.fName = fName;
        this.email = email;
        this.password = password;
        this.id_user = id_user;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getModel() {
        return model;
    }

    public int getMinTarget() {
        return minTarget;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public String getPartCode() {
        return partCode;
    }

    public String getPartName() {
        return partName;
    }

    public String getProcessName() {
        return processName;
    }

    public int getPlanQty() {
        return planQty;
    }

    public String getDate() {
        return date;
    }

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

    public int getWeight() {
        return weight;
    }

    public String getTagNumber() {
        return tagNumber;
    }

    public String getStart() {
        return start;
    }

    public String getFinish() {
        return finish;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getResult() {
        return result;
    }

    public String getFName() {
        return fName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getIdUser() {
        return id_user;
    }

    public int getRole() {
        return role;
    }
}
