package id.co.myproject.ticketpay.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

import id.co.myproject.ticketpay.FilmDetailActivity;
import id.co.myproject.ticketpay.Model.Film;
import id.co.myproject.ticketpay.ViewHolder.FilmCategoryAdapter;
import id.co.myproject.ticketpay.ViewHolder.FilmCategoryViewHolder;
import id.co.myproject.ticketpay.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AtTeaterFilmFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
//    FirebaseRecyclerAdapter<Film, FilmCategoryViewHolder> adapter;
    FilmCategoryAdapter adapter;
    List<Film> filmList;

    public AtTeaterFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incoming_film, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Film");

        filmList = new ArrayList<>();
        Query query = reference.orderByChild("status").equalTo("sedang tayang");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Film film = new Film();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                    film.setGambar(objectMap.get("gambar").toString());
                    film.setTitle(objectMap.get("title").toString());
                    film.setDurasi(objectMap.get("durasi").toString());
                    film.setId_film(objectMap.get("id_film").toString());
                    filmList.add(film);
                }
                adapter = new FilmCategoryAdapter(filmList, getActivity());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        FirebaseRecyclerOptions<Film> options = new FirebaseRecyclerOptions.Builder<Film>()
//                .setQuery(reference.orderByChild("status").equalTo("sedang tayang"), Film.class)
//                .build();
//        adapter = new FirebaseRecyclerAdapter<Film, FilmCategoryViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull FilmCategoryViewHolder holder, final int position, @NonNull Film model) {
//                holder.txt_title_film.setText(model.getTitle());
//                holder.txt_duration_film.setText(model.getDurasi());
//                Picasso.with(getActivity()).load(model.getGambar()).into(holder.img_film);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
//                        intent.putExtra("FilmId", adapter.getRef(position).getKey());
//                        startActivity(intent);
//                    }
//                });
//            }
//
//            @Override
//            public FilmCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item_layout, parent, false);
//                return new FilmCategoryViewHolder(itemView);
//
//            }
//        };
        return view;
    }

}
