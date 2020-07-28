package id.co.myproject.ticketpay.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.myproject.ticketpay.Model.Reservasi;
import id.co.myproject.ticketpay.Model.Ticket;
import id.co.myproject.ticketpay.R;
import id.co.myproject.ticketpay.TicketActivity;

public class TicketViewHolder extends RecyclerView.Adapter<TicketViewHolder.ViewHolder> {

    List<Ticket> ticketList;
    Context context;

    public TicketViewHolder(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    @Override
    public TicketViewHolder.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder.ViewHolder holder, final int position) {
        holder.txt_title.setText(ticketList.get(position).getFilmTitle());
        holder.txt_screen.setText(ticketList.get(position).getScreening());
        Picasso.with(context).load(ticketList.get(position).getImgFilm()).into(holder.imageFilm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TicketActivity.class);
                intent.putExtra("id_tiket", ticketList.get(position).getId_tiket());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageFilm;
        TextView txt_title, txt_screen;
        public ViewHolder(View itemView) {
            super(itemView);
            imageFilm = itemView.findViewById(R.id.img_film);
            txt_title = itemView.findViewById(R.id.title_film);
            txt_screen = itemView.findViewById(R.id.screening_film);
        }
    }
}
