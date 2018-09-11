package wisielec.wisielec.com.domain;

public class Word {
    private int difficultyLevel;
    private String name;

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Word{" +
                "difficultyLevel=" + difficultyLevel +
                ", name='" + name + '\'' +
                '}';
    }
}
