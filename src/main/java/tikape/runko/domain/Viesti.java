
package tikape.runko.domain;


import java.sql.Timestamp;

public class Viesti {
    private Integer id;
    private String viesti;
    private String kayttaja;
    private Timestamp aika;
    private Integer viestiketju_id;
    
    public Viesti(Integer id, String viesti, String kayttaja, Timestamp aika, Integer viestiketju_id) {
        this.id = id;
        this.viesti = viesti;
        this.kayttaja = kayttaja;
        this.aika = aika;
        this.viestiketju_id = viestiketju_id;
    }   

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getViesti() {
        return viesti;
    }

    public String getKayttaja() {
        return kayttaja;
    }

    public Timestamp getAika() {
        return aika;
    }

    public Integer getViestiketju_id() {
        return viestiketju_id;
    }
  
}
    
