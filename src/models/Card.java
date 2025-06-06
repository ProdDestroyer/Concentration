package models;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Card {

    private Vec2D center;
    private GameImage gameImage;
    private Vec2D dimensions;
    private String cardName;
    private boolean locked;
    private boolean removed;
    private boolean scoring;

    public Card(Vec2D center, Vec2D dimensions, String cardName) {
        this.center = center;
        this.dimensions = dimensions;
        this.cardName = cardName;
        initImage();
    }

    public Card(Vec2D center, Vec2D dimensions, String cardName, BufferedImage bufferedImage, BufferedImage backImage, BufferedImage hoveredBackImage) {
        this.center = center;
        this.dimensions = dimensions;
        this.cardName = cardName;
        initImage(bufferedImage, backImage, hoveredBackImage);
    }

    public void transform(float dx) {
        gameImage.transform(center, dimensions.getX(), dimensions.getY(), locked);
    }

    private void initImage() {
        gameImage = new GameImage(cardName, this);
    }

    private void initImage(BufferedImage bufferedImage, BufferedImage backImage, BufferedImage hoveredBackImage) {
        gameImage = new GameImage(cardName, this, bufferedImage, backImage, hoveredBackImage);
    }

    public int lock() {
        int lockResult = 0;
        if(locked) {
            lockResult = 1;
        } else {
            score();
        }
        locked = true;
        return lockResult;
    }

    public void unlock() {
        locked = false;
    }

    public void cover() {
        gameImage.cover();
    }

    public boolean isRevealed() {
        return gameImage.isRevealed();
    }

    public Vec2D getCenter() {
        return center;
    }

    public GameImage getGameImage() {
        return gameImage;
    }

    public BufferedImage getImage() {
        return gameImage.getImage();
    }

    public AffineTransform getAffineTransform() {
        return gameImage.getAffineTransform();
    }

    public Vec2D getDimensions() {
        return dimensions;
    }

    public String getCardName() {
        return cardName;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void remove() {
        removed = true;
        gameImage.remove();
    }

    public Rectangle2D getTargetRectangle() {
        return new Rectangle((int) ((center.x - dimensions.x / 2.0f) + dimensions.x * 0.1f),
                (int) ((center.y - dimensions.y / 2.0f) + dimensions.y * 0.1f), (int) (dimensions.x * 0.8f),
                (int) (dimensions.y * 0.8f));
    }

    public boolean isContained(Vec2D coords) {
        return getTargetRectangle().contains(coords.x, coords.y);
    }

    public void hover() {
        gameImage.hover();
    }

    public void unhover() {
        gameImage.unhover();
    }

    public boolean isHovered() {
        return gameImage.isHovered();
    }

    public void setDimensions(Vec2D dimensions) {
        this.dimensions = dimensions;
    }

    public void setCenter(Vec2D center) {
        this.center = center;
        transform(0.0f);
    }

    public void score() {
        scoring = true;
    }

    public boolean consumeScore() {
        boolean result = scoring;
        scoring = false;
        return result;
    }
}
