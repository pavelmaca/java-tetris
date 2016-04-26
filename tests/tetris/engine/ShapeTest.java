package tetris.engine;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by Assassik on 15. 4. 2016.
 */
public class ShapeTest {
    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void addPoint() throws Exception {
        //add point out of range
        try {
            Shape shape1 = new Shape(0, 0, Color.black);
            shape1.addPoint(1, 2);
            assertTrue(false);
        } catch (ArrayIndexOutOfBoundsException ex) {
            assertTrue(true);
        }

        Shape shape2 = new Shape(2, 3, Color.black);
        shape2.addPoint(0, 1);
        shape2.addPoint(0, 2);
        shape2.addPoint(1, 1);
       // shape2.addPoint(2, 0);
        boolean[][] expected = {
                {false, true, true},
                {false, true, false}
        };
        assertSame(expected, shape2.getPoints());


    }

    @Test
    public void rotate() throws Exception {

    }

    @Test
    public void rotateRight() throws Exception {

    }

    @Test
    public void rotateLeft() throws Exception {

    }

    @Test
    public void flipVerticaly() throws Exception {

    }

    @Test
    public void flipHorizontaly() throws Exception {

    }

    @Test
    public void getPoints() throws Exception {

    }

}