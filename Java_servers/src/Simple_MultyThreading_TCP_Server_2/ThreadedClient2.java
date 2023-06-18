package Simple_MultyThreading_TCP_Server_2;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ThreadedClient2 {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1",10000);
        System.out.println("Вы присоединились к серверу");
        Scanner inputFromConsole = new Scanner(System.in);
        DataOperator operator = new DataOperator(clientSocket);
        while (true){
            System.out.println("1 - Добавить студента\n"+
                    "2 - Удалить студента\n"+
                    "3 - Просмотреть студента\n"+
                    "4 - Вывести студентов по первой букве\n"+
                    "5 - Конец сессии");
            System.out.print("Введите действие:");
            String operation = inputFromConsole.nextLine();
            operator.send(operation);
            switch (operation){
                case ("1"):{
                    Student studentToAdd = new Student();
                    studentToAdd.inputData();
                    operator.sendStudent(studentToAdd);
                    if(Objects.equals(operator.accept(), "1")){
                        System.out.println("Студент был успешно добавлен");
                    }
                    else {
                        System.out.println("Студент с данным ФИО в этой группе уже существует");
                    }
                    continue;
                }
                case ("2"):{
                    System.out.println("Введите ФИО и группу студента");
                    Student studentForSearch = new Student();
                    studentForSearch.inputDataForSearch();
                    operator.sendStudent(studentForSearch);
                    if (Objects.equals(operator.accept(), "1")){
                        System.out.println("Студент был успешно удален");
                    }
                    else {
                        System.out.println("Студент с данным ФИО в этой группе не был найден");
                    }
                    continue;
                }

                case ("3"):{
                    System.out.println("Введите ФИО и группу студента");
                    Student studentToRemove = new Student();
                    studentToRemove.inputDataForSearch();
                    operator.sendStudent(studentToRemove);
                    if(Objects.equals(operator.accept(), "1")){
                        Student viewStudent = operator.acceptStudent();
                    }
                    else {
                        System.out.println("Студент с данным ФИО в этой группе не был найден");
                    }
                    continue;
                }
                case ("4"):{
                    System.out.println("Введите первую букву");
                    String firstChar = inputFromConsole.nextLine();
                    operator.send(firstChar);
                    ArrayList<Student> students = operator.acceptStudents();
                    if(students.size()>0){
                        System.out.println("Сотрудники с зарплатой ниже введенной не найдено");
                    }
                    else {}
                    continue;
                }
                case ("5"):{
                    operator.close();
                    clientSocket.close();
                    System.out.println("Вы закрыли сокет");
                }
            }
            System.out.println('\n');
            break;
        }
    }
}
