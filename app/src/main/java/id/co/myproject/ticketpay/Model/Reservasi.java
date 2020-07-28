package id.co.myproject.ticketpay.Model;

import java.util.ArrayList;

public class Reservasi {
    String id_jam_tayang;
    String seat_id;
    String id_user;
    String id_film;
    String id_mall;

    public Reservasi() {
    }


    public Reservasi(String id_jam_tayang, String seat_id, String id_user, String id_film, String id_mall) {
        this.id_jam_tayang = id_jam_tayang;
        this.seat_id = seat_id;
        this.id_user = id_user;
        this.id_film = id_film;
        this.id_mall = id_mall;
    }

    public String getId_jam_tayang() {
        return id_jam_tayang;
    }

    public void setId_jam_tayang(String id_jam_tayang) {
        this.id_jam_tayang = id_jam_tayang;
    }

    public String getSeats() {
        return seat_id;
    }

    public void setSeats(String seat_id) {
        this.seat_id = seat_id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_film() {
        return id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public String getId_mall() {
        return id_mall;
    }

    public void setId_mall(String id_mall) {
        this.id_mall = id_mall;
    }
}
