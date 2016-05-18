package tetris;

import tetris.engine.Engine;
import tetris.gui.Gui;

/**
 * Klasická akádová hra Tetris.
 * Používá grafické rozhraní založené na knihovně Java Swing
 *
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class Tetris {

    /**
     * Spouštěcí metoda, která má za úkol provést inicializaci
     * logické části (Engine) a grafického rozhraní (Gui)
     * Určuje velikost hrací plochy a počet čtverců uvnitř herního pole.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            int width = 10; // Počet bloků na šířku herního pole
            int height = 17; // Počet bloků na víšku herního pole
            int squereSize = 15; // Velikost jednoho bloku (čtverce) hrací plochy

            Engine tetris = new Engine(height, width);
            Gui graphics = new Gui(tetris, squereSize);
            graphics.render();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
