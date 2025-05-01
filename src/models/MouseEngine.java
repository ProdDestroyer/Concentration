package models;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEngine extends MouseAdapter {

    private float clickedX;
    private float clickedY;
    private float movedX;
    private float movedY;
    private boolean clicked;

    @Override
    public void mousePressed(MouseEvent e) {
        if (!clicked) {
            clicked = true;
            clickedX = e.getX();
            clickedY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        movedX = e.getX();
        movedY = e.getY();
    }

    public Vec2D readClick() {
        Vec2D clickCoords = new Vec2D(clickedX, clickedY);
        clicked = false;
        return clickCoords;
    }

    public boolean isClicked() {
        return clicked;
    }

    public Vec2D readMoved() {
        return new Vec2D(movedX, movedY);
    }

}
