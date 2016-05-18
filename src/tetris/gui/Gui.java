package tetris.gui;


import tetris.engine.Engine;
import tetris.gui.panels.GamePanel;
import tetris.gui.panels.StatusPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class Gui {

    private JFrame frame;

    private Engine engine;

    private int squereSize;

    public Gui(Engine engine, int squereSize) {
        this.engine = engine;
        this.squereSize = squereSize;
    }

    public void render() {
        frame = new JFrame("Tetris");

        // end program after window exit
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        renderGame();

        renderStatus();

        frame.setResizable(false);

        frame.pack();

        frame.setLocationRelativeTo(null); //run in center of screen

        frame.setVisible(true);
    }

    private void renderGame() {
        JPanel gamePanel = new GamePanel(engine);
        frame.add(gamePanel);

        Dimension panelSize = new Dimension();
        panelSize.setSize(engine.getColsCount() * squereSize, engine.getRowsCount() * squereSize);

        gamePanel.setPreferredSize(panelSize);

        gamePanel.setFocusable(true); // set focus for key events
    }

    private void renderStatus() {
        JPanel statusPanel = new StatusPanel(engine);

        statusPanel.setPreferredSize(new Dimension(125, 10));
        frame.add(statusPanel);
    }

}
