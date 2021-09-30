package LokiDB;

import Main.DataGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public class Ermittler extends DataGenerator {

    Person ermittler;
    String verwgr;
    String dstgr;
    double bg;
    int vorgesID;
    int teamID;


    public Ermittler(int persid, String verwgr, String dstgr, int vorgesID) throws URISyntaxException, IOException, ParseException {
    }


    public Ermittler(int persid, String verwgr, String dstgr) throws URISyntaxException, IOException, ParseException {
    }


    String generateVerwendungsgruppe(){
        String[] verwendungsgruppe = {"E2b", "E2a", "E1"};
        return verwendungsgruppe[super.generateRandomNumber(2)];
    }
}
