package tetris.engine;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Pavel on 8.5.2016.
 */
public class ShapeFlipTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void flipCorner() throws Exception {
        boolean[][] e0 = {
                {true, false, false},
                {true, true, false},
        };
        Shape s1 = new Shape(e0, Color.black);

        boolean[][] e1 = {
                {false, false, true},
                {false, true, true},
        };
        s1.flip();
        assertThat(s1.getPoints(), Is.is(e1));
    }

    @Test
    public void flipOnePoint() throws Exception {

        boolean[][] e1 = {
                {true},
        };
        Shape s1 = new Shape(e1, Color.black);

        s1.flip();
        assertThat(s1.getPoints(), Is.is(e1));
    }


    @Test
    public void flipEmpty() throws Exception {
        boolean[][] e1 = {
                {},
        };
        Shape s1 = new Shape(e1, Color.black);
        s1.flip();
        assertThat(s1.getPoints(), Is.is(e1));
    }
}