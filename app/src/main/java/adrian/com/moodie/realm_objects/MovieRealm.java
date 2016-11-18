package adrian.com.moodie.realm_objects;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by adrianzgaljic on 16/11/16.
 */

public class MovieRealm extends RealmObject {

    private int genre;
    private String movieTitle;
    private byte[] poster;

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }
}
