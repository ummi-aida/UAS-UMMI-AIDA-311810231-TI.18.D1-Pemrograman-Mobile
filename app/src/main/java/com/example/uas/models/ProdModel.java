package com.example.uas.models;

public class ProdModel {
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
    private int idUser;
    private int idSensor;
    private int idMaterial;

    public ProdModel(int id, String customer, String model, int minTarget, int runningTime, String partCode, String partName,
                     String processName, int planQty, String date, int idUser, int idSensor, int idMaterial) {
        this.id = id;
        this.customer = customer;
        this.model = model;
        this.minTarget = minTarget;
        this.runningTime = runningTime;
        this.partCode = partCode;
        this.partName = partName;
        this.processName = processName;
        this.planQty = planQty;
        this.date = date;
        this.idUser = idUser;
        this.idSensor = idSensor;
        this.idMaterial = idMaterial;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCustomer(String fName) {
        this.customer = customer;
    }

    public void setModel(String email) {
        this.model = model;
    }

    public void setMinTarget(String password) {
        this.minTarget = minTarget;
    }

    public void setRunningTime(int role) {
        this.runningTime = runningTime;
    }

    public void setPartCode(int role) {
        this.partCode = partCode;
    }

    public void setPartName(int role) {
        this.partName = partName;
    }

    public void setProcessName(int role) {
        this.processName = processName;
    }

    public void setPlanQty(int role) {
        this.planQty = planQty;
    }

    public void setDate(int role) {
        this.date = date;
    }

    public void setIdUser(int role) {
        this.idUser = idUser;
    }

    public void setIdSensor(int role) {
        this.idSensor = idSensor;
    }

    public void setIdMaterial(int role) { this.idMaterial = idMaterial; }

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

    public int getIdUser() {
        return idUser;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public int getIdMaterial() { return idMaterial; }
}
