package Renting;

public enum FilmType {
    NEW_RELEASE("New release"),
    REGULAR_FILM("Regular rental"),
    OLD_FILM("Old film");

    private final String toString;

    FilmType(String value) {
        toString = value;
    }

    @Override
    public String toString() {
        return toString;
    }
}