package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class TempUser extends RealmObject {
    private String username;
    private RealmList<TempUser> friends;
    private RealmList<TempSentence> sentences;

    public String getUsername() {
        return username;
    }

    public RealmList<TempUser> getFriends() {
        return friends;
    }

    public RealmList<TempSentence> getSentences() {
        return sentences;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFriends(RealmList<TempUser> friends) {
        this.friends = friends;
    }

    public void setSentences(RealmList<TempSentence> sentences) {
        this.sentences = sentences;
    }
}