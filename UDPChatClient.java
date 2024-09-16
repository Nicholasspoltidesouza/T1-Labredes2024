import java.net.*;
import java.io.*;

public class UDPChatClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        byte[] sendBuffer;
        byte[] receiveBuffer = new byte[1024];

        System.out.println("Digite sua mensagem:");
        String message = stdIn.readLine();
        sendBuffer = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, 12345);
        socket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivePacket);
        String received = new String(receivePacket.getData(), 0, receivePacket.getLength());

        System.out.println("Servidor: " + received);

        socket.close();
    }
}
