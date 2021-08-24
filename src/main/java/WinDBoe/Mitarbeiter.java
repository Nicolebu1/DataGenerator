package WinDBoe;

import java.sql.Date;

public class Mitarbeiter {
    int mid;
    String vorname;
    String nachname;
    Date geburtsdatum;
    String strasse;
    int plz;
    String ort;
    double bg;
    Enum taetigkeit;
    int fid;
    int vorgesid;
    int maid;

    public Mitarbeiter(int mid) {
        this.mid = mid;
    }

    public Mitarbeiter(int mid, int fid) {
        this.mid = mid;
        this.fid = fid;
    }

    public Mitarbeiter(int mid, String vorname, String nachname, Date geburtsdatum, String strasse, int plz, String ort, double bg, Enum taetigkeit, int fid, int vorgesid, int maid) {
        this.mid = mid;
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.bg = bg;
        this.taetigkeit = taetigkeit;
        this.fid = fid;
        this.vorgesid = vorgesid;
        this.maid = maid;
    }

    public int getMid() {
        return mid;
    }

    public int getFid() {
        return fid;
    }

    public int getVorgesid() {
        return vorgesid;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getStrasse() {
        return strasse;
    }

    public int getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    public double getBg() {
        return bg;
    }

    public Enum getTaetigkeit() {
        return taetigkeit;
    }

    public int getMaid() {
        return maid;
    }
}

enum Taetigkeit{
    Konditor, Bäcker, Verkauf, Personalmanagement, Sales_Management
}
