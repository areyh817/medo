package com.example.medo;

import com.google.firebase.database.Transaction;

public class RankingData {
    String name;
    Transaction.Result cnt;
    String idToken;

    public RankingData(String name, Transaction.Result cnt, String idToken) {
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

    public Transaction.Result getCnt() {
        return cnt;
    }

    public void setCnt(Transaction.Result cnt) {
        this.cnt = cnt;
    }
}
