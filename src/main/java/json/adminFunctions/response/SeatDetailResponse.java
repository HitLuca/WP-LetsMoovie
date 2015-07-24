package json.adminFunctions.response;

import com.google.gson.annotations.Expose;
import json.adminFunctions.request.SeatDetailRequest;

/**
 * Created by hitluca on 23/07/15.
 */
public class SeatDetailResponse {
    @Expose
    private int s_row;
    @Expose
    private int s_column;
    @Expose
    private String ticket_type;
    @Expose
    private float price;

    public SeatDetailResponse(SeatDetailRequest sdr) {
        this.s_row = sdr.getS_row();
        this.s_column = sdr.getS_column();
        this.ticket_type = sdr.getTicket_type();
        this.price = sdr.getPrice();
    }
}
