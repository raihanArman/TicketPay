package id.co.myproject.ticketpay.Model;

public class Times {
    String id_screening;
    String jam;
    Boolean selected;
    String tanggal;
    String id_mall;

    public Times() {
    }

    public String getId_screening() {
        return id_screening;
    }

    public void setId_screening(String id_screening) {
        this.id_screening = id_screening;
    }

    public Times(String jam, Boolean selected) {
        this.jam = jam;
        this.selected = selected;
    }

    public String getId_mall() {
        return id_mall;
    }

    public void setId_mall(String id_mall) {
        this.id_mall = id_mall;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
}
