package json.debitCards;

import com.google.gson.annotations.Expose;
import json.OperationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etrunon on 21/07/15.
 */
public class DebitCardResponse implements OperationResult {

    @Expose
    private List<DebitCard> cards;

    public DebitCardResponse(List<String> numbers) {
        this.cards = new ArrayList<>();

        for (String s : numbers) {
            cards.add(new DebitCard(s));
        }
    }
}
