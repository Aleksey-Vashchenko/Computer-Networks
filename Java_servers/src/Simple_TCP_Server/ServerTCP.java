package Simple_TCP_Server;

import java.net.*;
import java.io.*;
import java.util.Objects;

public class ServerTCP {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10000);
        System.out.println("сервер включен и работает на порте: 10000");
        int counter=0;
        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket.toString());
            counter++;
            System.out.println("пользователь" +counter+"был принят");
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            boolean workingWithClient = true;
            while (workingWithClient){
                String dataFromClient = dataInputStream.readUTF();
                System.out.println("получен запрос "+dataFromClient);
                if(Objects.equals(dataFromClient, "exit")){
                    dataInputStream.close();
                    dataOutputStream.close();
                    clientSocket.close();
                    System.out.println("Клиент закрыл сокет");
                    break;
                }
                else {
                    int separatorPosition = dataFromClient.indexOf('/');
                    if((dataFromClient.substring(0,separatorPosition)).equals(dataFromClient.substring(separatorPosition+1,dataFromClient.length()))){
                        dataOutputStream.writeUTF("Истина\n");
                    }
                    else {
                        dataOutputStream.writeUTF("Ложь\n");
                    }
                    dataOutputStream.flush();
                    System.out.println("Сервер ожидает новый запрос");
                }
            }
        }
    }
}
