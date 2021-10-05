package LokiDB;

import Main.Adresse;
import Main.DataGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdressenErmittler extends DataGenerator {

    int choosenID;

    ArrayList<Adresse> adressen;
    ArrayList<Integer> dstelladdi = new ArrayList<>();
    ArrayList<Integer> personaddi = new ArrayList<>();
    ArrayList<Integer> deliktaddi = new ArrayList<>();
    Adresse adresse;

    public AdressenErmittler(int type) throws Exception {

        //type 0 = Person
        //type 1 = Dienststelle
        //type 2 = Delikt

        //Initialize
        adresse = new Adresse();
        adressen = adresse.getAdressen();
        getAdressesSetsFromDB();

        switch (type) {
            case 0:
                this.choosenID = generatePersonAddi();
                break;

            case 1:
                this.choosenID = generateDstellenAddi();
                break;

            case 2:
                this.choosenID = generateDeliktAddi();
                break;

            default:
                throw new Exception("Falscher Adressentyp!");
        }
    }


    public void getAdressesSetsFromDB() throws SQLException {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM dienststelle;");
            while (rs.next()) {
                dstelladdi.add(rs.getInt("adressenid"));
            }
            rs.close();
            rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM person;");
            while (rs.next()) {
                personaddi.add(rs.getInt("adressenid"));
            }
            rs.close();
            rs = DataGenerator.stmt.executeQuery("SELECT adressenid FROM delikt;");
            while (rs.next()) {
                deliktaddi.add(rs.getInt("adressenid"));
            }
            rs.close();
        } catch (Exception e) {}
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
                adressid = super.generateRandomNumber(adressen.size()-1);
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
                adressid = super.generateRandomNumber(adressen.size()-1);
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
                adressid = super.generateRandomNumber(adressen.size()-1);
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


    public int getChoosenID() {
        return choosenID;
    }


    public ArrayList<Adresse> getAdressen() {
        return adressen;
    }
}
