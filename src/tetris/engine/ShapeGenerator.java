package tetris.engine;

import java.awt.*;
import java.util.Random;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class ShapeGenerator {

    private Random randomGenerator;

    public ShapeGenerator() {
        randomGenerator = new Random();
    }

    public Shape createNext() {
        Type randomType = getRandomType();
        Shape newShape = new Shape(randomType.points, randomType.color);

        randomFlip(newShape);
        randomRotation(newShape);

        return newShape;
    }

    private Type getRandomType() {
        Type[] types = Type.values();
        return types[randomGenerator.nextInt(types.length)];
    }

    private void randomRotation(Shape shape) {
        switch (randomGenerator.nextInt(4)) {// 4 is intentionaly.. make no changes
            case 0:
                shape.rotate();
            case 1:
                shape.rotate();
            case 2:
                shape.rotate();
                break;
        }
    }

    private void randomFlip(Shape shape) {
        switch (randomGenerator.nextInt(2)) { // 3 is intentionaly.. make no changes
            case 0:
                shape.flip();
                break;
        }
    }


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


        boolean[][] points;
        Color color;

        Type(boolean[][] points, Color color) {
            this.points = points;
            this.color = color;
        }
    }

}