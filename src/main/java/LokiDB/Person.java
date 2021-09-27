package LokiDB;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.ParseException;

public class Person extends LokiDB{

    int PersID;
    char sex;
    String vorname;
    String nachname;
    Date geburtsdatum;
    String telefon;
    String familienstand;
    String landID;
    int adressenID;

    public Person() throws URISyntaxException, IOException, ParseException {
        super();
        PersID = super.getHighestID("SELECT * FROM Person;", "persid") + 1;
        sex = super.generateRandomSex();
        vorname = super.generateRandomVorname(sex);
        nachname = super.generateRandomNachname();
        geburtsdatum = super.generateRandomDate(1940, 2001);
        telefon = super.generateTelNr();
        familienstand = super.genererateRandomFamilienstand();
        landID = "AUT";
        adressenID = super.getRandomNumber(super.getHighestID("SELECT * FROM adresse", "adressenid"));
    }

    public int getPersID() {
        return PersID;
    }

    public char getSex() {
        return sex;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getFamilienstand() {
        return familienstand;
    }

    public String getLandID() {
        return landID;
    }

    public int getAdressenID() {
        return adressenID;
    }
}
