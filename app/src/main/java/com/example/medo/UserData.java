package com.example.medo;

public class UserData {
    private String name;
    private String idToken;
    private String email;
    private String pw;

    public UserData() { }

    public UserData(String name, String idToken, String email, String pw) {
        this.name = name;
        this.idToken = idToken;
        this.email = email;
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}