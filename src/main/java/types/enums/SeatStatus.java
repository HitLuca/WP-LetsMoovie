package types.enums;

/**
 * Created by etrunon on 14/07/15.
 */
public enum SeatStatus {
    FREE(0),
    RESERVED(1),
    BROKEN(2);

    private int value;

    SeatStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
