package adrian.com.moodie.objects;

import android.graphics.Bitmap;

import io.realm.RealmObject;

/**
 * Created by adrianzgaljic on 12/11/16.
 */

public class Movie{

    private String name;
    private int genre;
    private String url;
    private Bitmap image;



    public Movie(String name) {
        this.name = name;
    }

    public Movie(String name, String url) {
        this.name = name;
        this.url = url;
    }


    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return "https://image.tmdb.org/t/p/w500" + url;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", url='" + getUrl() + '\'' +
                '}';
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
