package LokiDB;

import Main.DataGenerator;
import WinDBoe.Produkt;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.text.ParseException;

public class LokiDB extends DataGenerator {

    Ermittler[] ermittler;

    public static void main(String[] args) throws URISyntaxException, IOException, ParseException {
        LokiDB lokiDB = new LokiDB();
    }


    public LokiDB() throws URISyntaxException, IOException, ParseException {
        //super.createConnection("jdbc:postgresql://localhost:5432/LokiDB");
    }

    //------------------------------GET DATA FROM DATABASE-------------------------------------------

    public void getErmittler() {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT persid, verwgr, dstgr, vorgesid FROM ermittler;");
            while (rs.next()) {
                int persid = rs.getInt("persid");
                String verwgr = rs.getString("verwgr");
                String dstgr = rs.getString("dstgr");
                String vorgesid = rs.getString("vorgesid");
                ermittler.add(new Ermittler());
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void generatePerson() throws URISyntaxException, IOException, ParseException {
        Person person = new Person();
    }


    public void generateZeuge() throws URISyntaxException, IOException, ParseException {
        Person zeuge = new Person();
        String beruf = super.getRandomBeruf();
    }


    public void generateGeschaedigter() throws URISyntaxException, IOException, ParseException {
        Person geschaedigter = new Person();
        String beruf = super.getRandomBeruf();
        String blutgruppe = getRandomBlutgruppe();
    }
    
    public void generateVerdaechtiger() throws URISyntaxException, IOException, ParseException {
        Person verdaechtiger = new Person();
        int groesse = super.getRandomNumber(130, 210);
        //pseudonym
        //bandenname
        String beruf = super.getRandomBeruf();
        String haarfarbe = getRandomHaarfarbe();
        int schuhgroesse = super.getRandomNumber(36, 47);
        String augenfarbe = getRandomAugenfarbe();
        String blutgruppe = getRandomBlutgruppe();
        String fotolink = generateRandomLink();
        String fingerabdrucklink = generateRandomLink();
        double ergreifbel = super.generateRandomDecimal(200, 3000);
    }


    public void generateErmittler() throws URISyntaxException, IOException, ParseException {

    }



    // ---------------------------------GENERATE LOKI-SPECIFIC CONTENT--------------------------------------

    public String getRandomBlutgruppe(){
        String[] blutgruppen= {"A+", "A-", "B+", "B-", "AB+", "AB-", "0+", "0-"};
        return blutgruppen[getRandomNumber(blutgruppen.length-1)];
    }


    public String getRandomHaarfarbe(){
        String[] haarfarben = {"Weiß", "Grau", "Blond", "Rot", "Brünett", "Hellbraun", "Dunkelbraun", "Schwarz"};
        return haarfarben[getRandomNumber(haarfarben.length-1)];
    }


    public String getRandomAugenfarbe(){
        String[] augenfarben = {"Blau", "Grau", "Grün", "Braun"};
        return augenfarben[getRandomNumber(augenfarben.length-1)];
    }


    public String generateRandomLink(){
        return "http://polizeiinspektion.db/"+getRandomNumber(15483, 178269);
    }
}
