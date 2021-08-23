package WinDBoe;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

import static WinDBoe.Taetigkeit.Bäcker;
import static java.sql.Types.NULL;

import Main.Adresse;
import Main.DataGenerator;
import Main.Name;

public class WinDBoe extends DataGenerator {

    ArrayList<Filiale> Filialen = new ArrayList<>();
    ArrayList<Produkt> Produkte = new ArrayList<>();
    ArrayList<Verkauf> Verkäufe = new ArrayList<>();
    ArrayList<Mitarbeiter> Mitarbeiter = new ArrayList<>();

    public static void main(String[] args) throws URISyntaxException, IOException {
        WinDBoe winDBoe = new WinDBoe();
    }

    public WinDBoe() throws URISyntaxException, IOException {
        super.createConnection("jdbc:postgresql://localhost:5432/WinDBoe");
        getFilialen();
        getMitarbeiter();
        getProdukte();
        generateMitarbeiter();
        super.closeConnection();
    }


    //get data from database
    public void getProdukte() {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT pid, verkaufspreis FROM produkt;");
            int i = 0;
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
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT fid FROM filiale;");
            int i = 0;
            while (rs.next()) {
                int fid = rs.getInt("fid");
                Filialen.add(new Filiale(fid));
                i++;
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
            int i = 0;
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

    public Filiale getRandomFiliale(){
        return Filialen.get(getRandomNumber(Filialen.size() - 1));
    }

    public Mitarbeiter getRandomMitarbeiterFromFiliale(int fid){

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


    //insert data in database
    public void generateVerkauf() {
        int vid = super.getHighestID("SELECT * FROM verkauf;", "vid") + 1;

        //choose Filiale
        Filiale filiale = getRandomFiliale();
        int fid = filiale.getFid();

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

        //now finally create Verkauf
        //TODO: Parameter anpassen, wenn gewünscht!
        if (mid != -1) {
            Verkauf v = new Verkauf(vid, super.generateRandomDate(2021, 2021), verkaufspreis, fid, mid);
        }

        //insert into Database
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();

            //insert into Verkauf
            String sql = "INSERT INTO verkauf (vid, verkaufsdatum, rechnungsbetrag, fid, mid) " + "VALUES (" + vid + ", '" + super.generateRandomDate(2021, 2021) + "', " + verkaufspreis + " ," + fid + " ," + mid + ");";
            DataGenerator.stmt.execute(sql);
            System.out.println(sql);
            System.out.println("Inserted.");

            //insert into Verkaufsposition
            verkaufsposition.forEach((key, value) -> {
                String sql2 = "INSERT INTO verkaufsposition (menge, vid, pid) " + "VALUES (" + value + ", " + vid + ", " + key + ");";
                try {
                    DataGenerator.stmt.execute(sql2);
                    System.out.println(sql2);
                    System.out.println("Inserted");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateMitarbeiter() throws URISyntaxException, IOException {
        int mid = super.getHighestID("SELECT mid FROM mitarbeiter", "mid")+1;
        Name name = super.generateRandomName();
        Date geburtsdatum = super.generateRandomDate(1970, 2000);
        Double bg = super.generateRandomDecimal(2450, 3560);
        Enum taetigkeit = Taetigkeit.values()[getRandomNumber(Taetigkeit.values().length)];
        int fid = super.getRandomNumber(super.getHighestID("SELECT fid FROM Filiale", "fid"));
        //int maid = super.getRandomNumber(super.getHighestID("SELECT maid FROM Mitarbeiterausweis", "maid")+1);
        Adresse adresse = super.getRandomAdress();

        //look if there are available employees for being vorgesetzter
        int vorgesID;
        try{
            vorgesID = getRandomMitarbeiterFromFiliale(fid).getMid();
        }
        catch (IndexOutOfBoundsException e){
            vorgesID = 0;
        }

        Mitarbeiter mitarbeiter = new Mitarbeiter(mid, name.getVorname(), name.getNachname(), geburtsdatum, adresse.getStrasse(), adresse.getPlz(), adresse.getOrt(), bg, taetigkeit, fid, vorgesID, 1);
        System.out.println(mitarbeiter.getMid() +  mitarbeiter.getVorname() + mitarbeiter.getNachname() + mitarbeiter.getGeburtsdatum() + mitarbeiter.getOrt() + mitarbeiter.getBg() + mitarbeiter.getTaetigkeit() + mitarbeiter.getFid() + mitarbeiter.getVorgesid());

        //TODO : if vorgesID = 0, make it NULL in database
    }
}