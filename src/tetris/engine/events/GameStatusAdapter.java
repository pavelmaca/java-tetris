package tetris.engine.events;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public abstract class GameStatusAdapter implements GameStatusListener {
    @Override
    public void scoreChange(int score) {
    }

    @Override
    public void gameEnd() {
    }

    @Override
    public void shapeChange() {
    }
}
