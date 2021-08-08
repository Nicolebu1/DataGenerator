public class Produkt {
    int pid;
    String bezeichnung;
    float herstellungspreis;
    float verkaufspreis;
    int lagerbestand;
    String kategorie;

    public Produkt(int pid, float preis){
        this.pid = pid;
        this.herstellungspreis = preis;
    }

    public Produkt(int pid, String bezeichnung, float herstellungspreis, float verkaufspreis, int lagerbestand, String kategorie) {
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

    public float getHerstellungspreis() {
        return herstellungspreis;
    }
}
