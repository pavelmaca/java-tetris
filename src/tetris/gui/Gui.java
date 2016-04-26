package tetris.gui;


import tetris.engine.Engine;
import tetris.gui.panels.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Assassik on 6. 4. 2016.
 */
public class Gui {

    JFrame frame;

    Engine engine;

    public Gui(Engine engine) {
        this.engine = engine;
    }

    public void render() {
        frame = new JFrame("Tetris");

        // end program after window exit
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       // frame.setLayout(new BorderLayout());
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        // Center panel
        JPanel center = renderCenter();

        // Menu
        JPanel menu = renderMenu();

      /*  int width = center.getWidth() + menu.getWidth();
        int height = center.getHeight() + menu.getHeight();
        frame.setSize(width, height);*/
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);

    }

    private JPanel renderCenter() {
        JPanel centerPanel = new GamePanel(engine);
        frame.add(centerPanel);

        Dimension size = new Dimension();
        size.setSize(engine.getColsCount() * 10, engine.getRowsCount() * 10);
        centerPanel.setPreferredSize(size);
     /*   centerPanel.setMinimumSize(size);
        centerPanel.setMaximumSize(size);*/
        centerPanel.setFocusable(true); // set focus for key events

        return centerPanel;
    }

    private JPanel renderMenu() {
        JPanel rightPanel = new JPanel();
        frame.add(rightPanel);

        JButton btn = new JButton("Start");
        // btn.setSize(10, 200);
        btn.addActionListener((actionEvent) -> {
            System.out.println(actionEvent.getActionCommand());
        });
        rightPanel.add(btn);

        return rightPanel;
    }


}
