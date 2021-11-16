package com.example.medo;

public class ChallengeAdd {
    String name;
    String idToken;
    String title;

    public ChallengeAdd(String name, String idToken, String title) {
        this.name = name;
        this.idToken = idToken;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
