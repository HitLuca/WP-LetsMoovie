package utilities.InputValidator;

/**
 * Created by etrunon on 06/07/15.
 */
public enum Regex {

    EMAIL("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),
    NAME("^([A-Z][a-z]*([ ][a-z]+)*([ '-]([ ])?[A-Z][a-z]+)*)$"),
    PHONE("^(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d{8}))?)\\s*$"),
    // La Password deve contenere:
    PASSWORD("(?=[#$-:-?{-~!\"^_`\\[\\]a-zA-Z]*([0-9#$-:-?{-~!\"^_`\\[\\]]))(?=[#$-:-?{-~!\"^_`\\[\\]a-zA-Z0-9]*[a-zA-Z])[#$-:-?{-~!\"^_`\\[\\]a-zA-Z0-9]{4,}"),
    //Formato data yyyy-mm-dd
    DATE("(?:199[0-9]|20[0-9][0-9])-(?:0[1-9]|1[0-2])-(?:[0-2][0-9]|3[0-1])"),
    USERNAME("^([0-9]*[\\w]*){5}+$"),
    ID("^[1-9][0-9]*$");

    private String value;

    Regex(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
