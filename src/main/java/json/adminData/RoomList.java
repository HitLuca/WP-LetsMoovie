package json.adminData;

import com.google.gson.annotations.Expose;
import database.datatypes.seat.RoomData;
import json.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 22/07/15.
 */
public class RoomList implements OperationResult {

    @Expose
    List<Room> roomList;

    public RoomList() {
        this.roomList = new ArrayList<>();
    }

    public void addRooms(List<RoomData> datas) {
        for (RoomData r : datas) {
            roomList.add(new Room(r));
        }
    }
}

class Room {

    @Expose
    int room_number;
    @Expose
    int width;
    @Expose
    int length;

    public Room(RoomData r) {
        this.room_number = r.getRoom_number();
        this.width = r.getWidth();
        this.length = r.getLength();
    }
}
