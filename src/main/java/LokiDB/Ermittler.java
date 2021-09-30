package LokiDB;

import Main.DataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;

public class Ermittler extends DataGenerator {

    int persiId;
    String verwgr;
    String dstgr;
    double bg;
    int vorgesID;
    int teamID;


    //------------------------For loading existing Ermittler from LokiDB----------------------------------

    //if ermittler has vorgesID
    public Ermittler(int persid, String verwgr, String dstgr, int vorgesID) throws URISyntaxException, IOException, ParseException {
    this.persiId = persid;
    this.verwgr = verwgr;
    this.dstgr = dstgr;
    this.vorgesID = vorgesID;
    }


    //if ermittler don't have vorgesID
    public Ermittler(int persid, String verwgr, String dstgr) throws URISyntaxException, IOException, ParseException {
        this.persiId = persid;
        this.verwgr = verwgr;
        this.dstgr = dstgr;
    }

    //----------------------------------------for generating new Ermittler-----------------------------------------

    public Ermittler(int persid, int vorgesID){
        this.persiId = persid;
        this.vorgesID = vorgesID;
        generateVerwendungsgruppe();
        generateDstgrAndBG();
    }

    public Ermittler(int persid){
        this.persiId = persid;
        generateVerwendungsgruppe();
        generateDstgrAndBG();
    }

    void generateVerwendungsgruppe(){
        String[] verwendungsgruppe = {"E2c", "E2b", "E2a", "E1"};
        this.verwgr = verwendungsgruppe[super.generateRandomNumber(3)];
    }

    void generateDstgrAndBG(){
        ArrayList<String> Dsgr = new ArrayList<>();
        if (this.getVerwgr() == "E1"){
            Dsgr.add("Leutnant");
            Dsgr.add("Oberleutnant");
            Dsgr.add("Hauptmann");
            Dsgr.add("Major");
            Dsgr.add("Oberstleutnant");
            Dsgr.add("Oberst");
            Dsgr.add("Brigardier");
            Dsgr.add("Generalmajor");
            Dsgr.add("General");

            this.bg = super.generateRandomDecimal(2377, 4376);
        }
        else if (this.getVerwgr() == "E2a"){
            Dsgr.add("BezInsp");
            Dsgr.add("GrInsp");
            Dsgr.add("AbtInsp");
            Dsgr.add("ChefInsp");
            Dsgr.add("KontrInsp");

            this.bg = super.generateRandomDecimal(2072, 3216);
        }
        else if (this.getVerwgr() == "E2b"){
            Dsgr.add("I");      //Inspektor
            Dsgr.add("RI");     //Revierinspektor
            Dsgr.add("GI");     //Gruppeninspektor

            this.bg = super.generateRandomDecimal(1873, 3029);
        }
        else{
            this.dstgr = "A";

            this.bg = super.generateRandomDecimal(1765, 1948);
        };
    }


    public String getVerwgr() {
        return verwgr;
    }

    public String getDstgr() {
        return dstgr;
    }

    public double getBg() {
        return bg;
    }

    public int getVorgesID() {
        return vorgesID;
    }

    public int getTeamID() {
        return teamID;
    }

    public int getPersiId(){
        return persiId;
    }
}
