package tetris.engine;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Pavel on 8.5.2016.
 */
public class ShapeTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getZeroHeight() throws Exception {
        Shape s1 = new Shape(new boolean[0][10], Color.black);
        assertEquals(0, s1.getHeight());
    }

    @Test
    public void getZeroWidth() throws Exception {
        Shape s1 = new Shape(new boolean[10][0], Color.black);
        assertEquals(0, s1.getWidth());
    }


    @Test
    public void getHeight() throws Exception {
        Shape s1 = new Shape(new boolean[11][22], Color.black);
        assertEquals(11, s1.getHeight());
    }

    @Test
    public void getWidth() throws Exception {
        Shape s1 = new Shape(new boolean[11][22], Color.black);
        assertEquals(22, s1.getWidth());
    }

    @Test
    public void getColor() throws Exception {
        Shape s1 = new Shape(new boolean[1][1], Color.black);
        assertEquals(Color.black, s1.getColor());
    }

    @Test
    public void getNullColor() throws Exception {
        Shape s2 = new Shape(new boolean[1][1], null);
        assertEquals(null, s2.getColor());
    }

    @Test
    public void createEmpty() throws Exception {
        Shape s1 = new Shape(new boolean[2][2], Color.black);

        boolean[][] e1 = {
                {false, false},
                {false, false},
        };
        assertArrayEquals(e1, s1.getPoints());
    }

    @Test
    public void getPoints() throws Exception {
        boolean[][] e = {
                {false, false, true},
                {false, true, true},
        };

        Shape s = new Shape(e, Color.black);
        assertArrayEquals(e, s.getPoints());
    }

}