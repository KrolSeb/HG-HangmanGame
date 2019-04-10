package com.android.hangman.domain;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Word implements Serializable {
    private int difficultyLevel;
    private String name;

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getName() {
        return name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Word{" + "difficultyLevel=" + difficultyLevel + ", name='" + name + '\'' + '}';
    }
}
