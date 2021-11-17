package com.example.medo;

public class RankingData {
    String name;
    int cnt;
    String idToken;

    public RankingData(String name, int cnt, String idToken) {
        this.name = name;
        this.cnt = cnt;
        this.idToken = idToken;
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

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
