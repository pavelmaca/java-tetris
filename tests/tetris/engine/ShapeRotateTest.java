package tetris.engine;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Pavel on 8.5.2016.
 */
public class ShapeRotateTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void rotateNull() throws Exception {
        Shape s1 = new Shape(null, Color.black);
        exception.expect(NullPointerException.class);
        s1.rotate();
    }

    @Test
    public void rotateL() throws Exception {
        boolean[][] e0 = {
                {true, false, false},
                {true, true, true},
        };
        Shape s1 = new Shape(e0, Color.black);


        boolean[][] e1 = {
                {true, true},
                {true, false},
                {true, false},
        };
        s1.rotate();
        assertArrayEquals(e1, s1.getPoints());

        boolean[][] e2 = {
                {true, true, true},
                {false, false, true},
        };
        s1.rotate();
        assertArrayEquals(e2, s1.getPoints());

        boolean[][] e3 = {
                {false, true},
                {false, true},
                {true, true},
        };
        s1.rotate();
        assertArrayEquals(e3, s1.getPoints());


        s1.rotate();
        assertArrayEquals(e0, s1.getPoints());
    }

    @Test
    public void rotateT() throws Exception {
        boolean[][] e0 = {
                {true, true, true},
                {false, true, false},
        };
        Shape s1 = new Shape(e0, Color.black);


        boolean[][] e1 = {
                {false, true},
                {true, true},
                {false, true},
        };
        s1.rotate();
        assertArrayEquals(e1, s1.getPoints());

        boolean[][] e2 = {
                {false, true, false},
                {true, true, true},
        };
        s1.rotate();
        assertArrayEquals(e2, s1.getPoints());

        boolean[][] e3 = {
                {true, false},
                {true, true},
                {true, false},
        };
        s1.rotate();
        assertArrayEquals(e3, s1.getPoints());


        s1.rotate();
        assertArrayEquals(e0, s1.getPoints());
    }

    @Test
    public void rotateEmpty() {
        boolean[][] e1 = {
                {},
        };

        boolean[][] e2 = {
        };

        Shape s1 = new Shape(e1, Color.black);
        s1.rotate();
        assertArrayEquals(e2, s1.getPoints());

        Shape s2 = new Shape(e2, Color.black);
        s2.rotate();
        assertArrayEquals(e2, s2.getPoints());

    }
}