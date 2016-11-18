package adrian.com.moodie.api_calls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import adrian.com.moodie.listeners.ApiCallListener;
import adrian.com.moodie.listeners.HttpResponseListener;
import adrian.com.moodie.objects.Movie;

public class MovieListApiCall implements ApiCallListener {

    private String url;
    private int genreCode;
    private HttpResponseListener httpResponseListener;

    public MovieListApiCall(String url, int genreCode, HttpResponseListener httpResponseListener){
        this.url = url;
        this.genreCode = genreCode;
        this.httpResponseListener = httpResponseListener;
    }



    public void execute(){
        ApiCall apiCall = new ApiCall(url,this);
        apiCall.execute();
    }


    @Override
    public void jsonObjectReady(JSONObject jsonObject) {
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            Map<Integer, Movie> movieIdTitle = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                System.out.println(object.get("title"));
                Movie movie = new Movie(object.getString("title"));
                movie.setGenre(genreCode);
                movieIdTitle.put(Integer.parseInt(object.get("id").toString()),movie);
            }
            httpResponseListener.onMovieIDsResult(movieIdTitle);
        } catch (Exception e){

        }

    }
}