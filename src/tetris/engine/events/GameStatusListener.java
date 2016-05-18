package tetris.engine.events;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public interface GameStatusListener {

    void scoreChange(int score);

    void gameEnd();

    void shapeChange();
}
