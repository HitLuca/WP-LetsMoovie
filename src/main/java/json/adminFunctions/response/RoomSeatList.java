package json.adminFunctions.response;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hitluca on 21/07/15.
 */
public class RoomSeatList {

    @Expose
    int row;
    @Expose
    int column;
    @Expose
    List<RoomSeatCount> showSeatList;

    public RoomSeatList(int row, int column, List<RoomSeatCount> showSeatList) {
        this.row = row;
        this.column = column;
        this.showSeatList = showSeatList;
    }

    public RoomSeatList(int row, int column) {
        this.row = row;
        this.column = column;
        showSeatList = new ArrayList<>();
    }

    public void addSeat(RoomSeatCount s) {
        showSeatList.add(s);
    }
}
