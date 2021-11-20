package com.example.medo;

public class BasicData {

    int cnt;
    String idToken;
    String name;


    public BasicData(int cnt, String idToken, String name) {
        this.cnt = cnt;
        this.idToken = idToken;
        this.name = name;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
