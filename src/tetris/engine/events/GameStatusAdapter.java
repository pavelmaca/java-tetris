package tetris.engine.events;

import tetris.engine.Shape;

/**
 * Created by Assassik on 29. 4. 2016.
 */
public abstract class GameStatusAdapter implements GameStatusListener {
    @Override
    public void scoreChange(int score){
    }

    @Override
    public void gameEnd(){
    }

    @Override
    public void shapeChange(Shape shape){
    }
}
