package id.co.myproject.ticketpay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
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

import id.co.myproject.ticketpay.Model.Film;
import id.co.myproject.ticketpay.Model.MallModel;
import id.co.myproject.ticketpay.ViewHolder.FilmViewHolder;
import id.co.myproject.ticketpay.ViewHolder.MallScreeningAdapter;
import id.co.myproject.ticketpay.ViewHolder.MallViewHolder;

public class FilmDetailActivity extends AppCompatActivity {

    private static final String TAG = "FilmDetailActivity";
    String film_id;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference film, mall;
    Film filmModel;
    TextView txt_film_description;
    ImageView iv_film;
    RecyclerView rv_screening;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FirebaseRecyclerAdapter<MallModel, MallViewHolder> adapter;
    List<String> mall_list_id;
    List<MallModel> mall_list;
    MallScreeningAdapter mallScreeningAdapter ;
    FirebaseAuth auth;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        iv_film = findViewById(R.id.img_film);
        txt_film_description = findViewById(R.id.txt_film_description);
        rv_screening = findViewById(R.id.rv_screening);
        mall_list_id = new ArrayList<>();
        mall_list = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        login_btn = findViewById(R.id.login);

        filmModel = new Film();

        firebaseDatabase = FirebaseDatabase.getInstance();
        film = firebaseDatabase.getReference("Film");
        mall = firebaseDatabase.getReference("Mall");

        film_id = getIntent().getStringExtra("FilmId");

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);



        film.child(film_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                filmModel = dataSnapshot.getValue(Film.class);
                Picasso.with(getBaseContext()).load(filmModel.getGambar()).into(iv_film);
                collapsingToolbarLayout.setTitle(filmModel.getTitle());
                txt_film_description.setText(filmModel.getDeskripsi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FilmDetailActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (auth.getCurrentUser() == null){
            rv_screening.setVisibility(View.INVISIBLE);
            login_btn.setVisibility(View.VISIBLE);
            login_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(FilmDetailActivity.this, AccountActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            rv_screening.setVisibility(View.VISIBLE);
            login_btn.setVisibility(View.INVISIBLE);
            getData();
        }
////
////        FirebaseRecyclerOptions<MallModel> options = new FirebaseRecyclerOptions.Builder<MallModel>()
////                .setQuery(film.child(film_id), MallModel.class)
////                .build();
////        adapter = new FirebaseRecyclerAdapter<MallModel, MallViewHolder>(options) {
////            @Override
////            protected void onBindViewHolder(@NonNull MallViewHolder holder, int position, @NonNull MallModel model) {
////                holder.tv_mall_name.setText(model.getNama());
////                holder.tv_mall_address.setText(model.getAlamat());
////            }
////
////            @Override
////            public MallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_detail_date, parent, false);
////                return new MallViewHolder(view);
////            }
////        };
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rv_screening.setLayoutManager(layoutManager);
//        rv_screening.setAdapter(adapter);
//        rv_screening.getAdapter().notifyDataSetChanged();
    }

    private void getData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Film").child(film_id).child("malls");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user "+singleSnapshot.child("mall_id").getValue());
                    mall_list_id.add(singleSnapshot.child("mall_id").getValue().toString());
                }
                getDataMall();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getDataMall() {
        Log.d(TAG, "onDataChange: found banyak "+mall_list_id.size());
        for (int i=0; i<mall_list_id.size(); i++){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child("Mall").orderByKey().equalTo(mall_list_id.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                        MallModel mallModel = new MallModel();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                        mallModel.setNama(objectMap.get("nama").toString());
                        mallModel.setAlamat(objectMap.get("alamat").toString());
                        mallModel.setIdFilm(film_id);
                        mallModel.setIdMall(singleSnapshot.getKey());
                        mall_list.add(mallModel);
                        Log.d(TAG, "onDataChange: tampil "+mallModel.getNama());
                    }
                    getTampil();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void getTampil() {
        mallScreeningAdapter = new MallScreeningAdapter(mall_list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_screening.setLayoutManager(layoutManager);
        rv_screening.setAdapter(mallScreeningAdapter);
        rv_screening.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adapter != null)
            adapter.startListening();
    }
}
