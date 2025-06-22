import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientHandler implements Runnable
{
    private Socket socket;
    
    public static String readFileContent(String filePath) throws IOException {
        String content;
        filePath = filePath.trim();
        if(filePath == null || filePath.isEmpty()) {
            filePath = "www/index.html";
        }
        if(!filePath.startsWith("www/")) {
            filePath = "www/" + filePath; 
        }
        try {
            Path path = Paths.get(filePath);
            content = Files.readString(path);

        } catch (FileNotFoundException e) {
            content = "-1";
            System.out.println("Error reading file: " + e.getMessage());
        } catch (IOException e) {
            content = "-1";
            System.out.println("Error reading file: " + e.getMessage());
        }
        return content;
    }
    public static void writeToStream(PrintWriter out, String content) throws IOException {
        if(content == "-1") {
            System.out.println("File not found.");
            out.println("HTTP/1.1 400 Not Found\r\n\r\n" + "File not found.");
            return;
        }
        out.println("HTTP/1.1 200 OK\r\n\r\n" + content);
    }


    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("ClientHandler initialized.");
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = in.readLine();
            String[] requestParts = request.split(" ");
            if (requestParts[0].equals("GET")) {
            String filePath = requestParts[1].substring(1);
            String content = readFileContent(filePath);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            writeToStream(out, content);
            socket.close();

        }
    }
        catch (IOException e) {
            System.out.println("Error accepting client connection: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
        
}

