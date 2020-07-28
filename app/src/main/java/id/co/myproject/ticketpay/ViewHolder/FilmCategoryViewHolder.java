package id.co.myproject.ticketpay.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.myproject.ticketpay.R;

public class FilmCategoryViewHolder extends RecyclerView.ViewHolder {
    public ImageView img_film;
    public TextView txt_title_film;
    public TextView txt_duration_film;
    public FilmCategoryViewHolder(View itemView) {
        super(itemView);
        img_film = itemView.findViewById(R.id.img_film);
        txt_title_film = itemView.findViewById(R.id.title_film);
        txt_duration_film = itemView.findViewById(R.id.duration_film);
    }
}
