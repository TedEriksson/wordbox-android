package uk.co.vism.wordbox.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WordRequest extends RealmObject {
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
