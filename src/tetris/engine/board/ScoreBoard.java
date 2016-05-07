package tetris.engine.board;

import java.io.*;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by Pavel Máca on 7.5.2016.
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

    public Record saveScore(String name, int score) {
        Record record = new Record(name, score);

        board.add(record);

        /*
        int position = getPosition(score);

        for (int i = board.size(); i >= position; i--) {
            board.put(i + 1, board.get(i));
        }

        board.put(position, record);
*/
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
            System.out.println("board: no data file, creating new board");
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Record class not found");
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
