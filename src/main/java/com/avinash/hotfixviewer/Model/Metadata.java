package com.avinash.hotfixviewer.Model;

public abstract class Metadata<C, T> {
    private C count;
    private T details;

    public C getCount() {
        return count;
    }

    public void setCount(C count) {
        this.count = count;
    }

    public T getDetails() {
        return details;
    }

    public void setDetails(T details) {
        this.details = details;
    }
}
