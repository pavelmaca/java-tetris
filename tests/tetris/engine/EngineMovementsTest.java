package tetris.engine;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertThat;

/**
 * Created by Pavel on 8.5.2016.
 */
public class EngineMovementsTest {
    Engine engine;

    Color[][] empty;

    @Before
    public void setUp() throws Exception {
        engine = new Engine(10, 20);
        engine.start();

        empty = new Color[10][10];
    }

    @Test
    public void moveToLeft() {
        Color[][] status1 = engine.getStatus();

        engine.moveLeft();
        assertThat(engine.getStatus(), IsNot.not(status1));

        // move to left side
        for (int i = 0; i < engine.getColsCount(); i++) {
            engine.moveLeft();
        }

        Color[][] status2 = engine.getStatus();
        assertThat(status2, IsNot.not(empty)); // not outside field

        engine.moveLeft(); // nothing should happend
        assertThat(engine.getStatus(), Is.is(status2));
    }

    @Test
    public void moveToRight() {
        Color[][] status1 = engine.getStatus();

        engine.moveRight();
        assertThat(engine.getStatus(), IsNot.not(status1));

        // move to right side
        for (int i = 0; i < engine.getColsCount(); i++) {
            engine.moveRight();
        }

        Color[][] status2 = engine.getStatus();
        assertThat(status2, IsNot.not(empty)); // not outside field

        engine.moveRight(); // nothing should happend
        assertThat(engine.getStatus(), Is.is(status2));
    }

    @Test
    public void moveDown() {
        Color[][] status1 = engine.getStatus();

        engine.moveDown(); // moved
        assertThat(engine.getStatus(), IsNot.not(status1));

        // move to bottum
        for (int i = 0; i < engine.getRowsCount(); i++) {
            engine.moveDown();
        }

        Color[][] status2 = engine.getStatus();
        assertThat(status2, IsNot.not(empty)); // not outside field

        engine.moveDown(); // nothing should happend
        assertThat(engine.getStatus(), Is.is(status2));
    }

    @Test
    public void rotate() {
        for (int i = 0; i < 100; i++) {
            if (!isSquareInMiddle(engine.getStatus())) {
                rotateSingle(true);
            }
            engine.restart();
            engine.start();
        }
    }


    @Test
    public void rotateOnLeft() {
        for (int j = 0; j < 100; j++) {
            if (!isSquareInMiddle(engine.getStatus())) {
                moveToLeft();
                rotateSingle(false);
            }
            engine.restart();
            engine.start();
        }
    }

    @Test
    public void rotateOnRight() {
        for (int j = 0; j < 100; j++) {
            if (!isSquareInMiddle(engine.getStatus())) {
                moveToRight();
                rotateSingle(false);
            }
            engine.restart();
            engine.start();
        }
    }

    @Test
    public void naturalFall() {
        for (int i = 0; i < 5; i++) {
            Color[][] status1 = engine.getStatus();
            engine.tick(); // moved
            assertThat(engine.getStatus(), IsNot.not(status1));
        }
    }

    private void rotateSingle(boolean checkIfreturnTofirstStage) {
        Color[][] firstStatus = engine.getStatus();

        for (int i = 0; i < 4; i++) {
            Color[][] beforeStatus = engine.getStatus();
            engine.rotateShape();
            assertThat(engine.getStatus(), IsNot.not(beforeStatus));
        }

        if (checkIfreturnTofirstStage) {
            assertThat(engine.getStatus(), Is.is(firstStatus));
        }
    }

    private boolean isSquareInMiddle(Color[][] status) {
        int middle = engine.getColsCount() / 2;
        return (status[0][middle - 1] != null
                && status[0][middle - 1] != null
                && status[1][middle] != null
                && status[1][middle] != null);
    }

}