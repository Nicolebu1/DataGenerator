package WinDBoe;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static java.sql.Types.NULL;

import Main.Adresse;
import Main.DataGenerator;

public class WinDBoe extends DataGenerator {

    ArrayList<Filiale> Filialen = new ArrayList<>();
    ArrayList<Produkt> Produkte = new ArrayList<>();
    ArrayList<Mitarbeiter> Mitarbeiter = new ArrayList<>();
    ArrayList<Integer> fhmids = new ArrayList<Integer>();

    Adresse adress;
    Random random = new Random();

    public static void main(String[] args) throws URISyntaxException, IOException {
        WinDBoe winDBoe = new WinDBoe();
    }

    public WinDBoe() throws URISyntaxException, IOException {
        this.adress = new Adresse();
        super.createConnection("jdbc:postgresql://localhost:5432/WinDBoe");
        getFilialen();
        getMitarbeiter();
        getProdukte();
        getFirmenhandys();
        generateFirmenhandy();
    }


    //send queries to database
    public void sendToDatabase(String sql) {
        System.out.println(sql);
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            DataGenerator.stmt.execute(sql);
            System.out.println("Inserted.");
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
    }


    //get data from database
    public void getProdukte() {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT pid, verkaufspreis FROM produkt;");
            while (rs.next()) {
                int pid = rs.getInt("pid");
                double preis = rs.getDouble("verkaufspreis");
                Produkte.add(new Produkt(pid, preis));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void getFilialen() {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT fid, plz FROM filiale;");
            while (rs.next()) {
                int fid = rs.getInt("fid");
                int plz = rs.getInt("plz");
                Filialen.add(new Filiale(fid, plz));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void getFirmenhandys() {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT mid FROM firmenhandy;");
            while (rs.next()) {
                int mid = rs.getInt("mid");
                if (mid != 0) {
                    fhmids.add(Integer.valueOf(mid));
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void getMitarbeiter() {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT mid, fid FROM mitarbeiter;");
            while (rs.next()) {
                int mid = rs.getInt("mid");
                int fid = rs.getInt("fid");

                if (fid != NULL) {
                    Mitarbeiter.add(new Mitarbeiter(mid, fid));
                } else {
                    Mitarbeiter.add(new Mitarbeiter(mid));
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Filiale getRandomFiliale() {
        return Filialen.get(getRandomNumber(Filialen.size() - 1));
    }

    public Mitarbeiter getRandomMitarbeiterFromFiliale(int fid) {

        //select all Mitarbeiter from choosen Filiale
        ArrayList<Mitarbeiter> FilialenMitarbeiter = new ArrayList<>();
        for (Mitarbeiter m : Mitarbeiter) {
            if (m.getFid() == fid) {
                FilialenMitarbeiter.add(m);             //note: fid is not null in database
            }
        }
        //choose Mitarbeiter
        return FilialenMitarbeiter.get(getRandomNumber(FilialenMitarbeiter.size() - 1));
    }


    public Mitarbeiter getRandomMitarbeiter() {
        return Mitarbeiter.get(getRandomNumber(Mitarbeiter.size() - 1));
    }


    //insert data in database
    public void generateVerkauf() {
        int vid = super.getHighestID("SELECT * FROM verkauf;", "vid") + 1;

        //choose Filiale
        Filiale filiale = getRandomFiliale();
        int fid = filiale.getFid();

        //choose Verkaufsdatum
        Date verkaufsdatum = super.generateRandomDate(2021, 2021);

        //choose Random Mitarbeiter from Filiale
        int mid = getRandomMitarbeiterFromFiliale(fid).getMid();

        //choose Produkte
        int numberOfProdukte = 1 + getRandomNumber(14);         //note: Produkteanzahl can't be 0
        ArrayList<Produkt> buyed = new ArrayList<>();

        for (int i = 0; i < numberOfProdukte; i++) {
            buyed.add(Produkte.get(getRandomNumber(Produkte.size() - 1)));
        }

        //count different Produkte
        Map<Integer, Integer> verkaufsposition = new HashMap();
        for (Produkt p : buyed) {
            if (verkaufsposition.containsKey(p.getPid())) {
                verkaufsposition.put(p.getPid(), verkaufsposition.get(p.getPid()) + 1);
            } else {
                verkaufsposition.put(p.getPid(), 1);
            }
        }

        //calculate price
        double verkaufspreis = 0;
        for (Produkt p : buyed) {
            verkaufspreis = verkaufspreis + p.getVerkaufspreis();
        }

        //round to 2 decimal places
        verkaufspreis = Math.round(verkaufspreis * 100.0) / 100.0;

        //create statements
        String sql = "INSERT INTO verkauf (vid, verkaufsdatum, rechnungsbetrag, fid, mid) " +
                "VALUES (" + vid + ", '" + verkaufsdatum + "', " + verkaufspreis + " ," + fid + " ," + mid + ");";

        //insert into Database
        sendToDatabase(sql);

        //insert into Verkaufsposition
        verkaufsposition.forEach((key, value) -> {
            String sql2 = "INSERT INTO verkaufsposition (menge, vid, pid) " +
                    "VALUES (" + value + ", " + vid + ", " + key + ");";
            sendToDatabase(sql2);
        });
    }


    public void generateMitarbeiter() throws URISyntaxException, IOException {

        //define mid
        int mid = super.getHighestID("SELECT mid FROM mitarbeiter", "mid") + 1;

        //set personal data
        String vorname = super.generateRandomVorname();
        String nachname = super.generateRandomNachname();
        Date geburtsdatum = super.generateRandomDate(1970, 2000);

        //set Filiale
        Filiale filiale = getRandomFiliale();
        int fid = filiale.getFid();

        //set Adresse
        String strasse = adress.getRandomStrasse();
        int plz = filiale.getPlz();                 //to make sure mitarbeiter lives near his filiale
        String ort = adress.getRandomOrt();

        //set specific data
        double bg = super.generateRandomDecimal(2100, 3750);
        Enum taetigkeit = Taetigkeit.values()[getRandomNumber(Taetigkeit.values().length)];

        //generate Mitarbeiterausweis
        int maid = generateMitarbeiterausweis(mid);

        //look if there are available mitarbeiter for being vorgesetzter
        int vorgesID;
        try {
            vorgesID = getRandomMitarbeiterFromFiliale(fid).getMid();
        } catch (IndexOutOfBoundsException e) {
            vorgesID = 0;
        }

        String sql;

        if (vorgesID == 0) {
            sql = "INSERT INTO mitarbeiter (mid, vorname, nachname, strasse, plz, ort, bg, taetigkeit, fid, geburtsdatum, maid VALUES (" + mid + ", '" + vorname + "', '" + nachname + "', '" + strasse + "', " + plz + ", '" + ort + "', " + bg + ", '" + taetigkeit + "', " + fid + ", '" + geburtsdatum + "', " + maid + ");";
        } else {
            sql = "INSERT INTO mitarbeiter VALUES (" + mid + ", '" + vorname + "', '" + nachname + "', '" + strasse + "', " + plz + ", '" + ort + "', " + bg + ", '" + taetigkeit + "', " + fid + ", " + vorgesID + ", '" + geburtsdatum + "', " + maid + ");";
        }

        //insert into Database
        sendToDatabase(sql);
    }


    public int generateMitarbeiterausweis(int mid) {
        int maid = 1000 + mid;
        String berechtigungen = "Stufe: " + super.getRandomNumber(5);
        Date gueltigBis = super.generateRandomDate(2021, 2023);


        String sql = "INSERT INTO mitarbeiterausweis " +
                "VALUES (" + maid + ", '" + berechtigungen + "', '" + gueltigBis + "');";

        sendToDatabase(sql);
        return maid;
    }


    public void generateKunde() throws URISyntaxException, IOException {

        //define kundennummer
        int kdid = super.getHighestID("SELECT kdid FROM kunde", "kdid") + 1;

        //set personal data
        String vorname = super.generateRandomVorname();
        String nachname = super.generateRandomNachname();
        Date geburtsdatum = super.generateRandomDate(1950, 2000);
        String email = super.generateEmail(vorname, nachname);
        String telnr = super.generateTelNr();
        boolean newsletter = random.nextBoolean();

        //set Adresse
        String strasse = adress.getRandomStrasse();
        int plz = adress.getRandomPlz();
        String ort = adress.getRandomOrt();

        String sql = "INSERT INTO kunde VALUES (" + kdid + ", '" + vorname + "', '" + nachname + "', '" + strasse + "', " + plz + ", '" + ort + "', '" + geburtsdatum + "', '" + email + "', '" + telnr + "', " + newsletter + ");";

        sendToDatabase(sql);
    }


    public void generateFirmenhandy() {
        int fhid = super.getHighestID("SELECT fhid FROM firmenhandy", "fhid") + 1;
        String number = super.generateTelNr();
        boolean assigned = true; //random.nextBoolean();
        int counter = 0;
        Integer mid;
        String sql;


        //assign to mitarbeiter
        if (assigned == true) {
            boolean asngd = false;
            do {
                mid = Integer.valueOf(getRandomMitarbeiter().getMid());
                counter++;
                if (!fhmids.contains(mid)) {
                    asngd = true;
                }
            }
            while (counter < 10 && asngd == false);
            if (counter < 10) {
                sql = "INSERT INTO firmenhandy VALUES (" + fhid + ", '" + number + "', " + mid + ");";
            } else {
                sql = "INSERT INTO firmenhandy (fhid, telnr) VALUES (" + fhid + ", '" + number + "');";
            }
        } else {
            sql = "INSERT INTO firmenhandy (fhid, telnr) VALUES (" + fhid + ", '" + number + "');";
        }

        sendToDatabase(sql);
    }

    public void generateFiliale() {
        int fid = super.getHighestID("SELECT fid FROM filiale", "fid") + 1;
        String strasse = adress.getRandomStrasse();
        int plz = adress.getRandomPlz();
        String ort = adress.getRandomOrt();

        String sql = "INSERT INTO filiale VALUES (" + fid + ", '" + strasse + "', " + plz + ", '" + ort + "');";

        sendToDatabase(sql);
    }

    public void generateVerbindlichkeit() throws URISyntaxException, IOException {
        int rechnungsnummer = super.getHighestID("SELECT rechnungsnummer FROM verbindlichkeit", "rechnungsnummer") + 1;

        //set cooperate form
        String[] cooperateform = {"OG", "KG", "Co.KG", "AG"};

        String lieferantenname = super.generateRandomNachname() + " " + cooperateform[super.getRandomNumber(cooperateform.length - 1)];
        double rechnungsbetrag = super.generateRandomDecimal(100, 15000);
        Date rechnungsdatum = super.generateRandomDate(2021, 2022);

        //Todo: Min Max Datum definition in DataGenerator Class
        //Date zahlungsdatum = super.generateRandomDate(rechnungsdatum,);

        int fid = getRandomFiliale().getFid();

        String sql = "INSERT INTO verbindlichkeit (rechnungsnummer, lieferantenname, rechnungsbetrag, rechnungsdatum, fid) VALUES (" + rechnungsnummer + ", '" + lieferantenname + "', " + rechnungsbetrag + ", '" + rechnungsdatum + "', " + fid + ");";

        sendToDatabase(sql);
    }
}