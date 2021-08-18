package WinDBoe;

import java.sql.Date;

public class Verkauf {
    int vid;
    Date verkaufsdatum;
    double rechnungsbetrag;
    double provision;
    int kdid;
    int fid;
    int mid;

    //if customer isn't registered
    public Verkauf(int vid, Date verkaufsdatum, double rechnungsbetrag, int fid, int mid) {
        this.vid = vid;
        this.verkaufsdatum = verkaufsdatum;
        this.rechnungsbetrag = rechnungsbetrag;
        this.fid = fid;
        this.mid = mid;
    }

    //if customer is registered
    public Verkauf(int vid, Date verkaufsdatum, double rechnungsbetrag, double provision, int kdid, int fid, int mid) {
        this.vid = vid;
        this.verkaufsdatum = verkaufsdatum;
        this.rechnungsbetrag = rechnungsbetrag;
        this.provision = provision;
        this.kdid = kdid;
        this.fid = fid;
        this.mid = mid;
    }
}
