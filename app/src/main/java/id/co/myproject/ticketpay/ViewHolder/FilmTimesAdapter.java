package id.co.myproject.ticketpay.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.co.myproject.ticketpay.Model.Times;
import id.co.myproject.ticketpay.R;
import id.co.myproject.ticketpay.SeatSelectionActivity;
import id.co.myproject.ticketpay.Utils.Common;

import static id.co.myproject.ticketpay.ScreeningActivity.refreshItem;

public class FilmTimesAdapter extends RecyclerView.Adapter<FilmTimesAdapter.ViewHolder> {
    List<Times> timesList;
    private int preSelectedPosition = -1;
    Context context;

    public FilmTimesAdapter(Context context,List<Times> timesList)
    {
        this.context = context;
        this.timesList = timesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screening_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txt_film_times.setText(timesList.get(position).getTanggal()+", "+timesList.get(position).getJam());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeatSelectionActivity.class);
                intent.putExtra("id_jam_tayang", timesList.get(position).getId_screening());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return timesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_film_times;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_film_times = itemView.findViewById(R.id.txt_film_times);
        }

    }
}
