
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Servidor {

    public static String documentroot;

    public static void main(String[] args) {
        
        try {
            ServerSocket serverSocket = new ServerSocket(8080);

            while (true) {
                documentroot = "Content";
                Socket socket = serverSocket.accept();
                
                InputStream fluxoIn = socket.getInputStream();
                BufferedReader entrada = new BufferedReader(new InputStreamReader(fluxoIn));
                String linha;
                linha = entrada.readLine();

                String[] d = linha.split(" ");
                System.out.println(linha + "-");
                String diretorio = d[1];
 
                OutputStream out = socket.getOutputStream();
                
                StringBuilder builder = new StringBuilder();
                builder.append("HTTP/1.1 200 OK\n");
                builder.append("Content-Type: text/html\n");

                System.out.println(diretorio + "-");

                byte[] content = getContent(diretorio);

                builder.append("Content-Length: " + content.length + "\n");
                builder.append("Keep-Alive: timeout=10, max=1000\n\n");
                
                out.write(builder.toString().getBytes());
                out.write(content);
                out.flush();
                
                socket.close();
                System.out.println("\n\n\n");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static byte[] getContent(String file) throws Exception {
        String path;
        path = documentroot.concat(file);
        System.out.println(path);
        File reader;
        
        try {
            reader = new File(path);
        } catch (Exception ex) {
            reader = new File(documentroot.concat("/home.html"));
        }

        return Files.readAllBytes(reader.toPath());
    }
    
}
