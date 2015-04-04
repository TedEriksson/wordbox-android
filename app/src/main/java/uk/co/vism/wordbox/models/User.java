package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private int id;
    private String username;
    private RealmList<User> friends;
    private RealmList<Sentence> sentences;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public RealmList<User> getFriends() {
        return friends;
    }

    public RealmList<Sentence> getSentences() {
        return sentences;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFriends(RealmList<User> friends) {
        this.friends = friends;
    }

    public void setSentences(RealmList<Sentence> sentences) {
        this.sentences = sentences;
    }
}