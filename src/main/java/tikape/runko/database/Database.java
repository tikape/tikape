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
//        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();
            
            // suoritetaan komennot
            
            String lause=("INSERT INTO Aihealue (nimi,sisalto) VALUES ( '" + x + "', '/viestiketjut?id=" + x + "');") ;       
            System.out.println("Running command >> " + lause);
            st.executeUpdate(lause);
            

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }
    
    public void addViestiketju(String otsikko, String nimimerkki, int aihe_id) {
        //Lisätään timestamp
        java.util.Date date= new java.util.Date();
        long time = date.getTime();
        Timestamp a= new Timestamp(time);
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


    
    
    
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        //Luodaan Tietokannat

        lista.add(" CREATE TABLE Aihealue (id integer PRIMARY KEY AUTOINCREMENT, nimi varchar(50) NOT NULL, sisalto varchar(999));");
         //Lisätään arvoja
         
         
         /*        lista.add("INSERT INTO Aihealue (nimi,sisalto) VALUES ( 'AIHE 1','SISAÄLTÖÄÄÄÄ');");
        lista.add("INSERT INTO Aihealue (nimi,sisalto) VALUES ( 'AIHE 2','TOISTA SISAÄLTÖÄ');");
        lista.add("INSERT INTO Aihealue (nimi,sisalto) VALUES ( 'AIHE 3','SISAÄLTÖÄÄÄÄ');");
        lista.add("INSERT INTO Aihealue (nimi,sisalto) VALUES ( 'AIHE 4','TOISTA SISAÄLTÖÄ');");
        lista.add("INSERT INTO Aihealue (nimi,sisalto) VALUES ( 'AIHE 5','SISAÄLTÖÄÄÄÄ');");
        lista.add("INSERT INTO Aihealue (nimi,sisalto) VALUES ( 'AIHE 6','TOISTA SISAÄLTÖÄ');");         
*/
    return lista;
    }
}
