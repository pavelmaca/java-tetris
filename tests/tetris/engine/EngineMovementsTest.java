package tetris.engine;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Pavel on 8.5.2016.
 */
public class EngineMovementsTest {
    Engine engine;

    Color[][] empty;

    @Before
    public void setUp() throws Exception {
        engine = new Engine(10, 10);
        engine.start();

        empty = new Color[10][10];
    }

    @Test
    public void moveToLeft() {
        Color[][] status1 = engine.getStatus();

        engine.moveLeft();
        assertNotEquals(status1, engine.getStatus());

        // move to left side
        for (int i = 0; i < engine.getColsCount(); i++) {
            engine.moveLeft();
        }

        Color[][] status2 = engine.getStatus();
        assertNotEquals(empty, status2); // not outside field

        engine.moveLeft(); // nothing should happend
        assertArrayEquals(status2, engine.getStatus());
    }

    @Test
    public void moveToRight() {
        Color[][] status1 = engine.getStatus();

        engine.moveRight();
        assertNotEquals(status1, engine.getStatus());

        // move to right side
        for (int i = 0; i < engine.getColsCount(); i++) {
            engine.moveRight();
        }

        Color[][] status2 = engine.getStatus();
        assertNotEquals(empty, status2); // not outside field

        engine.moveRight(); // nothing should happend
        assertArrayEquals(status2, engine.getStatus());
    }

    @Test
    public void moveDown() {
        Color[][] status1 = engine.getStatus();

        engine.moveDown(); // moved
        assertNotEquals(status1, engine.getStatus());

        // move to bottum
        for (int i = 0; i < engine.getRowsCount(); i++) {
            engine.moveDown();
        }

        Color[][] status2 = engine.getStatus();
        assertNotEquals(empty, status2); // not outside field

        engine.moveDown(); // nothing should happend
        assertArrayEquals(status2, engine.getStatus());
    }

    @Test
    public void rotate() {
        Color[][] status1 = engine.getStatus();

        engine.rotateShape();
        Color[][] status2 = engine.getStatus();

        assertNotEquals(status1, status2);

        engine.rotateShape();
        engine.rotateShape();
        engine.rotateShape();

        assertArrayEquals(status1, engine.getStatus());
    }


    @Test
    public void rotateOnLeft() {
        for (int i = 0; i < 100; i++) {
            engine.restart();
            moveToLeft();
            rotate();
        }
    }

    @Test
    public void rotateOnRight() {
        for (int i = 0; i < 100; i++) {
            engine.restart();
            moveToRight();
            rotate();
        }
    }

    @Test
    public void rotateOnButtom() {
        for (int i = 0; i < 100; i++) {
            engine.restart();
            moveDown();
            rotate();
        }
    }

    @Test
    public void naturalFall() {
        for (int i = 0; i < 5; i++) {
            Color[][] status1 = engine.getStatus();
            engine.tick(); // moved
            assertNotEquals(status1, engine.getStatus());
        }
    }
}