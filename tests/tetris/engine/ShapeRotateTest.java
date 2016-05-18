package tetris.engine;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertThat;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
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
        assertThat(s1.getPoints(), Is.is(e1));

        boolean[][] e2 = {
                {true, true, true},
                {false, false, true},
        };
        s1.rotate();
        assertThat(s1.getPoints(), Is.is(e2));

        boolean[][] e3 = {
                {false, true},
                {false, true},
                {true, true},
        };
        s1.rotate();
        assertThat(s1.getPoints(), Is.is(e3));


        s1.rotate();
        assertThat(s1.getPoints(), Is.is(e0));
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
        assertThat(s1.getPoints(), Is.is(e1));

        boolean[][] e2 = {
                {false, true, false},
                {true, true, true},
        };
        s1.rotate();
        assertThat(s1.getPoints(), Is.is(e2));

        boolean[][] e3 = {
                {true, false},
                {true, true},
                {true, false},
        };
        s1.rotate();
        assertThat(s1.getPoints(), Is.is(e3));


        s1.rotate();
        assertThat(s1.getPoints(), Is.is(e0));
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
        assertThat(s1.getPoints(), Is.is(e2));

        Shape s2 = new Shape(e2, Color.black);
        s2.rotate();
        assertThat(s2.getPoints(), Is.is(e2));

    }
}