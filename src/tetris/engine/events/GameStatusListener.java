package tetris.engine.events;

/**
 * Created by Pavel Máca on 2. 5. 2016.
 */
public interface GameStatusListener {

    void scoreChange(int score);

    void gameEnd();

    void shapeChange();
}
