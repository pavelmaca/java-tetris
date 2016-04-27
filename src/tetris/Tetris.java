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
        int width = 20;
        int height = 30;
        int squereSize = 15;

        Engine tetris = new Engine(height, width);
        Gui graphics = new Gui(tetris, squereSize);
        graphics.render();
    }
}
