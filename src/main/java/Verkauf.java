import java.sql.Date;

public class Verkauf {
    int vid;
    Date verkaufsdatum;
    float rechnungsbetrag;
    float provision;
    int kdid;
    int fid;
    int mid;

    public Verkauf(int vid, Date verkaufsdatum, float rechnungsbetrag, int fid, int mid) {
        this.vid = vid;
        this.verkaufsdatum = verkaufsdatum;
        this.rechnungsbetrag = rechnungsbetrag;
        this.fid = fid;
        this.mid = mid;
    }

    public Verkauf(int vid, Date verkaufsdatum, float rechnungsbetrag, float provision, int kdid, int fid, int mid) {
        this.vid = vid;
        this.verkaufsdatum = verkaufsdatum;
        this.rechnungsbetrag = rechnungsbetrag;
        this.provision = provision;
        this.kdid = kdid;
        this.fid = fid;
        this.mid = mid;
    }
}
