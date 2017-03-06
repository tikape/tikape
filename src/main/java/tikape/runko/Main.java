package tikape.runko;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.*;
import tikape.runko.domain.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        //Luodaan tietokanta
        Database db = new Database("jdbc:sqlite:aihealueet.db");
        db.init();
    
        //Luodaan Data Access objectit
        AiheDao aiheDao = new AiheDao(db);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(db);
        ViestiDao viestiDao = new ViestiDao(db);
        
        //Aiheiden listaus
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            
            //Luodaan aihelista
            List<Aihe> lista = aiheDao.findAll();

            //Tehdään Hashmap
            map.put("viesti","Aiheet");
            map.put("lista", lista);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());             
        
        //aiheen lisäys
        post("/lisays:id", (req, res) -> {
            String id = req.queryParams("id");
            db.add(id);
            res.redirect("/");
            return "";
        });        
      
        //Viestiketjut
        get("/viestiketjut", (req, res) -> {
            HashMap map = new HashMap<>();
            //Otetaan aiheen id
            String id = req.queryParams("id");
            //Uusi aihe johon haetaan arvot
            Aihe aihe = aiheDao.findOne(Integer.parseInt(id));
            //Luodaan otsikko(viesti) aiheesta
            map.put("viesti", aihe.getNimi());
            //Luodaan lista viestiketjuista
            ArrayList<Viestiketju> viestilista = new ArrayList<>();
            int i = 1;
            while(true){
                if(viestiketjuDao.findOne(i) == null){
                    break;
            }
                if(viestiketjuDao.findOne(i).getaId() == aihe.getId()){
                    viestilista.add(viestiketjuDao.findOne(i));
                }            
            i++;            
            };
            map.put("aihe", aihe);
            //Suodatin pitää saada
            map.put("lista", viestilista);
            return new ModelAndView(map, "viestiketjut");
         }, new ThymeleafTemplateEngine()); 
        
        //Lisätään uusi viestiketju
        post("/viestiketjut/lisays", (req, res) -> {
            String nimimerkki = req.queryParams("nimimerkki");
            String otsikko = req.queryParams("otsikko");
            int i=Integer.parseInt(req.queryParams("aihe"));
            db.addViestiketju(otsikko, nimimerkki, i);
            String id=Integer.toString(i);
            res.redirect("/viestiketjut?id=" + id);
            return id;
        });
        
        get("/viesti", (req, res) -> {
            HashMap map = new HashMap<>();
            
            String viestiketjuId = req.queryParams("id");
            
            List<Viesti> viestit = viestiDao.kaikki(Integer.parseInt(viestiketjuId));
            
            map.put("viestit", viestit);
            
            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());
        
        /*
        post("/lisays:id", (req, res) -> {
            HashMap map = new HashMap<>();
            String id = req.queryParams("id");
            aiheet.add(id);
            db.add(id);
            map.put("viesti","Aiheet");
            map.put("lista", aiheet);
            res.redirect("/");
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());







        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
    }, new ThymeleafTemplateEngine());
   */     }
}
