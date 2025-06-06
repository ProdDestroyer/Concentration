package models;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game {

    private Board board;
    private MouseEngine mouseEngine;
    private BufferedImage backgroundImage;
    private BufferedImage frameImage;
    private boolean activePointer;
    private int tries;
    private CustomButton[] gameButtons;
    private GameMode gameMode;
    private OptionsWindow optionsWindow;
    private Vec2D[] windowDimensions = { new Vec2D(1024, 576), new Vec2D(1920, 1080), new Vec2D(2560, 1440) };
    private int windowDimensionsPivot;
    private int tilesDimensionsPivot;
    private int lastTilesDimensionsPivot;
    private int lastWindowDimensionsPivot;
    public Utilities utilities;
    private boolean movingOptions;
    private FontObserver fontObserver;
    private OptionsImage leftChain;
    private OptionsImage rightChain;
    private OptionsImage scoreBoard;
    private static final String GAME_BACKGROUND_PATH = "/img/GameBackground.jpg";
    private static final String BOARD_FRAME_PATH = "/img/FrameImage.png";
    private static final String CHAIN_IMAGE_PATH = "/img/ScoreChain.png";
    private static final String SCORE_BOARD_IMAGE_PATH = "/img/OptionsWindowBackground.png";

    public Game(Utilities utilities, MouseEngine mouseEngine) {
        this.fontObserver = new FontObserver();
        this.utilities = utilities;
        this.windowDimensionsPivot = 1;
        this.tilesDimensionsPivot = 1;
        this.lastTilesDimensionsPivot = tilesDimensionsPivot;
        utilities.windowDimensions = windowDimensions[this.windowDimensionsPivot];
        this.mouseEngine = mouseEngine;
        gameMode = GameMode.PLAYING;
        initBackgroundImage();
        float height = utilities.windowHeight() * (3.0f / 4.0f);
        float width = height;

        Vec2D newTilesDimensions = Utilities.tilesDimensions[tilesDimensionsPivot];

        this.board = new Board(
                new Vec2D((utilities.windowWidth() - width) / 2.0f, (utilities.windowHeight() - height) / 2.0f),
                new Vec2D(width, height), (int) newTilesDimensions.x, (int) newTilesDimensions.y);
        this.gameButtons = new CustomButton[GameButtonType.values().length - 1];

        float gameButtonsStartX = this.board.getTopLeftCorner().x * 0.075f;
        float gameButtonsStartY = this.board.getTopLeftCorner().y;
        float gameButtonsWidth = this.board.getTopLeftCorner().x * 0.70f;
        float gameButtonsHeight = height * 0.20f;
        Vec2D gameButtonDimensions = new Vec2D(gameButtonsWidth, gameButtonsHeight);

        for (int i = 0; i < GameButtonType.values().length - 1; i++) {
            Vec2D topLeftCorner = new Vec2D(gameButtonsStartX, gameButtonsStartY + (i * gameButtonsHeight));
            this.gameButtons[i] = new CustomButton(topLeftCorner, gameButtonDimensions, GameButtonType.values()[i], fontObserver);
        }

        float optionsWindowWidth = width * 1.5f;
        float optionsWindowHeight = height * 1.2f;

        Vec2D optionsWindowTopLeftCorner = new Vec2D((utilities.windowWidth() - optionsWindowWidth) / 2.0f,
                (utilities.windowHeight() - optionsWindowHeight) / 2.0f);
        Vec2D optionsWindowDimensions = new Vec2D(optionsWindowWidth, optionsWindowHeight);
        optionsWindow = new OptionsWindow(optionsWindowTopLeftCorner, optionsWindowDimensions, fontObserver); 

        float leftChainX = utilities.windowWidth() *0.83675f;
        float leftChainY = 0.0f;
        float rightChainX = utilities.windowWidth() *0.9125f;
        float rightChainY = 0.0f;
        
        Vec2D leftChainTopLeftCorner = new Vec2D(leftChainX, leftChainY);
        Vec2D rightChainTopLeftCorner = new Vec2D(rightChainX, rightChainY);
        Vec2D chainDimensions = new Vec2D(utilities.windowWidth() * 0.025f, utilities.windowHeight() * 0.15f);

        
        Vec2D scoreBoardDimensions = new Vec2D(utilities.windowWidth() *0.15f, utilities.windowHeight() * 0.15f);
        float scoreBoardX = leftChainX - ((leftChainX + scoreBoardDimensions.x) - (rightChainX + chainDimensions.x)) / 2.0f;
        float scoreBoardY = chainDimensions.y;
        
        Vec2D scoreBoardTopLeft = new Vec2D(scoreBoardX, scoreBoardY);

        leftChain = new OptionsImage(CHAIN_IMAGE_PATH, null, leftChainTopLeftCorner, chainDimensions);
        rightChain = new OptionsImage(CHAIN_IMAGE_PATH, null, rightChainTopLeftCorner, chainDimensions);
        scoreBoard = new OptionsImage(SCORE_BOARD_IMAGE_PATH, null, scoreBoardTopLeft, scoreBoardDimensions);
    }

    private void updateWindowDimensions() {
        utilities.windowDimensions = windowDimensions[windowDimensionsPivot];
        utilities.pendingWindowResize = true;
        float height = utilities.windowHeight() * (3.0f / 4.0f);
        float width = height;
        board.setTopLeftCorner(new Vec2D((utilities.windowWidth() - width) / 2.0f, (utilities.windowHeight() - height) / 2.0f));
        board.setDimensions(new Vec2D(width, height));

        float gameButtonsStartX = this.board.getTopLeftCorner().x * 0.075f;
        float gameButtonsStartY = this.board.getTopLeftCorner().y;
        float gameButtonsWidth = this.board.getTopLeftCorner().x * 0.70f;
        float gameButtonsHeight = height * 0.20f;
        Vec2D gameButtonDimensions = new Vec2D(gameButtonsWidth, gameButtonsHeight);

        fontObserver.resetSize();
        for (int i = 0; i < GameButtonType.values().length - 1; i++) {
            Vec2D topLeftCorner = new Vec2D(gameButtonsStartX, gameButtonsStartY + (i * gameButtonsHeight));
            this.gameButtons[i].setTopLeftCorner(topLeftCorner);
            this.gameButtons[i].setDimensions(gameButtonDimensions);
        }

        float optionsWindowWidth = width * 1.5f;
        float optionsWindowHeight = height * 1.2f;

        Vec2D optionsWindowTopLeftCorner = new Vec2D((utilities.windowWidth() - optionsWindowWidth) / 2.0f,
                (utilities.windowHeight() - optionsWindowHeight) / 2.0f);
        Vec2D optionsWindowDimensions = new Vec2D(optionsWindowWidth, optionsWindowHeight);
        optionsWindow.setTopleftCorner(optionsWindowTopLeftCorner);
        optionsWindow.setDimensions(optionsWindowDimensions);

        float leftChainX = utilities.windowWidth() *0.83675f;
        float leftChainY = 0.0f;
        float rightChainX = utilities.windowWidth() *0.9125f;
        float rightChainY = 0.0f;
        
        Vec2D leftChainTopLeftCorner = new Vec2D(leftChainX, leftChainY);
        Vec2D rightChainTopLeftCorner = new Vec2D(rightChainX, rightChainY);
        Vec2D chainDimensions = new Vec2D(utilities.windowWidth() * 0.025f, utilities.windowHeight() * 0.15f);

        
        Vec2D scoreBoardDimensions = new Vec2D(utilities.windowWidth() *0.15f, utilities.windowHeight() * 0.15f);
        float scoreBoardX = leftChainX - ((leftChainX + scoreBoardDimensions.x) - (rightChainX + chainDimensions.x)) / 2.0f;
        float scoreBoardY = chainDimensions.y;
        
        Vec2D scoreBoardTopLeftCorner = new Vec2D(scoreBoardX, scoreBoardY);

        leftChain.setTopLeftCorner(leftChainTopLeftCorner);
        leftChain.setDimensions(chainDimensions);
        leftChain.initBackgroundImage(CHAIN_IMAGE_PATH);
        rightChain.setTopLeftCorner(rightChainTopLeftCorner);
        rightChain.setDimensions(chainDimensions);
        rightChain.initBackgroundImage(CHAIN_IMAGE_PATH);
        scoreBoard.setTopLeftCorner(scoreBoardTopLeftCorner);
        scoreBoard.setDimensions(scoreBoardDimensions);
        scoreBoard.initBackgroundImage(SCORE_BOARD_IMAGE_PATH);
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

        if (gameMode == GameMode.PLAYING) {
            boolean isClicked = mouseEngine.isClicked();
            Vec2D coordsHolder = null;
            processCursorMovedPlaying(mouseEngine.readMoved());
            if (isClicked) {
                coordsHolder = mouseEngine.readClick();
                processGameButtonPressedPlaying(getButtonType(coordsHolder));
            }
            tries += board.refresh(dt, coordsHolder);
        } else {
            if (!movingOptions) {
                lastWindowDimensionsPivot = windowDimensionsPivot;
                movingOptions = true;
            }
            processCursorMovedOnOptions(mouseEngine.readMoved());
            if (mouseEngine.isClicked()) {
                processCursorPressedOptions(mouseEngine.readClick());
            }
        }
        scoreBoard.setText(String.valueOf(board.getScore()));
    }

    private void processCursorPressedOptions(Vec2D coords) {
        if (optionsWindow.getGameButton().isContained(coords)) {
            optionsWindow.initWoodenDivisionForGame();
            gameMode = GameMode.GAME_SETUP;
        }
        if (optionsWindow.getGraphicsButton().isContained(coords)) {
            optionsWindow.initWoodenDivisionForGraphics();
            gameMode = GameMode.GRAPHICS;
        }
        if (gameMode == GameMode.GRAPHICS) {
            if (optionsWindow.getWindowDimensionsSpinner().getDecreaseButton().isContained(coords)) {
                windowDimensionsPivot = windowDimensionsPivot > 0 ? (windowDimensionsPivot - 1) % 3 : windowDimensions.length - 1;
                optionsWindow.getWindowDimensionsSpinner()
                        .setValue((int) windowDimensions[windowDimensionsPivot].x + "x" + (int) windowDimensions[windowDimensionsPivot].y);
                updateWindowDimensions();
            }
            if (optionsWindow.getWindowDimensionsSpinner().getIncreaseButton().isContained(coords)) {
                windowDimensionsPivot = (windowDimensionsPivot + 1) % 3;
                optionsWindow.getWindowDimensionsSpinner()
                        .setValue((int) windowDimensions[windowDimensionsPivot].x + "x" + (int) windowDimensions[windowDimensionsPivot].y);
                updateWindowDimensions();
            }
        }
        if (gameMode == GameMode.GAME_SETUP) {
            if (optionsWindow.getTilesDimensionsSpinner().getDecreaseButton().isContained(coords)) {
                tilesDimensionsPivot = tilesDimensionsPivot > 0 ? tilesDimensionsPivot - 1 : Utilities.tilesDimensions.length - 1;
            }
            if (optionsWindow.getTilesDimensionsSpinner().getIncreaseButton().isContained(coords)) {
                tilesDimensionsPivot = (tilesDimensionsPivot + 1) % 4;
            }
            Vec2D newTilesDimensions = Utilities.tilesDimensions[tilesDimensionsPivot];
            int widthInTiles = (int) newTilesDimensions.x;
            int heightInTiles = (int) newTilesDimensions.y;
            optionsWindow.getTilesDimensionsSpinner().setValue(widthInTiles + " x " + heightInTiles);
        }
        if (optionsWindow.getConfirmButton().isContained(coords)) {
            if (tilesDimensionsPivot != lastTilesDimensionsPivot) {
                lastTilesDimensionsPivot = tilesDimensionsPivot;
                Vec2D newTilesDimensions = Utilities.tilesDimensions[tilesDimensionsPivot];
                float height = utilities.windowHeight() * (3.0f / 4.0f);
                float width = height;
                int widthInTiles = (int) newTilesDimensions.x;
                int heightInTiles = (int) newTilesDimensions.y;
                optionsWindow.getTilesDimensionsSpinner().setValue(widthInTiles + " x " + heightInTiles);
                this.board = new Board(
                        new Vec2D((utilities.windowWidth() - width) / 2.0f, (utilities.windowHeight() - height) / 2.0f),
                        new Vec2D(width, height), widthInTiles, heightInTiles);
            }
            gameMode = GameMode.PLAYING;
            movingOptions = false;
        }
        if (optionsWindow.getCancelButton().isContained(coords)) {
            windowDimensionsPivot = lastWindowDimensionsPivot;
            optionsWindow.getWindowDimensionsSpinner()
                    .setValue((int) windowDimensions[windowDimensionsPivot].x + "x" + (int) windowDimensions[windowDimensionsPivot].y);
            updateWindowDimensions();
            gameMode = GameMode.PLAYING;
        }

    }

    private void processCursorMovedOnOptions(Vec2D coords) {
        if (optionsWindow.getGameButton().isContained(coords)) {
        }
        if (optionsWindow.getGraphicsButton().isContained(coords)) {
        }
        if (optionsWindow.getWindowDimensionsSpinner().getDecreaseButton().isContained(coords)) {
        }
        if (optionsWindow.getWindowDimensionsSpinner().getIncreaseButton().isContained(coords)) {
        }
        if (optionsWindow.getTilesDimensionsSpinner().getDecreaseButton().isContained(coords)) {
        }
        if (optionsWindow.getTilesDimensionsSpinner().getIncreaseButton().isContained(coords)) {
        }
        if (optionsWindow.getConfirmButton().isContained(coords)) {
        }
        if (optionsWindow.getCancelButton().isContained(coords)) {
        }
    }

    private void processCursorMovedPlaying(Vec2D coords) {
        activePointer = getButtonType(coords) != GameButtonType.NONE;
        if (!activePointer && board.hoverValidation(coords)) {
            board.unhover();
            board.hover(coords);
            activePointer = true;
        } else {
            board.unhover();
            // activePointer = false;
        }
    }

    private void processGameButtonPressedPlaying(GameButtonType gameButtonType) {
        switch (gameButtonType) {
            case GameButtonType.NONE:

                break;
            case GameButtonType.OPTIONS:
                gameMode = GameMode.GRAPHICS;
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

    public boolean isActivePointer() {

        return activePointer;
    }

    public Vec2D getWindowDimensions() {
        return windowDimensions[windowDimensionsPivot];
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public OptionsWindow getOptionsWindow() {
        return optionsWindow;
    }

    public Utilities getUtilities() {
        return utilities;
    }

    public OptionsImage getLeftChain() {
        return leftChain;
    }

    public OptionsImage getRightChain() {
        return rightChain;
    }
    public OptionsImage getScoreBoard() {
        return scoreBoard;
    }
}
