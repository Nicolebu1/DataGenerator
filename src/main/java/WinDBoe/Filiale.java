package WinDBoe;

public class Filiale{
    int fid;
    String strasse;
    int plz;
    String ort;

    public Filiale(int fid) {
        this.fid = fid;
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
}
