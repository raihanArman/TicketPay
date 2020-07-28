package id.co.myproject.ticketpay.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.myproject.ticketpay.FilmDetailActivity;
import id.co.myproject.ticketpay.Model.Film;
import id.co.myproject.ticketpay.R;

public class FilmCategoryAdapter extends RecyclerView.Adapter<FilmCategoryViewHolder> {

    List<Film> filmList;
    Context context;

    public FilmCategoryAdapter(List<Film> filmList, Context context) {
        this.filmList = filmList;
        this.context = context;
    }

    @Override
    public FilmCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item_layout, parent, false);
        return new FilmCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmCategoryViewHolder holder, final int position) {
        Picasso.with(context).load(filmList.get(position).getGambar()).into(holder.img_film);
        holder.txt_title_film.setText(filmList.get(position).getTitle());
        holder.txt_duration_film.setText(filmList.get(position).getDurasi());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FilmDetailActivity.class);
                intent.putExtra("FilmId", filmList.get(position).getId_film());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }
}
