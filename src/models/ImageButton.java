package models;

import java.io.IOException;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageButton {

    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private String imagePath;
    private String hoveredImagePath;
    private AffineTransform at;
    private BufferedImage buttonImage;
    private BufferedImage hoveredButtonImage;
    private String text;
    private boolean hovered;

    public ImageButton(Vec2D topLeftCorner, Vec2D dimensions, String imagePath, String hoveredImagePath, String text) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.imagePath = imagePath;
        this.hoveredImagePath = hoveredImagePath;
        this.text = text;
        initBackgroundImage();
    }

    private void initBackgroundImage() {
        try {
            buttonImage = ImageIO.read(getClass().getResource(imagePath));
            hoveredButtonImage = ImageIO.read(getClass().getResource(hoveredImagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        float imageWidth = buttonImage.getWidth();
        float imageHeight = buttonImage.getHeight();

        at = new AffineTransform();
        at.translate(topLeftCorner.x, topLeftCorner.y);
        at.scale(dimensions.x / imageWidth, dimensions.y / imageHeight);
    }

    public boolean isContained( Vec2D coords) {
        Rectangle2D buttonRect = new Rectangle((int)topLeftCorner.x, (int)topLeftCorner.y, (int)dimensions.x, (int)dimensions.y);
        return hovered = buttonRect.contains(coords.x, coords.y);
    }

    public String getImagePath() {
        return imagePath;
    }

    public Vec2D getDimensions() {
        return dimensions;
    }

    public String getText() {
        return text;
    }

    public Vec2D getTopLeftCorner() {
        return topLeftCorner;
    }

    public void setTopLeftCorner(Vec2D topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }

    public void setDimensions(Vec2D dimensions) {
        this.dimensions = dimensions;
        initBackgroundImage();
    }

    public ImageBundle getImageBundle() {
        return new ImageBundle((!hovered) ? buttonImage : hoveredButtonImage, at);
    }

    public boolean isHovered() {
        return hovered;
    }

}
