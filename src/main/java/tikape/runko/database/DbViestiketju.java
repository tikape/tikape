package tikape.runko.database;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbViestiketju {

    private String databaseAddress;

    public DbViestiketju(String databaseAddress) throws ClassNotFoundException {
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
    public void add(String otsikko, String nimimerkki, int aihe_id) {
        //Lisätään timestamp
        Date date= new Date();
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

        lista.add("CREATE TABLE Viestiketju (id integer PRIMARY KEY AUTOINCREMENT,otsikko varchar(200) NOT NULL,aika timestamp NOT NULL,nimimerkki varchar(50) NOT NULL,aihealue_id integer NOT NULL,FOREIGN KEY(aihealue_id) REFERENCES Aihealue(id));");
         //Lisätään arvoja
         // CREATE TABLE Viestiketju (id integer PRIMARY KEY AUTOINCREMENT,otsikko varchar(200) NOT NULL,aika timestamp NOT NULL,nimimerkki varchar(50) NOT NULL,aihealue_id integer NOT NULL,FOREIGN KEY(aihealue_id) REFERENCES Aihealue(id));
        return lista;
    }
}
