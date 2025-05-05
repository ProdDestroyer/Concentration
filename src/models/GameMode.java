package models;

public enum GameMode {

    OPTIONS("OPTIONS"),
    LOAD("LOAD"),
    SAVE("SAVE"),
    GRAPHICS("GRAPHICS"),
    PLAYING("PLAYING"),
    GAME_SETUP("GAME");

    private final String text;

    GameMode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
}
