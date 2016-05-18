package tetris.engine;

/**
 * @author Pavel Máca <maca.pavel@gmail.com>
 */
public enum Difficulty {
    EASY("Lehká", 1, 350),
    MEDIUM("Střední", 2, 250),
    HARD("Těžká", 3, 200);

    final String description;
    final int scoreCoeficient;
    final int fallSpeed;    // number of ms betwean shape moves

    Difficulty(String description, int scoreCoeficient, int fallSpeed) {
        this.description = description;
        this.scoreCoeficient = scoreCoeficient;
        this.fallSpeed = fallSpeed;
    }

    public int getScoreCoeficient() {
        return scoreCoeficient;
    }

    @Override
    public String toString() {
        return description;
    }

    public int getFallSpeed() {
        return fallSpeed;
    }
}
