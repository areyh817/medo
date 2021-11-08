package com.example.medo;

public class User {

    static String id;
    static String pw;
    static String name;


    User(){ }

    // 생성자
    public User(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }


    public static String getId() {
        return id;
    }

    public static void setId(String userid) { id = userid; }

    public static String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}