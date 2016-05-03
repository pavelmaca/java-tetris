package tetris.engine;

import tetris.engine.events.GameStatusListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by Assassik on 7. 4. 2016.
 */
public class Engine {

    /**
     * Herní plocha tvořená souřadnicemi x a y
     * První rozměr je výška, druhý šířka
     * Hodnota NULL představuje prázdnou plochu
     */
    private Color[][] fileds;

    private Shape nextShape;
    private Shape actualShape = null;
    private int actualX;
    private int actualY;

    private int rowsCount;
    private int colsCount;

    private boolean running = false;

    private int score = 0;


    private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();
    private ShapeGenerator generator = new ShapeGenerator();

    public Engine(int rowsCount, int colsCount) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        initFileds();

        nextShape = generator.createNext();
        creteNewShape();
        /* Test corners*/
        /*
        fileds[rowsCount-1][0] = Color.YELLOW;
        fileds[0][colsCount-1] = Color.BLUE;
        fileds[rowsCount-1][colsCount-1] = Color.RED;
        fileds[0][0] = Color.GREEN;*/
    }


    protected void creteNewShape() {
        actualShape = nextShape;
        actualX = colsCount / 2 - actualShape.getWidth() / 2;
        actualY = 0;

        nextShape = generator.createNext();
        gameStatusListeners.forEach(new Consumer<GameStatusListener>() {
            @Override
            public void accept(GameStatusListener listener) {
                listener.shapeChaned(nextShape);
            }
        });
    }

    public void tick() {
        if (!running) {
            return;
        }

        int nextY = actualY + 1;
        if (!isColision(actualX, nextY)) {
            actualY = nextY;
            System.out.println("tick");
        } else {
            saveShape();
            cleanFields();
            creteNewShape();
            if (isColision(actualX, actualY)) {
                performGameEndEvent();
            }
            System.out.println("collision");
        }
    }

    protected void saveShape() {
        margeFildsAndShape(fileds, actualShape);
    }

    protected boolean isColision(int nextX, int nextY) {
        boolean[][] points = actualShape.getPoints();
        for (int x = 0; x < actualShape.getWidth(); x++) {
            for (int y = 0; y < actualShape.getHeight(); y++) {
                if (!points[y][x]) {
                    continue;
                }

                // sides and bottom collision
                if (nextX + x >= colsCount || nextX + x < 0 || nextY + y >= rowsCount) {
                    return true;
                }

                // field collision
                if (fileds[nextY + y][nextX + x] != null) {
                    return true;
                }
            }
        }

        return false;
    }

    protected void cleanFields() {
        Color[][] cleanFilds = new Color[rowsCount][colsCount];
        int n = rowsCount - 1;
        for (int y = rowsCount - 1; y > 0; y--) {
            boolean fullRow = true;
            for (int x = 0; x < colsCount; x++) {
                if (fileds[y][x] == null) {
                    fullRow = false;
                    break;
                }
            }
            if (!fullRow) {
                cleanFilds[n--] = fileds[y];
            } else {
                score += getColsCount();
                performScoreChangeEvent();
            }
        }

        fileds = cleanFilds;
    }


    public Color[][] getGameFields() {
        Color[][] fields = deepCopyFields();
        return margeFildsAndShape(fields, actualShape);
    }

    protected Color[][] margeFildsAndShape(Color[][] fields, Shape shape) {

        boolean[][] points = shape.getPoints();

        for (int y = 0; y < points.length; y++) {
            for (int x = 0; x < points[y].length; x++) {
                if (points[y][x]) {
                    fields[actualY + y][actualX + x] = actualShape.getColor();
                }
            }
        }

        return fields;
    }

    protected Color[][] deepCopyFields() {
        if (fileds == null)
            return null;
        Color[][] result = new Color[fileds.length][];
        for (int r = 0; r < fileds.length; r++) {
            result[r] = fileds[r].clone();
        }
        return result;
    }

    public void moveLeft() {
        if (!running) {
            return;
        }

        int nextX = actualX - 1;
        if (!isColision(nextX, actualY)) {
            actualX = nextX;
        }
    }

    public void moveRight() {
        if (!running) {
            return;
        }

        int nextX = actualX + 1;
        if (!isColision(nextX, actualY)) {
            actualX = nextX;
        }
    }

    public void moveDown() {
        if (!running) {
            return;
        }

        int nextY = actualY + 1;
        if (!isColision(actualX, nextY)) {
            actualY = nextY;
        }
    }

    public void rotateShape() {
        if (!running) {
            return;
        }

        actualShape.rotate();

        // check collision after rotation
        if (isColision(actualX, actualY)) {
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
        } else if (actualX + actualShape.getWidth() > colsCount) {
            actualX = colsCount - actualShape.getWidth();
        }

    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColsCount() {
        return colsCount;
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

    private void initFileds() {
        fileds = new Color[rowsCount][colsCount];
    }

    public void restart() {
        pause();

        score = 0;
        initFileds();
        nextShape = generator.createNext();
        creteNewShape();
        performScoreChangeEvent();
    }
}
