package tetris.engine;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Pavel on 8.5.2016.
 */
public class FieldStorageRemovingTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    // vertical line
    Shape sVertical = new Shape(new boolean[][]{
            {true}, {true}, {true}, {true}, {true}
    }, Color.red);
    // horizontal line
    Shape sHorizontal = new Shape(new boolean[][]{
            {true, true, true},
    }, Color.green);
    Color[][] eOneRow = {
            {null, null, null},
            {Color.red, null, Color.red},
            {Color.red, null, Color.red},
            {Color.red, null, Color.red},
            {Color.red, null, Color.red},
    };
    Color[][] eTwoRows = {
            {null, null, null},
            {null, null, null},
            {Color.red, null, Color.red},
            {Color.red, null, Color.red},
            {Color.red, null, Color.red},
    };
    FieldStorage fs;

    @Before
    public void setUp() throws Exception {
        fs = new FieldStorage(5, 3);
        fs.saveShape(sVertical, 0, 0);
        fs.saveShape(sVertical, 2, 0);
    }

    @Test
    public void oneRowOnTop() {
        fs.saveShape(sHorizontal, 0, 0);
        assertEquals(1, fs.removeFullRows());

        assertArrayEquals(eOneRow, fs.printStatus(null, 0, 0));
    }

    @Test
    public void twoRowOnTop() {
        fs.saveShape(sHorizontal, 0, 0);
        fs.saveShape(sHorizontal, 0, 1);
        assertEquals(2, fs.removeFullRows());

        assertArrayEquals(eTwoRows, fs.printStatus(null, 0, 0));
    }


    @Test
    public void oneRowOnBottom() {
        fs.saveShape(sHorizontal, 0, 4);
        assertEquals(1, fs.removeFullRows());

        assertArrayEquals(eOneRow, fs.printStatus(null, 0, 0));
    }

    @Test
    public void twoRowOnBottom() {
        fs.saveShape(sHorizontal, 0, 4);
        fs.saveShape(sHorizontal, 0, 3);
        assertEquals(2, fs.removeFullRows());

        assertArrayEquals(eTwoRows, fs.printStatus(null, 0, 0));
    }

    @Test
    public void oneRowInMiddle() {
        fs.saveShape(sHorizontal, 0, 2);
        assertEquals(1, fs.removeFullRows());

        assertArrayEquals(eOneRow, fs.printStatus(null, 0, 0));
    }

    @Test
    public void twoRowInMiddle() {
        fs.saveShape(sHorizontal, 0, 3);
        fs.saveShape(sHorizontal, 0, 1);
        assertEquals(2, fs.removeFullRows());

        assertArrayEquals(eTwoRows, fs.printStatus(null, 0, 0));
    }


    @Test
    public void zeroRows() {
        assertEquals(0, fs.removeFullRows());

        Color[][] eZeroRows = {
                {Color.red, null, Color.red},
                {Color.red, null, Color.red},
                {Color.red, null, Color.red},
                {Color.red, null, Color.red},
                {Color.red, null, Color.red},
        };
        assertArrayEquals(eZeroRows, fs.printStatus(null, 0, 0));
    }


    @Test
    public void allRows() {
        fs.saveShape(sHorizontal, 0, 4);
        fs.saveShape(sHorizontal, 0, 3);
        fs.saveShape(sHorizontal, 0, 2);
        fs.saveShape(sHorizontal, 0, 1);
        fs.saveShape(sHorizontal, 0, 0);

        assertEquals(5, fs.removeFullRows());


        Color[][] eAllRows = {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
        };
        assertArrayEquals(eAllRows, fs.printStatus(null, 0, 0));
    }


}