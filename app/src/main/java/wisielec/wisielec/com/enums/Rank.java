package wisielec.wisielec.com.enums;

public enum Rank {
    SZEREGOWY("Szeregowy"), SIERZANT("Sierżant"), CHORAZY("Chorąży"), PORUCZNIK("Porucznik"), KAPITAN("Kapitan");
    private final String text;

    Rank(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
