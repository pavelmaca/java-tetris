package tetris;

import tetris.engine.Engine;
import tetris.gui.Gui;

/**
 * Clasic arcade game Tetris realized in Java using Swing library.
 *
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class Tetris {

    /**
     * Main method, inicializing logic (engine) and gui
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            int width = 10;
            int height = 17;
            int squereSize = 15;

            Engine tetris = new Engine(height, width);
            Gui graphics = new Gui(tetris, squereSize);
            graphics.render();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
