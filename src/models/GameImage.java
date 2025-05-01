package models;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameImage {

    private BufferedImage backImage;
    private BufferedImage hoveredBackImage;
    private BufferedImage iconImage;
    private AffineTransform at;
    private Card parentCard;
    private float shearX;
    private float shearXDelta;
    private boolean iconShown;
    private boolean revealed;
    private boolean covering;
    private boolean removing;
    private boolean removed;
    private boolean hovered;

    private static final String IMAGES_FOLDER_PATH = "/img/";

    public GameImage(String imgFileName, Card parentCard) {
        shearX = 0.9f;
        shearXDelta = -0.05f;
        this.parentCard = parentCard;
        try {
            iconImage = ImageIO.read(getClass().getResource(IMAGES_FOLDER_PATH + imgFileName));
            backImage = ImageIO.read(getClass().getResource(IMAGES_FOLDER_PATH + "BackSideCard.png"));
            hoveredBackImage = ImageIO.read(getClass().getResource(IMAGES_FOLDER_PATH + "HoveredBackSideCard.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        at = new AffineTransform();
    }

    public void transform(Vec2D position, float cardWidth, float cardHeight, boolean locked) {
        if (!removed) {
            at.setToIdentity();
            if (locked) {
                if (!revealed) {
                    reveal(position, cardWidth, cardHeight);
                } else {
                    if (!covering && !removing) {
                        iconSideRender(position, cardWidth, cardHeight);
                    } else if (removing) {
                        removingRender(position, cardWidth, cardHeight);
                    } else {
                        coveringRender(position, cardWidth, cardHeight);
                    }
                }
            } else {
                backSideRender(position, cardWidth, cardHeight);
            }
        }
    }

    private void removingRender(Vec2D position, float cardWidth, float cardHeight) {
        shearXDelta = 0.05f;
        shearX += shearXDelta;
        if (shearX > 0) {
            shearXDelta *= -1;
            revealed = false;
            covering = false;
            parentCard.unlock();
            removed = true;
        }
        iconShown = shearX < 0;

        at.translate(position.getX(), position.getY());
        at.scale(shearX, -shearX);
        at.scale(cardWidth / iconImage.getWidth(), cardHeight / iconImage.getHeight());
        at.translate(-iconImage.getWidth() / 2, -iconImage.getHeight() / 2);
    }

    private void coveringRender(Vec2D position, float cardWidth, float cardHeight) {
        shearXDelta = 0.05f;
        shearX += shearXDelta;
        if (shearX > 1.0f) {
            shearXDelta *= -1;
            revealed = false;
            covering = false;
            parentCard.unlock();
        }
        iconShown = shearX < 0;

        at.translate(position.getX(), position.getY());
        at.scale(shearX, 1);
        at.scale(cardWidth / getImage().getWidth(), cardHeight / getImage().getHeight());
        at.translate(-getImage().getWidth() / 2, -getImage().getHeight() / 2);
    }

    private void reveal(Vec2D position, float cardWidth, float cardHeight) {
        shearX += shearXDelta;
        if (shearX < -1.0f || shearX > 1.0f) {
            shearXDelta *= -1;
            revealed = (iconShown && shearXDelta > 0);
        }
        iconShown = shearX < 0;

        at.translate(position.getX(), position.getY());
        at.scale(shearX, 1);
        at.scale(cardWidth / getImage().getWidth(), cardHeight / getImage().getHeight());
        at.translate(-getImage().getWidth() / 2, -getImage().getHeight() / 2);
    }

    private void backSideRender(Vec2D position, float cardWidth, float cardHeight) {
        at.translate(position.getX(), position.getY());
        at.scale(cardWidth / getImage().getWidth(), cardHeight / getImage().getHeight());
        at.translate(-getImage().getWidth() / 2, -getImage().getHeight() / 2);
    }

    private void iconSideRender(Vec2D position, float cardWidth, float cardHeight) {
        at.translate(position.getX(), position.getY());
        at.scale(-1, 1);
        at.scale(cardWidth / getImage().getWidth(), cardHeight / getImage().getHeight());
        at.translate(-getImage().getWidth() / 2, -getImage().getHeight() / 2);
    }

    public BufferedImage getImage() {
        return (removed) ? null : (iconShown || (revealed && !covering)) ? iconImage : (!hovered) ? backImage : (!parentCard.isLocked()) ? hoveredBackImage : backImage;
    }

    public AffineTransform getAffineTransform() {
        return at;
    }

    public void scale(float scale) {
        at.scale(0.02, 0.02);
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void cover() {
        this.covering = true;
    }

    public void remove() {
        this.removing = true;
    }

    public void hover() {
        this.hovered = true;
    }

    public void unhover() {
        this.hovered = false;
    }

    public boolean isHovered() {
        return hovered;
    }
}
