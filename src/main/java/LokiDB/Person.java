package LokiDB;

import Main.DataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.ParseException;

public class Person extends DataGenerator {

    int PersID;
    char sex;
    String vorname;
    String nachname;
    Date geburtsdatum;
    String telefon;
    String familienstand;
    String landID;
    int adressenID;

    public Person(int adressenID) throws URISyntaxException, IOException, ParseException {
        this.PersID = super.getHighestID("SELECT * FROM Person;", "persid") + 1;
        this.sex = super.generateRandomSex();
        this.vorname = super.generateRandomVorname(sex);
        this.nachname = super.generateRandomNachname();
        this.geburtsdatum = super.generateRandomDate(1940, 2001);
        this.telefon = super.generateTelNr();
        this.familienstand = super.generateRandomMaritalStatus();
        this.landID = "AUT";
        this.adressenID = adressenID;
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
