package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class ServerConnection {
	
	private static ServerSocket serverSocket;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public static void log(String message){
    	System.out.println(dateFormat.format(Calendar.getInstance().getTime()) + " " + message);
    }

    public static void main(String argv[]) throws Exception {

        int port = 8081; // Porta que o servidor ouvirá
        String dirBase = "/var/www"; // diretório onde estarão os arquivos

        log("Servidor Web iniciado.\n Porta:" + port + "\n Pasta WWW:" + dirBase);
        
        serverSocket = new ServerSocket(port);

        while (true) { // Loop infinito aguardando conexões
            Socket socket = serverSocket.accept(); // Escuta o socket
            new Thread(new Server(socket, dirBase)).run();
        }
    }
}
