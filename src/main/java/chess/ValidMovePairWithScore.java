package chess;

public class ValidMovePairWithScore implements Comparable<ValidMovePairWithScore> {

    private ValidMovePair validMovePair;
    private int score;

    public ValidMovePairWithScore(ValidMovePair validMovePair) {
        this.validMovePair = validMovePair;
        this.score = 0;
    }

    public ValidMovePair getValidMovePair() {
        return validMovePair;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(ValidMovePairWithScore o) {
        return this.score - o.score;
    }
}