package id.co.myproject.ticketpay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.co.myproject.ticketpay.Model.Reservasi;
import id.co.myproject.ticketpay.Model.Ticket;
import id.co.myproject.ticketpay.Model.Times;
import id.co.myproject.ticketpay.Utils.Common;
import id.co.myproject.ticketpay.Utils.FirebaseMethod;

public class ConfirmationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String id_screen;
    ArrayList<String> seats;
    String allIDs = "";
    private int countTicket;
    private String price, imgFilm, screen, titleFilm;
    private Times times;
    private FirebaseMethod db;
    private Ticket ticket;
    public static Boolean back = false;
    FirebaseAuth auth;

    Button btn_bayar, btn_ubah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        id_screen = getIntent().getStringExtra("ScreeningIdConfirmation");
        seats = getIntent().getExtras().getStringArrayList("seatIDList");
        price = getIntent().getStringExtra("TotalPrice");
        db = new FirebaseMethod(this);
        auth = FirebaseAuth.getInstance();
        countTicket = seats.size();
        btn_bayar = findViewById(R.id.btn_bayar);
        final TextView filmTitle = findViewById(R.id.titleFilmConfirmation);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference();
        Query query = reference.child("Film").child(Common.common_film).orderByValue();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (HashMap<String, Object>) dataSnapshot.getValue();
                filmTitle.setText(objectMap.get("title").toString());
                imgFilm = objectMap.get("gambar").toString();
                titleFilm = objectMap.get("title").toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        for (String id : seats){
            String sampleString = id;
            String[] stringArray = sampleString.split(",");
            int[] intArray = new int[stringArray.length];
            for (int i = 0; i<stringArray.length; i++){
                String numberAsString = stringArray[i];
                intArray[i] = Integer.parseInt(numberAsString);
            }

            if(allIDs.contains("Baris : " + (intArray[0] + 1 )+ " Nomor: " + (intArray[1] + 1))){
                allIDs="";
            }
            allIDs += "Baris : " + (intArray[0] + 1 )+ " Nomor: " + (intArray[1] + 1) + "\n";
        }
        TextView textView = (TextView) findViewById(R.id.giveSeats);
        textView.setText(allIDs);

        TextView textViewCountTickets = (TextView) findViewById(R.id.ticketAmount);
        textViewCountTickets.setText("Banyak Tiket: " + countTicket);

        TextView textViewPrice = (TextView) findViewById(R.id.price);
        textViewPrice.setText(" " + price);

        final TextView textViewDate = (TextView) findViewById(R.id.dateConfirmation);

        Query query1 = reference.child("Mall").child(Common.common_mall).child("films").child(Common.common_film).child("jam_tayang").child(id_screen).orderByValue();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (HashMap<String, Object>) dataSnapshot.getValue();
                textViewDate.setText(objectMap.get("tanggal").toString()+", "+objectMap.get("jam").toString());
                screen = objectMap.get("tanggal").toString()+", "+objectMap.get("jam").toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference referenceBayar = database.getReference("Reservasi");

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = auth.getCurrentUser().getUid();
                String reservasi_number;
                for (int i=0; i<seats.size(); i++) {
                    reservasi_number = String.valueOf(System.currentTimeMillis());
                    Reservasi reservasi = new Reservasi(
                            id_screen,
                            seats.get(i),
                            user,
                            Common.common_film,
                            Common.common_mall
                    );
                    referenceBayar.child(reservasi_number).setValue(reservasi);
                }

                String ticket_number = String.valueOf(System.currentTimeMillis());
                Ticket ticket = new Ticket(
                        ticket_number,
                        seats,
                        screen,
                        titleFilm,
                        imgFilm,
                        price,
                        auth.getCurrentUser().getUid()
                );
                reference.child("Ticket").child(ticket_number).setValue(ticket);

                Intent intent = new Intent(ConfirmationActivity.this, TicketActivity.class);
                intent.putExtra("id_tiket", ticket_number);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
