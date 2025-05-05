package controllers;

import javax.swing.SwingUtilities;
import models.MouseEngine;
import models.Game;
import models.TimeStamp;
import models.Utilities;
import models.Vec2D;

import java.lang.Thread;
import views.GameFrame;

public class Controller {
    Game game;
    GameFrame gameFrame;
    MouseEngine mouseEngine;
    Utilities utilities;

    public Controller() {
        initComponents();
        startGame();
    }

    private void startGame() {
        TimeStamp ts = new TimeStamp();
        while (true) {

            float dt = ts.getStamp();
            game.refreshModel(dt);
            SwingUtilities.invokeLater(() -> gameFrame.refresh());
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initComponents() {
        utilities = new Utilities();
        mouseEngine = new MouseEngine();
        game = new Game(utilities, mouseEngine);
        gameFrame = new GameFrame(utilities, game, mouseEngine);
    }
}
