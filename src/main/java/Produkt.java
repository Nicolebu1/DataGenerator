public class Produkt {
    int pid;
    String bezeichnung;
    double herstellungspreis;
    double verkaufspreis;
    int lagerbestand;
    String kategorie;

    public Produkt(int pid, double preis){
        this.pid = pid;
        this.herstellungspreis = preis;
    }

    public Produkt(int pid, String bezeichnung, double herstellungspreis, double verkaufspreis, int lagerbestand, String kategorie) {
        this.pid = pid;
        this.bezeichnung = bezeichnung;
        this.herstellungspreis = herstellungspreis;
        this.verkaufspreis = verkaufspreis;
        this.lagerbestand = lagerbestand;
        this.kategorie = kategorie;
    }

    public int getPid() {
        return pid;
    }

    public double getHerstellungspreis() {
        return herstellungspreis;
    }
}
