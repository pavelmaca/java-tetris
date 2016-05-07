package tetris.engine.board;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Pavel Máca on 7.5.2016.
 */
public class Record implements Serializable, Comparable<Record> {
    private String playerName;
    private int score;
    private Date date;

    Record(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.date = new Date();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    /**
     * DESC order
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Record o) {
        int result = Integer.compare(o.getScore(), this.getScore());
        if (result == 0) {
            result = o.getDate().compareTo(this.getDate());
        }

        return result;
    }
}
