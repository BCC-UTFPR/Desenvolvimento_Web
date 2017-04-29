package Servidor;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONHandler {
	public JsonObject convertStringJSON(String content) {
		Gson googleJSON = new Gson();
		JsonElement data = googleJSON.fromJson(content, JsonElement.class);
		JsonObject json = data.getAsJsonObject();
		return json;
	}
}
