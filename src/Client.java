import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAdd = InetAddress.getByName("localhost");
            int serverPort = 1234;

            byte[] sendBuf;
            byte[] recvBuf = new byte[1024];

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Inserisci un messaggio da mandare al server ('fine' per terminare): ");
                String userMsg = scanner.nextLine();
                sendBuf = userMsg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, serverAdd, serverPort);
                socket.send(sendPacket);

                if(userMsg.equalsIgnoreCase("fine")) {
                    System.out.println("Client terminato");
                    System.exit(0);
                }


                DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(recvPacket);
                String recvMsg = new String(recvPacket.getData(), 0, recvPacket.getLength());

                System.out.println(recvMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}