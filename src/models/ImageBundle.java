package models;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageBundle {
    private BufferedImage image;
    private AffineTransform at;

    public ImageBundle(BufferedImage image, AffineTransform at) {
        this.image = image;
        this.at = at;
    }

    public AffineTransform getAt() {
        return at;
    }

    public BufferedImage getImage() {
        return image;
    }
}
