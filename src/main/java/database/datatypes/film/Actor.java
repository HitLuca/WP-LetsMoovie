package database.datatypes.film;

import com.google.gson.annotations.Expose;

/**
 * Created by HitLuca on 06/07/15.
 *
 */

public class Actor {
    private int id_film;
    @Expose
    private String actor_name;
    @Expose
    private String role;
    @Expose
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

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }
}
