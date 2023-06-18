package Simple_MultyThreading_TCP_Server_2;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ThreadedClient2 {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1",10000);
        System.out.println("�� �������������� � �������");
        Scanner inputFromConsole = new Scanner(System.in);
        DataOperator operator = new DataOperator(clientSocket);
        while (true){
            System.out.println("1 - �������� ��������\n"+
                    "2 - ������� ��������\n"+
                    "3 - ����������� ��������\n"+
                    "4 - ������� ��������� �� ������ �����\n"+
                    "5 - ����� ������");
            System.out.print("������� ��������:");
            String operation = inputFromConsole.nextLine();
            operator.send(operation);
            switch (operation){
                case ("1"):{
                    Student studentToAdd = new Student();
                    studentToAdd.inputData();
                    operator.sendStudent(studentToAdd);
                    if(Objects.equals(operator.accept(), "1")){
                        System.out.println("������� ��� ������� ��������");
                    }
                    else {
                        System.out.println("������� � ������ ��� � ���� ������ ��� ����������");
                    }
                    continue;
                }
                case ("2"):{
                    System.out.println("������� ��� � ������ ��������");
                    Student studentForSearch = new Student();
                    studentForSearch.inputDataForSearch();
                    operator.sendStudent(studentForSearch);
                    if (Objects.equals(operator.accept(), "1")){
                        System.out.println("������� ��� ������� ������");
                    }
                    else {
                        System.out.println("������� � ������ ��� � ���� ������ �� ��� ������");
                    }
                    continue;
                }

                case ("3"):{
                    System.out.println("������� ��� � ������ ��������");
                    Student studentToRemove = new Student();
                    studentToRemove.inputDataForSearch();
                    operator.sendStudent(studentToRemove);
                    if(Objects.equals(operator.accept(), "1")){
                        Student viewStudent = operator.acceptStudent();
                    }
                    else {
                        System.out.println("������� � ������ ��� � ���� ������ �� ��� ������");
                    }
                    continue;
                }
                case ("4"):{
                    System.out.println("������� ������ �����");
                    String firstChar = inputFromConsole.nextLine();
                    operator.send(firstChar);
                    ArrayList<Student> students = operator.acceptStudents();
                    if(students.size()>0){
                        System.out.println("���������� � ��������� ���� ��������� �� �������");
                    }
                    else {}
                    continue;
                }
                case ("5"):{
                    operator.close();
                    clientSocket.close();
                    System.out.println("�� ������� �����");
                }
            }
            System.out.println('\n');
            break;
        }
    }
}
