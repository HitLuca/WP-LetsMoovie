package types;

/**
 * Created by marco on 24/06/15.
 */
public class UserLoginCredential {
    private String password;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
