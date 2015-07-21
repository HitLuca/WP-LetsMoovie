package json.tickets;

import com.google.gson.annotations.Expose;
import database.datatypes.other.Ticket;
import json.OperationResult;

import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class TicketResponse implements OperationResult {
    @Expose
    List<Ticket> tickets;

    public TicketResponse(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
