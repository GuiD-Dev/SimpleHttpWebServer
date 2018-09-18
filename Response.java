import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

public class Response {
    
    public String head;
    public String contentType;
    public String server;
    public String connection;
    public String contentLength;
    public Boolean keepAlive;

    Socket socket;
    private InputStream fluxoIn;
    private OutputStream fluxoOut;

    public Response(Socket socket) throws Exception {
        this.socket = socket;
        this.fluxoIn = socket.getInputStream();
        this.fluxoOut = socket.getOutputStream();
        
        BufferedReader entrada = new BufferedReader(new InputStreamReader(fluxoIn));
        this.head = entrada.readLine();
    }

    public void getResponse() throws Exception {
        byte[] content = getContent(getDirectory());
        
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.1 200 OK\n");
        builder.append("Content-Type: text/html\n");
        builder.append("Content-Length: " + content.length + "\n");
        builder.append("Keep-Alive: timeout=10, max=1000\n\n");
        
        fluxoOut.write(builder.toString().getBytes());
        fluxoOut.write(content);
        fluxoOut.flush();
    }

    public byte[] getContent(String path) {
        System.out.println("Caminho solicitado: " + path);
        byte[] retorno;
        
        try {
            retorno = Files.readAllBytes(new File(path).toPath());
        } catch (Exception ex) {
            retorno = getContent("Content/home.html");
        }

        return retorno;
    }
    
    public String getDirectory() {
        return "Content" + head.split(" ")[1];
    }
}


/* Exemplo de cabeçalho
HTTP/1.1 200 OK
Content-Type: text/html; charset=utf-8
Server: Microsoft-IIS/8.5
Connection: close
Content-Length: 211618
*/  