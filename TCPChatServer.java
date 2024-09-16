import java.io.*;
import java.net.*;
import java.util.*;

public class TCPChatServer {
    private static Map<String, Socket> clients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8566); // Porta do servidor

        System.out.println("Servidor TCP iniciado...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ClientHandler(clientSocket).start();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                // Registro do usuário
                out.println("Digite seu nickname:");
                nickname = in.readLine();
                synchronized (clients) {
                    clients.put(nickname, socket);
                }

                out.println("Você está registrado. Bem-vindo ao chat!");

                String message;
                while ((message = in.readLine()) != null) {
                    processMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                synchronized (clients) {
                    clients.remove(nickname);
                }
            }
        }

        private void processMessage(String message) throws IOException {
            if (message.startsWith("/MSG")) {
                String[] parts = message.split(" ", 3);
                String recipientNickname = parts[1];
                String chatMessage = parts[2];
                sendMessage(recipientNickname, chatMessage);
            } else if (message.startsWith("/FILE")) {
                // Implementar envio de arquivo
            }
        }

        private void sendMessage(String recipientNickname, String message) throws IOException {
            Socket recipientSocket;
            synchronized (clients) {
                recipientSocket = clients.get(recipientNickname);
            }

            if (recipientSocket != null) {
                PrintWriter recipientOut = new PrintWriter(recipientSocket.getOutputStream(), true);
                recipientOut.println(nickname + ": " + message);
            } else {
                out.println("Usuário não encontrado.");
            }
        }
    }
}
