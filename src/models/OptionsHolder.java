package models;

public class OptionsHolder {
    private Vec2D resolution;
    private Vec2D tilesSize;

    public Vec2D getResolution() {
        return resolution;
    }

    public Vec2D getTilesSize() {
        return tilesSize;
    }

    public void setResolution(Vec2D resolution) {
        this.resolution = resolution;
    }

    public void setTilesSize(Vec2D tilesSize) {
        this.tilesSize = tilesSize;
    }
}
