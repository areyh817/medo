package com.example.medo;

import java.util.HashMap;
import java.util.Map;

public class CountData {
    int oldcount;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public CountData() {
    }

    public CountData(int oldcount) {
        this.oldcount = oldcount;
    }


    public int getOldcount() {
        return oldcount;
    }

    public void setOldcount(int oldcount) {
        this.oldcount = oldcount;
    }

}
