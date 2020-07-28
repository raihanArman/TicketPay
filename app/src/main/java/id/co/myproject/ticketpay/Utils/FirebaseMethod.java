package id.co.myproject.ticketpay.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseMethod {
    Context context;
    private static final String TAG = "FirebaseMethod";

    public FirebaseMethod(Context context) {
        this.context = context;
    }

    public ArrayList<String> getSeats(String id_jam_tayang){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        final ArrayList<String> seatList = new ArrayList<>();

        Query query = reference.child("Reservasi").orderByChild("id_jam_tayang").equalTo(id_jam_tayang);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                    String seatId = objectMap.get("seats").toString();
                    seatList.add(seatId);
                }
                Log.d(TAG, "onDataChange: "+seatList.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return seatList;
    }
}
