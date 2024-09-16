import java.io.*;
import java.net.*;

public class TCPChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345); // Conecta ao servidor

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String serverMessage;
        while ((serverMessage = in.readLine()) != null) {
            System.out.println("Servidor: " + serverMessage);

            String userInput = stdIn.readLine();
            if (userInput != null) {
                out.println(userInput);
            }
        }
    }
}
