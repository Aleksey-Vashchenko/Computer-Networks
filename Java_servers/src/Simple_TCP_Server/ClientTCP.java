package Simple_TCP_Server;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) throws IOException{
        Socket clientSocket = new Socket("127.0.0.1",10000);
        DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("�� �������������� � �������");
        boolean isWorking = true;
        Scanner inputFromConsole = new Scanner(System.in);
        while (isWorking) {
            System.out.println("1 - ��������� ������\n2 - �����");
            String choose = inputFromConsole.nextLine();
            switch (choose){
                case "1":{
                    System.out.print("Input first word:");
                    String first = inputFromConsole.nextLine();
                    System.out.print("Input second word:");
                    String second = inputFromConsole.nextLine();
                    dataOutputStream.writeUTF(first+"/"+second);
                    dataOutputStream.flush();
                    System.out.println("�� ��������� ������ �������");
                    String responseFromServer = dataInputStream.readUTF();
                    System.out.println("�� �������� ����� �� �������: "+responseFromServer);
                    continue;
                }
                case "2":{
                    isWorking = false;
                    dataOutputStream.writeUTF("exit");
                    dataOutputStream.flush();
                }
            }
        }
        dataInputStream.close();
        dataOutputStream.close();
        clientSocket.close();
        System.out.println("�� ������� �����");
    }
}
