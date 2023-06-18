package Simple_MultyThreading_TCP_Server;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException{
        Socket clientSocket = new Socket("127.0.0.1",10000);
        System.out.println("�� �������������� � �������");
        Scanner inputFromConsole = new Scanner(System.in);
        Operator operator = new Operator(clientSocket);
        String responseFromServer;
        while (true){
            System.out.println("1 - �������� ����������\n"+
                    "2 - ������� ����������\n"+
                    "3 - ������������� ����������\n"+
                    "4 - ����������� ����������\n"+
                    "5 - ������� ����������� �� ��\n"+
                    "6 - ����� ������");
            System.out.print("������� ��������:");
            String operation = inputFromConsole.nextLine();
            operator.send(operation);
            switch (operation){
                case ("1"):{
                    Worker workerToAdd = new Worker();
                    operator.sendWorker(workerToAdd);
                    if(Objects.equals(operator.accept(), "1")){
                        System.out.println("��������� ��� ������� ��������");
                    }
                    else {
                        System.out.println("��������� � ������ ������� ��� ����������");
                    }
                    continue;
                }
                case ("2"):{
                    System.out.println("������� ��������� ����� ���������� ����������");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    if (Objects.equals(operator.accept(), "1")){
                        System.out.println("��������� ��� ������� ������");
                    }
                    else {
                        System.out.println("��������� � ������ ������� �� ��� ������");
                    }
                    continue;
                }
                case ("3"):{
                    System.out.println("������� ��������� ����� �������������� ���������");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    if (Objects.equals(operator.accept(), "1"))
                    {
                        while (true){
                            operator.sendWorker(new Worker());
                            if(Objects.equals(operator.accept(), "1")){
                                System.out.println("��������� ��� �������");
                                break;
                            }else {
                                System.out.println("��������� ��������� ����� �����, ������� ������ ������");
                                continue;
                            }
                        }
                    }
                    else {
                        System.out.println("��������� � ������ ������� �� ��� ������");
                    }
                    continue;
                }
                case ("4"):{
                    System.out.println("������� ��������� ����� ���������������� ���������");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    if(Objects.equals(operator.accept(), "1")){
                        Worker viewWorker = operator.acceptWorker();
                        System.out.println(viewWorker.toString());
                    }
                    else {
                        System.out.println("��������� � ������ ������� �� ��� ������");
                    }
                    continue;
                }
                case ("5"):{
                    System.out.println("������� ��������");
                    String salary=inputFromConsole.nextLine();
                    operator.send(salary);
                    ArrayList<Worker> workers = operator.acceptWorkers();
                    if(workers.size()>0){
                        System.out.println("���������� � ��������� ���� ��������� �� �������");
                    }
                    else {
                        for(Worker worker:workers){
                            System.out.println(worker.toString());
                        }
                    }
                    continue;
                }
                case ("6"):{
                    clientSocket.close();
                    System.out.println("�� ������� �����");
                }
            }
            System.out.println('\n');
            break;
        }
    }
}