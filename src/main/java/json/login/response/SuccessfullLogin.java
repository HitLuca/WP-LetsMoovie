package json.login.response;

/**
 * Created by marco on 06/07/15.
 */
public class SuccessfullLogin extends LoginStatus {
    private String username;

    public SuccessfullLogin()
    {
        success = true;
    }

    public SuccessfullLogin(String username) {
        this.username = username;
        success = true;
    }
}
