package models;

public class OptionsSpinner {

    private Vec2D topLeftCorner;
    private Vec2D dimensions;
    private OptionsWindowButton increaseButton;
    private OptionsWindowButton decreaseButton;
    private OptionsWindowButton valueButton;
    private String value;

    public OptionsSpinner(Vec2D topLeftCorner, Vec2D dimensions, String value) {
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.value = value;
        initButtons();
    }

    private void initButtons() {
        decreaseButton = new OptionsWindowButton(topLeftCorner, new Vec2D(dimensions.x * 0.15f, dimensions.y), "<", null);
        increaseButton = new OptionsWindowButton(new Vec2D(topLeftCorner.x + dimensions.x * 0.85f, topLeftCorner.y), new Vec2D(dimensions.x * 0.15f, dimensions.y), ">", null);
        valueButton = new OptionsWindowButton(new Vec2D(topLeftCorner.x + dimensions.x * 0.15f, topLeftCorner .y), new Vec2D(dimensions.x * 0.70f, dimensions.y),
                value, null);
    }

    private void resetButtons() {
        decreaseButton.setTopLeftCorner(topLeftCorner);
        decreaseButton.setDimensions(new Vec2D(dimensions.x * 0.15f, dimensions.y));

        increaseButton.setTopLeftCorner(new Vec2D(topLeftCorner.x + dimensions.x * 0.85f, topLeftCorner.y));
        increaseButton.setDimensions(new Vec2D(dimensions.x * 0.15f, dimensions.y));

        valueButton.setTopLeftCorner(new Vec2D(topLeftCorner.x + dimensions.x * 0.15f, topLeftCorner .y));
        valueButton.setDimensions(new Vec2D(dimensions.x * 0.70f, dimensions.y));

    }

    public OptionsWindowButton getIncreaseButton() {
        return increaseButton;
    }

    public OptionsWindowButton getDecreaseButton() {
        return decreaseButton;
    }

    public OptionsWindowButton getValueButton() {
        return valueButton;
    }

    public String getValue() {
        return value;
    }

    public Vec2D getDimensions() {
        return dimensions;
    }

    public Vec2D getTopLeftCorner() {
        return topLeftCorner;
    }

    public void setTopLeftCorner(Vec2D topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }
    public void setDimensions(Vec2D dimensions) {
        this.dimensions = dimensions;
        resetButtons();
    }

    public void setValue(String value) {
        this.value = value;
        valueButton.setText(value);
    }
}
