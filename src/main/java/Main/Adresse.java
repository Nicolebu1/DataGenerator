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

    public Adresse() {
        getAdresses();
    }


    public void getAdresses() {
        super.createConnection("jdbc:postgresql://localhost:5433/LokiDB");
        try {
            //get all adresses from loki
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT * FROM adresse;");
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
        super.closeConnection();
    }


    public String getRandomStrasse() {
        return strassen.get(super.getRandomNumber(strassen.size() - 1));
    }


    public Integer getRandomPlz() {
        return plzs.get(super.getRandomNumber(plzs.size() - 1));
    }


    public String getRandomOrt() {
        return orte.get(super.getRandomNumber(orte.size() - 1));
    }


    public BigDecimal getRandomLaengengrad() {
        return laengengrade.get(super.getRandomNumber(laengengrade.size() - 1));
    }


    public BigDecimal getRandomBreitengrad() {
        return breitengrade.get(super.getRandomNumber(breitengrade.size() - 1));
    }
}
