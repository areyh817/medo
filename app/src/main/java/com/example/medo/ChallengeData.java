package com.example.medo;

public class ChallengeData {
    String title;
    String content;
    private String idToken;

    ChallengeData(){}

    public ChallengeData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
