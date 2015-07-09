package utilities.mail.request;

/**
 * Created by marco on 08/07/15.
 */
public class UserEmailRequest {
    private long expireDate;
    private String username;
    private String email;

    public UserEmailRequest(long expireDate, String username, String email)
    {
        this.expireDate = expireDate;
        this.username = username;
        this.email = email;

    }

    public long getExpireDate() {
        return expireDate;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
