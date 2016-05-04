package tetris.engine;

import tetris.engine.events.GameStatusListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by Assassik on 7. 4. 2016.
 */
public class Engine {

    private FieldStorage storage;

    private Shape nextShape;
    private Shape actualShape;
    private int actualX;
    private int actualY;

    private boolean running = false;

    private int score = 0;


    private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();
    private ShapeGenerator generator = new ShapeGenerator();

    public Engine(int rowsCount, int colsCount) {
        storage = new FieldStorage(rowsCount, colsCount);

        nextShape = generator.createNext();
        creteNewShape();
    }


    protected void creteNewShape() {
        actualShape = nextShape;
        actualX = storage.getColsCount() / 2 - actualShape.getWidth() / 2;
        actualY = 0;

        nextShape = generator.createNext();
        gameStatusListeners.forEach(new Consumer<GameStatusListener>() {
            @Override
            public void accept(GameStatusListener listener) {
                listener.shapeChange(nextShape);
            }
        });
    }

    public void tick() {
        if (!running) {
            return;
        }

        int nextY = actualY + 1;
        if (!storage.isCollision(actualShape, actualX, nextY)) {
            actualY = nextY;
            System.out.println("tick");
        } else {
            storage.saveShape(actualShape, actualX, actualY);
            int removedCount= storage.removeFullRows();
            if(removedCount > 0){
                performScoreChangeEvent();
                score += removedCount;
            }
            
            creteNewShape();
            if (storage.isCollision(actualShape, actualX, actualY)) {
                performGameEndEvent();
            }
            System.out.println("collision");
        }
    }

    public Color[][] getStatus() {
        return storage.printStatus(actualShape, actualX, actualY);
    }

    public void moveLeft() {
        if (!running) {
            return;
        }

        int nextX = actualX - 1;
        if (!storage.isCollision(actualShape, nextX, actualY)) {
            actualX = nextX;
        }
    }

    public void moveRight() {
        if (!running) {
            return;
        }

        int nextX = actualX + 1;
        if (!storage.isCollision(actualShape, nextX, actualY)) {
            actualX = nextX;
        }
    }

    public void moveDown() {
        if (!running) {
            return;
        }

        int nextY = actualY + 1;
        if (!storage.isCollision(actualShape, actualX, nextY)) {
            actualY = nextY;
        }
    }

    public void rotateShape() {
        if (!running) {
            return;
        }

        actualShape.rotate();

        // check collision after rotation
        if (storage.isCollision(actualShape, actualX, actualY)) {
            // rotate back to original state and exit
            // TODO: create rotateBack () ??
            actualShape.rotate();
            actualShape.rotate();
            actualShape.rotate();
            return;
        }

        // fix x position after rotating on sides
        if (actualX < 0) {
            actualX = 0;
        } else if (actualX + actualShape.getWidth() > storage.getColsCount()) {
            actualX = storage.getColsCount() - actualShape.getWidth();
        }
    }

    public int getRowsCount() {
        return storage.getRowsCount();
    }

    public int getColsCount() {
        return storage.getColsCount();
    }

    public void start() {
        this.running = true;
    }

    public void pause() {
        this.running = false;
    }

    public void addGameStatusListener(GameStatusListener listener) {
        gameStatusListeners.add(listener);
    }

    private void performScoreChangeEvent() {
        gameStatusListeners.forEach(new Consumer<GameStatusListener>() {
            @Override
            public void accept(GameStatusListener listener) {
                listener.scoreChange(score);
            }
        });
    }

    private void performGameEndEvent() {
        gameStatusListeners.forEach(new Consumer<GameStatusListener>() {
            @Override
            public void accept(GameStatusListener listener) {
                listener.gameEnd();
            }
        });
    }

    public Shape getNextShape() {
        return nextShape;
    }

    public int getScore() {
        return score;
    }


    public void restart() {
        pause();

        score = 0;
        storage.initStorage();
        nextShape = generator.createNext();
        creteNewShape();
        performScoreChangeEvent();
    }
}
