package Main;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

public class Namen {

    //Todo: Liste mit genderneutralen Namen fehlt noch!
    public static void main(String[] args) throws URISyntaxException, IOException {
        Namen name = new Namen();
    }

    public Namen() throws URISyntaxException, IOException {
        readNames();
    }

    public void readNames() throws URISyntaxException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL Nachname = classLoader.getResource("Nachnamen.csv");
        URL VornameW = classLoader.getResource("VornamenW.csv");
        URL VornameM = classLoader.getResource("VornamenM.csv");
        if (Nachname == null || VornameM == null || VornameW == null) {
            throw new IllegalArgumentException("file not found!");
        }
        else {
            File Nachnamen = new File(Nachname.toURI());
            File VornamenW = new File(VornameW.toURI());
            File VornamenM = new File(VornameM.toURI());

            String surnames = new String(Files.readAllBytes(Nachnamen.toPath()));
            String firstnamesF = new String(Files.readAllBytes(VornamenW.toPath()));
            String firstnamesM = new String(Files.readAllBytes(VornamenM.toPath()));
            System.out.println(surnames);
            System.out.println(firstnamesM);
            System.out.println(firstnamesF);
        }
    }
}

