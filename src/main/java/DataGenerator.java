import java.sql.*;

public class DataGenerator {

    static Connection c = null;
    static Statement stmt = null;

    public void createConnection(String url) {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(url,
                            "postgres", "0000");
            c.setAutoCommit(false);
            System.out.println("Connection successful");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void closeConnection() {
        try {
            stmt.close();
            c.close();
            System.out.println("Connection closed");
        } catch (Exception e) {

        }
    }

    public void getVID() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM verkauf;");
            int i = 0;
            while (rs.next()) {
                i++;
            }
            System.out.println(i);
            rs.close();
            closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void createVerkauf(){

    }
}


            /*
            while ( rs.next() ) {
                int id = rs.getInt("fid");
                String  strasse = rs.getString("strasse");
                int plz  = rs.getInt("plz");
                String  ort = rs.getString("ort");
                System.out.println( "ID = " + id );
                System.out.println( "Strasse = " + strasse );
                System.out.println( "PLZ = " + plz );
                System.out.println( "Ort = " + ort );
                System.out.println();
            }
            */



