package database.datatypes;

/**
 * Created by hitluca on 29/06/15.
 */
public class Seat {
    private int id_seat;
    private int row;
    private int column;

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

    public int getId_seat() {
        return id_seat;
    }

    public void setId_seat(int id_seat) {
        this.id_seat = id_seat;
    }
}
