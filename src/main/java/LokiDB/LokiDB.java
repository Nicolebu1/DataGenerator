package LokiDB;

import Main.DataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

public class LokiDB extends DataGenerator {

    ArrayList<Ermittler> ermittler = new ArrayList<>();
    Random random = new Random();


    public static void main(String[] args) throws URISyntaxException, IOException, ParseException {
        LokiDB lokiDB = new LokiDB();
    }


    public LokiDB() throws URISyntaxException, IOException, ParseException {
        super.closeConnection();
        super.createConnection("jdbc:postgresql://localhost:5432/LokiDB");
        getErmittler();
        generateErmittler();
        super.closeConnection();
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
                int vorgesid;
                try {
                    vorgesid = rs.getInt("vorgesid");
                    ermittler.add(new Ermittler(persid, verwgr, dstgr, vorgesid));
                } catch (NullPointerException e) {
                    ermittler.add(new Ermittler(persid, verwgr, dstgr));
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    //------------------------------------GENERATE----------------------------------------


    public Person generatePerson() throws URISyntaxException, IOException, ParseException {
        Person person = new Person();
        String sql = "INSERT INTO person (" + person.getPersID() + ", " + person.getVorname() + ", " + person.getNachname() + ", " + person.getSex() + ", " + person.getGeburtsdatum() + ", " + person.getTelefon() + ", " + person.getFamilienstand() + ", " + person.getLandID() + ", " + person.getAdressenID() + ");";
        System.out.println(sql);
        //sendToDatabase(sql);
        return person;
    }


    public void generateZeuge() throws URISyntaxException, IOException, ParseException {
        Person zeuge = generatePerson();
        String beruf = super.generateRandomBeruf();
        String sql = "INSERT INTO zeuge (PersID, Beruf) VALUES (" + zeuge.getPersID() + ", " + beruf + ");";
        super.sendToDatabase(sql);
    }


    public void generateGeschaedigter() throws URISyntaxException, IOException, ParseException {
        Person geschaedigter = generatePerson();
        String beruf = super.generateRandomBeruf();
        String blutgruppe = generateRandomBlutgruppe();
        String sql = "INSERT INTO geschaedigter (persID, beruf) VALUES (" + geschaedigter.getPersID() + ", " + beruf + ", " + blutgruppe + ");";
        super.sendToDatabase(sql);
    }


    public void generateVerdaechtiger() throws URISyntaxException, IOException, ParseException {
        Person verdaechtiger = generatePerson();
        int groesse = super.generateRandomNumber(130, 210);
        //pseudonym
        //bandenname
        String beruf = super.generateRandomBeruf();
        String haarfarbe = generateRandomHaarfarbe();
        int schuhgroesse = super.generateRandomNumber(36, 47);
        String augenfarbe = generateRandomAugenfarbe();
        String blutgruppe = generateRandomBlutgruppe();
        String fotolink = generateRandomLink();
        String fingerabdrucklink = generateRandomLink();
        double ergreifbel = super.generateRandomDecimal(200, 3000);

        String sql = "INSERT INTO verdaechtiger (" + verdaechtiger.getPersID() + ", " + groesse + ", " + beruf + ", " + haarfarbe + ", " + schuhgroesse + ", " + augenfarbe + ", " + blutgruppe + ", " + fotolink + ", " + fingerabdrucklink + ", " + ergreifbel + ");";
        sendToDatabase(sql);
    }


    public void generateErmittler() throws URISyntaxException, IOException, ParseException {
        Person Eperson = generatePerson();
        Ermittler theErmittler;
        String sql;

        if (random.nextBoolean() == true) {
            Ermittler vorgesetzter = this.ermittler.get(generateRandomNumber(ermittler.size()));
            if (vorgesetzter.getVerwgr() == "E2c" || vorgesetzter.getVerwgr() == "E2b") {
                theErmittler = new Ermittler(Eperson.PersID);
                sql = "INSERT INTO ermittler (persid, verwgr, dstgr, bg) VALUES (" + Eperson.getPersID() + ", " + theErmittler.getVerwgr() + ", " + theErmittler.getDstgr() + ", " + theErmittler.getBg() + ");";
            } else {
                theErmittler = new Ermittler(Eperson.PersID, vorgesetzter.getPersiId());
                sql = "INSERT INTO ermittler (persid, verwgr, dstgr, bg, vorgesid) VALUES (" + Eperson.getPersID() + ", " + theErmittler.getVerwgr() + ", " + theErmittler.getDstgr() + ", " + theErmittler.getBg() + ", " + vorgesetzter.getPersiId() + ");";
            }
        } else {
            theErmittler = new Ermittler(Eperson.PersID);
            sql = "INSERT INTO ermittler (persid, verwgr, dstgr, bg) VALUES (" + Eperson.getPersID() + ", " + theErmittler.getVerwgr() + ", " + theErmittler.getDstgr() + ", " + theErmittler.getBg() + ");";
        }
        //super.sendToDatabase(sql);
    }


    // ---------------------------------GENERATE LOKI-SPECIFIC CONTENT--------------------------------------

    public String generateRandomBlutgruppe() {
        String[] blutgruppen = {"A+", "A-", "B+", "B-", "AB+", "AB-", "0+", "0-"};
        return blutgruppen[generateRandomNumber(blutgruppen.length - 1)];
    }


    public String generateRandomHaarfarbe() {
        String[] haarfarben = {"Weiß", "Grau", "Blond", "Rot", "Brünett", "Hellbraun", "Dunkelbraun", "Schwarz"};
        return haarfarben[generateRandomNumber(haarfarben.length - 1)];
    }


    public String generateRandomAugenfarbe() {
        String[] augenfarben = {"Blau", "Grau", "Grün", "Braun"};
        return augenfarben[generateRandomNumber(augenfarben.length - 1)];
    }


    public String generateRandomLink() {
        return "http://polizeiinspektion.db/" + generateRandomNumber(15483, 178269);
    }
}

/*      Team - Namen
        ALPHA
        BRAVO
        CHARLIE
        DELTA
        ECHO
        FOXTROT
        GOLF
        HOTEL
        INDIA
        JULIETT
        KILO
        LIMA
        MIKE
        NOVEMBER
        OSCAR
        PAPA
        QUEBEC
        ROMEO
        SIERRA
        TANGO
        UNIFORM
        VICTOR
        WHISKEY
        XRAY
        YANKEE
        ZULU
 */