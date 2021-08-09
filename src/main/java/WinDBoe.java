import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import static java.sql.Types.NULL;

public class WinDBoe extends DataGenerator {

    ArrayList<Filiale> Filialen = new ArrayList<>();
    ArrayList<Produkt> Produkte = new ArrayList<>();
    ArrayList<Verkauf> Verkäufe = new ArrayList<>();
    ArrayList<Mitarbeiter> Mitarbeiter = new ArrayList<>();

    public static void main(String[] args) {
        WinDBoe winDBoe = new WinDBoe();
    }

    public WinDBoe() {
        super.createConnection("jdbc:postgresql://localhost:5432/WinDBoe");
        getFilialen();
        getMitarbeiter();
        getProdukte();
        super.closeConnection();
    }

    //Find latest ID
    public int getHighestID(String query, String column) {
        int highestID = -1;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                if (rs.getInt(column) > highestID) {
                    highestID = rs.getInt(column);
                }
                i++;
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        if (highestID == -1) {
            throw new NullPointerException("Parameter checken!");
        }
        return highestID;
    }

    //Get data from database
    public void getProdukte() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pid, verkaufspreis FROM produkt;");
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
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT fid FROM filiale;");
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
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT mid, fid FROM mitarbeiter;");
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

    //Insert data in database
    public void createVerkauf() {
        int vid = getHighestID("SELECT * FROM verkauf;", "vid") + 1;

        //choose Filiale
        Filiale filiale = Filialen.get(getRandomNumber(Filialen.size() - 1));
        int fid = filiale.getFid();

        //select all Mitarbeiter from random Filiale
        ArrayList<Mitarbeiter> FilialenMitarbeiter = new ArrayList<>();
        for (Mitarbeiter m : Mitarbeiter) {
            if (m.getFid() == fid) {
                FilialenMitarbeiter.add(m);             //note: fid is not null in database
            }
        }

        //choose Mitarbeiter
        Mitarbeiter m = FilialenMitarbeiter.get(getRandomNumber(FilialenMitarbeiter.size() - 1));
        int mid = m.getMid();

        //choose Produkte
        int numberOfProdukte = 1 + getRandomNumber(14);         //note: Produkteanzahl can't be 0
        ArrayList<Produkt> buyed = new ArrayList<>();

        for (int i = 0; i < numberOfProdukte; i++) {
            buyed.add(Produkte.get(getRandomNumber(Produkte.size() - 1)));
        }

        for (Produkt p : buyed) {
            int count = Collections.frequency(buyed, p.getPid());
        }


        //Now finally create Verkauf
        //TODO: Parameter anpassen, wenn gewünscht!
        if (mid != -1) {
            Verkauf v = new Verkauf(vid, super.generateRandomDate(2021, 2021), super.generateRandomDecimal(10.0, 35.0), fid, mid);
        }
    }
}


