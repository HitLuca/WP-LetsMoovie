package json.debitCards;

import com.google.gson.annotations.Expose;

/**
 * Created by etrunon on 21/07/15.
 */
public class DebitCard {

    @Expose
    private String number;

    public DebitCard(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
