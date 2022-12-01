package WinDBoe;


public class Mitarbeiter {
    int mid;
    int fid;

    public Mitarbeiter(int mid) {
        this.mid = mid;
    }

    public Mitarbeiter(int mid, int fid) {
        this.mid = mid;
        this.fid = fid;
    }

    public int getMid() {
        return mid;
    }

    public int getFid() {
        return fid;
    }

}

enum Taetigkeit {
    Konditor, Bäcker, Verkauf, Personalmanagement, Sales_Management
}
