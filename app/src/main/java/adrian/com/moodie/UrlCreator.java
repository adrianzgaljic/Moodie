package adrian.com.moodie;

import android.content.Context;
import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by adrianzgaljic on 12/11/16.
 */

public class UrlCreator {

    public static String getListOfMOviesUrl(Context context, int genre){

        return  "https://api.themoviedb.org/3/discover/movie?api_key=" +
                context.getString(R.string.themoviedb_api_key) +
                "&language=en-US&sort_by=popularity.desc&page=1&with_genres=" +
                Integer.toString(genre);
    }

    public static  String getMovieImageUlr(Context context, int movieID){

        return "https://api.themoviedb.org/3/movie/" +
                Integer.toString(movieID) +
                "/images?api_key=" +
                context.getString(R.string.themoviedb_api_key);

    }

    public static String getWeatherForCity(String city){


        try{
            String query = "select item.condition from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+ city+", tx\")";
            return   "https://query.yahooapis.com/v1/public/yql?q="+
                    URLEncoder.encode(query, "UTF-8").replace("+", "%20").replace("%28","(").replace("%29",")")+
                    "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys\n";


        } catch (Exception e){
            Log.e("TAG", e.toString());
        }

        return  "";
    }
}
