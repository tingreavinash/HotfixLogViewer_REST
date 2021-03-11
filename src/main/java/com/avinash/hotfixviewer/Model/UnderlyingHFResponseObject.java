package com.avinash.hotfixviewer.Model;

import java.util.Map;

public class UnderlyingHFResponseObject {
    private int count;
    private Map<Integer, String> Records;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<Integer, String> getRecords() {
        return Records;
    }

    public void setRecords(Map<Integer, String> records) {
        Records = records;
    }

    @Override
    public String toString() {
        return "UnderlyingHFResponseObject [count=" + count + ", Records=" + Records + "]";
    }


}
