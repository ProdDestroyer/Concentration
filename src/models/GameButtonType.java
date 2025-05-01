package models;

public enum GameButtonType {
    SAVE("SAVE"),
    LOAD("LOAD"),
    OPTIONS("OPTIONS"),
    EXIT("EXIT"),
    NONE("NONE");

    private final String text;

    GameButtonType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
