package LokiDB;

import Main.DataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

public class LokiDB extends DataGenerator {

    ArrayList<Ermittler> ermittler = new ArrayList<>();
    Random random = new Random();
    AdressenErmittler ae;

    public static void main(String[] args) throws URISyntaxException, IOException, ParseException, SQLException {
        LokiDB lokiDB = new LokiDB();
    }


    public LokiDB() throws URISyntaxException, IOException, ParseException, SQLException {
        super.closeConnection();
        super.createConnection("jdbc:postgresql://localhost:5432/LokiDB");
        getErmittler();
        //getAdressesFromDB();
        //generateDienststelle();
        //generateDelikt();
        //generateIndiz();
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


    public Person generatePerson() throws Exception {
        ae = new AdressenErmittler(0);
        Person person = new Person(ae.getChoosenID());
        String sql = "INSERT INTO person (" + person.getPersID() + ", " + person.getVorname() + ", " + person.getNachname() + ", " + person.getSex() + ", " + person.getGeburtsdatum() + ", " + person.getTelefon() + ", " + person.getFamilienstand() + ", " + person.getLandID() + ", " + person.getAdressenID() + ");";
        System.out.println(sql);
        //sendToDatabase(sql);
        return person;
    }


    public void generateZeuge() throws Exception {
        Person zeuge = generatePerson();
        String beruf = super.generateRandomBeruf();
        String sql = "INSERT INTO zeuge (PersID, Beruf) VALUES (" + zeuge.getPersID() + ", " + beruf + ");";
        super.sendToDatabase(sql);
    }


    public void generateGeschaedigter() throws Exception {
        Person geschaedigter = generatePerson();
        String beruf = super.generateRandomBeruf();
        String blutgruppe = generateRandomBlutgruppe();
        String sql = "INSERT INTO geschaedigter VALUES (" + geschaedigter.getPersID() + ", " + beruf + ", " + blutgruppe + ");";
        super.sendToDatabase(sql);
    }


    public void generateVerdaechtiger() throws Exception {
        Person verdaechtiger = generatePerson();
        int groesse = super.generateRandomNumber(130, 210);
        //Todo : pseudonym
        //Todo : bandenname
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


    public void generateErmittler() throws Exception {
        Person Eperson = generatePerson();
        Ermittler theErmittler;
        String sql;

        if (random.nextBoolean() == true) {
            Ermittler vorgesetzter = this.ermittler.get(generateRandomNumber(ermittler.size() - 1));
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


    public void generateDienststelle() throws Exception {
        int dstelleID = super.getHighestID("Select * from dienststelle", "dstelleid") + 1;
        ae = new AdressenErmittler(1);
        int adressid = ae.getChoosenID();
        String name;
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT ort FROM adresse where adressenid = " + adressid + ";");
            name = "Polizeidienststelle " + rs.getString("ort");
            String sql = "INSERT INTO dienststelle VALUES (" + dstelleID + ", " + name + ", " + adressid + ");";
            System.out.println(sql);
            //super.sendToDatabase(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void generateDelikt() throws Exception {
        //Status, AdressenID)
        int deliktid = super.getHighestID("Select * from delikt", "deliktid") + 1;
        Timestamp tatzeitVon = super.generateRandomTimestamp(2021, 2022);
        Timestamp tatzeitBis = super.generateFollowUpTimestamp(tatzeitVon);
        Timestamp erfassungszeitpunkt = super.generateFollowUpTimestamp(tatzeitBis);

        //Todo : beschreibung
        String beschreibung = "NULL";

        double schadenshoehe;
        boolean schaden = random.nextBoolean();
        if (schaden == true) {
            schadenshoehe = super.generateRandomDecimal(25, 30000);
        } else {
            schadenshoehe = 0;
        }

        int delikttypid;
        DelikttypErmittler de = new DelikttypErmittler(schadenshoehe);
        delikttypid = de.getDelikttypid();

        ae = new AdressenErmittler(2);
        int adressenid = ae.getChoosenID();

        String status = generateRandomStatus();

        String sql = "INSERT INTO delikt VALUES (" + deliktid + ", " + erfassungszeitpunkt + ", " + beschreibung + ", " + tatzeitVon + ", " + tatzeitBis + ", " + schadenshoehe + ", " + status + ", " + delikttypid + ", " + adressenid + ");";
        System.out.println(sql);
        //sendToDatabase(sql);
    }


    public void generateIndiz() {
        //Indiz (IndizID, Beschreibung, Funddatum, Fundzeit, zugelBeweis, DeliktID)
        int indizid = super.getHighestID("SELECT * FROM indiz", "indizid");
        //Todo : beschreibung
        String beschreibung = "NULL";
        Date funddatum = super.generateRandomDate(2021, 2022);
        Time fundzeit = super.generateRandomTime();
        boolean zugelBeweis = random.nextBoolean();
        //Todo : wo welche Indizien?
        int deliktid = 0;

        String sql = "INSERT INTO indiz (" + indizid + ", " + beschreibung + ", " + funddatum + ", " + fundzeit + ", " + zugelBeweis + ", " + deliktid +");";
        //super.sendToDatabase(sql);
        System.out.println(sql);
    }


    public void generateBeute(){

        //Choose suitable delikttyp
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT deliktid FROM delikt WHERE delikttyp IN (5, 7, 13);");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



        // ---------------------------------GENERATE LOKI-SPECIFIC CONTENT--------------------------------------


        public String generateRandomBlutgruppe () {
            String[] blutgruppen = {"A+", "A-", "B+", "B-", "AB+", "AB-", "0+", "0-"};
            return blutgruppen[generateRandomNumber(blutgruppen.length - 1)];
        }


        public String generateRandomHaarfarbe () {
            String[] haarfarben = {"Weiß", "Grau", "Blond", "Rot", "Brünett", "Hellbraun", "Dunkelbraun", "Schwarz"};
            return haarfarben[generateRandomNumber(haarfarben.length - 1)];
        }


        public String generateRandomAugenfarbe () {
            String[] augenfarben = {"Blau", "Grau", "Grün", "Braun"};
            return augenfarben[generateRandomNumber(augenfarben.length - 1)];
        }


        public String generateRandomLink () {
            return "http://polizeiinspektion.db/" + generateRandomNumber(15483, 178269);
        }

        public String generateRandomStatus () {
            String[] status = {"wiederaufgenommen", "gelöst", "laufend", "abgelegt"};
            return status[generateRandomNumber(status.length - 1)];
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