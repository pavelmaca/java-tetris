package tetris;

import tetris.engine.Engine;
import tetris.gui.Gui;

/**
 * Created by Assassik on 6. 4. 2016.
 */
public class Tetris {
    public static void main(String[] args) throws Exception {
        int width = 17;
        int height = 25;
        int squereSize = 15;

        Engine tetris = new Engine(height, width);
        Gui graphics = new Gui(tetris, squereSize);
        graphics.render();
    }
}
