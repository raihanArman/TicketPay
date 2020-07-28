package id.co.myproject.ticketpay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import id.co.myproject.ticketpay.Model.MallModel;
import id.co.myproject.ticketpay.Model.Seat;
import id.co.myproject.ticketpay.Utils.FirebaseMethod;

public class SeatSelectionActivity extends AppCompatActivity {

    String id_screening;

    private static final int ROW = 15;
    private static final int COL = 15;
    private ArrayList<Seat> seats;
    private ArrayList<String> seatIds = new ArrayList<>();
    private FirebaseMethod filmDatabase;
    private MallModel room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        filmDatabase = new FirebaseMethod(this);
        id_screening = getIntent().getStringExtra("id_jam_tayang");
        createTable();
        Button button = (Button) findViewById(R.id.reserveerButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seats = room.getSeats();

                for (int i = 0; i < seats.size(); i++){
///
//                    System.out.println(i);
//                    Log.d(TAG, "onClick: -----------insert into data reservation seat " + showId + " " + seats.get(i).getSeatID());
//                    filmDatabase.insertDataToReservation(showId, seats.get(i).getSeatID());
                    seatIds.add(seats.get(i).getSeatID());
                }

                if (seats.size() == 0) {
                    Toast.makeText(view.getContext(), "No seat selected", Toast.LENGTH_SHORT).show();
                } else {

                    Intent itemDetailIntent = new Intent(view.getContext().getApplicationContext(), ConfirmationActivity.class);
                    itemDetailIntent.putExtra("ScreeningIdConfirmation", id_screening);
                    itemDetailIntent.putStringArrayListExtra("seatIDList", seatIds);
                    itemDetailIntent.putExtra("TotalPrice", Double.toString(room.calculate(seats)));
                    view.getContext().startActivity(itemDetailIntent);
                }
            }
        });
    }

    private void createTable() {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.TableSeats);
        final TextView titleTextView = (TextView) findViewById(R.id.textView);
        room = new MallModel(this,tableLayout, titleTextView, ROW, COL, filmDatabase.getSeats(id_screening));

    }
}
