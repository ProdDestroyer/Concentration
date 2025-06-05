package models;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class OptionsImage {

    private BufferedImage image;
    private AffineTransform at;
    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private boolean fontSizeCalculated;
    private String text;
    private Font font;
    private static final String GAME_BUTTON_FONT_PATH = "/fonts/jungle-adventurer/JungleAdventurer.ttf";

    public OptionsImage(String imagePath, String text, Vec2D topLeftCorner, Vec2D dimensinos) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensinos;
        this.text = text;
        initFont();
        initBackgroundImage(imagePath);
    }

    private void initFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(GAME_BUTTON_FONT_PATH));
            font = font.deriveFont(100f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
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

        public Font getFont(FontRenderContext frc) {
        if (!fontSizeCalculated) {
            float fontSize = 180f;
            font = font.deriveFont(fontSize);
            Rectangle2D fontRect = font.getStringBounds(text, frc);
            float fontWidth = (float) fontRect.getWidth();
            float fontHeight = (float) fontRect.getHeight();

            while (fontWidth > dimensions.x * 0.85f || fontHeight > dimensions.y * 0.85f) {
                fontSize -= 1.0f;
                font = font.deriveFont(fontSize);
                fontRect = font.getStringBounds(text, frc);
                fontWidth = (float) fontRect.getWidth();
                fontHeight = (float) fontRect.getHeight();
            }
            fontSizeCalculated = true;
        }
        return font;
    }

    public Vec2D getTextCoords(FontRenderContext frc) {
        float buttonXCenter = topLeftCorner.x + dimensions.x / 2;
        float buttonYCenter = topLeftCorner.y + dimensions.y / 2;

        Rectangle2D stringRect = font.getStringBounds(text, frc);
        LineMetrics metrics = font.getLineMetrics(text, frc);

        float ascent = metrics.getAscent();
        float descent = metrics.getDescent();

        return new Vec2D(
                buttonXCenter - (float) stringRect.getWidth() / 2.0f,
                buttonYCenter + (ascent - descent) / 2.0f);
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
        fontSizeCalculated = false;
    }

    public void setText(String text) {
        this.text = text;
    }
}
