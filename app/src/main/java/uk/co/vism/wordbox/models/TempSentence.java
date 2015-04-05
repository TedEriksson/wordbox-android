package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ted Eriksson on 05/04/15.
 */
public class TempSentence extends RealmObject {
    private int user_id;
    private RealmList<TempWord> words;
    private int hearts;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public RealmList<TempWord> getWords() {
        return words;
    }

    public void setWords(RealmList<TempWord> words) {
        this.words = words;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }
}
