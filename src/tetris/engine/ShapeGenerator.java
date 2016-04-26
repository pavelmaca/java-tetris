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
        Shape newShape = getRandomType().create();

        randomFlip(newShape);
        randomRotation(newShape);

        return newShape;
    }


    public enum Type {
        LINE {
            public Shape create() {
                Shape line = new Shape(3,3, Color.black);
                line.addPoint(1, 0);
                line.addPoint(1, 1);
                line.addPoint(1, 2);

                return line;
            }
        },
        CORNER {
            public Shape create() {
                Shape corner = new Shape(2, 2, Color.black);
                corner.addPoint(0, 0);
                corner.addPoint(0, 1);
                corner.addPoint(1, 0);
                return corner;
            }
        },
        T{
            public Shape create() {
                Shape t = new Shape(3, 3, Color.black);
                t.addPoint(0, 0);
                t.addPoint(0, 1);
                t.addPoint(0, 2);
                t.addPoint(1, 1);
                return t;
            }
        },
        SQUARE {
            public Shape create() {
                Shape square = new Shape(2, 2, Color.black);
                square.addPoint(0, 0);
                square.addPoint(0, 1);
                square.addPoint(1, 0);
                square.addPoint(1, 1);
                return square;
            }
        },
        N {
            public Shape create() {
                Shape n = new Shape(3, 3, Color.black);
                n.addPoint(0, 0);
                n.addPoint(0, 1);
                n.addPoint(1, 1);
                n.addPoint(1, 2);
                return n;
            }
        },
        L {
            public Shape create() {
                Shape l = new Shape(3, 3, Color.black);
                l.addPoint(0, 0);
                l.addPoint(0, 1);
                l.addPoint(0, 2);
                l.addPoint(1, 2);
                return l;
            }
        };

        public abstract Shape create();
    }

}