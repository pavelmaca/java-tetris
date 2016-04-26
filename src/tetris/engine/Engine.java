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
    private Color[][] herniPole;

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
        herniPole = new Color[rowsCount][colsCount];
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;

        creteNewShape();
        /* Test corners*/
        /*
        herniPole[rowsCount-1][0] = Color.YELLOW;
        herniPole[0][colsCount-1] = Color.BLUE;
        herniPole[rowsCount-1][colsCount-1] = Color.RED;
        herniPole[0][0] = Color.GREEN;*/
    }


    protected void creteNewShape() {
        actualShape = generator.createNext();
        actualX = colsCount / 2;
        actualY = 0;
    }

    public void tick() {
        if (!isColision(actualX, actualY + 1)) {
            actualY++;
            System.out.println("tick");
        } else {
            saveShape();
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
        margeFildsAndShape(herniPole, actualShape);
    }

    protected boolean isColision(int nextX, int nextY) {
       /* int shapeButtomY = nextY + actualShape.getHeight();

        // TODO: check actual points instead of dimension
        if (shapeButtomY >= rowsCount + 1) {
            return true;
        }*/

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
                if (herniPole[nextY + y][nextX + x] != null) {
                    return true;
                }
            }
        }

        return false;
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
                    fields[actualY + y][actualX + x] = Color.cyan;
                }
            }
        }

        return fields;
    }

    protected Color[][] deepCopyFields() {
        if (herniPole == null)
            return null;
        Color[][] result = new Color[herniPole.length][];
        for (int r = 0; r < herniPole.length; r++) {
            result[r] = herniPole[r].clone();
        }
        return result;
    }

    public void moveLeft() {
        if (!isColision(actualX - 1, actualY)) {
            actualX--;
        }
    }

    public void moveRight() {
        if (!isColision(actualX + 1, actualY)) {
            actualX++;
        }
    }

    public void moveDown() {
        if (!isColision(actualX, actualY + 1)) {
            actualY++;
        }
    }

    public void rotateShape() {
        actualShape.rotateRight();
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColsCount() {
        return colsCount;
    }
}
