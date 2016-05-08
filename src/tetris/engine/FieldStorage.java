package tetris.engine;

import java.awt.*;

/**
 * Created by Pavel Máca on 4. 5. 2016.
 */
class FieldStorage {

    /**
     * Store actual position and color of non-moving fields
     */
    private Color[][] fileds;

    /**
     * Initialize new storage with given size
     *
     * @param rowsCount number of rows
     * @param colsCount number of cols
     */
    public FieldStorage(int rowsCount, int colsCount) {
        fileds = new Color[rowsCount][colsCount];
    }

    /**
     * @return number of rows
     */
    public int getRowsCount() {
        return fileds.length;
    }

    /**
     * @return number of cols
     */
    public int getColsCount() {
        return fileds.length > 0 ? fileds[0].length : 0;
    }

    /**
     * Initialize new empty storage
     */
    public void resetStorage() {
        fileds = new Color[getRowsCount()][getColsCount()];
    }

    /**
     * Add shape to memory
     *
     * @param shape     saved shape
     * @param xPosition X position of shape in game field
     * @param yPosition Y position of shape in game field
     */
    public void saveShape(Shape shape, int xPosition, int yPosition) {
        margeShapeIntoFields(fileds, shape, xPosition, yPosition);
    }

    /**
     * Starting from bottom to top, walks thru all rows and remove these, that are full.
     * All rows over removed one will be moved down (falling).
     *
     * @return number of removed full rows
     */
    public int removeFullRows() {
        int count = 0; // nuber of removed rows

        int rowsCount = getRowsCount();
        int colsCount = getColsCount();

        Color[][] cleanedFilds = new Color[rowsCount][colsCount]; // new array with removed rows

        int n = rowsCount - 1; // current row in new array

        for (int y = n; y > 0; y--) {
            boolean fullRow = true;
            for (int x = 0; x < colsCount; x++) {
                if (fileds[y][x] == null) {
                    fullRow = false;
                    break;
                }
            }
            if (!fullRow) {
                cleanedFilds[n--] = fileds[y];
            } else {
                count++;
            }
        }

        fileds = cleanedFilds;
        return count;
    }


    /**
     * Check, if shape is outside of game fields, or in collision with saved points.
     *
     * @param shape     shape to check
     * @param xPosition X position of shape in fileds
     * @param yPosition Y position of shape in fileds
     * @return true when collision, otherwise false
     */
    public boolean isCollision(Shape shape, int xPosition, int yPosition) {
        boolean[][] points = shape.getPoints();

        int rowsCount = getRowsCount();
        int colsCount = getColsCount();

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

    /**
     * Merge shape point into given fileds array
     *
     * @param fields    array of fileds to marge shape
     * @param shape     shape to merge
     * @param xPosition X position of shape in fileds
     * @param yPosition Y position of shape in fileds
     * @return modified fields array, containing given shape
     */
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

    /**
     * Create array presenting acutal position of all fields and given shape.
     *
     * @param shape     shape to merge
     * @param xPosition X position of shape in fileds
     * @param yPosition Y position of shape in fileds
     * @return
     */
    public Color[][] printStatus(Shape shape, int xPosition, int yPosition) {
        Color[][] copyFields = deepCopyOfFileds(fileds);
        return margeShapeIntoFields(copyFields, shape, xPosition, yPosition);
    }

    /**
     * Create deep copy of two dimensional array of colors
     *
     * @param fieldsToCopy requested array
     * @return deep copy of array
     */
    private Color[][] deepCopyOfFileds(Color[][] fieldsToCopy) {
        if (fieldsToCopy == null)
            return null;
        Color[][] result = new Color[fieldsToCopy.length][];
        for (int r = 0; r < fieldsToCopy.length; r++) {
            result[r] = fieldsToCopy[r].clone();
        }
        return result;
    }
}
