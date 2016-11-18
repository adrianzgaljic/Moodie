package adrian.com.moodie.listeners;

import java.util.Map;

import adrian.com.moodie.objects.Movie;
import adrian.com.moodie.objects.Weather;

/**
 * Created by adrianzgaljic on 12/11/16.
 */

public interface HttpResponseListener {

    void onMovieIDsResult(Map<Integer, Movie> movieIdTtiles);
    void onMovieImageUrlResult(Movie movieImageUrl);
    void onMoveImageBitmapResult(Movie movie);
    void onWeatherResult(Weather weather);
    void onFacebookReactionsResult(Map<String, Integer> reactions);
}
