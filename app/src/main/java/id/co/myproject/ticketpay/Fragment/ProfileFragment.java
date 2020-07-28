package id.co.myproject.ticketpay.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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

import id.co.myproject.ticketpay.Model.Ticket;
import id.co.myproject.ticketpay.R;
import id.co.myproject.ticketpay.ViewHolder.TicketViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    List<Ticket> ticketList;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    TextView txt_fullname, txt_email;
    TicketViewHolder adapter;
    TextView txt_belum_pesan;
    ImageView iv_options;
    FrameLayout frameLayout;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.tiketRecycler);
        txt_fullname = view.findViewById(R.id.username);
        txt_email = view.findViewById(R.id.email);
        txt_belum_pesan = view.findViewById(R.id.txt_belum_pesan);
        iv_options = view.findViewById(R.id.options);
        frameLayout = getActivity().findViewById(R.id.frame);
        ticketList = new ArrayList<>();
        getDataProfile();
        getDataTicket();

        iv_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SettingsProfileFragment());
            }
        });

        return view;
    }

    private void getDataProfile() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("User").child(auth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Map<String, Object> objectMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    txt_fullname.setText(objectMap.get("username").toString());
                    txt_email.setText(objectMap.get("email").toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDataTicket(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Ticket").orderByChild("id_user").equalTo(auth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                    Ticket ticket = new Ticket();
                    ticket.setId_tiket(objectMap.get("id_tiket").toString());
                    ticket.setFilmTitle(objectMap.get("filmTitle").toString());
                    ticket.setImgFilm(objectMap.get("imgFilm").toString());
                    ticket.setScreening(objectMap.get("screening").toString());
                    ticketList.add(ticket);
                }
                getTampil();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void getTampil() {
        adapter = new TicketViewHolder(ticketList, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
