package json.logout.response;

/**
 * Created by marco on 06/07/15.
 */
public class InvalidLogout extends LogoutStatus {
    public InvalidLogout()
    {
        success = false;
    }
}
