package servidor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import database.Actions;

public class OperationHandler {
	Actions database = new Actions();
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static void log(String message){
    	System.out.println(dateFormat.format(Calendar.getInstance().getTime()) + " " + message);
    }
    
	public JsonObject execute(String requestBody) {
		Gson gson = new Gson();
		JsonElement element = gson.fromJson(requestBody, JsonElement.class);
		JsonObject request = element.getAsJsonObject();
		String operation = request.get("op").getAsString();
		OperationHandler.log("[Operação] Nova operação requisitada! Identificador: " + operation);
		
		switch(operation) {
		case "add-trophy":
			database.addTrophy(request);
			OperationHandler.log("[Operação] Troféu adicionado com sucesso!");
			return OK();
		case "get-trophy":
			OperationHandler.log("[Operação] Buscando troféu com sucesso");
			JsonObject data = database.getTrophy(request);
			return OK_withData(data);
		case "list-trophy":
			OperationHandler.log("[Operação] Listando troféu com sucesso");
			JsonArray data1 = database.listTrophy(request);
			return OK_withDataArray(data1);
		case "save-state":
			OperationHandler.log("[Operação] Checkpoint adicionado com sucesso");
			database.saveState(request);
			return OK();
		case "load-state":
			OperationHandler.log("[Operação] Checkpoing carregado com sucesso");
			JsonObject data2 = database.loadState(request);
			return OK_withData(data2);
		default:
			return Error("not_implemented");
		}	
	}
	
	private JsonObject OK_withDataArray(JsonArray data1) {
		JsonObject innerObject = new JsonObject();
		innerObject.addProperty("response","ok");
		innerObject.addProperty("data",data1.toString());
		return innerObject;
	}

	private JsonObject OK(){
		JsonObject innerObject = new JsonObject();
		innerObject.addProperty("response","ok");
		innerObject.addProperty("data","");
		return innerObject;
	}
	
	private JsonObject OK_withData(JsonObject data){
		JsonObject innerObject = new JsonObject();
		innerObject.addProperty("response","ok");
		innerObject.addProperty("data",data.toString());
		return innerObject;
	}
	
	private JsonObject Error(String reason) {
		JsonObject innerObject = new JsonObject();
		innerObject.addProperty("response","fail");
		innerObject.addProperty("data",reason);
		return innerObject;		
	}
}