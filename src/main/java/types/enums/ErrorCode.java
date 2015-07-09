package types.enums;

/**
 * Created by marco on 07/07/15.
 */
public enum ErrorCode {
    EMPTY_REQ(1),
    EMPTY_WRONG_FIELD(2),
    DUPLICATE_USERNAME(3),//TODO remove and fix
    DUPLICATE_MAIL(4),
    DUPLICATE_USERNAME_AND_MAIL(5),
    USER_NOT_FOUND(6),
    ALREADY_LOGGED(7),
    NON_USATO_DA_SOSTITUIRE(8),
    INVALID_MAIL(9),
    NOT_LOGGED_IN(10),
    WRONG_CONFIRMATION_CODE(11);

    private int value;

    ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
