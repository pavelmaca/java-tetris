package tetris.engine.events;

import tetris.engine.Shape;

/**
 * Created by Assassik on 2. 5. 2016.
 */
public interface GameStatusListener {

    void scoreChange(int score);
    void gameEnd();
    void shapeChange(Shape shape);
}
