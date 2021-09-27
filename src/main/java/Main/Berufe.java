package Main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class Berufe {

    List<String> Berufe;

    public Berufe() throws URISyntaxException, IOException {
        readBerufe();
    }

    public void readBerufe() throws URISyntaxException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL getBerufe = classLoader.getResource("Berufe.csv");
        if (getBerufe == null) {
            throw new IllegalArgumentException("File nicht gefunden!");
        }
        else {
            File Berufe = new File(getBerufe.toURI());
            this.Berufe = Files.readAllLines(Berufe.toPath());
        }
    }

    public List<String> getBerufe() {
        return Berufe;
    }
}
