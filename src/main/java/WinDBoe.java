import java.sql.*;

import static java.sql.Types.NULL;

public class WinDBoe extends DataGenerator {

    int highestVID;
    Filiale[] Filialen;
    Mitarbeiter[] Mitarbeiter;
    Produkt[] Produkte;

    public static void main(String[] args) {
        WinDBoe winDBoe = new WinDBoe();
    }

    public WinDBoe() {
        super.createConnection("jdbc:postgresql://localhost:5432/WinDBoe");
        getVID();
        getFilialen();
        getMitarbeiter();
        getProdukte();
        System.out.println("hi");
        super.closeConnection();
    }

    //Um neue VerkaufsID zu ermitteln, die höchste ID auslesen
    public void getVID() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM verkauf;");
            int i = 0;
            highestVID = 0;
            while (rs.next()) {
                if (rs.getInt("vid") > highestVID) {
                    highestVID = rs.getInt("vid");
                }
                i++;
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    //Relevante Attribute von Klassen auslesen & speichern
    public void getProdukte() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pid, verkaufspreis FROM produkt;");
            int i = 0;
            while (rs.next()) {
                int pid = rs.getInt("pid");
                float preis = rs.getFloat("verkaufspreis");
                Produkte[i] = new Produkt(pid, preis);
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
                Filialen[i] = new Filiale(fid);
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
                    Mitarbeiter[i] = new Mitarbeiter(mid, fid);
                }
                else{
                    Mitarbeiter[i] = new Mitarbeiter(mid);
                }
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public int getHighestVID() {
        return highestVID;
    }

    public void createVerkauf() {
        int vid = getHighestVID() + 1;

    }
}


