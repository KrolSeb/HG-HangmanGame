package wisielec.wisielec.com.enums;

public enum Category {
    SZEREGOWY("Szeregowy"), SIERZANT("Sierżant"), CHORAZY("Chorąży"), PORUCZNIK("Porucznik"), KAPITAN("Kapitan");
    private final String text;

    Category(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
