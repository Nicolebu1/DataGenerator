package LokiDB;

import Main.DataGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class BeuteErmittler extends DataGenerator {
    ArrayList<Integer> delikte = new ArrayList<>();
    ArrayList<Beute> beuten = new ArrayList<>();
    int deliktid;
    int schadenshoehe;
    int number;

    public BeuteErmittler() {
        findSuitableDelikt();
        generateNumberOfBeute(schadenshoehe);

    }

    public void findSuitableDelikt() {
        try {
            DataGenerator.stmt = DataGenerator.c.createStatement();
            ResultSet rs = DataGenerator.stmt.executeQuery("SELECT deliktid FROM delikt WHERE delikttypid IN (5, 7, 13) AND schadenshoehe > 0;");
            while (rs.next()) {
                delikte.add(rs.getInt("deliktid"));
            }
            deliktid = delikte.get(super.generateRandomNumber(delikte.size() - 1));
            rs = DataGenerator.stmt.executeQuery("SELECT schadenshoehe FROM delikt WHERE deliktid = " + deliktid + ";");
            if (rs.next()) {
                this.schadenshoehe = rs.getInt("schadenshoehe");
            }
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void generateNumberOfBeute(int schadenshoehe) {
        int noBeute = 1;
        if (schadenshoehe <= 100) {
            noBeute = 1;
        }
        if (schadenshoehe > 100 && schadenshoehe <= 500) {
            noBeute = super.generateRandomNumber(1, 3);
        } else if (schadenshoehe > 500 && schadenshoehe < 15000) {
            noBeute = super.generateRandomNumber(3, 5);
        } else if (schadenshoehe > 15000) {
            noBeute = super.generateRandomNumber(5, 10);
        }

        //Set values for Beute
        Random random = new Random();
        boolean sachbeschädigung = random.nextBoolean();
        double maxValue;
        if (sachbeschädigung == true) {
            maxValue = this.schadenshoehe / (noBeute + 1);
        } else {
            maxValue = this.schadenshoehe / noBeute;
        }

        double counter = 0;
        for (int i = 0; i < noBeute; i++) {
            double wert = super.generateRandomDecimal(1, maxValue);
            counter = counter + wert;
            beuten.add(new Beute(wert, this.deliktid));
        }
        if (sachbeschädigung == false){
            beuten.add(new Beute(schadenshoehe - counter, this.deliktid, "Bargeld"));
        }
    }

    public ArrayList<Beute> getBeuten() {
        return beuten;
    }
}
