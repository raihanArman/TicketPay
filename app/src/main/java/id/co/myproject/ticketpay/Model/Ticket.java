package id.co.myproject.ticketpay.Model;

import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private String id_tiket;
    private List<String> seats;
    private String screening;
    private String filmTitle;
    private String imgFilm;
    private String price;
    private String id_user;

    public Ticket() {
    }

    public Ticket(String id_tiket, List<String> seats, String screening, String filmTitle, String imgFilm, String price, String id_user) {
        this.seats = seats;
        this.screening = screening;
        this.filmTitle = filmTitle;
        this.imgFilm = imgFilm;
        this.price = price;
        this.id_user = id_user;
        this.id_tiket = id_tiket;
    }

    public String getId_tiket() {
        return id_tiket;
    }

    public void setId_tiket(String id_tiket) {
        this.id_tiket = id_tiket;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public String getScreening() {
        return screening;
    }

    public void setScreening(String screening) {
        this.screening = screening;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public String getImgFilm() {
        return imgFilm;
    }

    public void setImgFilm(String imgFilm) {
        this.imgFilm = imgFilm;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
