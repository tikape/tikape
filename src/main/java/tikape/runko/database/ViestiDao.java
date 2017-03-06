package tikape.runko.database;

import java.sql.*;
import java.util.*;
import tikape.runko.domain.Viesti;

public class ViestiDao {

    private DbAihealue database;

    public ViestiDao(DbAihealue database) {
        this.database = database;
    }

    public String lisaa(String viesti, int id, String kayttaja) throws Exception {
        
        if (viesti.trim().isEmpty()) {
            return "";
        }
        if (kayttaja.trim().isEmpty()) {
            return "";
        }
        
        Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Viesti (viestiNimi, viestiketju_id, kayttaja, aika) VALUES ('" + viesti + "', " + id + " , '" + kayttaja + "' , CURRENT_TIMESTAMP);");
        stmt.execute();

        conn.close();
        return "";
    }

    public Viesti haeYksi(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = " + key + "");

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String viesti = rs.getString("viestiNimi");
        String kayttaja = rs.getString("kayttaja");
        Timestamp aika = rs.getTimestamp("aika");
        int viestiketju_id = rs.getInt("viestiketju_id");

        Viesti a = new Viesti(id, viesti, kayttaja, aika, viestiketju_id);

        rs.close();
        stmt.close();
        connection.close();

        return a;
    }

    public List<Viesti> kaikki(Integer id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti Where viestiKetju_id = " + id + " ORDER BY aika ASC");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            int idV = rs.getInt("id");
            String viesti = rs.getString("viestiNimi");
            String kayttaja = rs.getString("kayttaja");
            Timestamp aika = rs.getTimestamp("aika");
            int viestiketju_id = rs.getInt("viestiketju_id");

            viestit.add(new Viesti(idV, viesti, kayttaja, aika, viestiketju_id));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

}
