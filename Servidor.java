
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static String documentroot;

    public static void main(String[] args) {
         try {
            while (true) {
                documentroot = "Content";
                
                ServerSocket serverSocket = new ServerSocket(8080);
                Socket socket = serverSocket.accept();
                serverSocket.close();
                InputStream fluxoIn = socket.getInputStream();
                BufferedReader entrada = new BufferedReader(new InputStreamReader(fluxoIn));
                String linha;
                linha = entrada.readLine();

                String[] d = linha.split(" ");
                System.out.println(linha);
                String diretorio = d[1];  // porque devolve só um "/" ?

                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
                out.write("HTTP/1.1 200 OK\n");
                out.write("Content-Type: text/html\n");
                System.out.println(diretorio);
                String content = getContent(diretorio);
                //System.out.print(content.toString());
                out.write("Content-Length: " + content.length() + "\n");
                out.newLine();
                out.write(content);
                out.flush();
                socket.close();
                System.out.println("\n\n\n");
            }  
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String getContent(String file) throws IOException {
        String path;
        path = documentroot.concat(file);
        System.out.println(path);
        FileReader reader;
        
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException ex) {
            return "<html><strong>A pagina nao existe!</strong></html>";
        }

        BufferedReader filecontent = new BufferedReader(reader);
        String linha = filecontent.readLine();
        StringBuilder content = new StringBuilder();
        content.append(linha);
        while ((linha = filecontent.readLine()) != null) {
            content.append(linha);
        }
        filecontent.close();
        return content.toString();
    }
    
}
