package LokiDB;

import Main.Adresse;
import Main.DataGenerator;
import WinDBoe.Mitarbeiter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

public class LokiDB extends DataGenerator {

    ArrayList<Ermittler> ermittler = new ArrayList<>();
    ArrayList<Adresse> adressen = new ArrayList<>();
    ArrayList<Integer> dstelladdi = new ArrayList<>();
    ArrayList<Integer> personaddi = new ArrayList<>();
    ArrayList<Integer> deliktaddi = new ArrayList<>();
    Random random = new Random();


    public static void main(String[] args) throws URISyntaxException, IOException, ParseException {
        LokiDB lokiDB = new LokiDB();
    }


    public LokiDB() throws URISyntaxException, IOException, ParseException, SQLException {
        super.closeConnection();
        super.createConnection("jdbc:postgresql://localhost:5432/LokiDB");
        getErmittler();
        getAdressesFromDB();
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


    public void getAdressesFromDB() throws SQLException {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT * FROM adresse;");
            while (rs.next()) {
                adressen.add(new Adresse(rs.getInt("adressenid"), rs.getString("strasse"), rs.getString("ort"), rs.getInt("plz")));
            }
            rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM dienststelle;");
            while (rs.next()) {
                dstelladdi.add(rs.getInt("adressenid"));
            }
            rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM person;");
            while (rs.next()) {
                personaddi.add(rs.getInt("adressenid"));
            }
            rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM delikt;");
            while (rs.next()) {
                deliktaddi.add(rs.getInt("adressenid"));
            }


        } catch (Exception e) {
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


    public void generateDienststelle() {
        int dstelleID = super.getHighestID("Select * from dienststelle", "dstelleid") + 1;
        int adressid;
        boolean available = true;
        adressid = generateRandomNumber(adressen.size());
        for (int m : dstelladdi) {
            if (m == adressid) {
                available = false;
            }
        }
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


    public int generatePersonAddi() {
        //Personen living with other persons is okay
        //Personen living on deliktadressen is okay, when delikttyp fits
        //Personen living on dienststellen is not okay

        boolean available = true;

        int adressenid = super.generateRandomNumber(adressen.size());

        //TODO

        return adressenid;
    }

    public int generateDstellenAddi() {
        //nothing else there besides dienststelle

        int adressid;
        boolean aval1 = false;
        boolean aval2 = false;

        //TODO DENKFEHLER! Tut noch nicht was es soll. :(

        do {
            adressid = super.generateRandomNumber(adressen.size());
            for (int i : personaddi) {
                if (i == adressid) {
                    aval1 = false;
                } else aval1 = true;
            }

            for (int i : deliktaddi) {
                if (i == adressid) {
                    aval2 = false;
                } else aval2 = true;
            }
        }
        while (aval1 == false || aval2 == false);
        return adressid;
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