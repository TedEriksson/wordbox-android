package uk.co.vism.wordbox.managers;

import io.realm.RealmList;
import uk.co.vism.wordbox.models.Sentence;
import uk.co.vism.wordbox.models.Word;

public class SentenceManager
{
    private Sentence sentence;
    private RealmList<Word> words;

    public SentenceManager()                 { sentence = new Sentence(); words = new RealmList<>(); }

    public void addWord(Word word)      { words.add(word); }
    public void commit()                { sentence.setWords(words); }
    public int count()                  { return words.size(); }

    public String toString()
    {
        String ret = "";
        for(Word word : words)
            ret += word.getText() + ", ";
        return ret;
    }
}
