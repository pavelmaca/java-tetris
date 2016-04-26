package tetris.engine;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

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

    private int score = 0;


    ShapeGenerator generator = new ShapeGenerator();

    public Engine(int width, int height) {
        herniPole = new Color[width][height];
        creteNewShape();


        /*
        herniPole[width-1][0] = Color.YELLOW;
        herniPole[0][height-1] = Color.BLUE;
        herniPole[width-1][height-1] = Color.RED;
        herniPole[0][0] = Color.GREEN;*/
    }

    protected void creteNewShape() {
        actualShape = generator.createNext();
        actualX = herniPole.length / 2;
        actualY = 0;
    }

    public void tick() {
        actualY++;
        System.out.println("tick");
        // move actualShape down
        // check colisions
    }

    public Color[][] getGameFields() {
        Color[][] fields = deepCopyFields();

        boolean[][] shapePoints = actualShape.getPoints();

        for (int x = 0; x < shapePoints.length; x++) {
            for (int y = 0; y < shapePoints[x].length; y++) {
                if (shapePoints[x][y]) {
                    fields[actualX + x][actualY + y] = Color.cyan;
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
        if (actualX != 0) {
            actualX--;
        }
    }

    public void moveRight() {
        if (actualX != herniPole.length - 1) {
            actualX++;
        }
    }

    public void moveDown() {
        actualY++;
    }

    public void rotateShape(){
        actualShape.rotateRight();
    }
}
