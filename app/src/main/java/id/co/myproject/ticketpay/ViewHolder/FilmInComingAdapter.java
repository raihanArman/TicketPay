package id.co.myproject.ticketpay.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.myproject.ticketpay.FilmDetailActivity;
import id.co.myproject.ticketpay.Interface.ItemClickListener;
import id.co.myproject.ticketpay.Model.Film;
import id.co.myproject.ticketpay.R;

public class FilmInComingAdapter extends RecyclerView.Adapter<FilmInComingAdapter.ViewHolder> {

    List<Film> filmList;
    Context context;

    public FilmInComingAdapter(List<Film> filmList, Context context) {
        this.filmList= filmList;
        this.context = context;
    }

    @NonNull
    @Override
    public FilmInComingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_layout, parent, false);
        return new FilmInComingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmInComingAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load(filmList.get(position).getGambar()).into(holder.img_film_image);
        holder.tv_film_title.setText(filmList.get(position).getTitle());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context , FilmDetailActivity.class);
                intent.putExtra("FilmId", filmList.get(position).getId_film());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_film_image;
        TextView tv_film_title;
        private ItemClickListener itemClickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            img_film_image = itemView.findViewById(R.id.image_film);
            tv_film_title = itemView.findViewById(R.id.title_film);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }

}
