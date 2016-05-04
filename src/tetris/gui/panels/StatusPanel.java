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
        //add(Box.createHorizontalStrut(50));

        createScoreLabel();

        add(Box.createVerticalStrut(5));

        JLabel nextLabel = new JLabel();
        nextLabel.setText("Další:");
        nextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(nextLabel);

        add(Box.createVerticalGlue());

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Shape nextShape = engine.getNextShape();
        boolean[][] points = nextShape.getPoints();

        int size = 10;
        int yStart = 180;
        int xStart = getWidth() / 2 - nextShape.getWidth() / 2 * size;
        for (int x = 0; x < nextShape.getWidth(); x++) {
            for (int y = 0; y < nextShape.getHeight(); y++) {
                if (points[y][x]) {
                    g.setColor(nextShape.getColor());
                    g.fillRect(xStart + x * size, yStart + y * size, size, size);
                }

            }
        }
    }

    private void creteAllButtons() {
        add(Box.createVerticalStrut(7));

        JComboBox<String> difficulty = new JComboBox<>();
        difficulty.addItem("Lehké");
        difficulty.addItem("Střední");
        difficulty.addItem("Těžké");
        difficulty.setSelectedIndex(1);
        difficulty.setMaximumSize(new Dimension(100, 20));
        add(difficulty);

        add(Box.createVerticalStrut(7));

        // Start - pause - continue
        JButton btnStart = new JButton("Start");
        // btnStart.setPreferredSize(new Dimension(100, 30));
        btnStart.setFocusable(false); // otherwise, it would steel focus from game
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnStart);

        add(Box.createVerticalStrut(7));

        // Restart
        JButton btnRestart = new JButton("Restart");
        //  btnRestart.setEnabled(false);
        btnRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRestart.setFocusable(false); // otherwise, it would steel focus from game
        btnRestart.setEnabled(false);
        add(btnRestart);

        // Listenre - Start - pause - continue
        btnStart.addActionListener((actionEvent) -> {
            String command = actionEvent.getActionCommand();
            if (command.equals("Start") || command.equals("Pokračovat")) {
                engine.start();
                difficulty.setEnabled(false);
                btnRestart.setEnabled(true);
                btnStart.setText("Pauza");
            } else {
                engine.pause();
                btnStart.setText("Pokračovat");
            }
        });


        // Listener - Restart
        btnRestart.addActionListener((actionEvent) -> {
            engine.restart();
            btnStart.setText("Start");
            btnRestart.setEnabled(false);
            difficulty.setEnabled(true);
        });
    }

    private void createScoreLabel() {
        add(Box.createVerticalStrut(10));

        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Skóre");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreLabel);

        JLabel scoreValueLabel = new JLabel();
        scoreValueLabel.setFont(new Font("Serif", Font.BOLD, 20));
        scoreValueLabel.setText("" + engine.getScore() );
        scoreValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreValueLabel);

        // Listen to score changes
        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void scoreChange(int score) {
                scoreValueLabel.setText("" + score );
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
