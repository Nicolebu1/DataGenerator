package Main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Adresse {

    public void scanAdresses() throws FileNotFoundException {
        File getAdressen = new File("/test/example.csv");
        Scanner sc = new Scanner(getAdressen);
        sc.useDelimiter(",");
        while (sc.hasNext()) {
            System.out.print(sc.next() + " | ");
        }
        sc.close();
    }
}
