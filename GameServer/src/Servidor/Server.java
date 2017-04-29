package Servidor ;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import Database.Actions;


public final class Server {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static void log(String message){
    	System.out.println(dateFormat.format(Calendar.getInstance().getTime()) + " " + message);
    }

    public static void main(String argv[]) throws Exception {
        int serverPort = 8081;
        log("Web Server - Status: Online - Port:" + serverPort);
        ServerSocket ServidorSocket = new ServerSocket(serverPort);

        while (true) {
            Socket socket = ServidorSocket.accept();
            new Thread(new RequesteHandle(socket)).run();
        }
    }
}

final class RequesteHandle implements Runnable {
	
    final static String CRLF = "\r\n";
    Socket socket;
    InputStream input;
    OutputStream output;
    private String requestPath;
    private BufferedReader requestBody;
    private String bodyContent;
    private HashMap<String, String> requestHeader;
    private HashMap<String, String> cookieParams;
    private ResponseHandler responses;
    private Actions databaseManager = new Actions();
    private JSONHandler jsonManager = new JSONHandler();
    
    public RequesteHandle(Socket socket) throws Exception {
        this.socket = socket;
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
    }
    
    @Override
    protected void finalize() throws Throwable {
    	super.finalize();
    	output.close();
        input.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            requestHeader = buildRequestMap();
            cookieParams = buildCookieParams();
        	responses = new ResponseHandler(requestHeader.get("Host:"));
        	
            byte[] response = null;
            if (requestHeader.containsKey("GET")) {
            	Server.log("[GET] Method received.");
                response = responseGet(requestHeader);
            } else if(requestHeader.containsKey("POST")){
            	Server.log("[POST] Method received.");
            	response = responsePost(requestHeader);
            }
            else {
                response = responses.response501_NotImplemented();    
            }
            output.write(response);
            finalize();
            
        } catch (Throwable e) {
			Server.log(e.getMessage());
			e.printStackTrace();
		}        
    }

    private HashMap<String, String> buildRequestMap() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.input));

        HashMap<String, String> requestHeader = new HashMap<String, String>();
        String requestLine = reader.readLine();
        while (requestLine != null && !requestLine.trim().isEmpty()) {
            String[] split = requestLine.split(" ", 2);
            requestHeader.put(split[0], split[1]);
            Server.log(split[0] + " " + split[1]);
            requestLine = reader.readLine();
        }
        requestBody = reader;  
        return requestHeader;
    }

    private HashMap<String, String> buildCookieParams() throws IOException {
    	HashMap<String, String> cookieParams = new HashMap<String, String>();
    	if (requestHeader.containsKey("Cookie:")) {
    		String[] cookieLine = requestHeader.get("Cookie:").split(";");
    		for(String param: cookieLine) {
    			String[] split = param.split("=");
    			cookieParams.put(split[0].trim(), split[1].trim());
    			Server.log(split[0] + " " + split[1]);
    		}
    	}
    	
    	return cookieParams;
    }
    
    private HashMap<String, String> buildGetParams(String[] parameters) throws IOException {
		HashMap<String, String> params = new HashMap<String, String>();
		
		try {
			if(parameters[1].contains("&")) {
				for(String parameter : parameters[1].split("&")) {
					String key = parameter.split("=")[0];
					String value = parameter.split("=")[1];
					params.put(key, value);
				}
			} else {
				String key = parameters[1].split("=")[0];
				String value = parameters[1].split("=")[1];
				params.put(key, value);
			}
		} catch (Exception e) {
			Server.log(e.toString());
		}
		return params;
    }
    
    private String buildBody() throws IOException {
    	String requestLine = requestBody.readLine();
    	String body = requestLine;
    	
        while(requestLine != null) {
        	requestLine = requestBody.readLine();
        	if(!body.isEmpty() && requestLine != null)
        		body = body + requestLine;
        }
        return body;
    }
    
    private byte[] responsePost(HashMap<String, String> requestHeader) throws Exception {
   
    	if(requestHeader.containsKey("Content-Type:")){
    		String contentType = requestHeader.get("Content-Type:");
    		if(contentType.equals("application/json")){
    			Server.log("[Application/JSON] Body received from request. Inserting into the database.");
    			bodyContent = buildBody();
    			JsonObject json = jsonManager.convertStringJSON(bodyContent);
    			databaseManager.insertJSON(json);
        		return responses.responseJSON_Success();
        	}
    	}
    	return responses.response501_NotImplemented();
    }

    private byte[] responseGet(HashMap<String, String> requestHeader) throws Exception {

    	HashMap<String, String> parameters = new HashMap();
    	parameters.put("Olá", "Mundo");
    	JsonObject json = databaseManager.getJSON(parameters);
    	return responses.response501_NotImplemented();
    }

}
