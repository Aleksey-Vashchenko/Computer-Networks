package Simple_UPD_Server;

import java.io.IOException;
import java.net.*;

public class ServerUPD {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(10000);
        System.out.println("������ �������");
        while (true){
            byte[] receivingDataBuffer = new byte[1024];
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("������ ������� ������ �������");
            serverSocket.receive(inputPacket);
            String receivedData = new String(inputPacket.getData(),0,inputPacket.getLength());
            System.out.println("������ ������ �� �������: "+receivedData);
            String stringToClient;
            if(receivedData.length()%2==1){
                stringToClient = receivedData.substring(0,receivedData.length()/2)+
                        receivedData.substring(receivedData.length()/2+1);
            }
            else {
                stringToClient=receivedData;
            }
            System.out.println("������ �������� ����������");
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();
            DatagramPacket sendingPacket = new DatagramPacket(stringToClient.getBytes(),stringToClient.getBytes().length,senderAddress,senderPort);
            serverSocket.send(sendingPacket);
            System.out.println("������ �������� ������ ������ " +senderAddress+" "+senderPort+" ������� ����� ������");
        }
    }
}
