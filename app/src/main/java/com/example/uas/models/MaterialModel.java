package com.example.uas.models;

public class MaterialModel {
    private int id;
    private int weight;
    private String tagNumber;
    private String start;
    private String finish;
    private int totalTime;
    private int result;

    public MaterialModel(int id, int weight, String tagNumber, String start, String finish,
                       int totalTime, int result) {
        this.id = id;
        this.weight = weight;
        this.tagNumber = tagNumber;
        this.start = start;
        this.finish = finish;
        this.totalTime = totalTime;
        this.result = result;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTagNumber(String tagNumber) {
        this.tagNumber = tagNumber;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getId() { return id; }

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

}
