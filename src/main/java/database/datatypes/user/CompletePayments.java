package database.datatypes.user;

import com.google.gson.annotations.Expose;
import json.OperationResult;

import java.util.List;

/**
 * Created by hitluca on 21/07/15.
 */
public class CompletePayments implements OperationResult {

    @Expose
    private String code;

    @Expose
    private String payment_date;
    @Expose
    private String payment_time;
    @Expose
    private int room_number;
    @Expose
    private String film_title;
    @Expose
    private List<UserPayment> payments;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getFilm_title() {
        return film_title;
    }

    public void setFilm_title(String film_title) {
        this.film_title = film_title;
    }

    public List<UserPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<UserPayment> payments) {
        this.payments = payments;
    }
}
