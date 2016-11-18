package adrian.com.moodie.api_calls;

import org.json.JSONArray;
import org.json.JSONObject;

import adrian.com.moodie.listeners.ApiCallListener;
import adrian.com.moodie.listeners.HttpResponseListener;
import adrian.com.moodie.objects.Movie;

/**
 * Created by adrianzgaljic on 12/11/16.
 */

public class MovieImageUrlApiCall implements ApiCallListener {

    private String url;
    private String title;
    private int genre;
    private HttpResponseListener httpResponseListener;

    public MovieImageUrlApiCall(String url, String movieIdTitle, int genre, HttpResponseListener httpResponseListener){
        this.url = url;
        this.title = movieIdTitle;
        this.genre = genre;
        this.httpResponseListener = httpResponseListener;
    }

    public void execute(){
        ApiCall apiCall = new ApiCall(url,this);
        apiCall.execute();
    }


    @Override
    public void jsonObjectReady(JSONObject jsonObject) {
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("posters");
            JSONObject poster = jsonArray.getJSONObject(0);
            String movieImageUrl = poster.get("file_path").toString();
            Movie movie = new Movie(title,movieImageUrl);
            movie.setGenre(genre);
            httpResponseListener.onMovieImageUrlResult(movie);
        } catch (Exception e){

        }


    }
}
