package uk.co.vism.wordbox.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ted Eriksson on 30/03/15.
 */
public class Sentence extends RealmObject {
    private RealmList<Word> words;

    public RealmList<Word> getWords() {
        return words;
    }

    public void setWords(RealmList<Word> words) {
        this.words = words;
    }
}
