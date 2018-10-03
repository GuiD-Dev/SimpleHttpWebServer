import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        
        try {
            ServerSocket serverSocket = new ServerSocket(8080);

            while (true) {
                Socket socket = serverSocket.accept();
                
                Response response = new Response(socket);
                response.request = "HTTP/1.1 200 OK";
                response.contentType = "text/html; charset=utf-8";
                response.connection = "keep-alive";
                response.getResponse();
                
                socket.close();
                System.out.println("\n\n\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
