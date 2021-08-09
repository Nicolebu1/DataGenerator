import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

import static java.sql.Types.NULL;

public class WinDBoe extends DataGenerator {

    ArrayList Filialen = new ArrayList();
    ArrayList Mitarbeiter = new ArrayList();
    ArrayList Produkte = new ArrayList();
    ArrayList Verkäufe = new ArrayList();

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

    //Um neue ID zu ermitteln, die höchste ID auslesen
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
        if(highestID == -1){
            throw new NullPointerException("Parameter checken!");
        }
        return highestID;
    }

    //Relevante Attribute von Klassen auslesen & speichern
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
                }
                else{
                    Mitarbeiter.add(new Mitarbeiter(mid));
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void createVerkauf() {
        int vid = getHighestID("SELECT * FROM verkauf;", "vid") + 1;
        int fid = Collections.shuffle(Filialen).getFID();


        //TODO: Parameter anpassen, wenn gewünscht!
        Verkäufe.add(new Verkauf(vid, super.generateRandomDate(2021, 2021), super.generateRandomDecimal(10.0, 35.0), ));
    }
}


