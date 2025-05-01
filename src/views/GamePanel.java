package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import models.MouseEngine;
import models.Utilities;
import models.Card;
import models.CustomButton;
import models.Game;
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
        setCursor(new Cursor(game.isActivePointer() ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.red);

        paintBackground(g2);
        paintBoard(g2);
        paintCards(g2);
        paintGameButtons(g2);
    }

    private void paintGameButtons(Graphics2D g2) {
        for ( CustomButton gameButton : game.getGameButtons()) {
            g2.drawImage(gameButton.getButtonImage(), gameButton.getAt(), null);
            g2.setFont(gameButton.getFont(g2.getFontRenderContext()));
            g2.setColor(Color.red);
            Vec2D textCoords = gameButton.getTextCoords(g2.getFontRenderContext());
            g2.drawString(gameButton.getText(), textCoords.x, textCoords.y);
        }
        int b = 0;
    }

    private void paintBackground(Graphics2D g2) {
        g2.drawImage(game.getBackgroundImage(), 0, 0, getWidth(), getHeight(), this);
        g2.drawImage(game.getFrameImage(), (int)((Utilities.WINDOW_WIDTH -  Utilities.WINDOW_HEIGHT) / 2.0f), 0, Utilities.WINDOW_HEIGHT, Utilities.WINDOW_HEIGHT, this);
    }

    private void paintCards(Graphics2D g2) {
        for (Card card : game.getBoard().getCards()) {
            g2.drawImage(card.getImage(), card.getAffineTransform(), null);
            Rectangle2D cardRect = card.getTargetRectangle();
            g2.drawRect((int)cardRect.getX(), (int)cardRect.getY(), (int)cardRect.getWidth(), (int)cardRect.getHeight());
        }
    }

    private void paintBoard(Graphics2D g2) {
        g2.setColor(Color.red);
        Vec2D topLeftCorner = game.getBoard().getTopLeftCorner();
        Vec2D dimensions = game.getBoard().getDimensions();
        g2.drawRect((int) topLeftCorner.getX(), (int) topLeftCorner.getY(), (int) dimensions.getX(),
                (int) dimensions.getY());
    }

}
