package database.datatypes.seat;

/**
 * Created by hitluca on 16/07/15.
 */
public class SeatCount {
    private int row;
    private int column;
    private int count;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
