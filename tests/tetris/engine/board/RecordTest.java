package tetris.engine.board;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Pavel on 8.5.2016.
 */
public class RecordTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void getPlayerName() throws Exception {
        Record r1 = new Record("abcd", 146);
        assertEquals("abcd", r1.getPlayerName());

        Record r2 = new Record("", 146);
        assertEquals("", r2.getPlayerName());

        Record r3 = new Record("AbCdEČŘ", 146);
        assertEquals("AbCdEČŘ", r3.getPlayerName());
    }

    @Test
    public void getScore() throws Exception {
        Record r1 = new Record("a", 146);
        assertEquals(146, r1.getScore());

        Record r3 = new Record("a", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, r3.getScore());

        exception.expect(Record.InvalidScoreException.class);
        new Record("a", -1);
    }

    @Test
    public void getDate() throws Exception {
        Record r1 = new Record("a", 146);
        assertNotNull(r1.getDate());
    }

    @Test
    public void compareSameScore() throws Exception {
        Record r1 = new Record("a", 146);
        Thread.sleep(10); // make sure date is different
        Record r2 = new Record("a", 146);
        Thread.sleep(10);
        Record r3 = new Record("a", 146);

        assertEquals(0, r2.compareTo(r2));
        assertEquals(-1, r2.compareTo(r1));
        assertEquals(1, r2.compareTo(r3));
    }

    @Test
    public void compareVariusScore() throws Exception {
        Record r1 = new Record("a", 146);
        Thread.sleep(10); // make sure date is different
        Record r2 = new Record("a", 150);
        Thread.sleep(10);
        Record r3 = new Record("a", 1);
        Thread.sleep(10);
        Record r4 = new Record("a", 150);

        // expected order: 4, 2, 1, 3

        assertEquals(-1, r4.compareTo(r3));
        assertEquals(-1, r4.compareTo(r2));
        assertEquals(-1, r4.compareTo(r1));

        assertEquals(1, r3.compareTo(r4));
        assertEquals(1, r3.compareTo(r2));
        assertEquals(1, r3.compareTo(r1));

        assertEquals(1, r2.compareTo(r4));
        assertEquals(-1, r2.compareTo(r3));
        assertEquals(-1, r2.compareTo(r1));
    }

}