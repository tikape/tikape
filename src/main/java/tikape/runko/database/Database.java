package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }
    //Tietokantaan lisäys
    public void add(String x) {

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();
            
            // suoritetaan komennot
            
            String lause=("INSERT INTO Aihealue (nimi) VALUES ( '" + x + "');") ;       
            System.out.println("Running command >> " + lause);
            st.executeUpdate(lause);
            

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }
    
    public void addViestiketju(String otsikko, String nimimerkki, int aihe_id) {
        //Lisätään timestamp
        java.util.Date date = new java.util.Date();
        long time = date.getTime();
        Timestamp a = new Timestamp(time);
        String lause=("INSERT INTO Viestiketju (otsikko,aika,nimimerkki,aihealue_id) VALUES ( '" + otsikko + "','"+a + "' ,'" + nimimerkki + "','"+ aihe_id +"');") ;
        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();
            st.executeUpdate(lause);

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }    

    public void addViesti(String sisalto, String nimimerkki, String viestiketju_id) {
        //Lisätään timestamp
        java.util.Date date = new java.util.Date();
        long time = date.getTime();
        Timestamp a = new Timestamp(time);
        
        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            PreparedStatement st = conn.prepareStatement("INSERT INTO Viesti (sisalto, aika, nimimerkki, viestiketju_id) VALUES (?, ?, ?, ?);");
            
            st.setString(1, sisalto);
            st.setTimestamp(2, a);
            st.setString(3, nimimerkki);
            st.setString(4, viestiketju_id);
 
            
            System.out.println(st.toString());
            
            st.execute();

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }
    
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        lista.add(" CREATE TABLE Aihealue (id integer PRIMARY KEY AUTOINCREMENT, nimi varchar(50) NOT NULL);");
        
        return lista;
    }
}
