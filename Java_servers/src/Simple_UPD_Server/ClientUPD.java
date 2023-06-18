package Simple_UPD_Server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Objects;
import java.util.Scanner;

public class ClientUPD {
    public static void main(String[] args) throws IOException{
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        Scanner inputFromConsole = new Scanner(System.in);
        boolean isWorking=true;
        while (isWorking){
            System.out.print("Введите строку:");
            String first = inputFromConsole.nextLine();
            if(Objects.equals(first, "exit")){
                isWorking=false;
                break;
            }
            DatagramPacket sendingPacket = new DatagramPacket(first.getBytes(),first.getBytes().length,IPAddress,10000);
            clientSocket.send(sendingPacket);
            System.out.println("Вы отправили данные серверу");
            byte[] receivingDataBuffer = new byte[1024];
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Клиент ожидает данные с сервера");
            clientSocket.receive(inputPacket);
            String receivedData = new String(inputPacket.getData(),0,inputPacket.getLength());
            System.out.println("Клиент получил строку: "+receivedData);
        }
        clientSocket.close();
        System.out.println("Вы закрыли сокет");
    }
}
