package models;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OptionsImage {
    
    private BufferedImage image;
    private AffineTransform at;
    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private String text;

    public OptionsImage(String imagePath, String text, Vec2D topLeftCorner, Vec2D dimensinos) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensinos;
        this.text = text;
        initBackgroundImage(imagePath);
    }

    public void initBackgroundImage(String imagePath) {
        try {
            image = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();

        at = new AffineTransform();
        at.translate(topLeftCorner.x, topLeftCorner.y);
        at.scale(dimensions.x / imageWidth, dimensions.y / imageHeight);
    }

    public BufferedImage getImage() {
        return image;
    }

    public AffineTransform getAt() {
        return at;
    }

    public String getText() {
        return text;
    }

    public ImageBundle getImageBundle() {
        return new ImageBundle(image, at);
    }

    public void setTopLeftCorner(Vec2D topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }
    public void setDimensions(Vec2D dimensions) {
        this.dimensions = dimensions;
    }
}
