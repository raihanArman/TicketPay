package id.co.myproject.ticketpay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.myproject.ticketpay.Interface.ItemClickListener;
import id.co.myproject.ticketpay.Model.Film;
import id.co.myproject.ticketpay.Model.HomeModel;
import id.co.myproject.ticketpay.ViewHolder.FilmViewHolder;
import id.co.myproject.ticketpay.ViewHolder.HomeAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FirebaseDatabase firebaseDatabase;
    DatabaseReference film;
    RecyclerView rv_home;
    List<Film> filmAtTeaterList, filmInComingList;
    HomeAdapter adapter;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView iv_akun, iv_notif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iv_akun = findViewById(R.id.iv_akun);
        iv_notif = findViewById(R.id.iv_notif);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        rv_home = findViewById(R.id.rv_home);
        filmAtTeaterList = new ArrayList<>();
        filmInComingList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final List<HomeModel> homeModelList = new ArrayList<>();
        film = firebaseDatabase.getReference("Film");


        if(auth.getCurrentUser() == null || !user.isEmailVerified()){
            iv_notif.setVisibility(View.INVISIBLE);
        }else {
            iv_notif.setVisibility(View.VISIBLE);
        }


        iv_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
            }
        });

        Query query = film.orderByChild("status").equalTo("sedang tayang");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Film film = new Film();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                    film.setGambar(objectMap.get("gambar").toString());
                    film.setTitle(objectMap.get("title").toString());
                    film.setId_film(objectMap.get("id_film").toString());
                    Log.d(TAG, "onDataChange: id film "+film.getId_film());
                    filmAtTeaterList.add(film);
                }
                Log.d(TAG, "onDataChange: size at teater "+filmAtTeaterList.size());
                Query query1 = film.orderByChild("status").equalTo("akan datang");
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            Film film = new Film();
                            Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                            film.setGambar(objectMap.get("gambar").toString());
                            film.setTitle(objectMap.get("title").toString());
                            film.setId_film(objectMap.get("id_film").toString());
                            filmInComingList.add(film);
                        }

                        homeModelList.add(new HomeModel(0, filmAtTeaterList));
                        homeModelList.add(new HomeModel(1, filmInComingList));
                        adapter = new HomeAdapter(homeModelList, MainActivity.this);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv_home.setLayoutManager(layoutManager);
                        rv_home.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//        sedangTayangRecyclerView = findViewById(R.id.recyclerview_sedangTayang);
//        akanDatangRecyclerView = findViewById(R.id.recyclerview_akanDatang);
//
//
//        FirebaseRecyclerOptions<Film> options = new FirebaseRecyclerOptions.Builder<Film>()
//                .setQuery(film.orderByChild("status").equalTo("sedang tayang"), Film.class)
//                .build();
//        adapter = new FirebaseRecyclerAdapter<Film, FilmViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull FilmViewHolder holder, int position, @NonNull Film model) {
//                holder.titleFilm.setText(model.getTitle());
//                Picasso.with(getBaseContext()).load(model.getGambar()).into(holder.imageFilm);
//                final Film clickItem = model;
//                holder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//                        Intent intent = new Intent(MainActivity.this, FilmDetailActivity.class);
//                        intent.putExtra("FilmId", adapter.getRef(position).getKey());
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_layout, viewGroup, false);
//                return new FilmViewHolder(itemView);
//            }
//
//
//        };
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        sedangTayangRecyclerView.setLayoutManager(layoutManager);
//        adapter.startListening();
//        sedangTayangRecyclerView.setAdapter(adapter);
//        sedangTayangRecyclerView.getAdapter().notifyDataSetChanged();
//
//
//        FirebaseRecyclerOptions<Film> options2 = new FirebaseRecyclerOptions.Builder<Film>()
//                .setQuery(film.orderByChild("status").equalTo("akan datang"), Film.class)
//                .build();
//        adapter2 = new FirebaseRecyclerAdapter<Film, FilmViewHolder>(options2) {
//            @Override
//            protected void onBindViewHolder(@NonNull FilmViewHolder holder, int position, @NonNull Film model) {
//                holder.titleFilm.setText(model.getTitle());
//                Picasso.with(getBaseContext()).load(model.getGambar()).into(holder.imageFilm);
//                final Film clickItem = model;
//                holder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean isLongClick) {
//                        Intent intent = new Intent(MainActivity.this, FilmDetailActivity.class);
//                        intent.putExtra("FilmId", adapter2.getRef(position).getKey());
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_layout, viewGroup, false);
//                return new FilmViewHolder(itemView);
//            }
//
//        };
//
//
//        akanDatangRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        adapter2.startListening();
//        akanDatangRecyclerView.setAdapter(adapter2);
//        akanDatangRecyclerView.getAdapter().notifyDataSetChanged();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//        adapter2.stopListening();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (adapter != null)
//            adapter.startListening();
//
//        if (adapter2 != null)
//            adapter2.startListening();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (adapter != null)
//            adapter.startListening();
//
//        if (adapter2 != null)
//            adapter2.startListening();
//    }
    }
}
