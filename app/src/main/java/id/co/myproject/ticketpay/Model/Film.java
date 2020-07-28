package id.co.myproject.ticketpay.Model;

public class Film {
    String id_film;
    String deskripsi;
    String durasi;
    String gambar;
    String title;
    String status;

    public Film() {
    }

    public Film(String deskripsi, String durasi, String gambar, String title, String status, String id_film) {
        this.deskripsi = deskripsi;
        this.durasi = durasi;
        this.gambar = gambar;
        this.title = title;
        this.status = status;
        this.id_film = id_film;
    }

    public String getId_film() {
        return id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
