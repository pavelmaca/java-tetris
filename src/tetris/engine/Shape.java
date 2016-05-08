package tetris.engine;

import java.awt.*;

/**
 * Created by Pavel Máca on 7. 4. 2016.
 */
public class Shape {

    /**
     * Points creating shape, stored in two dimensional array
     * NULL represents empty space
     */
    private boolean[][] points;

    /**
     * Color of shape
     */
    private Color color;

    /**
     * @param points Points creating shape, stored in two dimensional array
     * @param color  Color of shape
     */
    public Shape(boolean[][] points, Color color) {

        this.points = points;
        this.color = color;
    }

    public int getHeight() {
        return points.length;
    }

    public int getWidth() {
        return points.length > 0 ? points[0].length : 0;
    }

    public Color getColor() {
        return color;
    }

    public boolean[][] getPoints() {
        return points;
    }


    /**
     * Rotate shape clock-wise (+90 degrees]
     */
    public void rotate() {
        int width = getWidth();
        int height = getHeight();

        //create new array with switched dimensions
        boolean[][] newShape = new boolean[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                newShape[i][height - j - 1] = points[j][i]; // Rotate by +90: Transpose + Reverse each row
            }
        }

        points = newShape;
    }


    /**
     * Flip verticaly
     */
    public void flip() {
        int width = getWidth();
        int height = getHeight();

        // round up
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