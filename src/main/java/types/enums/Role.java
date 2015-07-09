package types.enums;

/**
 * Created by etrunon on 24/06/15.
 */
public enum Role {
    USER(0),
    ADMIN(1),
    SUPER_ADMIN(2);

    private int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}