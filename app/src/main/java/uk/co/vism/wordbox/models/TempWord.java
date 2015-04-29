package uk.co.vism.wordbox.models;

import io.realm.RealmObject;

public class TempWord extends RealmObject {
    private String text;
    private int order;

    public TempWord() {
    }

    public TempWord(int order, String text) {
        setOrder(order);
        setText(text);
    }

    public String getText() {
        return text;
    }

    public int getOrder() {
        return order;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}