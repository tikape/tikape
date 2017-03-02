package tikape.runko.domain;
import java.util.*;
import java.sql.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Phoenix
 */
public class Viestiketju {
    private Integer id;
    private String otsikko;
    private Timestamp aika;
    private String nimimerkki;
    private Integer aihealue_id;
    

    public Viestiketju(Integer id, String nimi, Timestamp sis, String a, Integer c) {
        this.id = id;
        this.otsikko = nimi;
        this.aika = sis;
        this.nimimerkki=a;
        this.aihealue_id=c;
    }

    public Integer getId() {
        return id;
    }
    public Integer getaId() {
        return aihealue_id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }


    public String getOtsikko() {
        return otsikko;
    }

      
}
