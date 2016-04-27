package tetris.gui.panels;

import tetris.engine.Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Assassik on 25. 4. 2016.
 */
public class StatusPanel extends JPanel {

    private Engine engine;

    public StatusPanel(Engine engine) {
        this.engine = engine;

    }

    /*@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }*/
}
