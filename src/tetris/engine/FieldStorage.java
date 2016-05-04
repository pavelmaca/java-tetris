package tetris.engine;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.*;

/**
 * Created by Assassik on 4. 5. 2016.
 */
public class FieldStorage {

    private Color[][] fileds;

    private int rowsCount;
    private int colsCount;

    public FieldStorage(int rowsCount, int colsCount) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;

        initStorage();
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColsCount() {
        return colsCount;
    }


    /**
     * Initialize new empty storage
     */
    public void initStorage() {
        fileds = new Color[rowsCount][colsCount];
    }

    /**
     * Add shape to memory
     *
     * @param shape
     */
    public void saveShape(Shape shape, int xPosition, int yPosition) {
        margeShapeIntoFields(fileds, shape, xPosition, yPosition);
    }

    /**
     * Return number of removed full rows
     *
     * @return
     */
    public int removeFullRows() {
        int count = 0;
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
                count++;
            }
        }

        fileds = cleanFilds;
        return count;
    }


    public boolean isCollision(Shape shape, int xPosition, int yPosition) {
        boolean[][] points = shape.getPoints();
        for (int x = 0; x < shape.getWidth(); x++) {
            for (int y = 0; y < shape.getHeight(); y++) {
                if (!points[y][x]) {
                    continue;
                }

                // sides and bottom collision
                if (xPosition + x >= colsCount || xPosition + x < 0 || yPosition + y >= rowsCount) {
                    return true;
                }

                // field collision
                if (fileds[yPosition + y][xPosition + x] != null) {
                    return true;
                }
            }
        }

        return false;
    }

    private Color[][] margeShapeIntoFields(Color[][] fields, Shape shape, int xPosition, int yPosition) {

        boolean[][] points = shape.getPoints();

        for (int y = 0; y < points.length; y++) {
            for (int x = 0; x < points[y].length; x++) {
                if (points[y][x]) {
                    fields[yPosition + y][xPosition + x] = shape.getColor();
                }
            }
        }

        return fields;
    }

    public Color[][] printStatus(Shape shape, int xPosition, int yPosition){
            Color[][] copyFields = deepCopyOfFileds(fileds);
        return margeShapeIntoFields(copyFields, shape, xPosition, yPosition);
    }

    private Color[][] deepCopyOfFileds(Color[][] fieldsToCopy){
        if (fieldsToCopy == null)
            return null;
        Color[][] result = new Color[fieldsToCopy.length][];
        for (int r = 0; r < fieldsToCopy.length; r++) {
            result[r] = fieldsToCopy[r].clone();
        }
        return result;
    }
}
