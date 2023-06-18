package Simple_MultyThreading_TCP_Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Operator {
    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;


    public Operator(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
        this.dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void send(String stringToSend) throws IOException {
        dataOutputStream.writeUTF(stringToSend);
        System.out.println("Отправлено:"+stringToSend);
        dataOutputStream.flush();
    }
    public void send(String stringToSend,int client) throws IOException {
        dataOutputStream.writeUTF(stringToSend);
        System.out.println("Клиенту "+client+" Отправлено:"+stringToSend);
        dataOutputStream.flush();
    }

    public String accept() throws IOException{
        String response = dataInputStream.readUTF();
        System.out.println("Получено: "+response);
        return response;
    }
    public String accept(int client) throws IOException{
        String response = dataInputStream.readUTF();
        System.out.println("Получено от клиента "+client+": "+response);
        return response;
    }

    public Worker acceptWorker() throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            Worker newWorker =(Worker) oos.readObject();
            System.out.println("Был принят работник: "+newWorker.toString());
            return  newWorker;
        }
        catch (ClassNotFoundException e){
            System.out.println("Ошибка");
            return null;
        }
    }
    public Worker acceptWorker(int client) throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            Worker newWorker =(Worker) oos.readObject();
            System.out.println("Был принят работник от клиента "+client+" :"+newWorker.toString());
            return  newWorker;
        }
        catch (ClassNotFoundException e){
            System.out.println("Ошибка");
            return null;
        }
    }

    public ArrayList<Worker> acceptWorkers() throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            ArrayList<Worker>  workers =(ArrayList<Worker>) oos.readObject();
            System.out.println("Были приняты сотрудники:");
            for (Worker worker:workers){
                System.out.println(worker.toString());
            }
            return  workers;
        }
        catch (ClassNotFoundException e){
            System.out.println("Ошибка");
            return null;
        }
    }

    public void sendWorker(Worker worker) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(worker);
        System.out.println("Был отправлен сотрудник: "+worker.toString());
        dataOutputStream.flush();
    }

    public void sendWorker(Worker worker,int client) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(worker);
        System.out.println("Клиенту "+client+" был отправлен сотрудник: "+worker.toString());
        dataOutputStream.flush();
    }

    public void sendWorkers(ArrayList<Worker> workers,int client) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(workers);
        if(workers==null){
            System.out.println("Был отправлен пустой массив");
        }
        else {
            System.out.println("Клиенту "+client+" были отправлены сотрудники:");
            for(Worker worker:workers){
                System.out.println(worker.toString());
            }
        }
        dataOutputStream.flush();
    }

    public void close() throws IOException{
        this.clientSocket.close();
        this.dataOutputStream.close();
        this.dataInputStream.close();
    }
}
