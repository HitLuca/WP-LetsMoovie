package database.datatypes.seat;

/**
 * Created by hitluca on 29/06/15.
 */
public class Seat {
    private int id_seat;
    private int room_number;
    private int row;
    private int column;
    private String status;

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

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
