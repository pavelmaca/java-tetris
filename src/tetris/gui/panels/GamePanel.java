package tetris.gui.panels;

import tetris.engine.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by Assassik on 25. 4. 2016.
 */
public class GamePanel extends JPanel {


    private Timer timer;
    private int engineTick = 0;

    private int fallSpeed = 50;

    private Engine engine;

    public GamePanel(Engine engine) {
        this.engine = engine;

        timer = new Timer(10, (ActionEvent e) -> {
            engineTick++;
            if(engineTick == fallSpeed) {
                engineTick = 0;
                engine.tick();
            }

            repaint();
        });


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        engine.moveLeft(); break;
                    case KeyEvent.VK_RIGHT:
                        engine.moveRight(); break;
                    case KeyEvent.VK_DOWN:
                        engine.moveDown(); break;
                    case KeyEvent.VK_SPACE:
                        engine.rotateShape(); break;
                };
            }
        });

        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //System.out.println("paint me!");

        // Background
        setBackground(Color.GRAY);

        Color[][] fileds = engine.getGameFields();

        int squareSize = getWidth() / fileds.length;
        for (int x = 0; x < fileds.length; x++) {
            for (int y = 0; y < fileds[x].length; y++) {
                if (fileds[x][y] == null) {
                    continue;
                }

                g.setColor(fileds[x][y]);
                g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
            }
        }
    }
}
