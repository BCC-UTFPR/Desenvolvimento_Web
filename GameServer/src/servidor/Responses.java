package servidor;

import java.io.IOException;

import com.google.gson.JsonObject;

public class Responses {
	String localHost;
	
	public Responses(String localHost) {
		this.localHost = localHost;
	}
	
    public byte[] responseJSON_Success(JsonObject response) throws IOException {
        String responseHeader = "HTTP/1.1 200 OK\n"
        		+ "\n"
                + response;
        return responseHeader.getBytes();
    }
    
    public byte[] response404_NotFound(String resource) throws IOException {
        String responseHeader = "HTTP/1.1 404 Not found\n"
                + "\n"
        		+ "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"><html><head><title>"
                + "404 Not Found"
        		+ "</title></head><body><h1>Not Found</h1><p>The requested URL "
                + resource
                + " was not found on this Servidor.</p><hr><address>"
                + "Game Server [Primeira entrega] | "
                + localHost
                + "</address></body></html>";
        return responseHeader.getBytes();
    }

    public byte[] response401_NotAuthorized() throws IOException {
        String responseHeader = "HTTP/1.1 401 Not Authorized\n"
                + "WWW-Authenticate: Basic realm=\"Entre com usuário e senha\"\n"
                + "\n"
                + "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"><html><head><title>"
                + "401 Not Authorized"
        		+ "</title></head><body><h1>Not Authorized</h1><p>The requested URL "
                + " was under security constraint.</p><hr><address>"
                + "Game Server [Primeira entrega] | "
                + localHost
                + "</address></body></html>";
        return responseHeader.getBytes();        
    }

    public byte[] response501_NotImplemented() throws IOException {
        String responseHeader = "HTTP/1.1 501 Not implemented\n"
                + "\n"
                + "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"><html><head><title>"
                + "501 Not implemented"
        		+ "</title></head><body><h1>Not implemented</h1><p>The requested URL "
                +" was under security constraint.</p><hr><address>"
                + "Game Server [Primeira entrega] | "
                + localHost
                +"</address></body></html>";
        return responseHeader.getBytes();
    }

    public byte[] response400_BadRequest() throws IOException {
        String responseHeader = "HTTP/1.1 400 Bad Request\n"
        		+ "\n"
        		+ "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"><html><head><title>"
        		+ "400 Bad Request"
        		+ "</title></head><body><h1>Bad Request</h1><p>The requested URL "
        		+" was under security constraint.</p><hr><address>"
        		+ "Game Server [Primeira entrega] | "
        		+ localHost
        		+"</address></body></html>";
        return responseHeader.getBytes();
    }
}