package models;

public class Utilities {

    public boolean pendingWindowResize;

    public Vec2D windowDimensions;

    public static final Vec2D[] tilesDimensions = { new Vec2D(3, 4), new Vec2D(4, 4), new Vec2D(4, 5), new Vec2D(6, 6) };

    public float windowWidth() {
        return windowDimensions.x;
    }

    public float windowHeight() {
        return windowDimensions.y;
    }

}