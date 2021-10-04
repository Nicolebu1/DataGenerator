package LokiDB;

import Main.Adresse;
import Main.DataGenerator;

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


    public static void main(String[] args) throws URISyntaxException, IOException, ParseException, SQLException {
        LokiDB lokiDB = new LokiDB();
    }


    public LokiDB() throws URISyntaxException, IOException, ParseException, SQLException {
        super.closeConnection();
        super.createConnection("jdbc:postgresql://localhost:5432/LokiDB");
        getErmittler();
        getAdressesFromDB();
        generateDienststelle();
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
        Person person = new Person(generatePersonAddi());
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


    //Todo: Fix connection
    public void generateDienststelle() {
        int dstelleID = super.getHighestID("Select * from dienststelle", "dstelleid") + 1;
        int adressid = generateDstellenAddi();
        String name;
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT ort FROM adresse where adressenid = " + adressid + ";");
            name = "Polizeidienststelle " + rs.getString("ort");
            String sql = "INSERT INTO dienststelle VALUES (" + dstelleID + ", " + name + ", " + adressid +");";
            System.out.println(sql);
            //super.sendToDatabase(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void generateDelikt(){
        //Delikt (DeliktID, Erfassungszeitpunkt, Beschreibung, TatzeitVon, TatzeitBis, Schadenshoehe, Status,
        //DelikttypID, AdressenID)
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
        //Personen living on deliktadressen is okay, when delikttyp is not 3 or 7
        //Personen living on dienststellen is not okay

        int adressid = 0;

        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM dienststelle");
            ResultSet rs2 = DataGenerator.stmt.executeQuery("SELECT adressenid, delikttypid FROM delikt");
            do {
                adressid = super.generateRandomNumber(adressen.size());
                while (rs.next()) {
                    if (rs.getInt("adressenid") == adressid) {
                        adressid = 0;
                        break;
                    }
                }
                while (rs2.next()) {
                    if (rs.getInt("adressenid") == adressid && (rs.getInt("delikttypid") == 3 || rs.getInt("delikttypid") == 7)) {
                        adressid = 0;
                        break;
                    }
                }
            }
            while (adressid == 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return adressid;
    }


    public int generateDstellenAddi() {
        //nothing else there besides dienststelle

        int adressid = 0;

        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM person UNION SELECT adressenid FROM delikt UNION SELECT adressenid FROM dienststelle");
            do {
                adressid = super.generateRandomNumber(adressen.size());
                while (rs.next()) {
                    if (rs.getInt("adressenid") == adressid) {
                        adressid = 0;
                        break;
                    }
                }
            }
            while (adressid == 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return adressid;
    }


    public int generateDeliktAddi() {
        //crime happens everywhere, except dienststelle

        int adressid = 0;

        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM delikt");
            do {
                adressid = super.generateRandomNumber(adressen.size());
                while (rs.next()) {
                    if (rs.getInt("adressenid") == adressid) {
                        adressid = 0;
                        break;
                    }
                }
            }
            while (adressid == 0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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