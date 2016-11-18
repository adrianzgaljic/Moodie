package adrian.com.moodie.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adrian.com.moodie.objects.Movie;
import adrian.com.moodie.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieList = new ArrayList<>();




    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imageView = (ImageView) itemLayoutView.findViewById(R.id.image_view_poster);
            textView = (TextView) itemLayoutView.findViewById(R.id.tv_movie_title);
        }
    }



    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setMovies(List<Movie> newMovieList){
        movieList.clear();
        movieList.addAll(newMovieList);
        Log.d("tag", "m=swapped"+newMovieList.size());
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try{
            holder.imageView.setImageBitmap(movieList.get(position).getImage());
            holder.textView.setText(movieList.get(position).getName());
        } catch (Exception e){

        }


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}