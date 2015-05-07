package uk.co.vism.wordbox.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Word extends RealmObject {
    @PrimaryKey
    private int id;
    private String text;
    private int order;

    public Word() {}

    public Word(int order, String text) {
        setOrder(order);
        setText(text);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getOrder() {
        return order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}