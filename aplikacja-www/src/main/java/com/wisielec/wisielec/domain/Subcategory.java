package com.wisielec.wisielec.domain;

import java.util.List;

/**
 * Created by Emerex on 14/01/2018.
 */
public class Subcategory {
    private List<Word> wordList;

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "wordList=" + wordList +
                '}';
    }
}
