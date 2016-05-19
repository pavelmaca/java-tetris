package tetris.engine;

import java.awt.*;
import java.util.Random;

/**
 * Genrátor náhodných tvarů
 *
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class ShapeGenerator {

    /* Generátor náhodných čísel */
    private Random randomGenerator;

    public ShapeGenerator() {
        randomGenerator = new Random();
    }

    /**
     * Vytvoření nové instance náhodného tvaru a jeho náhodného natočení a překlopení.
     *
     * @return Náhodně vybraný tvar
     */
    public Shape createNext() {
        Type randomType = getRandomType();
        Shape newShape = new Shape(randomType.points, randomType.color);

        randomFlip(newShape);
        randomRotation(newShape);

        return newShape;
    }

    /**
     * Náhodný výběr tvyru ze seznamu typů
     *
     * @return Náhodně vybraný typ tvaru
     */
    private Type getRandomType() {
        Type[] types = Type.values();
        return types[randomGenerator.nextInt(types.length)];
    }

    /**
     * Náhodné otočení tvaru
     *
     * @param shape Otáčený tvar
     */
    private void randomRotation(Shape shape) {
        switch (randomGenerator.nextInt(4)) { // 0 - 3, pokud bude 3, tak se nepřeklopí, jinak číslo určuje (počet otočení - 1 )
            case 0:
                shape.rotate();
            case 1:
                shape.rotate();
            case 2:
                shape.rotate();
                break;
        }
    }

    /**
     * Náhodné překlopení tvaru
     *
     * @param shape Překlápěný tvar
     */
    private void randomFlip(Shape shape) {
        switch (randomGenerator.nextInt(2)) { // 0 - 1, pokud bude 1, tak se nepřeklopí
            case 0:
                shape.flip();
                break;
        }
    }


    /**
     * Seznam dostupných typů tvarů a jejich předpisy.
     * Každý typ má pevně nastavenou barvu a je definován dvourozměrným polem "bodů".
     */
    private enum Type {
        LINE(new boolean[][]{
                {false, false, false},
                {true, true, true},
                {false, false, false},
        }, Color.MAGENTA),

        CORNER(new boolean[][]{
                {true, false},
                {true, true},
        }, Color.ORANGE),

        T(new boolean[][]{
                {true, true, true},
                {false, true, false},
        }, Color.GREEN),

        SQUARE(new boolean[][]{
                {true, true},
                {true, true},
        }, Color.RED),


        N(new boolean[][]{
                {true, true, false},
                {false, true, true},
        }, Color.YELLOW),

        L(new boolean[][]{
                {true, true, true},
                {true, false, false},
        }, Color.BLUE);

        /**
         * Pole určijící tvar objektu
         * Logické hodnota určuje zda bod do objektu patří, či nikoli.
         */
        boolean[][] points;

        /* Barva objektu */
        Color color;

        /**
         * @param points Dvourozměrné pole učující tvar pomocí logických hodnot.
         * @param color  Barva objektu
         */
        Type(boolean[][] points, Color color) {
            this.points = points;
            this.color = color;
        }
    }

}