package json.film;

import com.google.gson.annotations.Expose;

/**
 * Wrapper per il genere
 * Created by etrunon on 23/07/15.
 */
public class Genre {
    @Expose
    String genre;

    public Genre(String genre) {
        this.genre = genre;
    }
}
