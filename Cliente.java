
import com.oracle.nio.BufferSecrets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class Cliente {
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("www.feevale.br", 80);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println("GET / HTTP/1.0");
            out.println("Connection: Close");
            out.println();
            out.flush();
            
            String linha;
            while ((linha = in.readLine()) != null) {
                System.out.println(linha);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
