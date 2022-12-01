package LokiDB;

import Main.DataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;

public class LokiDB extends DataGenerator {

    public LokiDB() {
        super.createConnection("jdbc:postgresql://localhost:5433/LokiDB");
    }

    public void generateRandomPerson() throws URISyntaxException, IOException {
        int PersID = super.getHighestID("SELECT * FROM Person;", "persid") + 1;
        char geschlecht = super.generateRandomSex();
        String vorname = super.generateRandomVorname(geschlecht);
        String nachname = super.generateRandomNachname();
        Date geburtsdatum = super.generateRandomDate(1940, 2001);
        String telnr = super.generateTelNr();
        String familienstand = super.generateRandomMaritalStatus();
        String sql = "INSERT INTO person (persid, vorname, nachname, geburtsdatum, telnr, geschlecht, familienstand, landid) VALUES (" + PersID + ", '" + vorname + "', '" + nachname + "', '" + geburtsdatum + "', '" + telnr + "', '" + geschlecht + "', '" + familienstand + "', 'AUT');";

        DataGenerator.sendToDatabase(sql);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        LokiDB lokiDB = new LokiDB();
        for (int i = 0; i < 100; i++) {
            lokiDB.generateRandomPerson();
        }
    }
}
