package tetris.gui;


import tetris.engine.*;
import tetris.gui.panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Assassik on 6. 4. 2016.
 */
public class Gui {

    JFrame frame;

    Engine engine;

    public Gui(Engine engine){
        this.engine = engine;
    }

    private JPanel renderCenter(){
        JPanel centerPanel = new GamePanel(engine);
        frame.add(centerPanel, BorderLayout.CENTER);

        centerPanel.setSize(525, 675);
        centerPanel.setFocusable(true);

        JLabel label = new JLabel("Test label.");
        centerPanel.add(label);

        return centerPanel;
    }

    private JPanel renderMenu(){
        JPanel rightPanel = new JPanel();
        frame.add(rightPanel, BorderLayout.EAST);

        JButton btn = new JButton("Start");
        // btn.setSize(10, 200);
        btn.addActionListener((actionEvent) -> {
            System.out.println(actionEvent.getActionCommand());
        });
        rightPanel.add(btn);

        return rightPanel;
    }

    public void render() {
        frame = new JFrame("Tetris");

        // end program after window exit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        // Center panel
        JPanel center = renderCenter();

        // Menu
        JPanel menu = renderMenu();

        int width = center.getWidth() + menu.getWidth();
        int height = center.getHeight() + menu.getHeight();
        frame.setSize(width, height);
        frame.setResizable(false);

        frame.setVisible(true);
    }


    private void testShapes() {
        ShapeGenerator gen = new ShapeGenerator();
        try {
            for (int i = 0; i < 20; i++) {
                System.out.println();
                printShape(gen.createNext());
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }

    private void printShape(tetris.engine.Shape shape) {
        boolean[][] points = shape.getPoints();
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                System.out.print(points[i][j] == false ? "-" : "x");
            }
            System.out.println();
        }
    }

}
