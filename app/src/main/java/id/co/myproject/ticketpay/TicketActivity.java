package id.co.myproject.ticketpay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.co.myproject.ticketpay.Model.Ticket;

public class TicketActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView imgFilm, iv_barcode;
    TextView titleFilm, screening, seat, price;

    private static final String TAG = "TicketActivity";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TicketActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        imgFilm = findViewById(R.id.imgFilm);
        iv_barcode = findViewById(R.id.iv_barcode);
        titleFilm = findViewById(R.id.titleFilm);
        screening = findViewById(R.id.screening_film);
        seat = findViewById(R.id.seat);
        price = findViewById(R.id.price);
        String id = getIntent().getStringExtra("id_tiket");
        Log.d(TAG, "onId Ticket: "+id);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Ticket");

        Query query = reference.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (HashMap<String, Object>) dataSnapshot.getValue();
                titleFilm.setText(objectMap.get("filmTitle").toString());
                screening.setText(objectMap.get("screening").toString());
                price.setText(objectMap.get("price").toString());
                Picasso.with(TicketActivity.this).load(objectMap.get("imgFilm").toString()).into(imgFilm);

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(price.getText().toString(), BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    iv_barcode.setImageBitmap(bitmap);
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
