package tetris.engine;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Pavel on 8.5.2016.
 */
public class FieldStorageCollisionTest {

    FieldStorage fs;

    Shape s1 = new Shape(new boolean[][]{
            {true, false},
            {false, false},
    }, Color.red);

    Shape s2 = new Shape(new boolean[][]{
            {false, false},
            {true, false},
    }, Color.red);

    Shape s3 = new Shape(new boolean[][]{
            {false, false},
            {false, true},
    }, Color.red);

    Shape s4 = new Shape(new boolean[][]{
            {false, true},
            {false, false},
    }, Color.red);


    @Before
    public void setUp() throws Exception {
        fs = new FieldStorage(5, 3);
    }

    @Test
    public void lefSide() {
        assertFalse(fs.isCollision(s1, 0, 1));
        assertTrue(fs.isCollision(s1, -1, 1));

        assertFalse(fs.isCollision(s3, 0, 1));
        assertFalse(fs.isCollision(s3, -1, 1));
        assertTrue(fs.isCollision(s3, -2, 1));

        assertTrue(fs.isCollision(s3, -10, 1));
    }

    @Test
    public void rightSide() {
        assertFalse(fs.isCollision(s1, 2, 1));
        assertTrue(fs.isCollision(s1, 3, 1));

        assertTrue(fs.isCollision(s3, 2, 1));
        assertTrue(fs.isCollision(s3, 3, 1));

        assertTrue(fs.isCollision(s3, 10, 1));
    }

    @Test
    public void topSide() {
        assertFalse(fs.isCollision(s1, 1, 0));
        assertTrue(fs.isCollision(s1, 1, -1));

        assertFalse(fs.isCollision(s2, 1, 0));
        assertFalse(fs.isCollision(s2, 0, -1));
        assertTrue(fs.isCollision(s2, 0, -2));
    }

    @Test
    public void bottomSide() {
        assertFalse(fs.isCollision(s1, 1, 4));
        assertTrue(fs.isCollision(s1, 1, 5));

        assertFalse(fs.isCollision(s2, 1, 3));
        assertTrue(fs.isCollision(s2, 1, 4));
        assertTrue(fs.isCollision(s2, 0, 5));
    }

    @Test
    public void innerCollision() {
        fs.saveShape(s1, 1, 1);

        assertTrue(fs.isCollision(s1, 1, 1));
        assertFalse(fs.isCollision(s2, 1, 1));
        assertFalse(fs.isCollision(s3, 1, 1));
        assertFalse(fs.isCollision(s4, 1, 1));


        fs.saveShape(s3, 1, 1);
        assertTrue(fs.isCollision(s3, 1, 1));
    }


}