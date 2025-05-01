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

public class CustomButton {

    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private BufferedImage buttonImage;
    private BufferedImage hoveredButtonImage;
    private AffineTransform at;
    private String text;
    private Font font;
    private boolean fontSizeCalculated;
    private boolean hovered;
    private Rectangle2D fontRectangle2d;
    private GameButtonType gameButtonType;
    private static final String GAME_BUTTON_IMAGE_PATH = "/img/ButtonBackground.png";
    private static final String GAME_HOVERED_BUTTON_IMAGE_PATH = "/img/HoveredButtonBackground.png";
    private static final String GAME_BUTTON_FONT_PATH = "/fonts/rebellion-squad-font/RebellionSquad-ZpprZ.ttf";

    public CustomButton(Vec2D topLeftCorner, Vec2D dimensions, GameButtonType gameButtonType) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.gameButtonType = gameButtonType;
        this.text = gameButtonType.getText();
        initFont();
        initBackgroundImage();
    }

    private void initFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(GAME_BUTTON_FONT_PATH));
            font = font.deriveFont(100f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initBackgroundImage() {
        try {
            buttonImage = ImageIO.read(getClass().getResource(GAME_BUTTON_IMAGE_PATH));
            hoveredButtonImage = ImageIO.read(getClass().getResource(GAME_HOVERED_BUTTON_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        float imageWidth = buttonImage.getWidth();
        float imageHeight = buttonImage.getHeight();

        at = new AffineTransform();
        at.translate(topLeftCorner.x, topLeftCorner.y);
        at.scale(dimensions.x / imageWidth, dimensions.y / imageHeight);
    }

    public boolean isContained(Vec2D coords) {
        return hovered = coords.x >= topLeftCorner.x && coords.x <= topLeftCorner.x + dimensions.x && coords.y >= topLeftCorner.y && coords.y <= topLeftCorner.y + dimensions.y; 
    }

    public Font getFont(FontRenderContext frc) {
        if (!fontSizeCalculated) {
            Rectangle2D fontRect = font.getStringBounds(text, frc);
            float fontSize = 100f;
            float fontRectArea = (float) (fontRect.getWidth() * fontRect.getHeight());
            float buttonArea = (dimensions.x * dimensions.y) * 0.4f;
            while (fontRectArea > buttonArea) {
                fontSize -= 1.0f;
                font = font.deriveFont(fontSize);
                fontRect = font.getStringBounds(text, frc);
                fontRectArea = (float) (fontRect.getWidth() * fontRect.getHeight());
            }
            fontRectangle2d = fontRect;
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

    public BufferedImage getButtonImage() {
        return !hovered ? buttonImage : hoveredButtonImage;
    }

    public AffineTransform getAt() {
        return at; 
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

    public Rectangle2D getFontRectangle2d() {
        return fontRectangle2d;
    }

    public GameButtonType getGameButtonType() {
        return gameButtonType;
    }

}
