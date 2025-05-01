package views;
import javax.swing.*;
import java.awt.*;

public class MyCustomButton extends JButton {

    public MyCustomButton(String text) {
        super(text);
        setContentAreaFilled(false); // So the default painting doesn't interfere
        setFocusPainted(false);      // Optional: disables focus ring
        setBorderPainted(false);     // Optional: disables border
        setBackground(Color.red);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Cast to Graphics2D for better control
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing for smoother text and graphics
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Custom background
        if (getModel().isPressed()) {
            g2.setColor(Color.DARK_GRAY);
        } else {
            g2.setColor(Color.GRAY);
        }
        g2.fillRoundRect(0, 0, Math.min(50,getWidth()), Math.min(50, getHeight()), 20, 20);

        // Custom text
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        g2.drawString(getText(), (Math.min(50,getWidth()) - textWidth) / 2, (Math.min(50,getHeight()) + textHeight) / 2 - 4);

        g2.dispose(); // Clean up
    }
}
