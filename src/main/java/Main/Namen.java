package Main;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class Namen {

    List <String> Nachnamen;
    List <String> VornamenW;
    List <String> VornamenM;

    public Namen() throws URISyntaxException, IOException {
        readNames();
    }

    public void readNames() throws URISyntaxException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL getNachnamen = classLoader.getResource("Nachnamen.csv");
        URL getVornamenW = classLoader.getResource("VornamenW.csv");
        URL getVornamenM = classLoader.getResource("VornamenM.csv");
        if (getNachnamen == null || getVornamenM == null || getVornamenW == null) {
            throw new IllegalArgumentException("File nicht gefunden!");
        }
        else {
            File Nachnamen = new File(getNachnamen.toURI());
            File VornamenW = new File(getVornamenW.toURI());
            File VornamenM = new File(getVornamenM.toURI());

            this.Nachnamen = Files.readAllLines(Nachnamen.toPath());
            this.VornamenW = Files.readAllLines(VornamenW.toPath());
            this.VornamenM = Files.readAllLines(VornamenM.toPath());
        }
    }

    public List<String> getNachnamen() {
        return Nachnamen;
    }

    public List<String> getVornamenW() {
        return VornamenW;
    }

    public List<String> getVornamenM() {
        return VornamenM;
    }

}

