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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.myproject.ticketpay.Model.MallModel;
import id.co.myproject.ticketpay.Model.Times;
import id.co.myproject.ticketpay.R;
import id.co.myproject.ticketpay.ScreeningActivity;
import id.co.myproject.ticketpay.Utils.Common;

public class MallScreeningAdapter extends RecyclerView.Adapter<MallViewHolder> {

    List<MallModel> mallModelList = new ArrayList<>();
    List<Times> timesList = new ArrayList<>();
    Context context;
    static FilmTimesAdapter adapter;

    public MallScreeningAdapter(List<MallModel> mallModelList, Context context) {
        this.mallModelList = mallModelList;
        this.context = context;
    }

    @Override
    public MallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_detail_date, parent, false);
        return new MallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MallViewHolder holder, final int position) {
        holder.tv_mall_name.setText(mallModelList.get(position).getNama());
        holder.tv_mall_address.setText(mallModelList.get(position).getAlamat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScreeningActivity.class);
                intent.putExtra("id_mall", mallModelList.get(position).getIdMall());
                intent.putExtra("id_film", mallModelList.get(position).getIdFilm());
                context.startActivity(intent);
            }
        });

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        Query query = reference.child("Mall").child(mallModelList.get(position).getIdMall()).child("films").child(mallModelList.get(position).getIdFilm()).child("jam_tayang").orderByValue();
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                    Times times = new Times();
//                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
//                    times.setJam(objectMap.get("jam").toString());
//                    times.setSelected(false);
//                    timesList.add(times);
//                    timesList.get(0).setSelected(true);
//                }
//                adapter = new FilmTimesAdapter(timesList);
//                holder.rv_times.setLayoutManager(new GridLayoutManager(context, 3));
//                holder.rv_times.setAdapter(adapter);
//                holder.rv_times.getAdapter().notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mallModelList.size();
    }

}
