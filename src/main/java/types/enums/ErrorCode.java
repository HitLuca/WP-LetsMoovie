package types.enums;

/**
 * Created by marco on 07/07/15.
 */
public enum ErrorCode {
    EMPTY_REQ(1),
    EMPTY_WRONG_FIELD(2),
    DUPLICATE_USERNAME(3),
    DUPLICATE_MAIL(4),
    USER_NOT_FOUND(5),
    ALREADY_LOGGED(6),
    ALREADY_LOGGED_OUT(7);

    private int value;

    ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
