package wisielec.wisielec.com.enums;

public enum Difficulty {
    LATWY("Łatwy"), SREDNI("Średni"), TRUDNY("Trudny");
    private final String text;

    Difficulty(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
