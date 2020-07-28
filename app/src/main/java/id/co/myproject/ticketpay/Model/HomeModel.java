package id.co.myproject.ticketpay.Model;

import java.util.List;

public class HomeModel {
    public static final int FILM_AT_TEATER = 0;
    public static final int FILM_INCOMING = 1;

    private int type;

    //Film
    private List<Film> films;

    public HomeModel(int type, List<Film> films) {
        this.type = type;
        this.films = films;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    //Film

}
