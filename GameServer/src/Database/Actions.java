package Database;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Actions {
  public void insertJSON(JsonObject json) {
	Set<Map.Entry<String, JsonElement>> entries = json.entrySet();
	for (Map.Entry<String, JsonElement> entry: entries) {
	      System.out.println("A sua chave é... " + entry.getKey());
	      System.out.println("O valor desta chave é... " + entry.getValue());
	      
	}
  }
  
  public JsonObject getJSON(HashMap<String, String> parameters) {
	  for (String i: parameters.keySet()){
          String key = i.toString();
          String value = parameters.get(i).toString();  
          System.out.println(key + " " + value);  
	  	} 
	return null;
  }
  
//
//  public addPlayer(HashMap<String, String> parameters){
//	  EntityManagerFactory factory = Persistence.createEntityManagerFactory("gameserver");
//	  EntityManager em = factory.createEntityManager();
//	  
//	  Player p = new Player();
//	  for (String i: parameters.keySet()){
//          String key = i.toString();
//          String value = parameters.get(i).toString();  
//          p.setId(Integer.parseInt(key));
//          p.setName(value);
//          em.getTransaction().begin();
//          em.persist(p);
// 			em.getTransaction().commit(); 
//	  	} 
	  
	  
  }



