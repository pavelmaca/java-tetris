package tetris.engine;

import java.awt.*;
import java.util.Random;

/**
 * Created by Assassik on 13. 4. 2016.
 */
public class ShapeGenerator {

    private Random randomGenerator;

    public ShapeGenerator() {
        randomGenerator = new Random();
    }

    public Shape createNext() {
        Shape newShape = getRandomType().create();

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
        LINE {
            Color color = Color.MAGENTA;
            public Shape create() {
                Shape line = new Shape(3, 3, color);
                line.addPoint(1, 0);
                line.addPoint(1, 1);
                line.addPoint(1, 2);

                return line;
            }
        },
        CORNER {
            Color color = Color.ORANGE;
            public Shape create() {
                Shape corner = new Shape(2, 2, color);
                corner.addPoint(0, 0);
                corner.addPoint(0, 1);
                corner.addPoint(1, 0);
                return corner;
            }
        },
        T {
            Color color = Color.GREEN;
            public Shape create() {
                Shape t = new Shape(3, 3, color);
                t.addPoint(0, 0);
                t.addPoint(0, 1);
                t.addPoint(0, 2);
                t.addPoint(1, 1);
                return t;
            }
        },
        SQUARE {
            Color color = Color.RED;
            public Shape create() {
                Shape square = new Shape(2, 2, color);
                square.addPoint(0, 0);
                square.addPoint(0, 1);
                square.addPoint(1, 0);
                square.addPoint(1, 1);
                return square;
            }
        },
        N {
            Color color = Color.YELLOW;
            public Shape create() {
                Shape n = new Shape(3, 3, color);
                n.addPoint(0, 0);
                n.addPoint(0, 1);
                n.addPoint(1, 1);
                n.addPoint(1, 2);
                return n;
            }
        },
        L {
            Color color = Color.BLUE;
            public Shape create() {
                Shape l = new Shape(3, 3, color);
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