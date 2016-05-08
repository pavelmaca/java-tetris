package tetris.engine;

import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Pavel on 8.5.2016.
 */
public class FieldStorageTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void createNew() {
        FieldStorage fs = new FieldStorage(10, 20);
        assertEquals(10, fs.getRowsCount());
        assertEquals(20, fs.getColsCount());
    }

    @Test
    public void createNewEmpty() {
        FieldStorage fs = new FieldStorage(0, 0);
        assertEquals(0, fs.getRowsCount());
        assertEquals(0, fs.getColsCount());
    }

    @Test
    public void createNewInvalidRows() {
        exception.expect(NegativeArraySizeException.class);
        new FieldStorage(-10, 20);
    }


    @Test
    public void createNewInvalidCols() {
        exception.expect(NegativeArraySizeException.class);
        new FieldStorage(10, -20);
    }

    @Test
    public void getRowsCount() {
        FieldStorage fs = new FieldStorage(152, 0);
        assertEquals(152, fs.getRowsCount());

        FieldStorage fs2 = new FieldStorage(0, 10);
        assertEquals(0, fs2.getRowsCount());
    }

    @Test
    public void getColsCount() {
        FieldStorage fs = new FieldStorage(152, 0);
        assertEquals(0, fs.getColsCount());

        FieldStorage fs2 = new FieldStorage(0, 10);
        assertEquals(0, fs2.getColsCount());
    }

    @Test
    public void saveShape() {
        FieldStorage fs = new FieldStorage(5, 5);

        Shape s = new Shape(new boolean[][]{
                {false, true},
                {false, true}
        }, Color.green);

        fs.saveShape(s, 0, 0);


        Color[][] result1 = fs.printStatus(null, 0, 0);
        Color[][] e1 = {
                {null, Color.green, null, null, null},
                {null, Color.green, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
        };

        assertThat(result1, Is.is(e1));

        fs.saveShape(s, 3, 3);
        Color[][] result2 = fs.printStatus(null, 0, 0);
        Color[][] e2 = {
                {null, Color.green, null, null, null},
                {null, Color.green, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, Color.green},
                {null, null, null, null, Color.green},
        };

        assertThat(result2, Is.is(e2));
    }

    @Test
    public void saveShapeInNotValidPosition() {
        FieldStorage fs = new FieldStorage(3, 3);
        Shape s = new Shape(new boolean[][]{
                {true, true},
                {true, true}
        }, Color.green);

        fs.saveShape(s, 2, 2);

        Color[][] result = fs.printStatus(null, 0, 0);
        Color[][] e = {
                {null, null, null},
                {null, null, null},
                {null, null, Color.green},
        };

        assertThat(result, Is.is(e));
    }


    @Test
    public void resetStorage() {
        FieldStorage fs = new FieldStorage(5, 5);

        Shape s = new Shape(new boolean[][]{
                {true},
                {true}
        }, Color.green);

        fs.saveShape(s, 0, 0);


        fs.resetStorage();

        Color[][] result1 = fs.printStatus(null, 0, 0);
        Color[][] e1 = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
        };

        assertThat(result1, Is.is(e1));
    }


    @Test
    public void printStatusEmptyAll() {
        FieldStorage fs = new FieldStorage(2, 2);

        Color[][] e1 = {
                {null, null},
                {null, null},
        };

        assertThat(fs.printStatus(null, 0, 0), Is.is(e1));
    }

    @Test
    public void printStatusEmptyField() {
        FieldStorage fs = new FieldStorage(2, 2);

        Shape s1 = new Shape(new boolean[][]{{true}, {true}}, Color.BLUE);

        Color[][] e2 = {
                {null, Color.BLUE},
                {null, Color.BLUE},
        };
        assertThat(fs.printStatus(s1, 1, 0), Is.is(e2));
    }

    @Test
    public void printStatusCollision() {
        FieldStorage fs = new FieldStorage(2, 2);

        Shape s1 = new Shape(new boolean[][]{{true}, {true}}, Color.BLUE);

        fs.printStatus(s1, 2, 0);
    }

}