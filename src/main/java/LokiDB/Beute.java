package LokiDB;

import Main.DataGenerator;

public class Beute extends DataGenerator {

    //Beute (BeuteID, Bezeichnung, Wert, Beutetyp, Beschreibung, VersSum, Fotolink, DeliktID)

    int Beuteid;
    String bezeichnung;
    double wert;
    String beutetyp;
    String beschreibung;
    double verssum;
    int deliktid;

    public Beute(double wert, int deliktid) {
        this.wert = wert;
        this.deliktid = deliktid;
        this.verssum = generateVersSum();
    }

    public Beute(double wert, int deliktid, String beutetyp){
        this.wert = wert;
        this.deliktid = deliktid;
        this.verssum = generateVersSum();
        this.beutetyp = beutetyp;
    }

    public double generateVersSum(){

        //Todo : in Ordnung?
        //VersSum between 70 and 90% of actual value

        this.verssum = this.wert * super.generateRandomDecimal(0.7, 0.9);
        return verssum;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public double getWert() {
        return wert;
    }

    public String getBeutetyp() {
        return beutetyp;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public double getVerssum() {
        return verssum;
    }

    public int getDeliktid() {
        return deliktid;
    }
}
