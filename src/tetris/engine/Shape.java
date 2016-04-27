package tetris.engine;

import java.awt.*;

/**
 * Created by Assassik on 7. 4. 2016.
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
        // skip colsCount is 0, nothing to do
        if (points.length == 0) {
            return;
        }

        int xCount = points[0].length;
        int yCount = points.length;

        //create new array with switched dimensions
        boolean[][] newShape = new boolean[xCount][yCount];

        //TODO do round up
        int xStop = xCount / 2 + (xCount % 2 == 0 ? 0 : 1);
        int yStop = yCount / 2 + (yCount % 2 == 0 ? 0 : 1);

        for (int yTop = 0; yTop < yStop; yTop++) {
            for (int xLeft = 0; xLeft < xStop; xLeft++) {
                int yBottom = yCount - 1 - yTop;
                int xRight = xCount - 1 - xLeft;

                // rotate right
                newShape[xLeft][yBottom] = points[yBottom][xRight];
                newShape[xLeft][yTop] = points[yTop][xRight];
                newShape[xRight][yTop] = points[yTop][xLeft];
                newShape[xRight][yBottom] = points[yBottom][xLeft];
            }

        }

        points = newShape;
    }

    public void flipVerticaly() {
        flip();
    }

    //TODO flip horizontaly
    public void flipHorizontaly() {
        flip();
    }

    private void flip() {
        // skip colsCount is 0, nothing to do
        if (points.length == 0) {
            return;
        }

        int xLenght = points[0].length;
        int yLenght = points.length;

        int yStop = yLenght / 2;
        int xStop = xLenght / 2;
        for (int y = 0; y < yStop; y++) {
            for (int x = 0; x < xStop; x++) {
                boolean tmp = points[y][x];
                points[y][x] = points[y][xLenght - 1 - x];
                points[y][xLenght - 1 - x] = tmp;
            }
        }
    }

    public boolean[][] getPoints() {
        return points;
    }

}