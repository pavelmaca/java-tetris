package tetris;

import tetris.engine.Engine;
import tetris.engine.Shape;
import tetris.engine.ShapeGenerator;

import tetris.gui.Gui;

import java.awt.*;

/**
 * Created by Assassik on 6. 4. 2016.
 */
public class Tetris {
    public static void main(String[] args) throws Exception {
        int width = 35;
        int height = 45;

        Engine tetris = new Engine(width, height);
        Gui graphics = new Gui(tetris);
        graphics.render();
    }
}
