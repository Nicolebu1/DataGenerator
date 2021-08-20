package Main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

public class DataGenerator {

    public static Connection c = null;
    public static Statement stmt = null;


    public void createConnection(String url) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(url,
                            "postgres", "0000");
            c.setAutoCommit(true);
            System.out.println("Connection successful");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void closeConnection() {
        try {
            stmt.close();
            c.close();
            System.out.println("Connection closed");
        } catch (Exception e) {

        }
    }


    //get latest ID
    public int getHighestID(String query, String column) {
        int highestID = -1;
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery(query);
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


    //--------------Usefull generators for all Databases-----------------------

    public Date generateRandomDate(int min, int max) {
        int startYear = min;
        int endYear = max;
        long start = Timestamp.valueOf(startYear + 1 + "-1-1 0:0:0").getTime();
        long end = Timestamp.valueOf(endYear + "-1-1 0:0:0").getTime();
        long ms = (long) ((end - start) * Math.random() + start);
        return new Date(ms);
    }


    public double generateRandomDecimal(double min, double max) {
        return min + Math.random() * (max - min);
    }


    public int getRandomNumber(int max) {
        return (int) (Math.random() * max);
    }


    public Adresse getRandomAdress() {
        Adresse newAdress = new Adresse();
        newAdress.setRandomStrasse();
        newAdress.setRandomOrt();
        newAdress.setRandomPlz();
        newAdress.setRandomLaengengrad();
        newAdress.setRandomBreitengrad();
        return newAdress;
    }


    public Name generateRandomName(char g) throws URISyntaxException, IOException {
        Namen namen = new Namen();

        String vorname = null;
        String nachname;

        if (g == 'f' || g == 'F' || g == 'w' || g == 'W') {
            vorname = namen.getVornamenW().get(getRandomNumber(namen.getVornamenW().size() - 1));
        } else if (g == 'm' || g == 'M') {
            vorname = namen.getVornamenM().get(getRandomNumber(namen.getVornamenM().size() - 1));
        }
        nachname = namen.getNachnamen().get(getRandomNumber(namen.getNachnamen().size() - 1));

        return new Name(vorname, nachname);
    }


    public Name generateRandomName() throws URISyntaxException, IOException {
        Namen namen = new Namen();

        String vorname = null;
        String nachname;

        int g = getRandomNumber(1);

        //Todo: Option für Nicht Binäre Namen!

        if (g == 0) {
            vorname = namen.getVornamenW().get(getRandomNumber(namen.getVornamenW().size() - 1));
        } else {
            vorname = namen.getVornamenM().get(getRandomNumber(namen.getVornamenM().size() - 1));
        }
        nachname = namen.getNachnamen().get(getRandomNumber(namen.getNachnamen().size() - 1));

        return new Name(vorname, nachname);
    }
}



