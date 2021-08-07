import java.sql.ResultSet;

public class WinDBoe extends DataGenerator {

    int lastVID;
    String[] Produkte;

    public static void main(String[] args) {
         WinDBoe winDBoe = new WinDBoe();
         winDBoe.getVID();
         winDBoe.getProdukte();
    }

    public WinDBoe(){
        super.createConnection("jdbc:postgresql://localhost:5432/WinDBoe");
    }

    public void getVID() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM verkauf;");
            int i = 0;
            while (rs.next()) {
                i++;
            }
            lastVID = i;
            System.out.println(i);
            rs.close();
            closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void getProdukte(){
        try{
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT pid FROM verkauf;");
            int i = 0;
            while (rs.next()) {
               //Produkte[i] = rs.last();
            }
            lastVID = i;
            System.out.println(i);
            rs.close();
            closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
