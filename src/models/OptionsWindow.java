package models;

public class OptionsWindow {

    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private OptionsImage optionsWindowImage;
    private OptionsWindowButton graphicsButton;
    private OptionsWindowButton gameButton;
    private OptionsImage bottomWoodenDivisionImage;
    private OptionsImage upperWoodenDivisionImage;
    private OptionsSpinner windowDimensionsSpinner;
    private OptionsWindowButton dimensionsLabel;
    private OptionsSpinner tilesDimensionsSpinner;
    private OptionsWindowButton tilesDimensionsLabel;
    private ImageButton confirmButton;
    private ImageButton cancelButton;
    private FontObserver fontObserver;
    private static final String OPTIONS_WINDOW_BACKGROUND_IMAGE_PATH = "/img/OptionsWindowBackground.png";
    private static final String OPTIONS_WINDOW_WOOD_DIVISION_IMAGE_PATH = "/img/WoodenBorder.png";

    public OptionsWindow(Vec2D topLeftCorner, Vec2D dimensions, FontObserver fontObserver) {
        this.fontObserver = fontObserver;
        fontObserver.addoptionsWindowButton(gameButton);
        fontObserver.addoptionsWindowButton(graphicsButton);
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        Vec2D innerButtonsTopLeftCorner = new Vec2D(topLeftCorner.x + dimensions.x * 0.725f, topLeftCorner.y + dimensions.y * 0.1f);
        Vec2D innerButtonsDimensions = new Vec2D(dimensions.x * 0.225f, dimensions.y * 0.075f);
        this.windowDimensionsSpinner = new OptionsSpinner(innerButtonsTopLeftCorner, innerButtonsDimensions, "1920x1080");
        Vec2D innerLabelsTopLeftCorner = new Vec2D(topLeftCorner.x + dimensions.x * 0.375f, topLeftCorner.y + dimensions.y * 0.1f);
        this.dimensionsLabel = new OptionsWindowButton(innerLabelsTopLeftCorner, innerButtonsDimensions, "Window Size", null);
        this.tilesDimensionsLabel = new OptionsWindowButton(innerLabelsTopLeftCorner, innerButtonsDimensions, "Tiles Dimensions", null);
        this.tilesDimensionsSpinner = new OptionsSpinner(innerButtonsTopLeftCorner, innerButtonsDimensions, "4 x 4");
        initWoodenDivisionForGraphics();
        initOptionsWindowBackgroundImage();
        initButtons();
    }

    private void resetSizes() {
        Vec2D innerButtonsTopLeftCorner = new Vec2D(topLeftCorner.x + dimensions.x - dimensions.x * 0.275f, topLeftCorner.y + dimensions.y * 0.1f);
        Vec2D innerButtonsDimensions = new Vec2D(dimensions.x * 0.225f, dimensions.y * 0.075f);
        windowDimensionsSpinner.setTopLeftCorner(innerButtonsTopLeftCorner);
        windowDimensionsSpinner.setDimensions(innerButtonsDimensions);
        tilesDimensionsSpinner.setTopLeftCorner(innerButtonsTopLeftCorner);
        tilesDimensionsSpinner.setDimensions(innerButtonsDimensions);

        Vec2D innerLabelsTopLeftCorner = new Vec2D(topLeftCorner.x + dimensions.x * 0.375f, topLeftCorner.y + dimensions.y * 0.1f);
        dimensionsLabel.setTopLeftCorner(innerLabelsTopLeftCorner);
        dimensionsLabel.setDimensions(innerButtonsDimensions);
        tilesDimensionsLabel.setTopLeftCorner(innerLabelsTopLeftCorner);
        tilesDimensionsLabel.setDimensions(innerButtonsDimensions);

        Vec2D buttonsTopLeftCorner = new Vec2D(topLeftCorner.x + dimensions.x * 0.04f, topLeftCorner.y + dimensions.y * 0.05f);
        Vec2D buttonsDimensions = new Vec2D(dimensions.x * 0.25f, dimensions.y * 0.15f);
        graphicsButton.setTopLeftCorner(buttonsTopLeftCorner);
        graphicsButton.setDimensions(buttonsDimensions);
        gameButton.setTopLeftCorner(new Vec2D(buttonsTopLeftCorner.x, buttonsTopLeftCorner.y + buttonsDimensions.y));
        gameButton.setDimensions(buttonsDimensions);
        optionsWindowImage.setTopLeftCorner(topLeftCorner);
        optionsWindowImage.setDimensions(dimensions);
        optionsWindowImage.initBackgroundImage(OPTIONS_WINDOW_BACKGROUND_IMAGE_PATH);
        initWoodenDivisionForGraphics();

        // ControlButtons
        Vec2D panelButtonsTopLeftCorner = new Vec2D(topLeftCorner.x + 0.9f * dimensions.x, topLeftCorner.y);
        Vec2D panelButtonsDimensions = new Vec2D(0.05f * dimensions.x, dimensions.y * 0.075f);
        confirmButton.setTopLeftCorner(panelButtonsTopLeftCorner);
        confirmButton.setDimensions(panelButtonsDimensions);
        cancelButton.setTopLeftCorner(new Vec2D(panelButtonsTopLeftCorner.x + dimensions.x * 0.05f, topLeftCorner.y));
        cancelButton.setDimensions(panelButtonsDimensions);
    }

    public void initWoodenDivisionForGraphics() {
        float bottomDivisionX = topLeftCorner.x + dimensions.x * 0.04f + dimensions.x * 0.25f;
        float bottomDivisionY = topLeftCorner.y + dimensions.y * 0.05f + dimensions.y * 0.15f;
        this.bottomWoodenDivisionImage = new OptionsImage(OPTIONS_WINDOW_WOOD_DIVISION_IMAGE_PATH, null, new Vec2D(bottomDivisionX, bottomDivisionY),
                new Vec2D(dimensions.x * 0.025f, dimensions.y - bottomDivisionY));

        float upperDivisionX = topLeftCorner.x + dimensions.x * 0.04f + dimensions.x * 0.25f;
        float upperDivisionY = topLeftCorner.y + dimensions.y * 0.05f;

        this.upperWoodenDivisionImage = new OptionsImage(OPTIONS_WINDOW_WOOD_DIVISION_IMAGE_PATH, null, new Vec2D(upperDivisionX, upperDivisionY),
                new Vec2D(dimensions.x * 0.025f, 0));
    }

    public void initWoodenDivisionForGame() {
        float bottomDivisionX = topLeftCorner.x + dimensions.x * 0.04f + dimensions.x * 0.25f;
        float bottomDivisionY = topLeftCorner.y + dimensions.y * 0.05f + dimensions.y * 0.30f;

        float upperDivisionX = topLeftCorner.x + dimensions.x * 0.04f + dimensions.x * 0.25f;
        float upperDivisionY = topLeftCorner.y + dimensions.y * 0.05f;

        this.bottomWoodenDivisionImage = new OptionsImage(OPTIONS_WINDOW_WOOD_DIVISION_IMAGE_PATH, null, new Vec2D(bottomDivisionX, bottomDivisionY),
                new Vec2D(dimensions.x * 0.025f, dimensions.y - bottomDivisionY));

        this.upperWoodenDivisionImage = new OptionsImage(OPTIONS_WINDOW_WOOD_DIVISION_IMAGE_PATH, null, new Vec2D(upperDivisionX, upperDivisionY),
                new Vec2D(dimensions.x * 0.025f, dimensions.y * 0.15f));

    }

    private void initButtons() {
        Vec2D buttonsTopLeftCorner = new Vec2D(topLeftCorner.x + dimensions.x * 0.04f, topLeftCorner.y + dimensions.y * 0.05f);
        Vec2D buttonsDimension = new Vec2D(dimensions.x * 0.25f, dimensions.y * 0.15f);
        graphicsButton = new OptionsWindowButton(buttonsTopLeftCorner, buttonsDimension, "GRAPHICS", fontObserver);
        gameButton = new OptionsWindowButton(new Vec2D(buttonsTopLeftCorner.x, buttonsTopLeftCorner.y + buttonsDimension.y), buttonsDimension, "GAME",
                fontObserver);

        Vec2D panelButtonsTopLeftCorner = new Vec2D(topLeftCorner.x + 0.9f * dimensions.x, topLeftCorner.y);
        Vec2D panelButtonsDimensions = new Vec2D(0.05f * dimensions.x, dimensions.y * 0.075f);
        confirmButton = new ImageButton(panelButtonsTopLeftCorner, panelButtonsDimensions, "/img/ConfirmButton.png", "/img/HoveredConfirmButton.png", null);
        cancelButton = new ImageButton(new Vec2D(panelButtonsTopLeftCorner.x + dimensions.x * 0.05f, topLeftCorner.y), panelButtonsDimensions,
                "/img/CancelButton.png", "/img/HoveredCancelButton.png", null);
    }

    private void initOptionsWindowBackgroundImage() {
        this.optionsWindowImage = new OptionsImage(OPTIONS_WINDOW_BACKGROUND_IMAGE_PATH, null, topLeftCorner, dimensions);
    }

    public Vec2D getDimensions() {
        return dimensions;
    }

    public Vec2D getTopleftCorner() {
        return topLeftCorner;
    }

    public ImageBundle getBackgroundImageBundle() {
        return optionsWindowImage.getImageBundle();
    }

    public OptionsWindowButton getGraphicsButton() {
        return graphicsButton;
    }

    public OptionsWindowButton getGameButton() {
        return gameButton;
    }

    public ImageBundle getBottomWoodenDivisionImageBundle() {
        return bottomWoodenDivisionImage.getImageBundle();
    }

    public ImageBundle getUpperWoodenDivisionImageBundle() {
        return upperWoodenDivisionImage.getImageBundle();
    }

    public boolean areButtonsHovered() {
        return gameButton.isHovered() || graphicsButton.isHovered() || windowDimensionsSpinner.getDecreaseButton().isHovered()
                || windowDimensionsSpinner.getIncreaseButton().isHovered() || confirmButton.isHovered() || cancelButton.isHovered();
    }

    public OptionsSpinner getWindowDimensionsSpinner() {
        return windowDimensionsSpinner;
    }

    public void setDimensions(Vec2D dimensions) {
        this.dimensions = dimensions;
        resetSizes();
    }

    public void setTopleftCorner(Vec2D topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }

    public ImageButton getConfirmButton() {
        return confirmButton;
    }

    public ImageButton getCancelButton() {
        return cancelButton;
    }

    public OptionsWindowButton getDimensionsLabel() {
        return dimensionsLabel;
    }

    public OptionsWindowButton getTilesDimensionsLabel() {
        return tilesDimensionsLabel;
    }

    public OptionsSpinner getTilesDimensionsSpinner() {
        return tilesDimensionsSpinner;
    }

}
