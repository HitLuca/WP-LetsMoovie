package json.adminFunctions.request;

import java.util.List;

/**
 * Created by hitluca on 23/07/15.
 */
public class DeleteReservationRequest {
    private String code;
    private List<SeatDetailRequest> seatList;

    public String getCode() {
        return code;
    }

    public List<SeatDetailRequest> getSeatList() {
        return seatList;
    }
}
