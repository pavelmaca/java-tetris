package tetris.engine;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.*;
import java.util.Random;

/**
 * Created by Assassik on 13. 4. 2016.
 */
public class ShapeGenerator {

    protected Random randomGenerator;

    public ShapeGenerator() {
        randomGenerator = new Random();
    }

    private Type getRandomType() {
        Type[] types = Type.values();
        return types[randomGenerator.nextInt(types.length)];
    }

    private void randomRotation(Shape shape) {
        switch (randomGenerator.nextInt(7)) {// 7 is intentionaly.. make no changes
            case 0:
                shape.rotateLeft();
            case 1:
                shape.rotateLeft();
            case 2:
                shape.rotateLeft();
                break;
            case 3:
                shape.rotateRight();
            case 4:
                shape.rotateRight();
            case 5:
                shape.rotateRight();
                break;
        }
    }

    private void randomFlip(Shape shape) {
        switch (randomGenerator.nextInt(3)) { // 3 is intentionaly.. make no changes
            case 0:
                shape.flipVerticaly();
                break;
            case 1:
                shape.flipHorizontaly();
                break;
        }
    }

    public Shape createNext() {
        Shape newShape = null;

        switch (getRandomType()) {
            case LINE:
                newShape = createLine();
                break;
            case CORNER:
                newShape = createCorner();
                break;
            case T:
                newShape = createT();
                break;
            default:
                //throw new Exception("Requested shape cant be generated.");
        }

        randomFlip(newShape);
        randomRotation(newShape);

        return newShape;
    }

    public Shape createLine() {
        Shape line = new Shape(3,3, Color.black);
        line.addPoint(1, 0);
        line.addPoint(1, 1);
        line.addPoint(1, 2);

        return line;
    }

    public Shape createCorner() {
        Shape corner = new Shape(2, 2, Color.black);
        corner.addPoint(0, 0);
        corner.addPoint(0, 1);
        corner.addPoint(1, 0);
        return corner;
    }

    public Shape createT() {
        Shape t = new Shape(3, 3, Color.black);
        t.addPoint(0, 0);
        t.addPoint(0, 1);
        t.addPoint(0, 2);
        t.addPoint(1, 1);
        return t;
    }

    public enum Type {
        LINE, CORNER, T//, C;
    }

}