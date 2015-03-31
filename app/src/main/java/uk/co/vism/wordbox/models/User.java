package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ted Eriksson on 30/03/15.
 */
public class User extends RealmObject {
    @PrimaryKey
    private int id;
    private RealmList<User> friends;
    private RealmList<Sentence> sentences;

    public RealmList<User> getFriends() {
        return friends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFriends(RealmList<User> friends) {
        this.friends = friends;
    }

    public RealmList<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(RealmList<Sentence> sentences) {
        this.sentences = sentences;
    }
}
