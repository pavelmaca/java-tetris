package tetris.engine;

import java.awt.*;

/**
 * Created by Pavel Máca on 7. 4. 2016.
 */
public class Shape {

    /**
     * Body ze kterých se objekt skládá
     * NULL představuje prázdné místo
     */
    private boolean[][] points;
    private Color color;

    public Shape(int width, int height, Color color) {

        points = new boolean[height][width];
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

    public void addPoint(int x, int y) {
        points[y][x] = true;
    }


    public void rotate() {
        int width = getWidth();
        int height = getHeight();

        //create new array with switched dimensions
        boolean[][] newShape = new boolean[width][height];

        // round up
        int xStop = (int) Math.ceil(width / 2.0);
        int yStop = (int) Math.ceil(height / 2.0);

        for (int yTop = 0; yTop < yStop; yTop++) {
            for (int xLeft = 0; xLeft < xStop; xLeft++) {
                int yBottom = height - 1 - yTop;
                int xRight = width - 1 - xLeft;

                // rotate right
                newShape[xLeft][yBottom] = points[yBottom][xRight];
                newShape[xLeft][yTop] = points[yTop][xRight];
                newShape[xRight][yTop] = points[yTop][xLeft];
                newShape[xRight][yBottom] = points[yBottom][xLeft];
            }

        }

        points = newShape;
    }


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

    public boolean[][] getPoints() {
        return points;
    }

}