import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        while (true) {
            if(args.length ==2) {
                try {
                    GreetClient greetClient = new GreetClient();
                    greetClient.startConnection(args[0], Integer.valueOf(args[1]));
                    String response = greetClient.sendMessage("hello server");
                    System.out.println(response);
                    greetClient.stopConnection();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public void startConnection(String hostname, int port) throws IOException {
        clientSocket = new Socket(InetAddress.getByName(hostname), port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
