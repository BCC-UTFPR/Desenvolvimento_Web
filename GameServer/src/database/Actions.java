package database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Actions {
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("gameserver");
	EntityManager manager = factory.createEntityManager();
	DataTrophy dataTrophy = new DataTrophy();
	DataSave dataSave = new DataSave();
	DataMedia dataMedia = new DataMedia();

	public void addTrophy(JsonObject json) {
		JsonObject jsonData = json.getAsJsonObject("data");
		dataTrophy.setName(jsonData.get("name").getAsString());
		dataTrophy.setXp(jsonData.get("xp").getAsInt());
		dataTrophy .setTitle(jsonData.get("title").getAsString());
		dataTrophy.setDescription(jsonData.get("description").getAsString());
		manager.getTransaction().begin();
		manager.persist(dataTrophy);
		manager.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public JsonObject getTrophy (JsonObject json) {
		/* Faça toda a lógica do listTrophy */
		/* Não se esqueça que o listTrophy é um array de JsonObjects, ver na documentação Gson como faz... */
		String data = json.get("data").getAsString();
		manager.getTransaction().begin();
		JsonObject innerObject = new JsonObject();
		List<DataTrophy> query = (List<DataTrophy>) manager.createQuery("from DataTrophy where name = :busca").setParameter("busca", data).getResultList();
		for ( DataTrophy dataTrophy : query ) {
			innerObject.addProperty("name",dataTrophy.getName());
			innerObject.addProperty("xp",dataTrophy.getXp());
			innerObject.addProperty("title",dataTrophy.getTitle());
			innerObject.addProperty("description",dataTrophy.getDescription());
		}
		return innerObject;
	}
	
	//tem que ver pq está imprimindo duas vezes o último
	public JsonArray listTrophy (JsonObject json) {
		
		JsonArray saidaFinal = new JsonArray();
		manager.getTransaction().begin();
		List<DataTrophy> queryList = (List<DataTrophy>) manager.createQuery("from DataTrophy",DataTrophy.class).getResultList();
		for(DataTrophy dataTrophy: queryList){
			JsonObject resultado = new JsonObject();
			resultado.addProperty("name", dataTrophy.getName());
			resultado.addProperty("xp", dataTrophy.getXp());
			resultado.addProperty("title", dataTrophy.getTitle());
			resultado.addProperty("description", dataTrophy.getDescription());
			saidaFinal.add(resultado);
		}
		return saidaFinal;
		
	}
	
	public void clearTrophy(JsonObject json){
		manager.getTransaction().begin();
		manager.createQuery("delete from DataTrophy").executeUpdate();
		manager.getTransaction().commit();
		
	}
	
	public void saveState(JsonObject json) {
		JsonObject jsonData = json.getAsJsonObject("data");
		dataSave.setPosX(jsonData.get("x").getAsInt());
		dataSave.setPosY(jsonData.get("y").getAsInt());
		manager.getTransaction().begin();
		manager.persist(dataSave);
		manager.getTransaction().commit();
	}
	
	public JsonObject loadState (JsonObject json) {
		JsonObject saida = new JsonObject();
		manager.getTransaction().begin();
		List<DataSave> querySave = (List<DataSave>) manager.createQuery("from DataSave", DataSave.class).getResultList();
		for(DataSave dataSave : querySave){
			saida.addProperty("x", dataSave.getPosX());
			saida.addProperty("y", dataSave.getPosY());
		}
		return saida;
	}
	
	//Salvei como string, depois só modificar para o formato no hibernate
	public void saveMedia(JsonObject json){
		JsonObject jsonDataMedia = json.getAsJsonObject("data");
		dataMedia.setMimeType(jsonDataMedia.get("mimeType").getAsString());
		dataMedia.setSrc(jsonDataMedia.get("src").getAsString());
		manager.getTransaction().begin();
		manager.persist(dataMedia);
		manager.getTransaction().commit();
	}
	
	public JsonArray listMedia (JsonObject json){
		JsonArray resultFinal = new JsonArray();
		manager.getTransaction().begin();
		List<DataMedia> query = (List<DataMedia>) manager.createQuery("from DataMedia",DataMedia.class).getResultList();
		for(DataMedia dataMedia: query){
			JsonObject result = new JsonObject();
			result.addProperty("mimeType", dataMedia.getMimeType());
			result.addProperty("src", dataMedia.getSrc());
			resultFinal.add(result);
		}
		return resultFinal;
	}
	
  }