package wisielec.wisielec.com.enums;

public enum Difficulty {
    EASY("Łatwy","#2E8E3D"), MEDIUM("Średni","#FFBF00"), HARD("Trudny","#F44336");
    private final String text;
    private final String color;

    Difficulty(final String text,final String color) {
        this.text = text;
        this.color = color;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getColor() {return color;}
}
