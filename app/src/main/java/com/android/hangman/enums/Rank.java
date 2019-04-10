package com.android.hangman.enums;

import androidx.annotation.NonNull;

public enum Rank {
    LACK_OF_DEGREE("Brak stopnia"),PRIVATE("Szeregowy"), CORPORAL("Kapral"), SERGEANT ("Sierżant"), WARRANT_OFFICER("Chorąży"),
    LIEUTENANT("Porucznik"), CAPTAIN("Kapitan"), MAJOR("Major"), COLONEL("Pułkownik"), GENERAL("Generał");
    private final String text;

    Rank(final String text) {
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text;
    }
}
