package tetris.engine.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Pavel Máca on 7.5.2016.
 */
public class ScoreBoardTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    ScoreBoard sb;
    String testFile;

    @Before
    public void setUp() throws Exception {
        Random gen = new Random();
        testFile = "test_" + gen.nextInt(2048) + "_scores.dat";

        new File(testFile).delete();

        sb = new ScoreBoard(testFile);
    }

    @After
    public void teerDown() {
        new File(testFile).delete();
    }

    @Test
    public void invalidDataFile() {
        ScoreBoard sb2 = new ScoreBoard("invalid/gfg**t");
        assertArrayEquals(new Record[1], sb2.getTop(1));
    }

    @Test
    public void getPosition() throws Exception {
        Record e1 = sb.saveScore("A", 155);
        assertEquals(1, sb.getPosition(e1));

        Thread.sleep(10); // make sure date is different
        Record e2 = sb.saveScore("B", 22);
        assertEquals(2, sb.getPosition(e2));

        Thread.sleep(10); // make sure date is different
        Record e3 = sb.saveScore("C", 170);
        assertEquals(1, sb.getPosition(e3));

        Thread.sleep(10); // make sure date is different
        Record e4 = sb.saveScore("D", 155);
        assertEquals(2, sb.getPosition(e4));
    }

    @Test
    public void getPositionNull() throws Exception {
        exception.expect(NullPointerException.class);
        sb.getPosition(null);
    }

    @Test
    public void getPositionNotInBoard() throws Exception {
        Record r = new Record("a", 1245);

        assertEquals(0, sb.getPosition(r));
    }

    @Test
    public void saveScore() throws Exception {
        Record expected = new Record("Tester 1", 5544);

        Record result = sb.saveScore(expected.getPlayerName(), expected.getScore());
        Thread.sleep(100); // make sure date is different
        Record result2 = sb.saveScore(expected.getPlayerName(), expected.getScore());

        compareRecords(expected, result);
        compareRecords(expected, result2);

        Record[] top = sb.getTop(2);
        assertSame(result2, top[0]);
        assertSame(result, top[1]);
    }

    @Test
    public void loadScoreBoard() throws Exception {
        Record expected = new Record("Tester 1", 5544);
        sb.saveScore(expected.getPlayerName(), expected.getScore());
        sb.saveData();

        ScoreBoard sb2 = new ScoreBoard(testFile);

        compareRecords(expected, sb2.getTop(1)[0]);
    }

    @Test
    public void getTop() throws Exception {
        Record[] expected = {
                new Record("a", 130),
                new Record("b", 100),
                new Record("c", 100),
                new Record("d", 90),
                new Record("e", 70),
                new Record("f", 20),
                new Record("g", 20),
                new Record("h", 1),
        };

        sb.saveScore(expected[2].getPlayerName(), expected[2].getScore());
        Thread.sleep(10); // make sure date is different
        sb.saveScore(expected[0].getPlayerName(), expected[0].getScore());
        Thread.sleep(10);
        sb.saveScore(expected[4].getPlayerName(), expected[4].getScore());
        Thread.sleep(10);
        sb.saveScore(expected[6].getPlayerName(), expected[6].getScore());
        Thread.sleep(10);
        sb.saveScore(expected[5].getPlayerName(), expected[5].getScore());
        Thread.sleep(10);
        sb.saveScore(expected[1].getPlayerName(), expected[1].getScore());
        Thread.sleep(10);
        sb.saveScore(expected[7].getPlayerName(), expected[7].getScore());
        Thread.sleep(10);
        sb.saveScore(expected[3].getPlayerName(), expected[3].getScore());

        Record[] result = sb.getTop(9);


        assertEquals(9, result.length);

        for (int i = 0; i < expected.length; i++) {
            compareRecords(expected[i], result[i]);
        }

        assertNull(result[8]);
    }

    @Test
    public void saveData() throws Exception {
        loadScoreBoard();
    }


    private void compareRecords(Record expected, Record actual) {
        assertEquals(
                expected.getPlayerName() + " : " + expected.getScore(),
                actual.getPlayerName() + " : " + actual.getScore()
        );
    }

}