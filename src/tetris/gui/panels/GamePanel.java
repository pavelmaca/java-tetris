package tetris.gui.panels;

import tetris.engine.Engine;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * Created by Assassik on 25. 4. 2016.
 */
public class GamePanel extends JPanel {


    private java.util.Timer timer;

    private final int FPS = 60;

    // number of ms betwean repainting squers
    private final int FALL_SPEED = 250;

    private Engine engine;

    public GamePanel(Engine engine) {
        this.engine = engine;

        /*
        System.out.println("FPS : "+FPS + " interval: "+(1000/FPS));
        System.out.println("FALL_SPEED: "+FALL_SPEED + " fall interval:"+ FALL_SPEED / (1000 / FPS));
        */

        timer = new java.util.Timer();
        timer.schedule(new RepaintTask(), 1000, 1000 / FPS);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        engine.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        engine.moveRight();
                        break;
                    case KeyEvent.VK_DOWN:
                        engine.moveDown();
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_SPACE:
                        engine.rotateShape();
                        break;
                }
                ;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        setBackground(Color.LIGHT_GRAY);

        Color[][] fileds = engine.getGameFields();

        // always use squere, so we need only one dimension
        int squareSize = getWidth() / engine.getColsCount();

        for (int x = 0; x < engine.getColsCount(); x++) {
            for (int y = 0; y < engine.getRowsCount(); y++) {
                if (fileds[y][x] == null) {
                    continue;
                }

                drawSquere(g, x * squareSize, y * squareSize, squareSize, fileds[y][x]);
                //  System.out.println(x + ":"+y + "x: "+(x * squareSizeX) + " y:" + (y * squareSizeY));
            }
        }
    }

    private void drawSquere(Graphics g, int x, int y, int size, Color bgColor) {
        g.setColor(bgColor);
        g.fillRect(x, y, size, size);

        g.setColor(Color.GRAY);
        g.drawRect(x, y, size, size);
    }

    class RepaintTask extends TimerTask {

        int tickNumber = 0;
        @Override
        public void run() {
            tickNumber++;
            if (tickNumber == (FALL_SPEED * FPS) / 1000) {
                tickNumber = 0;
                engine.tick();

                if (engine.isGameOver()) {
                    System.out.println("Game over!");
                    System.out.println("Deal with it...");
                    this.cancel();
                }
            }

            repaint();
        }
    }
}
