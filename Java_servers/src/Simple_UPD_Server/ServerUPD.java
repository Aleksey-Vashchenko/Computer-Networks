package Simple_UPD_Server;

import java.io.IOException;
import java.net.*;

public class ServerUPD {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(10000);
        System.out.println("Сервер включен");
        while (true){
            byte[] receivingDataBuffer = new byte[1024];
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Сервер ожидает данные клиента");
            serverSocket.receive(inputPacket);
            String receivedData = new String(inputPacket.getData(),0,inputPacket.getLength());
            System.out.println("Пришли данные от клиента: "+receivedData);
            String stringToClient;
            if(receivedData.length()%2==1){
                stringToClient = receivedData.substring(0,receivedData.length()/2)+
                        receivedData.substring(receivedData.length()/2+1);
            }
            else {
                stringToClient=receivedData;
            }
            System.out.println("Сервер произвел вычисления");
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();
            DatagramPacket sendingPacket = new DatagramPacket(stringToClient.getBytes(),stringToClient.getBytes().length,senderAddress,senderPort);
            serverSocket.send(sendingPacket);
            System.out.println("Сервер отправил данные адресу " +senderAddress+" "+senderPort+" ожидает новый запрос");
        }
    }
}
