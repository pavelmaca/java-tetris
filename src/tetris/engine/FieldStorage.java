package tetris.engine;

import java.awt.*;

/**
 * Uložiště uchovávající nepohyblivé objekty
 * jako jeden blok.
 * Představuje celou herní plochu.
 * Pohlcuje tvary a jejich souřadnice a uchovává je jako jeden blok.
 * Představuje celou herní plochu nepohyblivých objektů.
 *
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
class FieldStorage {

    /**
     * Uchovává aktuální polohu a tvar nepohyblivých objektů
     */
    private Color[][] fileds;

    /**
     * Vytvoří nové uložiště s danou velikostí
     *
     * @param rowsCount počet řádek
     * @param colsCount počet sloupců
     */
    public FieldStorage(int rowsCount, int colsCount) {
        fileds = new Color[rowsCount][colsCount];
    }

    /**
     * @return počet řádků
     */
    public int getRowsCount() {
        return fileds.length;
    }

    /**
     * @return počet sloupců
     */
    public int getColsCount() {
        return fileds.length > 0 ? fileds[0].length : 0;
    }

    /**
     * Vyresetuje aktuální stav na prázdnou plochu
     */
    public void resetStorage() {
        fileds = new Color[getRowsCount()][getColsCount()];
    }

    /**
     * Přidá tvar do aktuální paměti
     * Pokud je tvar, nebo jeho část umístěna mimo herní pole, tato část bude ztracena
     *
     * @param shape     ukládaný tvar
     * @param xPosition pozice ukládaného tvaru na ose X
     * @param yPosition pozice ukládaného tvaru na ose YY
     */
    public void saveShape(Shape shape, int xPosition, int yPosition) {
        margeShapeIntoFields(fileds, shape, xPosition, yPosition);
    }

    /**
     * Odebere všechny plné řádky a vrátí počet řádků, které byli odebrány.
     * Každý řádek nad odebíraným je posunut směrem dolů a je tak reprezentovat jejich "pád".
     *
     * @return počet odebraných řádek
     */
    public int removeFullRows() {
        int count = 0; // počet odebraných řádek

        int rowsCount = getRowsCount();
        int colsCount = getColsCount();

        Color[][] cleanedFilds = new Color[rowsCount][colsCount]; // nové pole bez odebraných řádků

        int n = rowsCount - 1; // aktuální řádek, kam ukládá v novém poli

        for (int y = n; y >= 0; y--) {
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
     * Kontrola, zda předaný tvar a jeho souřadnice jsou v kolizi s pevnou částí uložiště.
     * Zárovneň kontroluj, zda je tvar celý uvnitř rozměrů pro uložiště.
     *
     * @param shape     tvar ke kontrole
     * @param xPosition pozice na ose X, kontrolovaného tvaru
     * @param yPosition pozice na ose Y, kontrolovaného tvaru
     * @return true, pokud nastala kolize
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

                // kontrola přetečení okraje: postraní, spodní a horní
                if (xPosition + x >= colsCount || xPosition + x < 0 || yPosition + y >= rowsCount || yPosition + y < 0) {
                    return true;
                }

                // kolize s pevným blokem
                if (fileds[yPosition + y][xPosition + x] != null) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Spojí body z tvaru do předaného pole barev reprezentující statický blok
     * Pokud je nějkaý bod mimo velikost statického bloku, bude skryt
     *
     * @param fields    pole barev, reprezentující statický tvar, do které se připojuje
     * @param shape     tvar k připojení
     * @param xPosition pozice na ose X pro tvar
     * @param yPosition pozice na ose Y pro tvar
     * @return vstupní pole modifikované o přidaný tvar
     */
    private Color[][] margeShapeIntoFields(Color[][] fields, Shape shape, int xPosition, int yPosition) {

        boolean[][] points = shape.getPoints();

        for (int y = 0; y < points.length; y++) {
            for (int x = 0; x < points[y].length; x++) {
                int yAbs = yPosition + y;
                int xAbs = xPosition + x;
                if (points[y][x] && xAbs >= 0 && xAbs < getColsCount() && yAbs >= 0 && yAbs < getRowsCount()) {
                    fields[yPosition + y][xPosition + x] = shape.getColor();
                }
            }
        }

        return fields;
    }

    /**
     * Vytvoří dvojrozměrné pole reprezentujicí aktuální stav spojený s pohyblivým tvarem.
     * Nijak nemění vtniřní stav uložiště
     *
     * @param shape     pohyblivý tvar
     * @param xPosition pozice pohyblivého tvaru na ose X
     * @param yPosition pozice pohyblivého tvaru na ose Y
     * @return
     */
    public Color[][] printStatus(Shape shape, int xPosition, int yPosition) {
        Color[][] copyFields = deepCopyOfFileds(fileds);
        if (shape == null) {
            return copyFields;
        }
        return margeShapeIntoFields(copyFields, shape, xPosition, yPosition);
    }

    /**
     * Vytvoří hlubokou kopii předaného dvojrozměrného pole barev
     *
     * @param fieldsToCopy kopírované dvojrozměrné pole barev
     * @return dvojrozměrná hluboká kopie předanéhp pole na vstupu
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
