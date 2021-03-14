package com.avinash.hotfixviewer.Model;

public abstract class Metadata<C, T> {
    private int count;
    private T details;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getDetails() {
        return details;
    }

    public void setDetails(T details) {
        this.details = details;
    }
}
