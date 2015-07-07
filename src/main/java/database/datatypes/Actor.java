package database.datatypes;

/**
 * Created by hitluca on 06/07/15.
 */
public class Actor {
    private String actor_name;
    private String role;
    private String url_photo;

    public String getActor_name() {
        return actor_name;
    }

    public void setActor_name(String actor_name) {
        this.actor_name = actor_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }
}
