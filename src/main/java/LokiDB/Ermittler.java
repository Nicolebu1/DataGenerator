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

    String[] dienstgrade = {"Inspektor", "Revierinspektor",}

    //define BG and Dstgr
        switch (verwgr) {
        case "E2b": dstgr = getRandomDienstgrad(verwgr)
    }

    public Ermittler() throws URISyntaxException, IOException, ParseException {
        ermittler = new Person();
        verwgr = verwendungsgruppe[super.getRandomNumber(0, 2)];
    }

    String generateVerwendungsgruppe(){
        String[] verwendungsgruppe = {"E2b", "E2a", "E1"};
    }
}
