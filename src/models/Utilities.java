package models;

public class Utilities {

    public boolean pendingWindowResize;

    public Vec2D windowDimensions;

    public float windowWidth() {
        return windowDimensions.x;
    }
    public float windowHeight() {
        return windowDimensions.y;
    }

}  