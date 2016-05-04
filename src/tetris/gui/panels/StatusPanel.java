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

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        creteAllButtons();

        createScoreLabel();

        JLabel nextLabel = new JLabel();
        nextLabel.setText("Další:");

        add(nextLabel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Shape nextShape = engine.getNextShape();
        boolean[][] points = nextShape.getPoints();

        for (int x = 0; x < nextShape.getWidth(); x++) {
            for (int y = 0; y < nextShape.getHeight(); y++) {
                if (points[y][x]) {
                    g.setColor(nextShape.getColor());
                    g.fillRect(20 + x * 10, 50 + y * 10, 10, 10);
                }

            }
        }
    }

    private void creteAllButtons(){
        // Start - pause - continue
        JButton btnStart = new JButton("Start");
        // btnStart.setPreferredSize(new Dimension(100, 30));
        btnStart.setFocusable(false); // otherwise, it would steel focus from game
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        // Restart
        JButton btnRestart = new JButton("Restart");
        //  btnRestart.setEnabled(false);
        btnRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRestart.setFocusable(false); // otherwise, it would steel focus from game
        add(btnRestart);

        btnRestart.addActionListener((actionEvent)->{
            engine.restart();
            btnStart.setText("Start");
        });
    }

    private void createScoreLabel(){
        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Skóre :" + engine.getScore());
        add(scoreLabel);

        // Listen to score changes
        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void scoreChange(int score) {
                scoreLabel.setText("Skóre :" + score);
            }

            @Override
            public void gameEnd() {
                scoreLabel.setEnabled(false);
            }

            @Override
            public void shapeChange(Shape shape) {
                repaint();
            }
        });
    }
}
