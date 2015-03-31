package uk.co.vism.wordbox.models;

import io.realm.RealmObject;

/**
 * Created by Ted Eriksson on 30/03/15.
 */
public class Word extends RealmObject {
    private String text;
    private int order;

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
