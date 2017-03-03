package tikape.runko;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.domain.Viestiketju;
import tikape.runko.database.AiheDao;
import tikape.runko.database.DbAihealue;
import tikape.runko.domain.Aihe;
import tikape.runko.database.DbViestiketju;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        //Luodaan tietokannat
        DbAihealue db = new DbAihealue("jdbc:sqlite:aihealueet.db");
        db.init();
//        DbViestiketju db2 = new DbViestiketju("jdbc:sqlite:viestiketju.db");
//        db2.init();


        
        //Luodaan Data Access objectit
        AiheDao aiheDao = new AiheDao(db);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(db);
        
 // lisäys viestiketjut db      db2.add("otsikko", "nimimerkki", 1);
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
            //Lisäys: INSERT INTO Aihealue (nimi,sisalto) VALUES ( '" + x + "', '/viestiketjut?id=" + x + "');")  DbAihealue
            db.add(id);
            //Palaa takaisin
            res.redirect("/");
            return "";
        });        
         
         
         
         
        //Viestiketjut
        get("/viestiketjut", (req, res) -> {
            HashMap map = new HashMap<>();
            //Otetaan aiheen nimi
            String id= req.queryParams("id");
            //UUsi aihe johon haetaan arvot
            Aihe whatever = (aiheDao.findOneWith(id));
            //Luodaan otsikko(viesti) aiheesta
            map.put("viesti",whatever.getNimi() );
            //Luodaan lista viestiketjuista
            ArrayList<Viestiketju> viestilista = new ArrayList<>();
            int i=1;
            //tÄYTETÄÄN LISTA
            while(true){
                if(viestiketjuDao.findOne(i)==null){
                    break;
            }
                if(viestiketjuDao.findOne(i).getaId()==whatever.getId()){
                    viestilista.add(viestiketjuDao.findOne(i));
                }
             
            i++;            
            };
            map.put("aihe",whatever);
            //Suodatin pitää saada
            map.put("lista",viestilista);
            return new ModelAndView(map, "viestiketjut");
         }, new ThymeleafTemplateEngine());  
        //Lisätään uusi viestiketju
        post("/viestiketjut/lisays", (req, res) -> {
            String nimimerkki = req.queryParams("nimimerkki");
            String otsikko = req.queryParams("otsikko");
            int i=Integer.parseInt(req.queryParams("aihe"));
            db.addViestiketju(otsikko, nimimerkki, i);
            res.redirect("/");
            String id=Integer.toString(i);
            return id;
        });
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
