package tetris;

import tetris.engine.Engine;
import tetris.gui.Gui;

/**
 * Created by Pavel Máca on 6. 4. 2016.
 */
public class Tetris {
    public static void main(String[] args) throws Exception {
        try {
            int width = 10;
            int height = 17;
            int squereSize = 15;

            Engine tetris = new Engine(height, width);
            Gui graphics = new Gui(tetris, squereSize);
            graphics.render();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
