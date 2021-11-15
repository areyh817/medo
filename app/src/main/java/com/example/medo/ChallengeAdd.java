package com.example.medo;

public class ChallengeAdd {
    String title;
    String name;

    ChallengeAdd() { }

    public ChallengeAdd(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

