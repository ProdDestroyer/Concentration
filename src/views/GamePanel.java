package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import models.MouseEngine;
import models.OptionsWindowButton;
import models.Utilities;
import models.Card;
import models.CustomButton;
import models.Game;
import models.GameMode;
import models.ImageBundle;
import models.Vec2D;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game, MouseEngine mouseEngine) {
        initProperties();
        initComponents(game, mouseEngine);
    }

    private void initProperties() {
        setBackground(Color.black);
        setFocusable(true);
    }

    private void initComponents(Game game, MouseEngine mouseEngine) {
        this.game = game;
        addMouseListener(mouseEngine);
        addMouseMotionListener(mouseEngine);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        if (game.getGameMode() == GameMode.PLAYING) {
            setCursor(new Cursor(game.isActivePointer() ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
        }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.green);

        paintBackground(g2);
        paintBoard(g2);
        paintCards(g2);
        paintGameButtons(g2);
        if (game.getGameMode() != GameMode.PLAYING) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setColor(Color.green);
            paintGameOptionsWindow(g2);
        }
    }

    private void paintGameOptionsWindow(Graphics2D g2) {
        ImageBundle backgroundImageBundle = game.getOptionsWindow().getBackgroundImageBundle();
        ImageBundle bottomWoodenDivisionImageBundle = game.getOptionsWindow().getBottomWoodenDivisionImageBundle();
        ImageBundle upperWoodenDivisionImageBundle = game.getOptionsWindow().getUpperWoodenDivisionImageBundle();
        OptionsWindowButton graphicsButton = game.getOptionsWindow().getGraphicsButton();
        OptionsWindowButton gameButton = game.getOptionsWindow().getGameButton();
        g2.drawImage(backgroundImageBundle.getImage(), backgroundImageBundle.getAt(), null);
        g2.drawImage(bottomWoodenDivisionImageBundle.getImage(), bottomWoodenDivisionImageBundle.getAt(), null);
        g2.drawImage(upperWoodenDivisionImageBundle.getImage(), upperWoodenDivisionImageBundle.getAt(), null);

        g2.drawRect((int) graphicsButton.getTopLeftCorner().x, (int) graphicsButton.getTopLeftCorner().y, (int) graphicsButton.getDimensions().x,
                (int) graphicsButton.getDimensions().y);
        g2.setFont(graphicsButton.getFont(g2.getFontRenderContext()));
        Vec2D graphicsButtonTextCoords = graphicsButton.getTextCoords(g2.getFontRenderContext());
        g2.drawString(graphicsButton.getText(), (int) graphicsButtonTextCoords.x, (int) graphicsButtonTextCoords.y);
        paintOptionsButtonsHovered(g2, graphicsButton, graphicsButtonTextCoords);

        g2.drawRect((int) gameButton.getTopLeftCorner().x, (int) gameButton.getTopLeftCorner().y, (int) gameButton.getDimensions().x,
                (int) gameButton.getDimensions().y);
        g2.setFont(gameButton.getFont(g2.getFontRenderContext()));
        Vec2D gameButtonTextCoords = gameButton.getTextCoords(g2.getFontRenderContext());
        g2.drawString(gameButton.getText(), (int) gameButtonTextCoords.x, (int) gameButtonTextCoords.y);
        paintOptionsButtonsHovered(g2, gameButton, gameButtonTextCoords);
        setCursor(new Cursor(game.getOptionsWindow().areButtonsHovered() ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));

        ImageBundle confirmButtonBundle = game.getOptionsWindow().getConfirmButton().getImageBundle();
        g2.drawImage(confirmButtonBundle.getImage(), confirmButtonBundle.getAt(), null);

        ImageBundle cancelButtonBundle = game.getOptionsWindow().getCancelButton().getImageBundle();
        g2.drawImage(cancelButtonBundle.getImage(), cancelButtonBundle.getAt(), null);

        switch (game.getGameMode()) {
            case GRAPHICS:
                OptionsWindowButton windowDimensionsDecreaseButton = game.getOptionsWindow().getWindowDimensionsSpinner().getDecreaseButton();
                Vec2D windowDimensionsDecreaseButtonTextCoords = windowDimensionsDecreaseButton.getTextCoords(g2.getFontRenderContext());

                OptionsWindowButton windowDimensionsIncreaseButton = game.getOptionsWindow().getWindowDimensionsSpinner().getIncreaseButton();
                Vec2D windowDimensionsIncreaseButtonTextCoords = windowDimensionsIncreaseButton.getTextCoords(g2.getFontRenderContext());

                OptionsWindowButton windowDimensionsValueButton = game.getOptionsWindow().getWindowDimensionsSpinner().getValueButton();
                Vec2D windowDimensionsValueButtonTextCoords = windowDimensionsValueButton.getTextCoords(g2.getFontRenderContext());

                g2.setFont(windowDimensionsDecreaseButton.getFont(g2.getFontRenderContext()));
                g2.drawString(windowDimensionsDecreaseButton.getText(), windowDimensionsDecreaseButtonTextCoords.x, windowDimensionsDecreaseButtonTextCoords.y);
                paintOptionsButtonsHovered(g2, windowDimensionsDecreaseButton, windowDimensionsDecreaseButtonTextCoords);

                g2.setFont(windowDimensionsIncreaseButton.getFont(g2.getFontRenderContext()));
                g2.drawString(windowDimensionsIncreaseButton.getText(), windowDimensionsIncreaseButtonTextCoords.x, windowDimensionsIncreaseButtonTextCoords.y);
                paintOptionsButtonsHovered(g2, windowDimensionsIncreaseButton, windowDimensionsIncreaseButtonTextCoords);

                g2.setFont(windowDimensionsValueButton.getFont(g2.getFontRenderContext()));
                g2.drawString(windowDimensionsValueButton.getText(), windowDimensionsValueButtonTextCoords.x, windowDimensionsValueButtonTextCoords.y);

                g2.drawRect((int) game.getOptionsWindow().getWindowDimensionsSpinner().getTopLeftCorner().x,
                        (int) game.getOptionsWindow().getWindowDimensionsSpinner().getTopLeftCorner().y,
                        (int) game.getOptionsWindow().getWindowDimensionsSpinner().getDimensions().x,
                        (int) game.getOptionsWindow().getWindowDimensionsSpinner().getDimensions().y);

                g2.setFont(game.getOptionsWindow().getDimensionsLabel().getFont(g2.getFontRenderContext()));
                Vec2D dimensionsLabelTextCoords = game.getOptionsWindow().getDimensionsLabel().getTextCoords(g2.getFontRenderContext());
                g2.drawString(game.getOptionsWindow().getDimensionsLabel().getText(), dimensionsLabelTextCoords.x, dimensionsLabelTextCoords.y);

                break;
            case GAME_SETUP:
                break;
            case LOAD:
                break;
            case OPTIONS:
                break;
            case PLAYING:
                break;
            case SAVE:
                break;
            default:
                break;
        }
    }

    private void paintOptionsButtonsHovered(Graphics2D g2, OptionsWindowButton button, Vec2D buttonTextCoords) {
        if (button.isHovered()) {
            for (int i = 4; i >= 1; i--) {
                float alpha = 0.04f * i;
                g2.setColor(new Color(0f, 1f, 0f, alpha)); // Green glow with transparency
                g2.drawString(button.getText(), buttonTextCoords.x - i, buttonTextCoords.y - i);
                g2.drawString(button.getText(), buttonTextCoords.x + i, buttonTextCoords.y - i);
                g2.drawString(button.getText(), buttonTextCoords.x - i, buttonTextCoords.y + i);
                g2.drawString(button.getText(), buttonTextCoords.x + i, buttonTextCoords.y + i);
            }
        }
        g2.setColor(Color.green);
    }

    private void paintGameButtons(Graphics2D g2) {
        for (CustomButton gameButton : game.getGameButtons()) {
            g2.drawImage(gameButton.getButtonImage(), gameButton.getAt(), null);
            g2.setFont(gameButton.getFont(g2.getFontRenderContext()));
            g2.setColor(Color.GREEN);
            Vec2D textCoords = gameButton.getTextCoords(g2.getFontRenderContext());
            g2.drawString(gameButton.getText(), textCoords.x, textCoords.y);
        }
    }

    private void paintBackground(Graphics2D g2) {
        g2.drawImage(game.getBackgroundImage(), 0, 0, getWidth(), getHeight(), this);
        g2.drawImage(game.getFrameImage(), (int) ((game.getUtilities().windowWidth() - game.getUtilities().windowHeight()) / 2.0f), 0,
                (int) game.getUtilities().windowHeight(),
                (int) game.getUtilities().windowHeight(), this);
    }

    private void paintCards(Graphics2D g2) {
        for (Card card : game.getBoard().getCards()) {
            g2.drawImage(card.getImage(), card.getAffineTransform(), null);
            Rectangle2D cardRect = card.getTargetRectangle();
            g2.drawRect((int) cardRect.getX(), (int) cardRect.getY(), (int) cardRect.getWidth(), (int) cardRect.getHeight());
        }
    }

    private void paintBoard(Graphics2D g2) {
        g2.setColor(Color.green);
        Vec2D topLeftCorner = game.getBoard().getTopLeftCorner();
        Vec2D dimensions = game.getBoard().getDimensions();
        g2.drawRect((int) topLeftCorner.getX(), (int) topLeftCorner.getY(), (int) dimensions.getX(),
                (int) dimensions.getY());
    }

}
