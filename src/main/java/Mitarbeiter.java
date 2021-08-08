import java.sql.Date;

public class Mitarbeiter {
    int mid;
    String vorname;
    String nachname;
    String strasse;
    int plz;
    String ort;
    float bg;
    String taetigkeit;
    int fid;
    int vorgesid;
    Date geburtsdatum;

    public Mitarbeiter(int mid) {
        this.mid = mid;
    }

    public Mitarbeiter(int mid, int fid) {
        this.mid = mid;
        this.fid = fid;
    }

    public Mitarbeiter(int mid, String vorname, String nachname, String strasse, int plz, String ort, float bg, String taetigkeit, int fid, int vorgesid, Date geburtsdatum) {
        this.mid = mid;
        this.vorname = vorname;
        this.nachname = nachname;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.bg = bg;
        this.taetigkeit = taetigkeit;
        this.fid = fid;
        this.vorgesid = vorgesid;
        this.geburtsdatum = geburtsdatum;
    }
}
