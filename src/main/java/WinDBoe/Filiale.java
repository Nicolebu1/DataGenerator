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

    public int getFid() {
        return fid;
    }

    public int getPlz() {
        return plz;
    }
}
