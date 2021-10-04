package LokiDB;

import Main.DataGenerator;

import java.util.ArrayList;

public class DelikttypErmittler extends DataGenerator {

    int delikttypid;

    public DelikttypErmittler(double schadenshoehe) {

        ArrayList<Integer> schadenshoehen = new ArrayList<>();
        if (schadenshoehe == 0) {
            schadenshoehen.add(2);
            schadenshoehen.add(3);
            schadenshoehen.add(12);
            schadenshoehen.add(15);
            schadenshoehen.add(16);
            schadenshoehen.add(17);
        } else if (schadenshoehe <= 100) {
            schadenshoehen.add(1);
            schadenshoehen.add(3);
            schadenshoehen.add(8);
            schadenshoehen.add(9);
            schadenshoehen.add(12);
            schadenshoehen.add(15);
        } else if (schadenshoehe <= 1000) {
            schadenshoehen.add(1);
            schadenshoehen.add(3);
            schadenshoehen.add(6);
            schadenshoehen.add(8);
            schadenshoehen.add(9);
            schadenshoehen.add(10);
            schadenshoehen.add(11);
            schadenshoehen.add(12);
            schadenshoehen.add(13);
            schadenshoehen.add(16);
        }
        else {
            //more of one to increase probability of appearing
            schadenshoehen.add(4);
            schadenshoehen.add(5);
            schadenshoehen.add(5);
            schadenshoehen.add(5);
            schadenshoehen.add(6);
            schadenshoehen.add(7);
            schadenshoehen.add(12);
            schadenshoehen.add(13);
            schadenshoehen.add(13);
            schadenshoehen.add(13);
            schadenshoehen.add(13);
            schadenshoehen.add(13);
        }
        this.delikttypid = schadenshoehen.get(super.generateRandomNumber(schadenshoehen.size()));
    }


    public int getDelikttypid() {
        return delikttypid;
    }

    /*
        1	"Sachbeschädigung"
        2	"Hausfriedensbruch"
        3	"Verkehrsgefährdung"
        4	"Geldwäsche"
        5	"Raubüberfall"
        6	"Geldfälschung"
        7	"Schmuggel"
        8	"Körperverletzung"
        9	"Brandstiftung"
        10	"Steuerhinterziehung"
        11	"Entführung"
        12	"Betrug"
        13	"Einbruchdiebstahl"
        14	"Mord"
        15	"Erpressung"
        16	"Urkundenfälschung"
        17	"Beleidigung"
     */
}
