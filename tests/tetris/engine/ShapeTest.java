package tetris.engine;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
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
        assertThat(s1.getPoints(), Is.is(e1));
    }

    @Test
    public void getPoints() throws Exception {
        boolean[][] e = {
                {false, false, true},
                {false, true, true},
        };

        Shape s = new Shape(e, Color.black);
        assertThat(s.getPoints(), Is.is(e));
    }

}