/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viestiketju;
import java.util.*;
import java.sql.*;
/**
 *
 * @author Phoenix
 */
public class ViestiketjuDao implements Dao<Viestiketju ,Integer> {
    private DbAihealue database;

    public ViestiketjuDao(DbAihealue database) {
        this.database = database;
    }

    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        Timestamp aika= rs.getTimestamp("aika");
        String nimimerkki = rs.getString("nimimerkki");
        Integer aihealue_id = rs.getInt("aihealue_id");

        Viestiketju o = new Viestiketju(id, otsikko, aika, nimimerkki,aihealue_id);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    
    public Viestiketju findOneWith(String key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE nimi = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        Timestamp aika= rs.getTimestamp("aika");
        String nimimerkki = rs.getString("nimimerkki");
        Integer aihealue_id = rs.getInt("aihealue_id");

        Viestiketju o = new Viestiketju(id, otsikko, aika, nimimerkki,aihealue_id);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    @Override
    public List<Viestiketju> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue");

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> aiheet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            Timestamp aika= rs.getTimestamp("aika");
            String nimimerkki = rs.getString("nimimerkki");
            Integer aihealue_id = rs.getInt("aihealue_id");

        aiheet.add( new Viestiketju(id, otsikko, aika, nimimerkki,aihealue_id));           


        }

        rs.close();
        stmt.close();
        connection.close();

        return aiheet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    
    
   
}
