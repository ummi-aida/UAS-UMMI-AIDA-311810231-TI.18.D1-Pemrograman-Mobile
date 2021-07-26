package com.example.uas.models;

public class UserModel {
    private long id;
    private String fName;
    private String email;
    private String password;
    private int role;

    public UserModel(int id, String fName, String email, String password, int role) {
        this.id = id;
        this.fName = fName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public long getId() {
        return id;
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

    public int getRole() {
        return role;
    }
}
