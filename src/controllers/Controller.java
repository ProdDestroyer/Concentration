package controllers;

import javax.swing.SwingUtilities;
import models.MouseEngine;
import models.Game;
import models.TimeStamp;
import java.lang.Thread;
import views.GameFrame;

public class Controller {
    Game game;
    GameFrame gameFrame;
    MouseEngine mouseEngine;

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
        mouseEngine = new MouseEngine();
        game = new Game(5, mouseEngine);
        gameFrame = new GameFrame(game, mouseEngine);
    }
}
