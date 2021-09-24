package LokiDB;

import Main.DataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;

public class LokiDB extends DataGenerator {

    public void generateRandomPerson() throws URISyntaxException, IOException {
        int PersID = super.getHighestID("SELECT * FROM Person;", "persid") + 1;
        char sex = super.generateRandomSex();
        String vorname = super.generateRandomVorname(sex);
        String nachname = super.generateRandomNachname();
        Date geburtsdatum = super.generateRandomDate(1940, 2001);
        String telefon = super.generateTelNr();
        String familienstand = super.genererateRandomFamilienstand();
        String landID = "AUT";
        int adressenID = super.getRandomNumber(super.getHighestID("SELECT * FROM adresse", "adressenid"));
    }

    public void generateGeschaedigter(){

    }
}
