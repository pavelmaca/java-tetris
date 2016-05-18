package tetris.engine;

import tetris.engine.events.GameStatusListener;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class Engine {

    private FieldStorage storage;

    private Shape nextShape;
    private Shape actualShape;
    private int actualX;
    private int actualY;

    private boolean running = false;

    private Difficulty difficulty = Difficulty.MEDIUM;

    private int score = 0;


    private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();

    private ShapeGenerator generator = new ShapeGenerator();

    public Engine(int rowsCount, int colsCount) {
        storage = new FieldStorage(rowsCount, colsCount);

        nextShape = generator.createNext();
        creteNewShape();
    }


    private void creteNewShape() {
        actualShape = nextShape;
        actualX = storage.getColsCount() / 2 - actualShape.getWidth() / 2;
        actualY = 0;

        nextShape = generator.createNext();
        gameStatusListeners.forEach(listener -> listener.shapeChange());
    }

    public void tick() {
        if (running && !moveDown()) {
            storage.saveShape(actualShape, actualX, actualY);
            int removedCount = storage.removeFullRows();
            if (removedCount > 0) {
                score += removedCount * storage.getColsCount() * difficulty.getScoreCoeficient();
                performScoreChangeEvent();
            }

            creteNewShape();
            if (storage.isCollision(actualShape, actualX, actualY)) {
                running = false;
                performGameEndEvent();
            }
        }
    }

    public Color[][] getStatus() {
        return storage.printStatus(actualShape, actualX, actualY);
    }

    private boolean tryMove(int nextX, int nextY) {
        return running && !storage.isCollision(actualShape, nextX, nextY);
    }

    public void moveLeft() {
        int nextX = actualX - 1;
        if (tryMove(nextX, actualY)) {
            actualX = nextX;
        }
    }

    public void moveRight() {
        int nextX = actualX + 1;
        if (tryMove(nextX, actualY)) {
            actualX = nextX;
        }
    }

    public boolean moveDown() {
        int nextY = actualY + 1;
        if (tryMove(actualX, nextY)) {
            actualY = nextY;
            return true;
        }
        return false;
    }

    public void rotateShape() {
        if (!running) {
            return;
        }

        int prevWidth = actualShape.getWidth();
        int newX = actualX;

        actualShape.rotate();


        if (prevWidth != actualShape.getWidth()) {
            newX += (prevWidth - actualShape.getWidth()) / 2;
        }

        // fix x position after rotating on sides
        if (newX < 0) {
            newX = 0;
        } else if (newX + actualShape.getWidth() > storage.getColsCount()) {
            newX = storage.getColsCount() - actualShape.getWidth();
        }

        // check collision after rotation and move
        if (storage.isCollision(actualShape, newX, actualY)) {
            // rotate back to original state
            actualShape.rotate();
            actualShape.rotate();
            actualShape.rotate();
        } else {
            actualX = newX;
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
        gameStatusListeners.forEach(listener -> listener.scoreChange(score));
    }

    private void performGameEndEvent() {
        gameStatusListeners.forEach(listener -> listener.gameEnd());
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
        storage.resetStorage();
        nextShape = generator.createNext();
        creteNewShape();
        performScoreChangeEvent();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isRunning() {
        return running;
    }
}
