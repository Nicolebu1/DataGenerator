package Main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;

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

    /*
    boolean askDB(String query) throws SQLException {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery(query);
            if (rs.isBeforeFirst()) {
                return true;
            }
        }
        catch (Exception e){}
        return false;
    }
    */


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
        long start = Timestamp.valueOf(startYear + "-1-1 0:0:0").getTime();
        long end = Timestamp.valueOf(endYear + "-1-1 0:0:0").getTime();
        long ms = (long) ((end - start) * Math.random() + start);
        return new Date(ms);
    }


    public Date generateFollowUpDate(Date date1) throws ParseException {
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            cal.add(Calendar.DATE, generateRandomNumber(31));
            return new java.sql.Date(cal.getTimeInMillis());
        }
    }


    public double generateRandomDecimal(double min, double max) {
        return min + Math.random() * (max - min);
    }


    public int generateRandomNumber(int max) {
        return (int) (Math.random() * max);
    }


    public int generateRandomNumber(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }


    public String generateRandomNachname() throws URISyntaxException, IOException {
        Namen namen = new Namen();
        return namen.getNachnamen().get(generateRandomNumber(namen.getNachnamen().size() - 1));
    }


    //Todo: Option for non-Binary names!
    public String generateRandomVorname(char g) throws URISyntaxException, IOException {
        Namen namen = new Namen();

        if (g == 'f' || g == 'F' || g == 'w' || g == 'W') {
            return namen.getVornamenW().get(generateRandomNumber(namen.getVornamenW().size() - 1));
        } else if (g == 'm' || g == 'M') {
            return namen.getVornamenM().get(generateRandomNumber(namen.getVornamenM().size() - 1));
        }
        return null;
    }


    public String generateRandomVorname() throws URISyntaxException, IOException {
        Namen namen = new Namen();

        //decide sex
        int g = generateRandomNumber(1);

        if (g == 0) {
            return namen.getVornamenW().get(generateRandomNumber(namen.getVornamenW().size() - 1));
        } else {
            return namen.getVornamenM().get(generateRandomNumber(namen.getVornamenM().size() - 1));
        }
    }


    public String generateEmail(String vorname, String nachname) {
        return vorname + '.' + nachname + "@email.db";
    }


    public String generateEmail(String vorname, String nachname, String endung) {
        return vorname + '.' + nachname + "@" + endung + ".db";
    }


    public String generateTelNr() {
        int number = 10 + generateRandomNumber(89);
        int number1 = 100 + generateRandomNumber(899);
        int number2 = 10 + generateRandomNumber(89);
        int number3 = 10 + generateRandomNumber(89);
        return "06" + number + " " + number1 + " " + number2 + " " + number3;
    }

    public char generateRandomSex() {

        //Todo: Implement non-binary options

        if (this.generateRandomNumber(1) == 0) {
            return 'w';
        } else //if (this.getRandomNumber(2) == 1)
        {
            return 'm';
        }
    }


    enum Familienstand {
        ledig, verheiratet, geschieden, verwittwet, in_eingetragener_Partnerschaft, eingetragene_Lebenspartnerschaft_aufgehoben, eingetragener_Lebenspartner_verstorben,
    }


    public String genererateRandomFamilienstand() {
        return Familienstand.values()[generateRandomNumber(Familienstand.values().length)].toString();
    }


    public String generateRandomBeruf() throws URISyntaxException, IOException {
        Berufe beruf = new Berufe();
        return beruf.getBerufe().get(generateRandomNumber(beruf.getBerufe().size() - 1));
    }

}



