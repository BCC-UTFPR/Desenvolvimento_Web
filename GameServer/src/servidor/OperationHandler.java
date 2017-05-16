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
		//Boolean parsing = verifyJson(request);
		OperationHandler.log("[Operação] Nova operação requisitada! Identificador: " + operation);
		/*if(parsing){
			if(operation.equals("add-trophy")){
				database.addTrophy(request);
				OperationHandler.log("[Operação] Troféu adicionado com sucesso!");
				return OK();
			}else if(operation.equals("get-trophy")){
				OperationHandler.log("[Operação] Buscando troféu com sucesso");
				JsonObject data = database.getTrophy(request);
				return OK_withData(data);
			}else if(operation.equals("list-trophy")){
				OperationHandler.log("[Operação] Listando troféu com sucesso");
				JsonArray data1 = database.listTrophy(request);
				return OK_withDataArray(data1);
			}else if(operation.equals("save-state")){
				database.saveState(request);
				OperationHandler.log("[Operação] Checkpoint adicionado com sucesso");
				return OK();
			}else if(operation.equals("load-state")){
				OperationHandler.log("[Operação] Checkpoing carregado com sucesso");
				JsonObject data2 = database.loadState(request);
				return OK_withData(data2);
			}else if(operation.equals("save-media")){
				database.saveMedia(request);
				OperationHandler.log("[Operação] Imagem salva com sucesso");
				return OK();
			}else if(operation.equals("list-media")){
				OperationHandler.log("[Operação] Listando imagens com sucesso");
				JsonArray data3 = database.listMedia(request);
				return OK_withDataArray(data3);
			}
		}
		*/
		switch(operation) {
		case "add-trophy":
			//Boolean parsing = verifyParsingTrophy(request);
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
		case "clear-trophy":
			OperationHandler.log("[Operação] Limpando todos os troféus");
			database.clearTrophy(request);
			return OK();
		case "save-state":
			database.saveState(request);
			OperationHandler.log("[Operação] Checkpoint adicionado com sucesso");
			return OK();
		case "load-state":
			OperationHandler.log("[Operação] Checkpoing carregado com sucesso");
			JsonObject data2 = database.loadState(request);
			return OK_withData(data2);
		case "save-media":
			database.saveMedia(request);
			OperationHandler.log("[Operação] Imagem salva com sucesso");
			return OK();
		case "list-media":
			OperationHandler.log("[Operação] Listando imagens com sucesso");
			JsonArray data3 = database.listMedia(request);
			return OK_withDataArray(data3);
		default:
			return Error("not_implemented");
		}	
		
	}
	
	/*public Boolean verifyJson(JsonObject request){
		JsonObject jsonOp = request.getAsJsonObject("op");
		JsonObject jsonData = request.getAsJsonObject("data");
		if(jsonOp.get("op").getAsString().equals("add-trophy")){
			if(request.getAsJsonObject("data").isJsonNull()){
				OperationHandler.log("Json Data esta vazio");
				return false;
			}else if (jsonData.get("name").isJsonNull()){
				OperationHandler.log("Json Data.name esta vazio");
				return false;
			}else if (jsonData.get("xp").isJsonNull()){
				OperationHandler.log("Json Data.xp esta vazio");
				return false;
			}else if (jsonData.get("title").isJsonNull()){
				OperationHandler.log("Json Data.title esta vazio");
				return false;
			}else if (jsonData.get("description").isJsonNull()){
				OperationHandler.log("Json Data.description esta vazio");
				return false;
			}else{
				return true;
			}
		}else if(jsonOp.get("op").getAsString().equals("get-trophy")){
			if(jsonData.isJsonNull()){
				OperationHandler.log("Json Data esta vazio");
				return false;
			}
		}else if(jsonOp.get("op").getAsString().equals("save-state")){
			if(jsonData.isJsonNull()){
				OperationHandler.log("Json Data esta vazio");
				return false;
			}else if(jsonData.get("x").isJsonNull()){
				OperationHandler.log("Json Data.x esta vazio");
				return false;
			}else if(jsonData.get("y").isJsonNull()){
				OperationHandler.log("Json Data.y esta vazio");
				return false;
			}	
		}else if(jsonOp.get("op").getAsString().equals("save-media")){
			if(jsonData.isJsonNull()){
				OperationHandler.log("Json Data esta vazio");
				return false;
			}else if(jsonData.get("mimeType").isJsonNull()){
				OperationHandler.log("Json Data.mimeType esta vazio");
				return false;
			}else if(jsonData.get("src").isJsonNull()){
				OperationHandler.log("Json Data.src esta vazio");
				return false;
			}
		}else if(jsonOp.get("op").getAsString().equals("list-media")){
			if(jsonData.isJsonNull()){
				OperationHandler.log("Json Data esta vazio");
				return false;
			}else if(jsonData.get("mimeType").isJsonNull()){
				OperationHandler.log("Json Data.mimeType esta vazio");
				return false;
			}
		}else{
			return true;
		}
		return false;
	}*/
	
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