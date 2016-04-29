package tetris.gui.panels;

import tetris.engine.Engine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Assassik on 25. 4. 2016.
 */
public class StatusPanel extends JPanel {

    private Engine engine;

    public StatusPanel(Engine engine) {
        this.engine = engine;

        JButton btnStart = new JButton("Start");
        btnStart.setPreferredSize(new Dimension(100, 30));
        btnStart.setFocusable(false); // otherwise, it would steel focus from game
        add(btnStart);

        btnStart.addActionListener((actionEvent) -> {
            String command = actionEvent.getActionCommand();
            if (command.equals("Start") || command.equals("Pokračovat")) {
                engine.start();
                btnStart.setText("Pauza");
            } else {
                engine.pause();
                btnStart.setText("Pokračovat");
            }
        });

        JLabel scoreLabel = new JLabel();
        add(scoreLabel);

        // Listen to score changes
        engine.addScoreListener(score -> {
            scoreLabel.setText("" + score);
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //score.setText("Skóre je: " + engine.getScore());
    }
}
