package adrian.com.moodie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adrian.com.moodie.adapters.MovieAdapter;
import adrian.com.moodie.api_calls.ApiCall;
import adrian.com.moodie.api_calls.GraphApiCall;
import adrian.com.moodie.api_calls.MovieImageUrlApiCall;
import adrian.com.moodie.api_calls.MovieListApiCall;
import adrian.com.moodie.api_calls.WeatherApiCall;
import adrian.com.moodie.listeners.HttpResponseListener;
import adrian.com.moodie.moods.MoodCalculator;
import adrian.com.moodie.objects.Movie;
import adrian.com.moodie.objects.Weather;
import adrian.com.moodie.realm_objects.MovieRealm;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements HttpResponseListener {

    private MovieAdapter movieAdapter;
    private List<Movie> movieList = new ArrayList<>();
    private CallbackManager callbackManager;
    private  AccessToken accessToken;
    private Weather weather = null;
    private Realm realm;

    @BindView(R.id.facebook_layout)
    LinearLayout facebookLayout;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.movies_recycler_view)
    RecyclerView movieRecyclerView;
    @BindView(R.id.iv_mood)
    ImageView ivMood;
    @BindView(R.id.tv_mood)
    TextView tvMood;
    @BindView(R.id.tv_happy_count)
    TextView tvHappy;
    @BindView(R.id.tv_haha_count)
    TextView tvHaha;
    @BindView(R.id.tv_inlove_count)
    TextView tvInLove;
    @BindView(R.id.tv_wow_count)
    TextView tvWow;
    @BindView(R.id.tv_sad_count)
    TextView tvSad;
    @BindView(R.id.tv_angry_count)
    TextView tvAngry;
    @BindView(R.id.tv_weather)
    TextView tvWeather;
    @BindView(R.id.iv_weather_icon)
    ImageView ivWeatherIcon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRealm();

        accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email", "user_posts", "public_profile", "user_likes"));
            }
        });

        if (accessToken!=null && !accessToken.isExpired()){
            graphApliCall();
        }

        RecyclerView.LayoutManager movieLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        movieRecyclerView.setLayoutManager(movieLayoutManager);

        movieAdapter = new MovieAdapter();
        movieRecyclerView.setAdapter(movieAdapter);

        WeatherApiCall weatherApiCall = new WeatherApiCall(UrlCreator.getWeatherForCity(getString(R.string.current_city)), this);
        weatherApiCall.execute();

    }


    @Override
    public void onMovieIDsResult(Map<Integer, Movie> movieIdTitle) {

        Set<Integer> movieIDs = movieIdTitle.keySet();
        Log.d("PROGRESS", "movie ids and titles ready");

        for (Integer movieID: movieIDs){
            Log.d("tag", "realm movie: "+movieIdTitle.get(movieID).getGenre());
            String movieTitle = movieIdTitle.get(movieID).getName();
            MovieImageUrlApiCall movieImageUrlApiCall = new MovieImageUrlApiCall(UrlCreator.getMovieImageUlr(this, movieID), movieTitle, movieIdTitle.get(movieID).getGenre(), this);
            movieImageUrlApiCall.execute();

        }
    }

    @Override
    public void onMovieImageUrlResult(Movie movie) {
        Log.d("PROGRESS", "movie url ready "+movie.getName());
        try {
            ApiCall.MovieImageDownloader imageDownloader = new ApiCall.MovieImageDownloader(movie, this);
            imageDownloader.execute();
            movieList.add(movie);
            movieAdapter.setMovies(movieList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMoveImageBitmapResult(Movie movie) {
        Log.d("PROGRESS", "bitmap image ready");
        realm.beginTransaction();
        MovieRealm m = realm.createObject(MovieRealm.class);
        m.setGenre(movie.getGenre());
        m.setMovieTitle(movie.getName());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        movie.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();
        m.setPoster(image);
        realm.commitTransaction();
        movieAdapter.setMovies(movieList);

    }

    @Override
    public void onWeatherResult(Weather weather) {
        Log.d("PROGRESS", "weather ready "+weather);
        this.weather = weather;
    }

    @Override
    public void onFacebookReactionsResult(Map<String, Integer> reactions) {
        Log.d("PROGRESS", "facebook reactions ready "+reactions);
        Log.d("tag", "fb resuts = "+reactions+ "  "+reactions.containsKey("LOVE") + reactions.get("LOVE"));
        if (reactions.containsKey("LIKE")){
            tvHappy.setText(Integer.toString(reactions.get("LIKE")));
        }
        if (reactions.containsKey("HAHA")){
            tvHaha.setText(Integer.toString(reactions.get("HAHA")));        }
        if (reactions.containsKey("LOVE")){
            tvInLove.setText(Integer.toString(reactions.get("LOVE")));
        }
        if (reactions.containsKey("WOW")){
            tvWow.setText(Integer.toString(reactions.get("WOW")));
        }
        if (reactions.containsKey("SAD")){
            tvSad.setText(Integer.toString(reactions.get("SAD")));
        }
        if (reactions.containsKey("ANGRY")){
            tvAngry.setText(Integer.toString(reactions.get("ANGRY")));
        }


        while (weather==null){
            Log.d("PROGRESS", "waiting for weather to be ready ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int weatherCode = weather.getDescriptionCode();
        Log.d("tag", "weather code="+weatherCode);

        if (weatherCode<13){
            ivWeatherIcon.setImageResource(R.drawable.weather_rain);
            tvWeather.setText("Rainy");
        } else if ((weatherCode>12 && weatherCode<17)||(weatherCode>41 && weatherCode<44)){
            ivWeatherIcon.setImageResource(R.drawable.weather_snow);
            tvWeather.setText("Snow");
        } else if ((weatherCode>16 && weatherCode<31) || (weatherCode>36 && weatherCode<41) || weatherCode>43){
            ivWeatherIcon.setImageResource(R.drawable.weather_cloudy);
            tvWeather.setText("Cloudy");
        } else if (weatherCode>30 && weatherCode<35){
            ivWeatherIcon.setImageResource(R.drawable.weather_sunny);
            tvWeather.setText("Sunny");
        }

        MoodCalculator moodCalculator = new MoodCalculator();
        String mood = moodCalculator.getMood(weather.getDescriptionCode(), reactions);
        tvMood.setText(mood);

        int genreCode = 0;
        switch (mood){
            case "HAPPY":
                ivMood.setImageResource(R.drawable.happy);
                genreCode = 35;
                break;
            case "SAD":
                ivMood.setImageResource(R.drawable.sad);
                genreCode = 18;
                break;
            case "INLOVE":
                ivMood.setImageResource(R.drawable.inlove);
                genreCode = 10749;
                break;
            case "EXCITED":
                ivMood.setImageResource(R.drawable.excited);
                genreCode = 28;
                break;

        }

        RealmResults<MovieRealm> movies = realm.where(MovieRealm.class).equalTo("genre",genreCode).findAll();

        if (movies.size()!=0){
            for (MovieRealm movieRealm:movies){
                Movie movie = new Movie(movieRealm.getMovieTitle());
                movie.setGenre(movieRealm.getGenre());
                movie.setImage(BitmapFactory.decodeByteArray(movieRealm.getPoster(), 0, movieRealm.getPoster().length));
                movieList.add(movie);
                movieAdapter.setMovies(movieList);

            }
        } else {
            MovieListApiCall movieListApiCall = new MovieListApiCall(UrlCreator.getListOfMOviesUrl(this,genreCode), genreCode, this);
            movieListApiCall.execute();
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        accessToken = AccessToken.getCurrentAccessToken();
        graphApliCall();



        if (requestCode == 1 && resultCode == RESULT_OK) {
        }
        if(callbackManager.onActivityResult(requestCode, resultCode, data)) {
        }

    }

    private void initRealm(){
        Realm.init(this);
        final RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        // Realm.deleteRealm(realmConfiguration);
        realm = Realm.getInstance(realmConfiguration);
    }

    public void graphApliCall(){
        facebookLayout.setVisibility(View.GONE);
        GraphApiCall graphApiCall = new GraphApiCall();
        graphApiCall.execute(accessToken, this);
    }

}
