package tetris.gui.panels;

import tetris.engine.Difficulty;
import tetris.engine.Engine;
import tetris.engine.Shape;
import tetris.engine.board.Record;
import tetris.engine.board.ScoreBoard;
import tetris.engine.events.GameStatusAdapter;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Pavel Máca on 25. 4. 2016.
 */
public class StatusPanel extends JPanel {

    private final String BTN_START = "Start";
    private final String BTN_PAUSE = "Pauza";
    private final String BTN_CONTINUE = "Pokračovat";
    private final String BTN_RESTART = "Restart";

    private Engine engine;

    private ScoreBoard scoreBoard = new ScoreBoard("score_board.dat");

    public StatusPanel(Engine engine) {
        this.engine = engine;

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        add(Box.createVerticalStrut(7));

        creteButtons();

        add(Box.createVerticalStrut(10));

        createScoreLabel();

        add(Box.createVerticalStrut(5));

        JLabel nextLabel = new JLabel();
        nextLabel.setText("Další:");
        nextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(nextLabel);


        add(Box.createVerticalGlue());

        // Top score
        JButton btnBoard = new JButton("Top skóre");
        btnBoard.setFocusable(false); // otherwise, it would steel focus from game
        btnBoard.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnBoard);

        btnBoard.addActionListener((eventListener) -> showScoreBoard());


        add(Box.createVerticalStrut(7));
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

    private void creteButtons() {
        JComboBox<Difficulty> difficulty = new JComboBox<>();
        for (Difficulty difficultyLevel : Difficulty.values()) {
            difficulty.addItem(difficultyLevel);
        }
        difficulty.setSelectedItem(engine.getDifficulty());

        difficulty.setMaximumSize(new Dimension(100, 20));
        add(difficulty);

        difficulty.addActionListener((actionEvent) -> {
                    if (actionEvent.getSource().equals(difficulty)) {
                        engine.setDifficulty((Difficulty) difficulty.getSelectedItem());
                    }
                }
        );

        add(Box.createVerticalStrut(7));

        // Start - pause - continue
        JButton btnStart = new JButton(BTN_START);
        // btnStart.setPreferredSize(new Dimension(100, 30));
        btnStart.setFocusable(false); // otherwise, it would steel focus from game
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(btnStart);

        add(Box.createVerticalStrut(7));

        // Restart
        JButton btnRestart = new JButton(BTN_RESTART);
        //  btnRestart.setEnabled(false);
        btnRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRestart.setFocusable(false); // otherwise, it would steel focus from game
        btnRestart.setEnabled(false);
        add(btnRestart);

        // Listenre - Start - pause - continue
        btnStart.addActionListener((actionEvent) -> {
            String command = actionEvent.getActionCommand();
            if (command.equals(BTN_START) || command.equals(BTN_CONTINUE)) {
                engine.start();
                difficulty.setEnabled(false);
                btnRestart.setEnabled(true);
                btnStart.setText(BTN_PAUSE);
            } else {
                engine.pause();
                btnStart.setText(BTN_CONTINUE);
            }
        });


        // Listener - Restart
        btnRestart.addActionListener((actionEvent) -> {
            engine.restart();
            btnStart.setText(BTN_START);
            btnStart.setEnabled(true);
            btnRestart.setEnabled(false);
            difficulty.setEnabled(true);
        });

        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void gameEnd() {
                btnStart.setEnabled(false);

                showScoreDialog();
            }
        });
    }

    private void createScoreLabel() {
        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Skóre");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreLabel);

        JLabel scoreValueLabel = new JLabel();
        scoreValueLabel.setFont(new Font("Serif", Font.BOLD, 20));
        scoreValueLabel.setText("" + engine.getScore());
        scoreValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(scoreValueLabel);

        // Listen to score changes
        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void scoreChange(int score) {
                scoreValueLabel.setText("" + score);
            }

            @Override
            public void shapeChange() {
                repaint();
            }
        });
    }

    private void showScoreDialog() {
        String playerName = JOptionPane.showInputDialog(this,
                "Scóre: " + engine.getScore() + "\n" +
                        "Vaše jméno:",
                "Konec hry",
                JOptionPane.ERROR_MESSAGE);

        Record actualRecord = null;
        if (playerName != null) {
            try {
                actualRecord = scoreBoard.saveScore(playerName, engine.getScore());
                scoreBoard.saveData();
            } catch (Record.InvalidScoreException e) {
                e.printStackTrace();
            }
        }

        showScoreBoard(actualRecord);

    }

    private void showScoreBoard() {
        showScoreBoard(null);
    }

    private void showScoreBoard(Record actualRecord) {

        int topLimit = 10;
        Record[] list = scoreBoard.getTop(topLimit);

        String textBoard = "Top " + topLimit + " skóre:\n";
        for (int i = 1; i <= list.length; i++) {
            if (list[i - 1] == null) break;

            textBoard += i + ". \t" + list[i - 1].getScore() + " : \t" + list[i - 1].getPlayerName() + "\n";
        }

        if (actualRecord != null) {
            textBoard += "\n";
            textBoard += "Vaše pozice: \n" + scoreBoard.getPosition(actualRecord) + ". " + actualRecord.getScore() + " : " + actualRecord.getPlayerName();
        }


        boolean running = engine.isRunning();
        if (running) engine.pause();
        JOptionPane.showMessageDialog(this, textBoard, "Žebříček", JOptionPane.INFORMATION_MESSAGE);
        if (running) engine.start();
    }
}
