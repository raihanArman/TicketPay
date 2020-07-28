package id.co.myproject.ticketpay;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

import id.co.myproject.ticketpay.Model.Screening;
import id.co.myproject.ticketpay.Model.Times;
import id.co.myproject.ticketpay.Utils.Common;
import id.co.myproject.ticketpay.ViewHolder.FilmTimesAdapter;

public class ScreeningActivity extends AppCompatActivity {

    List<Times> timesList;
    FilmTimesAdapter adapter;
    static RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening);
        timesList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_times);
        String id_mall = getIntent().getStringExtra("id_mall");
        String id_film = getIntent().getStringExtra("id_film");

        Common.common_mall = id_mall;
        Common.common_film = id_film;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Mall").child(id_mall).child("films").child(id_film).child("jam_tayang").orderByValue();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Times times = new Times();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                    times.setJam(objectMap.get("jam").toString());
                    times.setTanggal(objectMap.get("tanggal").toString());
                    times.setSelected(false);
                    times.setId_screening(singleSnapshot.getKey());
                    timesList.add(times);
                    timesList.get(0).setSelected(true);
                }
                adapter = new FilmTimesAdapter(ScreeningActivity.this,timesList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ScreeningActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public static void refreshItem(int deselect, int select){
        recyclerView.getAdapter().notifyItemChanged(deselect);
        recyclerView.getAdapter().notifyItemChanged(select);
    }
}
