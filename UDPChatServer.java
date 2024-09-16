import java.io.IOException;
import java.net.*;

public class UDPChatServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(8567);
        byte[] receiveBuffer = new byte[1024];

        System.out.println("Servidor UDP iniciado...");

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Mensagem recebida: " + message);

            // Enviar resposta para o cliente (echo)
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            byte[] sendBuffer = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
            socket.send(sendPacket);
        }
    }
}
