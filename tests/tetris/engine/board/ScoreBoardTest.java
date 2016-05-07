package tetris.engine.board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by Pavel Máca on 7.5.2016.
 */
public class ScoreBoardTest {
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
    public void getPosition() throws Exception {
        Record e1 = sb.saveScore("A", 155);
        assertEquals(1, sb.getPosition(e1));

        Record e2 = sb.saveScore("B", 22);
        assertEquals(2, sb.getPosition(e2));

        Record e3 = sb.saveScore("C", 170);
        assertEquals(1, sb.getPosition(e3));

        Record e4 = sb.saveScore("D", 155);
        assertEquals(2, sb.getPosition(e4));

    }

    @Test
    public void saveScore() throws Exception {
        Record expected = new Record("Tester 1", 5544);

        Record result = sb.saveScore(expected.getPlayerName(), expected.getScore());
        Record result2 = sb.saveScore(expected.getPlayerName(), expected.getScore());


        sb.saveData();

        compareRecords(expected, result);
        compareRecords(expected, result2);

        Record[] top = sb.getTop(2);
        assertSame(result, top[0]);
        assertSame(result2, top[1]);
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
        sb.saveScore(expected[0].getPlayerName(), expected[0].getScore());
        sb.saveScore(expected[4].getPlayerName(), expected[4].getScore());
        sb.saveScore(expected[6].getPlayerName(), expected[6].getScore());
        sb.saveScore(expected[5].getPlayerName(), expected[5].getScore());
        sb.saveScore(expected[1].getPlayerName(), expected[1].getScore());
        sb.saveScore(expected[7].getPlayerName(), expected[7].getScore());
        sb.saveScore(expected[3].getPlayerName(), expected[3].getScore());

        Record[] result = sb.getTop(8);


        for (int i = 0; i < result.length; i++) {
            compareRecords(expected[i], result[i]);
        }
    }


    private void compareRecords(Record expected, Record actual) {
        assertEquals(
                expected.getPlayerName() + " : " + expected.getScore(),
                actual.getPlayerName() + " : " + actual.getScore()
        );
    }

}