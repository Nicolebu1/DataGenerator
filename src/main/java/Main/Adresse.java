package Main;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Adresse extends DataGenerator {

    //generating randoms
    ArrayList<String> strassen = new ArrayList<>();
    ArrayList<Integer> plzs = new ArrayList<>();
    ArrayList<String> orte = new ArrayList<>();
    ArrayList<BigDecimal> laengengrade = new ArrayList<>();
    ArrayList<BigDecimal> breitengrade = new ArrayList<>();

    //Loki
    ArrayList<Adresse> adressen = new ArrayList<>();
    int adressid;
    String strasse;
    String ort;
    int plz;

    public Adresse() {
        getAdressesFromDB();
    }

    public Adresse(int adressid, String strasse, String ort, int plz) {
        this.adressid = adressid;
        this.strasse = strasse;
        this.ort = ort;
        this.plz = plz;
    }


    public void getAdressesFromDB() {
        super.createConnection("jdbc:postgresql://localhost:5432/LokiDB");
        try {
            //get all adresses from loki
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT * FROM adresse;");
            int i = 0;
            while (rs.next()) {
                strassen.add(rs.getString("strasse"));
                plzs.add(rs.getInt("plz"));
                orte.add(rs.getString("ort"));
                laengengrade.add(rs.getBigDecimal("laengengrad"));
                breitengrade.add(rs.getBigDecimal("breitengrad"));
                adressen.add(new Adresse(rs.getInt("adressenid"), rs.getString("strasse"), rs.getString("ort"), rs.getInt("plz")));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public ArrayList<Adresse> getAdressen() {
        return adressen;
    }


    //-------------------------------generate random adresses for other DBs than LokiDB--------------------------------

    public String getRandomStrasse() {
        return strassen.get(super.generateRandomNumber(strassen.size() - 1));
    }


    public Integer getRandomPlz() {
        return plzs.get(super.generateRandomNumber(plzs.size() - 1));
    }


    public String getRandomOrt() {
        return orte.get(super.generateRandomNumber(orte.size() - 1));
    }


}
