package uk.co.botondbutuza.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ted Eriksson on 30/03/15.
 */
public class User extends RealmObject {
    private RealmList<User> friends;
    private RealmList<Sentence> sentences;

    public RealmList<User> getFriends() {
        return friends;
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
