package servidor;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.google.gson.JsonObject;
import servidor.Responses;


public final class Server {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
 
    public static void log(String message){
    	System.out.println(dateFormat.format(Calendar.getInstance().getTime()) + " " + message);
    }

    public static void main(String argv[]) throws Exception {
        int serverPort = 8081;
        log("Game Server [Primeira entrega] | Status: Online - Porta:" + serverPort);
        ServerSocket server = new ServerSocket(serverPort);

        while (true) {
            Socket socket = server.accept();
            new Thread(new RequestHandle(socket)).run();
        }
    }
}

final class RequestHandle implements Runnable {
	
    final static String CRLF = "\r\n";
    Socket socket;
    InputStream input;
    OutputStream output;
    private Responses responses;
    private OperationHandler operation;
    private RequestBuilder requests;
    
    public RequestHandle(Socket socket) throws Exception {
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

    public void run() {
        try {
        	requests = new RequestBuilder(this.input);
        	responses = new Responses(requests.header.get("Host:"));
        	operation = new OperationHandler();
        	
            byte[] response = null;
            if (requests.header.containsKey("GET")) {
            	Server.log("[GET] Método recebido");
                response = responseGet();
            } else if(requests.header.containsKey("POST")){
            	Server.log("[POST] Método recebido");
            	response = responsePost();
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
    
    private byte[] responsePost() throws Exception {
    	if(requests.header.containsKey("Content-Type:")){
    		String contentType = requests.header.get("Content-Type:");
    		if(contentType.equals("application/json")){
    			Server.log("[JSON] Uma requisição foi recebida!");
    			String request = new String(requests.body);
    			JsonObject response = operation.execute(request);
        		return responses.responseJSON_Success(response);
        	}
    	}
    	return responses.response501_NotImplemented();
    }

    private byte[] responseGet() throws Exception {
    	return responses.response501_NotImplemented();
    }
}