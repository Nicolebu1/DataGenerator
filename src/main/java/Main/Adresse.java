package Main;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Adresse extends DataGenerator {

    ArrayList<String> strassen = new ArrayList<>();
    ArrayList<Integer> plzs = new ArrayList<>();
    ArrayList<String> orte = new ArrayList<>();
    ArrayList<BigDecimal> laengengrade = new ArrayList<>();
    ArrayList<BigDecimal> breitengrade = new ArrayList<>();

    String strasse;
    Integer plz;
    String ort;
    BigDecimal laengengrad;
    BigDecimal breitengrad;

    public Adresse() {
        getAdresses();
    }

    /*
    public Adresse(String strasse, Integer plz, String ort, BigDecimal laengengrad, BigDecimal breitengrad) {
        getAdresses();
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.laengengrad = laengengrad;
        this.breitengrad = breitengrad;
    }
    */

    public void getAdresses() {
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
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public String setRandomStrasse() {
        this.strasse = strassen.get(super.getRandomNumber(strassen.size() - 1));
        return this.strasse;
    }

    public String getStrasse() {
        return strasse;
    }

    public Integer setRandomPlz() {
        this.plz = plzs.get(super.getRandomNumber(plzs.size() - 1));
        return this.plz;
    }

    public Integer getPlz() {
        return plz;
    }

    public String setRandomOrt() {
        this.ort = orte.get(super.getRandomNumber(orte.size() - 1));
        return this.ort;
    }

    public String getOrt() {
        return ort;
    }

    public BigDecimal setRandomLaengengrad() {
        this.laengengrad = laengengrade.get(super.getRandomNumber(laengengrade.size() - 1));
        return this.laengengrad;
    }

    public BigDecimal getLaengengrad() {
        return laengengrad;
    }

    public BigDecimal setRandomBreitengrad() {
        this.breitengrad = breitengrade.get(super.getRandomNumber(breitengrade.size() - 1));
        return this.breitengrad;
    }

    public BigDecimal getBreitengrad() {
        return breitengrad;
    }
}
