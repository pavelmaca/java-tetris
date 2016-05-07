package tetris.gui.panels;

import tetris.engine.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

/**
 * Created by Pavel Máca on 25. 4. 2016.
 */
public class GamePanel extends JPanel {


    private final int FPS = 60;

    private java.util.Timer timer;
    private Engine engine;

    public GamePanel(Engine engine) {
        this.engine = engine;

        timer = new java.util.Timer();
        timer.schedule(new RepaintTask(), 1000, 1000 / FPS);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        engine.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        engine.moveRight();
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        engine.moveDown();
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_SPACE:
                    case KeyEvent.VK_W:
                        engine.rotateShape();
                        break;
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        setBackground(Color.LIGHT_GRAY);

        Color[][] fileds = engine.getStatus();

        // always use squere, so we need only one dimension
        int squareSize = getWidth() / engine.getColsCount();

        for (int x = 0; x < engine.getColsCount(); x++) {
            for (int y = 0; y < engine.getRowsCount(); y++) {
                if (fileds[y][x] == null) {
                    continue;
                }

                drawSquare(g, x * squareSize, y * squareSize, squareSize, fileds[y][x]);
            }
        }
    }

    private void drawSquare(Graphics g, int x, int y, int size, Color bgColor) {
        g.setColor(bgColor);
        g.fillRect(x, y, size, size);

        g.setColor(Color.GRAY);
        g.drawRect(x, y, size, size);
    }

    private class RepaintTask extends TimerTask {

        int tickNumber = 0;

        @Override
        public void run() {
            tickNumber++;
            if (tickNumber >= (engine.getDifficulty().getFallSpeed() * FPS) / 1000) {
                tickNumber = 0;
                engine.tick();
            }

            repaint();
        }
    }
}
