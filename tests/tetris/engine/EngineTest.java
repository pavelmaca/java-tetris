package tetris.engine;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import tetris.engine.events.GameStatusAdapter;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class EngineTest {
    Engine engine;

    boolean eventCalled = false;

    @Before
    public void setUp() throws Exception {
        engine = new Engine(20, 30);

    }

    @Test
    public void initialization() {
        assertNotNull(engine.getNextShape());

        assertEquals(20, engine.getRowsCount());
        assertEquals(30, engine.getColsCount());

        assertEquals(0, engine.getScore());

        assertFalse(engine.isRunning());

        assertNotNull(engine.getDifficulty());
    }

    @Test
    public void start() {
        assertFalse(engine.isRunning());

        engine.start();

        assertTrue(engine.isRunning());
    }

    @Test
    public void pause() {
        engine.start();
        assertTrue(engine.isRunning());

        engine.pause();
        assertFalse(engine.isRunning());
    }

    @Test
    public void changeDifficulty() {
        engine.setDifficulty(Difficulty.HARD);
        assertEquals(Difficulty.HARD, engine.getDifficulty());

        engine.setDifficulty(Difficulty.EASY);
        assertEquals(Difficulty.EASY, engine.getDifficulty());
    }

    @Test
    public void gameOver() {
        engine.start();

        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void gameEnd() {
                eventCalled = true;
            }
        });

        makeEngineTick(engine.getRowsCount() * engine.getRowsCount());

        assertFalse(engine.isRunning());

        assertTrue(eventCalled);
    }

    @Test
    public void nextShape() {
        engine.start();

        Shape prevShape = engine.getNextShape();

        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void shapeChange() {
                eventCalled = true;

                assertThat(engine.getNextShape(), IsNot.not(prevShape));
            }
        });

        makeEngineTick(engine.getRowsCount() * 2);

        assertTrue(eventCalled);
    }


    @Test
    public void restart() {
        engine.start();

        engine.addGameStatusListener(new GameStatusAdapter() {
            @Override
            public void shapeChange() {
                eventCalled = true;
            }
        });

        Color[] empty = new Color[engine.getColsCount()];

        // make sure last row is not empty
        makeEngineTick(engine.getRowsCount() * 2);
        Color[][] status = engine.getStatus();
        assertThat(status[engine.getRowsCount() - 1], IsNot.not(empty));

        // save next shape before restart
        Shape prevShape = engine.getNextShape();

        engine.restart();

        // score changed after restart
        assertTrue(eventCalled);

        // next shape must be different
        assertThat(engine.getNextShape(), IsNot.not(prevShape));

        // Check if last row is empty
        Color[][] status2 = engine.getStatus();
        assertThat(status2[engine.getRowsCount() - 1], Is.is(empty));
    }


    private void makeEngineTick(int count) {
        while (count > 0) {
            count--;
            engine.tick();
        }
    }


}