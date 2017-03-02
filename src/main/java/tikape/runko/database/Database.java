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

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        //Luodaan Tietokannat
        lista.add(" CREATE TABLE Viestiketju\n" + "(\n" + "id integer PRIMARY KEY,\n" + "otsikko varchar(200) NOT NULL,\n" + "aika timestamp NOT NULL,\n" + "nimimerkki varchar(50) NOT NULL,\n" + "aihealue_id integer NOT NULL,\n" + "FOREIGN KEY(aihealue_id) REFERENCES Aihealue(id)\n" + ");");
        lista.add(" CREATE TABLE Aihealue\n" + "(\n" + "id integer PRIMARY KEY,\n" + "nimi varchar(50) NOT NULL,\n" + "sisalto varchar(999)  \n" + ");");
        lista.add("CREATE TABLE Viesti\n" + "(\n" + "id integer PRIMARY KEY,\n" + "sisalto varchar(999),\n" + "aika timestamp NOT NULL,\n" + "nimimerkki varchar(50) NOT NULL,\n" + "viestiketju_id integer NOT NULL,\n" + "FOREIGN KEY(viestiketju_id) REFERENCES Viestiketju(id)\n" + ");");
        //Lisätään arvoja
        lista.add("INSERT INTO Aihealue  VALUES ('1', 'AIHE 1','SISAÄLTÖÄÄÄÄ');");
        lista.add("INSERT INTO Aihealue  VALUES ('2', 'AIHE 2','TOISTA SISAÄLTÖÄ');");
        lista.add("INSERT INTO Viesti(id,nimi,otsikko)  VALUES ('1', 'sisältöä','SISAÄLTÖÄÄÄÄ');");
        return lista;
    }
}
