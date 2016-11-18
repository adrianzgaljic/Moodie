package adrian.com.moodie.api_calls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import adrian.com.moodie.listeners.ApiCallListener;
import adrian.com.moodie.listeners.HttpResponseListener;
import adrian.com.moodie.objects.Movie;

/**
 * Created by adrianzgaljic on 13/11/16.
 */

public class ApiCall extends AsyncTask<String, Object, JSONObject>

    {

        private String url;
        private ApiCallListener apiCallListener;

        public ApiCall(String url, ApiCallListener apiCallListener){
        this.url = url;
        this.apiCallListener = apiCallListener;
    }


        @Override
        protected JSONObject doInBackground(String... params) {
        Log.d("TAG", "background");

        JSONObject jsonObject = null;
        try{

            HttpURLConnection yahooHttpConn = (HttpURLConnection) (new URL(url)).openConnection();
            yahooHttpConn.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(yahooHttpConn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            jsonObject = new JSONObject(response.toString());

        } catch(Exception e){
            Log.d("tag", e.toString());
        }

        return jsonObject;
    }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
        apiCallListener.jsonObjectReady(jsonObject);
    }

        /**
         * Created by adrianzgaljic on 12/11/16.
         */

        public static class MovieImageDownloader extends AsyncTask<String, Object, Bitmap> {

            private Movie movie;
            private HttpResponseListener listener;

            public MovieImageDownloader(Movie movie, HttpResponseListener listener){
                this.movie = movie;
                this.listener = listener;
            }


            @Override
            protected Bitmap doInBackground(String[] params) {
                Log.d("TAG", "background");
                Bitmap bitmap = null;

                try{
                    bitmap = BitmapFactory.decodeStream((InputStream)new URL(movie.getUrl()).getContent());
                } catch(Exception e){
                    Log.d("tag", e.toString());
                }

                return bitmap;


            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                movie.setImage(bitmap);
                listener.onMoveImageBitmapResult(movie);
            }

        }
    }
