package Simple_MultyThreading_TCP_Server;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException{
        Socket clientSocket = new Socket("127.0.0.1",10000);
        System.out.println("Вы присоединились к серверу");
        Scanner inputFromConsole = new Scanner(System.in);
        Operator operator = new Operator(clientSocket);
        String responseFromServer;
        while (true){
            System.out.println("1 - Добавить сотрудника\n"+
                    "2 - Удалить сотрудника\n"+
                    "3 - Редактировать сотрудника\n"+
                    "4 - Просмотреть сотрудника\n"+
                    "5 - Вывести сотрудников по зп\n"+
                    "6 - Конец сессии");
            System.out.print("Введите действие:");
            String operation = inputFromConsole.nextLine();
            operator.send(operation);
            switch (operation){
                case ("1"):{
                    Worker workerToAdd = new Worker();
                    operator.sendWorker(workerToAdd);
                    if(Objects.equals(operator.accept(), "1")){
                        System.out.println("Сотрудник был успешно добавлен");
                    }
                    else {
                        System.out.println("Сотрудник с данным номером уже существует");
                    }
                    continue;
                }
                case ("2"):{
                    System.out.println("Введите табельный номер удаляемого сотрудника");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    if (Objects.equals(operator.accept(), "1")){
                        System.out.println("Сотрудник был успешно удален");
                    }
                    else {
                        System.out.println("Сотрудник с данным номером не был найден");
                    }
                    continue;
                }
                case ("3"):{
                    System.out.println("Введите табельный номер редактируемого работника");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    if (Objects.equals(operator.accept(), "1"))
                    {
                        while (true){
                            operator.sendWorker(new Worker());
                            if(Objects.equals(operator.accept(), "1")){
                                System.out.println("Сотрудник был изменен");
                                break;
                            }else {
                                System.out.println("Введенный табельный номер занят, введите данные заново");
                                continue;
                            }
                        }
                    }
                    else {
                        System.out.println("Сотрудник с данным номером не был найден");
                    }
                    continue;
                }
                case ("4"):{
                    System.out.println("Введите табельный номер просматриваемого работника");
                    String number = inputFromConsole.nextLine();
                    operator.send(number);
                    if(Objects.equals(operator.accept(), "1")){
                        Worker viewWorker = operator.acceptWorker();
                        System.out.println(viewWorker.toString());
                    }
                    else {
                        System.out.println("Сотрудник с данным номером не был найден");
                    }
                    continue;
                }
                case ("5"):{
                    System.out.println("Введите зарплату");
                    String salary=inputFromConsole.nextLine();
                    operator.send(salary);
                    ArrayList<Worker> workers = operator.acceptWorkers();
                    if(workers.size()>0){
                        System.out.println("Сотрудники с зарплатой ниже введенной не найдено");
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
                    System.out.println("Вы закрыли сокет");
                }
            }
            System.out.println('\n');
            break;
        }
    }
}