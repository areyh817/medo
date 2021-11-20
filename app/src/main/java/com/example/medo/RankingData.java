package com.example.medo;

import com.google.firebase.database.Transaction;

public class RankingData {
    String name;
    String idToken;
    String data;

    public RankingData(String name, String idToken, String data) {
        this.name = name;
        this.idToken = idToken;
        this.data = data;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
