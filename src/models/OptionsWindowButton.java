package models;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class OptionsWindowButton {

    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private String text;
    private Font font;
    private boolean fontSizeCalculated;
    private Rectangle2D fontRectangle2d;
    private boolean hovered;
    private FontObserver fontObserver;
    private static final String GAME_BUTTON_FONT_PATH = "/fonts/jungle-adventurer/JungleAdventurer.ttf";

    public OptionsWindowButton(Vec2D topLeftCorner, Vec2D dimensions, String text, FontObserver fontObserver) {
        this.fontObserver = fontObserver;
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.text = text;
        initFont();
    }

    private void initFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(GAME_BUTTON_FONT_PATH));
            font = font.deriveFont(100f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Font getFont(FontRenderContext frc) {
        if (!fontSizeCalculated) {
            float fontSize = 100f;
            font = font.deriveFont(fontSize);
            Rectangle2D fontRect = font.getStringBounds(text, frc);
            float fontWidth = (float) fontRect.getWidth();
            float fontHeight = (float) fontRect.getHeight();

            while (fontWidth > dimensions.x * 0.85 || fontHeight > dimensions.y) {
                fontSize -= 1.0f;
                font = font.deriveFont(fontSize);
                fontRect = font.getStringBounds(text, frc);
                fontWidth = (float) fontRect.getWidth();
                fontHeight = (float) fontRect.getHeight();
            }
            fontRectangle2d = fontRect;
            fontSizeCalculated = true;
            if (fontObserver != null) {
                if (font.getSize() < fontObserver.getCustomButtonsMinFontSize()) {
                    fontObserver.setCustomButtonsMinFontSize(font.getSize());
                } else {
                    resizeFont(fontObserver.getCustomButtonsMinFontSize());
                }
            }
        }
        return font;
    }

    public void resizeFont(float size) {
        font = font.deriveFont(size);
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

    public boolean isContained(Vec2D coords) {
        Rectangle2D buttonRect = new Rectangle((int) topLeftCorner.x, (int) topLeftCorner.y, (int) dimensions.x, (int) dimensions.y);
        return hovered = buttonRect.contains(coords.x, coords.y);
    }

    public Rectangle2D getFontRectangle2d() {
        return fontRectangle2d;
    }

    public boolean isHovered() {
        return hovered;
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
        fontSizeCalculated = false;
    }

    public void setText(String text) {
        this.text = text;
    }
}
