package id.co.myproject.ticketpay.Model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MallModel {
    String idFilm;
    String idMall;
    String alamat;
    String nama;
    String seat_baris;
    String seat_colom;
    List<FilmsModel> films;
    private ArrayList<Seat> seats;
    public MallModel(Context context, TableLayout layout, TextView textView, int ROW, int COL, ArrayList<String> bookedseats){
        if (bookedseats.size() != 0) {
            Log.d(TAG, "Room: ----------------------------------------------- " + bookedseats.get(0));
        }

        TableLayout tableLayout = layout;
        final TextView titleTextView = textView;

        seats = new ArrayList<>();

        for (int i = 0; i < ROW; i++){
            TableRow tableRow = new TableRow(context);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f
            ));
            tableLayout.addView(tableRow);
            for (int j = 0; j < COL; j++){
                final int FINAL_ROW = i;
                final int FINAL_COL = j;

                final Seat seat = new Seat(context, i, j);
                seat.setStatus(false);

                if(bookedseats.contains(seat.getSeatID())){
                    seat.setIsBooked();

                    Log.d(TAG, "Room: ------------------------------- " + seat.getSeatID());
                }

                seat.setBackground();
                seat.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT, 1.0f
                ));

                seat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        seatButtonClicked(seat, FINAL_ROW, FINAL_COL);
                        addSeat(seat);

                        System.out.println("size---------- " + seats.size());
                        System.out.println(calculate(seats));
                        String result = Double.toString(calculate(seats));
                        titleTextView.setText("Total: " + result);

                    }

                });
                tableRow.addView(seat);
            }
        }
    }


    private Boolean addSeat (Seat seat){
        if (seat.IsBooked()){
            return false;
        }

        if(!seat.getStatus()){
            seats.add(seat);
            return true;

        }else {
            seats.remove(seat);
            return false;
        }
    }

    public double calculate(ArrayList<Seat>seats){
        double price = 0;
        for(Seat seat: seats){
            price += seat.getPrice();
        }
        return price;
    }

    private void seatButtonClicked(Seat seat, int row, int col) {
//        Toast.makeText(this, "Button clicked " + row + " " + col, Toast.LENGTH_SHORT).show();
        seat.setBackground();
    }


    public ArrayList<Seat> getSeats(){
        return seats;
    }

    public MallModel() {
    }

    public MallModel(String alamat, String nama, String seat_baris, String seat_colom, List<FilmsModel> films) {
        this.alamat = alamat;
        this.nama = nama;
        this.seat_baris = seat_baris;
        this.seat_colom = seat_colom;
        this.films = films;
    }

    public MallModel(String idFilm, String idMall, String alamat, String nama, String seat_baris, String seat_colom, List<FilmsModel> films) {
        this.idFilm = idFilm;
        this.idMall = idMall;
        this.alamat = alamat;
        this.nama = nama;
        this.seat_baris = seat_baris;
        this.seat_colom = seat_colom;
        this.films = films;
    }

    public String getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(String idFilm) {
        this.idFilm = idFilm;
    }

    public String getIdMall() {
        return idMall;
    }

    public void setIdMall(String idMall) {
        this.idMall = idMall;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSeat_baris() {
        return seat_baris;
    }

    public void setSeat_baris(String seat_baris) {
        this.seat_baris = seat_baris;
    }

    public String getSeat_colom() {
        return seat_colom;
    }

    public void setSeat_colom(String seat_colom) {
        this.seat_colom = seat_colom;
    }

    public List<FilmsModel> getFilms() {
        return films;
    }

    public void setFilms(List<FilmsModel> films) {
        this.films = films;
    }
}
