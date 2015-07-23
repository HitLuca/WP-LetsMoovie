package json.adminFunctions.response;

import com.google.gson.annotations.Expose;
import json.adminFunctions.request.SeatDetailRequest;

/**
 * Created by hitluca on 23/07/15.
 */
public class SeatDetailResponse {
    @Expose
    private int srow;
    @Expose
    private int scolumn;
    @Expose
    private String ticket_type;
    @Expose
    private float price;

    public SeatDetailResponse(SeatDetailRequest sdr) {
        this.srow = sdr.getRow();
        this.scolumn = sdr.getColumn();
        this.ticket_type = sdr.getTicket_type();
        this.price = sdr.getPrice();
    }
}
