package views;

import java.awt.Toolkit;
import javax.swing.JFrame;
import models.MouseEngine;
import models.Game;
import models.Utilities;

public class GameFrame extends JFrame {

    private GamePanel gamePanel;
    private Utilities utilities;

    public GameFrame(Utilities utilities, Game game, MouseEngine mouseEngine) {
        this.utilities = utilities;
        initLocation();
        initComponents(game, mouseEngine);
        initProperties();
    }
    
    private void initLocation() {
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();

        double windowWidth = utilities.windowWidth();
        double windowHeight = utilities.windowHeight();
        setSize((int)windowWidth, (int)windowHeight);  
        setLocation((int)(width / 2 - windowWidth / 2), (int)(height / 50));
    }

    private void initProperties() {
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        refresh();
    }
    
    private void initComponents(Game game, MouseEngine aimingEngine) {
        gamePanel = new GamePanel(game, aimingEngine);
        add(gamePanel);

    }

    public void refresh() {
        if(utilities.pendingWindowResize) {
            initLocation();
            utilities.pendingWindowResize = false;
        }
        gamePanel.repaint();
    }

}
