package tetris.engine;

import java.awt.*;

/**
 * Created by Assassik on 7. 4. 2016.
 */
public class Engine {

    /**
     * Herní plocha tvořená souřadnicemi x a y
     * První rozměr je výška, druhý šířka
     * Hodnota NULL představuje prázdnou plochu
     */
    private Color[][] fileds;

    private Shape nextShape;
    private Shape actualShape = null;
    int actualX;
    int actualY;

    int rowsCount;
    int colsCount;

    private int score = 0;
    private boolean gameOver = false;


    ShapeGenerator generator = new ShapeGenerator();

    public Engine(int rowsCount, int colsCount) {
        fileds = new Color[rowsCount][colsCount];
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;

        nextShape = generator.createNext();
        creteNewShape();
        /* Test corners*/
        /*
        fileds[rowsCount-1][0] = Color.YELLOW;
        fileds[0][colsCount-1] = Color.BLUE;
        fileds[rowsCount-1][colsCount-1] = Color.RED;
        fileds[0][0] = Color.GREEN;*/
    }


    protected void creteNewShape() {
        actualShape = nextShape;
        actualX = colsCount / 2;
        actualY = 0;

        nextShape = generator.createNext();
    }

    public void tick() {
        int nextY = actualY +1;
        if (!isColision(actualX, nextY)) {
            actualY = nextY;
            System.out.println("tick");
        } else {
            saveShape();
            tryCleanup();
            creteNewShape();
            if (isColision(actualX, actualY)) {
                gameOver = true;
            }
            System.out.println("collision");
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }


    protected void saveShape() {
        margeFildsAndShape(fileds, actualShape);
    }

    protected boolean isColision(int nextX, int nextY) {
        boolean[][] points = actualShape.getPoints();
        for (int x = 0; x < actualShape.getWidth(); x++) {
            for (int y = 0; y < actualShape.getHeight(); y++) {
                if (!points[y][x]) {
                    continue;
                }

                // sides and bottom collision
                if (nextX + x >= colsCount || nextX + x < 0 || nextY + y >= rowsCount) {
                    return true;
                }

                // field collision
                if (fileds[nextY + y][nextX + x] != null) {
                    return true;
                }
            }
        }

        return false;
    }

    protected void tryCleanup() {
        boolean[] rowsToClean = new boolean[rowsCount];
        for (int y = 0; y < rowsCount; y++) {
            boolean fullRow = true;
            for (int x = 0; x < colsCount; x++) {
                if (fileds[y][x] == null) {
                    fullRow = false;
                    break;
                }
            }
            rowsToClean[y] = fullRow;
        }

        // TODO not working in more complex situations
        // maybe use recursion??
        int n = 0;
        for (int y = rowsCount - 1; y > 0; y--) {
            if (rowsToClean[y]) {
                n++;
            }

            if (n > 0) {
                Color[] oldRow = y - n < 0 ? new Color[colsCount] : fileds[y - n];
                fileds[y] = oldRow;
            }
        }
    }


    public Color[][] getGameFields() {
        Color[][] fields = deepCopyFields();
        return margeFildsAndShape(fields, actualShape);
    }

    protected Color[][] margeFildsAndShape(Color[][] fields, Shape shape) {

        boolean[][] points = shape.getPoints();

        for (int y = 0; y < points.length; y++) {
            for (int x = 0; x < points[y].length; x++) {
                if (points[y][x]) {
                    fields[actualY + y][actualX + x] = actualShape.getColor();
                }
            }
        }

        return fields;
    }

    protected Color[][] deepCopyFields() {
        if (fileds == null)
            return null;
        Color[][] result = new Color[fileds.length][];
        for (int r = 0; r < fileds.length; r++) {
            result[r] = fileds[r].clone();
        }
        return result;
    }

    public void moveLeft() {
        int nextX = actualX - 1;
        if (!isColision(nextX, actualY)) {
            actualX = nextX;
        }
    }

    public void moveRight() {
        int nextX = actualX + 1;
        if (!isColision(nextX, actualY)) {
            actualX = nextX;
        }
    }

    public void moveDown() {
        int nextY = actualY + 1;
        if (!isColision(actualX, nextY)) {
            actualY = nextY;
        }
    }

    public void rotateShape() {
        actualShape.rotate();

        // check collision after rotation
        if (isColision(actualX, actualY)) {
            // rotate back to original state and exit
            // TODO: create rotateBack () ??
            actualShape.rotate();
            actualShape.rotate();
            actualShape.rotate();
            return;
        }

        // fix x position after rotating on sides
        if (actualX < 0) {
            actualX = 0;
        } else if (actualX + actualShape.getWidth() > colsCount) {
            actualX = colsCount - actualShape.getWidth();
        }

    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColsCount() {
        return colsCount;
    }
}
