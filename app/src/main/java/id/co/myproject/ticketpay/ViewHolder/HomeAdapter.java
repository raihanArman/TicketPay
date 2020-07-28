package id.co.myproject.ticketpay.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

import id.co.myproject.ticketpay.FilmCategoryActivity;
import id.co.myproject.ticketpay.Model.Film;
import id.co.myproject.ticketpay.Model.HomeModel;
import id.co.myproject.ticketpay.R;

public class HomeAdapter extends RecyclerView.Adapter{

    private List<HomeModel> homeModelList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference film;
    Context context;
    String film_id;

    public HomeAdapter(List<HomeModel> homeModelList, Context context) {
        this.homeModelList = homeModelList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homeModelList.get(position).getType()){
            case 0 : return HomeModel.FILM_AT_TEATER;
            case 1 : return HomeModel.FILM_INCOMING;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomeModel.FILM_AT_TEATER:
                View filmAtTeaterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_film_layout, parent, false);
                return new FilmAtTeaterViewHolder(filmAtTeaterView);
            case HomeModel.FILM_INCOMING:
                View filmInComingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_film_layout, parent, false);
                return new FilmInComingViewHolder(filmInComingView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homeModelList.get(position).getType()){
            case HomeModel.FILM_AT_TEATER:
                List<Film> filmAtTeaterList = homeModelList.get(position).getFilms();
                ((FilmAtTeaterViewHolder)holder).setFilmAtTeater(filmAtTeaterList);
                break;
            case HomeModel.FILM_INCOMING:
                List<Film> filmInComingList = homeModelList.get(position).getFilms();
                ((FilmInComingViewHolder)holder).setFilmIncoming(filmInComingList);
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }

    class FilmAtTeaterViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_film_at_teater_more;
        private RecyclerView rv_film_at_teater;
        public FilmAtTeaterViewHolder(View itemView) {
            super(itemView);
            tv_film_at_teater_more = itemView.findViewById(R.id.horizontal_film_at_teater_more);
            rv_film_at_teater = itemView.findViewById(R.id.rv_film_at_teater);
        }

        public void setFilmAtTeater(List<Film> films){
            if (films.size() > 8){
                tv_film_at_teater_more.setVisibility(View.VISIBLE);
                tv_film_at_teater_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, FilmCategoryActivity.class);
                        intent.putExtra("category", "0");
                        context.startActivity(intent);
                    }
                });
            }else {
                tv_film_at_teater_more.setVisibility(View.INVISIBLE);
            }
            FilmAtTeaterAdapter atTeaterAdapter = new FilmAtTeaterAdapter(films,context);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_film_at_teater.setLayoutManager(linearLayoutManager);
            rv_film_at_teater.setAdapter(atTeaterAdapter);
            atTeaterAdapter.notifyDataSetChanged();

        }
    }

    class FilmInComingViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_film_at_teater_more;
        private RecyclerView rv_film_in_coming;
        public FilmInComingViewHolder(View itemView) {
            super(itemView);
            tv_film_at_teater_more = itemView.findViewById(R.id.horizontal_film_incoming_more);
            rv_film_in_coming = itemView.findViewById(R.id.rv_film_in_coming);
        }

        public void setFilmIncoming(List<Film> films){
            if (films.size() > 8){
                tv_film_at_teater_more.setVisibility(View.VISIBLE);
                tv_film_at_teater_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, FilmCategoryActivity.class);
                        intent.putExtra("category", "1");
                        context.startActivity(intent);
                    }
                });
            }else {
                tv_film_at_teater_more.setVisibility(View.INVISIBLE);
            }
            FilmInComingAdapter inComingAdapter = new FilmInComingAdapter(films,context);
            rv_film_in_coming.setLayoutManager(new GridLayoutManager(context, 2));
            rv_film_in_coming.setAdapter(inComingAdapter);
            inComingAdapter.notifyDataSetChanged();

        }
    }
}
