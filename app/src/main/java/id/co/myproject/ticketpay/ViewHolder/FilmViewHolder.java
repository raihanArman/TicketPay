package id.co.myproject.ticketpay.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.myproject.ticketpay.Interface.ItemClickListener;
import id.co.myproject.ticketpay.R;

public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView titleFilm;
    public ImageView imageFilm;
    private ItemClickListener itemClickListener;
    public FilmViewHolder(@NonNull View itemView) {
        super(itemView);
        titleFilm = itemView.findViewById(R.id.title_film);
        imageFilm = itemView.findViewById(R.id.image_film);
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
