package models;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game {

    private Board board;
    private int score;
    private MouseEngine mouseEngine;
    private BufferedImage backgroundImage;
    private BufferedImage frameImage;
    private boolean activePointer;
    private int tries;
    private CustomButton[] gameButtons;
    private static final String GAME_BACKGROUND_PATH = "/img/GameBackground.jpg";
    private static final String BOARD_FRAME_PATH = "/img/FrameImage.png";

    public Game(int cardsAmountBase, MouseEngine mouseEngine) {
        this.mouseEngine = mouseEngine;
        initBackgroundImage();
        float height = Utilities.WINDOW_HEIGHT * (3.0f / 4.0f);
        float width = height;

        this.board = new Board(
                new Vec2D((Utilities.WINDOW_WIDTH - width) / 2.0f, (Utilities.WINDOW_HEIGHT - height) / 2.0f),
                new Vec2D(width, height), 4, 4);
        this.gameButtons = new CustomButton[GameButtonType.values().length - 1];

        float gameButtonsStartX = this.board.getTopLeftCorner().x * 0.075f;
        float gameButtonsStartY = this.board.getTopLeftCorner().y;
        float gameButtonsWidth = this.board.getTopLeftCorner().x * 0.70f;
        float gameButtonsHeight = height * 0.20f;
        Vec2D gameButtonDimensions = new Vec2D(gameButtonsWidth, gameButtonsHeight);

        for (int i = 0; i < GameButtonType.values().length - 1; i++) {
            Vec2D topLeftCorner = new Vec2D(gameButtonsStartX, gameButtonsStartY + (i * gameButtonsHeight));
            this.gameButtons[i] = new CustomButton(topLeftCorner, gameButtonDimensions, GameButtonType.values()[i]);
        }
    }

    private void initBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource(GAME_BACKGROUND_PATH));
            frameImage = ImageIO.read(getClass().getResource(BOARD_FRAME_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshModel(float dt) {
        boolean isClicked = mouseEngine.isClicked();
        Vec2D coordsHolder = null;
        processCursorMoved(mouseEngine.readMoved());
        if (isClicked) {
            coordsHolder = mouseEngine.readClick();
                processGameButtonPressed(getButtonType(coordsHolder));
        }
        tries += board.refresh(dt, coordsHolder);
    }

    private void processCursorMoved(Vec2D coords) {
        activePointer = getButtonType(coords) != GameButtonType.NONE;
        if(!activePointer && board.hoverValidation(coords)) {
            board.unhover();
            board.hover(coords);
            activePointer = true;
        } else {
            board.unhover();
            // activePointer = false;
        }
    }
    private void processGameButtonPressed(GameButtonType gameButtonType) {
        switch (gameButtonType) {
            case GameButtonType.NONE:
                
                break;
            case GameButtonType.OPTIONS:
                
                break;
            case GameButtonType.EXIT:
            System.exit(0);
                break;
            case GameButtonType.SAVE:
                
                break;
            case GameButtonType.LOAD:
                
                break;
        }
    }

    private GameButtonType getButtonType(Vec2D coords) {
        GameButtonType gbt = GameButtonType.NONE;
        for (CustomButton customButton : gameButtons) {
            gbt = customButton.isContained(coords) ? customButton.getGameButtonType() : gbt;
        }
        return gbt;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public int getTries() {
        return tries;
    }

    public BufferedImage getFrameImage() {
        return frameImage;
    }

    public CustomButton[] getGameButtons() {
        return gameButtons;
    }

    public Board getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public boolean isActivePointer() {
        return activePointer;
    }
}
