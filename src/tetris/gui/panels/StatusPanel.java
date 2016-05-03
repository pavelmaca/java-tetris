package tetris.gui.panels;

import tetris.engine.*;
import tetris.engine.Shape;
import tetris.engine.events.GameStatusAdapter;

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
        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void scoreChange(int score) {
                scoreLabel.setText("" + score);
            }

            @Override
            public void gameEnd() {
                scoreLabel.setEnabled(false);
            }

            @Override
            public void shapeChaned(Shape shape) {
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Shape nextShape = engine.getNextShape();
        boolean[][] points = nextShape.getPoints();

        for(int x = 0; x < nextShape.getWidth(); x++){
            for (int y = 0; y < nextShape.getHeight(); y++) {
                if(points[y][x]){
                    g.setColor(nextShape.getColor());
                    g.fillRect(20 + x * 10, 50+ y*10, 10,10);
                }

            }
        }
    }
}
