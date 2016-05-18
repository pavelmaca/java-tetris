package tetris.engine.board;

import java.io.*;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public class ScoreBoard {

    private String dataFile;

    private TreeSet<Record> board = new TreeSet<>();

    public ScoreBoard(String dataFile) {
        this.dataFile = dataFile;
        loadData();

    }

    public int getPosition(Record record) {
        return board.size() - board.tailSet(record, false).size();
    }

    public Record saveScore(String name, int score) throws Record.InvalidScoreException {
        Record record = new Record(name, score);
        board.add(record);

        return record;
    }

    public Record[] getTop(int number) {

        Record[] list = new Record[number];

        Iterator<Record> it = board.iterator();
        for (int i = 0; i < number && it.hasNext(); i++) {
            list[i] = it.next();
        }

        return list;
    }

    private void loadData() {
        try {
            FileInputStream fileIn = new FileInputStream(dataFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            board = (TreeSet<Record>) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            // file not found, creating new board
            System.out.println("board: new file will be created");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        }
    }

    public void saveData() {
        try {
            FileOutputStream fileOut = new FileOutputStream(dataFile);

            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(board);
            out.close();
            fileOut.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }


}
