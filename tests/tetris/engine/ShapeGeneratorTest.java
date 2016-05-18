package tetris.engine;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class ShapeGeneratorTest {

    @Test
    public void createNext() throws Exception {
        ShapeGenerator gen = new ShapeGenerator();

        Shape s1 = gen.createNext();

        int topSame = 0;
        int i = 0;
        while (i < 50) {
            topSame += Arrays.deepEquals(gen.createNext().getPoints(), s1.getPoints()) ? 1 : 0;
            i++;
        }

        assertFalse(topSame >= i / 5);
    }

}