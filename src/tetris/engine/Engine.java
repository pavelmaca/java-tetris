package tetris.engine;

import tetris.engine.events.GameStatusListener;

import java.awt.*;
import java.util.ArrayList;

/**
 * Logická implementace aplikace.
 * Řeší pohyby tvarů, stav hry a nastavení
 *
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class Engine {

    /* Uložiště uchovávající nepohyblivé objekty */
    private FieldStorage storage;

    /* Další tvar v pořadí */
    private Shape nextShape;

    /* Aktuální pohyblivý tvar na kterém se provádějí akce */
    private Shape actualShape;

    /* Pozice aktuálního tvaru na ose X */
    private int actualX;

    /* Pozice aktuálního tvaru na ose Y */
    private int actualY;

    /* Stav určujicí zda hra běží */
    private boolean running = false;

    /* Nastavná uroveň obtížnosti */
    private Difficulty difficulty = Difficulty.MEDIUM;

    /* Aktuální skóre */
    private int score = 0;

    /* Pole listenerů reagující na změnu stavu hry */
    private ArrayList<GameStatusListener> gameStatusListeners = new ArrayList<>();

    /* Generátor náhodných tvarů */
    private ShapeGenerator generator = new ShapeGenerator();

    /**
     * Inicializace hry
     *
     * @param rowsCount počet řádků herního pole
     * @param colsCount počet soupců herního pole
     */
    public Engine(int rowsCount, int colsCount) {
        storage = new FieldStorage(rowsCount, colsCount);

        nextShape = generator.createNext();
        creteNewShape();
    }


    /**
     * Změní stav předchozího tvaru na aktuální a vytvoří nový následujicí.
     * Předchozí tvar má výchozí souřadnice na horním okraji uprostřed herní plochy.
     */
    private void creteNewShape() {
        actualShape = nextShape;
        actualX = storage.getColsCount() / 2 - actualShape.getWidth() / 2;
        actualY = 0;

        nextShape = generator.createNext();
        gameStatusListeners.forEach(listener -> listener.shapeChange());
    }

    /**
     * Pohyb aktuálního objektu a kotrola dopadu.
     * Při dopadu se promazávají plné řádky.
     * Počet promazaných řádků se násobý kooficientem podle obtížnosti a přičítá se do skóre
     * <p>
     * Pokud dojde ke kolizi po  dopadu a nastavení nového aktuálního objektu, hra končí.
     * <p>
     * Při odmazání řádku je vyvolán event typu {@link GameStatusListener.scoreChange()}.
     * Konec hry vyvolá událost typu {@link GameStatusListener.gameEnd()}.
     */
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

    /**
     * Vytvoří dvojrozměrné pole barev, představující aktuální stav hry.
     *
     * @return dvojrozměrné pole barev, představující aktuální stav hry.
     */
    public Color[][] getStatus() {
        return storage.printStatus(actualShape, actualX, actualY);
    }

    /**
     * Kontrola, zda je možné provést pohyb na dané souřadnice
     *
     * @param nextX pozice na ose X
     * @param nextY pozice na ose Y
     * @return true, pokud je možné aktuální tvar posunout na dané souřadnice
     */
    private boolean tryMove(int nextX, int nextY) {
        return running && !storage.isCollision(actualShape, nextX, nextY);
    }

    /*
     * Pohyb v levo, pokud je možný
     */
    public void moveLeft() {
        int nextX = actualX - 1;
        if (tryMove(nextX, actualY)) {
            actualX = nextX;
        }
    }

    /**
     * Pohyb v pravo, pokud je možný
     */
    public void moveRight() {
        int nextX = actualX + 1;
        if (tryMove(nextX, actualY)) {
            actualX = nextX;
        }
    }

    /**
     * Pohyb dolů, pokud je možný
     *
     * @return true, pokud byl proveden pohyb
     */
    public boolean moveDown() {
        int nextY = actualY + 1;
        if (tryMove(actualX, nextY)) {
            actualY = nextY;
            return true;
        }
        return false;
    }

    /**
     * Otočení aktuálního tvaru
     * Otočený tvar je vycentrován relativně k původní pozici,
     * případně posunut, aby nepřetékal herní plochu.
     * <p>
     * Pokud dojde ke kolizi je objekt vrácen do původní pozice a rotace se neguje.
     */
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

        // vycentrování - oprava pozice na x, po otočení na straně herní plochy
        if (newX < 0) {
            newX = 0;
        } else if (newX + actualShape.getWidth() > storage.getColsCount()) {
            newX = storage.getColsCount() - actualShape.getWidth();
        }

        // kontrola kolize po otočení a vycentrování
        if (storage.isCollision(actualShape, newX, actualY)) {
            // rotace zpět do původní polohy
            actualShape.rotate();
            actualShape.rotate();
            actualShape.rotate();
        } else {
            actualX = newX;
        }
    }

    /**
     * @return Počet řádků herní plochy
     */
    public int getRowsCount() {
        return storage.getRowsCount();
    }

    /**
     * @return Počet sloupců herní plochy
     */
    public int getColsCount() {
        return storage.getColsCount();
    }

    /**
     * Nastaví stav hry na "běží"
     */
    public void start() {
        this.running = true;
    }

    /**
     * Nastaví stav hry na "pozastaveno"
     */
    public void pause() {
        this.running = false;
    }

    /**
     * Zaregistruje listener, poslouchající události hry
     *
     * @param listener Listener implemetující rozhrani {@link GameStatusListener}
     */
    public void addGameStatusListener(GameStatusListener listener) {
        gameStatusListeners.add(listener);
    }

    /**
     * Provedení všech akcí vazaných na změnu skóre v registrovaných listenerech
     */
    private void performScoreChangeEvent() {
        gameStatusListeners.forEach(listener -> listener.scoreChange(score));
    }

    /**
     * Provedení všech akcí vazaných na konec hry v registrovaných listenerech
     */
    private void performGameEndEvent() {
        gameStatusListeners.forEach(listener -> listener.gameEnd());
    }

    /**
     * @return Tvar, který bude následovat po katuálním
     */
    public Shape getNextShape() {
        return nextShape;
    }

    /**
     * @return Aktuální skóre
     */
    public int getScore() {
        return score;
    }

    /**
     * Pozastaví hru a vyresetuje její stav.
     * Nuluje skóre, uložiště a generuje nové tvary.
     * Vyvolává událost změna skóre
     */
    public void restart() {
        pause();

        score = 0;
        storage.resetStorage();
        nextShape = generator.createNext();
        creteNewShape();
        performScoreChangeEvent();
    }

    /**
     * @return {@link #difficulty}
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Natavení obtížnosti hry
     *
     * @param difficulty nastavovaná obtížnost
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Kontroluje,zda hra běží.
     *
     * @return true, pokud hra běží
     */
    public boolean isRunning() {
        return running;
    }
}
