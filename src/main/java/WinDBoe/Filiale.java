package WinDBoe;

public class Filiale{
    int fid;
    String strasse;
    int plz;
    String ort;

    public Filiale(int fid, int plz) {
        this.fid = fid;
        this.plz = plz;
    }

    public Filiale(int fid, String strasse, int plz, String ort) {
        this.fid = fid;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
    }

    public int getFid() {
        return fid;
    }

    public int getPlz() {
        return plz;
    }
}
