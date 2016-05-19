package tetris.engine;

import java.awt.*;

/**
 * Objekt představující tvar v herní ploše.
 * Umožnuje otáčení a překlápění.
 *
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class Shape {

    /**
     * Body reprezentující tvar objektu v dvojrozměrném poli.
     * FALSE značí, že bod do objektu nepatří.
     */
    private boolean[][] points;

    /**
     * Barva tvaru
     */
    private Color color;

    /**
     * @param points Body tvořící objekt v dvojrozměrném poli.
     * @param color  Barva objektu
     */
    public Shape(boolean[][] points, Color color) {

        this.points = points;
        this.color = color;
    }

    /**
     * @return Výška objektu
     */
    public int getHeight() {
        return points.length;
    }

    /**
     * @return Šířka objektu
     */
    public int getWidth() {
        return points.length > 0 ? points[0].length : 0;
    }

    /**
     * @return Barva objektu
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return Dvojrozměrné pole představujicí tvar objektu pomocí logických hodnot
     */
    public boolean[][] getPoints() {
        return points;
    }


    /**
     * Otočí objekt po sěru hodinových ručiček o 90 stupnů
     */
    public void rotate() {
        int width = getWidth();
        int height = getHeight();

        // nové dvojrozměrné pole s opačnými rozměry
        boolean[][] newShape = new boolean[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                newShape[i][height - j - 1] = points[j][i]; // Rotace o +90 stupnů: Transpose + Reverse each row
            }
        }

        points = newShape;
    }


    /**
     * Vertikální překlopení
     */
    public void flip() {
        int width = getWidth();
        int height = getHeight();

        // zaokrouhleno nahoru
        int widthStop = (int) Math.ceil(width / 2.0);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < widthStop; x++) {
                boolean tmp = points[y][x];
                points[y][x] = points[y][width - 1 - x];
                points[y][width - 1 - x] = tmp;
            }
        }
    }
}