package wisielec.wisielec.com.domain;

import java.io.Serializable;

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

    @Override
    public String toString() {
        return "Word{" + "difficultyLevel=" + difficultyLevel + ", name='" + name + '\'' + '}';
    }
}
