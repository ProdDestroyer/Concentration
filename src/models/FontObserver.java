package models;

public class FontObserver {

    private int customButtonsMinFontSize;
    private int customButtonsIndex;
    private CustomButton[] customButtons;
    
    private int optionsWindowButtonsMinFontSize;
    private int optionsWindowButtonsIndex;
    private OptionsWindowButton[] optionsWindowButtons;

    public FontObserver() {
        resetSize();
        customButtons = new CustomButton[10]; // Increase if needed
        optionsWindowButtons = new OptionsWindowButton[10]; // Increase if needed
    }

    public int getCustomButtonsMinFontSize() {
        return customButtonsMinFontSize;
    }

    public void setCustomButtonsMinFontSize(int customButtonsMinFontSize) {
        this.customButtonsMinFontSize = customButtonsMinFontSize;
        for (int i = 0; i < customButtonsIndex; i++) {
            customButtons[i].resizeFont(this.customButtonsMinFontSize);
        }
    }

    public void resetSize() {
        this.customButtonsMinFontSize = Integer.MAX_VALUE;
        this.optionsWindowButtonsMinFontSize = Integer.MAX_VALUE;
    }

    public void addCustomButton(CustomButton customButton) {
        customButtons[customButtonsIndex++] = customButton;
    }

    public void addoptionsWindowButton(OptionsWindowButton optionsWindowButton) {
        optionsWindowButtons[optionsWindowButtonsIndex++] = optionsWindowButton;
    }

    public void setoptionsWIndowButtonsMinFontSize(int optionsWindowButtonsMinFontSize) {
        this.optionsWindowButtonsMinFontSize = optionsWindowButtonsMinFontSize;
        for (int i = 0; i < optionsWindowButtonsIndex; i++) {
            optionsWindowButtons[i].resizeFont(this.optionsWindowButtonsMinFontSize);
        }
    }
}
