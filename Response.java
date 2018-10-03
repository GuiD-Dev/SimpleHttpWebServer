import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class Response {
    
    public String request;
    public String contentType;
    public String server;
    public String connection;
    public String contentLength;
    public String keepAlive;

    private Socket socket;
    private OutputStream fluxoOut;
    private String firstLine;

    public Response(Socket socket) throws Exception {
        this.socket = socket;
        this.fluxoOut = socket.getOutputStream();
        
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.firstLine = entrada.readLine();
        
        String line = this.firstLine;
        while (!line.isEmpty()) {
            //System.out.println(line);
            line = entrada.readLine();
        }
    }

    public void getResponse() throws Exception {
        byte[] content = getContent(getDirectory());
        
        StringBuilder header = new StringBuilder();
        header.append(request + "\n");
        header.append("Content-Type: " + contentType + "\n");
        header.append("Content-Length: " + content.length + "\n");
        header.append("Connection: " + connection + "\n\n");

        System.out.println(header.toString());
        
        fluxoOut.write(header.toString().getBytes());
        fluxoOut.write(content);
        fluxoOut.flush();
    }

    public byte[] getContent(String path) {
        System.out.println("Caminho solicitado: " + path);
        byte[] retorno;
        
        try {
            retorno = Files.readAllBytes(new File(path).toPath());
        } catch (Exception ex) {
            retorno = getContent("www/notfound.html");
        }

        return retorno;
    }
    
    public String getDirectory() {
        return "www" + firstLine.split(" ")[1];
    }
}


/* Exemplo de cabeçalho
HTTP/1.1 200 OK
Content-Type: text/html; charset=utf-8
Server: Microsoft-IIS/8.5
Connection: close
Content-Length: 211618
*/