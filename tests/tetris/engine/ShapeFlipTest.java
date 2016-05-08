package tetris.engine;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertArrayEquals;

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
        assertArrayEquals(e1, s1.getPoints());
    }

    @Test
    public void flipOnePoint() throws Exception {

        boolean[][] e2 = {
                {true},
        };
        Shape s2 = new Shape(e2, Color.black);

        s2.flip();
        assertArrayEquals(e2, s2.getPoints());
    }


    @Test
    public void flipEmpty() throws Exception {
        boolean[][] e1 = {
                {},
        };
        Shape s1 = new Shape(e1, Color.black);
        s1.flip();
        assertArrayEquals(e1, s1.getPoints());
    }
}