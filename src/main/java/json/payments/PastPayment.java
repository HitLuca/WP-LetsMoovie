package json.payments;

import com.google.gson.annotations.Expose;
import database.datatypes.user.CompletePayments;
import database.datatypes.user.UserPayment;
import database.mappers.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * f
 * Created by etrunon on 14/07/15.
 */
public class PastPayment {

    @Expose
    private String payment_date;
    @Expose
    private String payment_time;
    @Expose
    private int room_number;
    @Expose
    private String film_title;
    @Expose
    private List<SingleSeatPaid> seatsPaid;
    @Expose
    private float total;
    @Expose
    private String code;

    public PastPayment(CompletePayments c, UserMapper userMapper, String username, String code) {

        payment_date = c.getPayment_date();
        payment_time = c.getPayment_time();
        room_number = c.getRoom_number();
        film_title = c.getFilm_title();
        seatsPaid = new ArrayList<>();
        this.code = code;

        List<UserPayment> up = userMapper.getUserPayments(payment_date, payment_time, username);

        float x = 0;

        for (UserPayment u : up) {
            seatsPaid.add(new SingleSeatPaid(u));
            x += u.getPrice();
        }

        total = x;

    }
}
