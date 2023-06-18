package Simple_MultyThreading_TCP_Server_2;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

public class Student implements Serializable{
    public String FIO;
    public String group;
    public String scholarship;
    public ArrayList<String> marks = new ArrayList<>();
    public Student() {
    }

    public Student(String FIO, String group, String scholarship, ArrayList<String> marks) {
        this.FIO = FIO;
        this.group = group;
        this.scholarship = scholarship;
        this.marks = marks;
    }

    public String getFIO() {
        return FIO;
    }

    public String getGroup() {
        return group;
    }

    public String getScholarship() {
        return scholarship;
    }

    public ArrayList<String> getMarks() {
        return marks;
    }

    public void inputData(){
        Scanner inputFromConsole = new Scanner(System.in);
        System.out.println("Введите ФИО");
        this.FIO=inputFromConsole.nextLine();
        System.out.println("Введите номер группы");
        this.group=inputFromConsole.nextLine();
        System.out.println("Введите стипендию");
        this.scholarship=inputFromConsole.nextLine();
        System.out.println("Введите оценки");
        String mark="";
        while (true){
            mark=inputFromConsole.nextLine();
            if(!Objects.equals(mark, "exit")){
                this.marks.add(mark);
            }
            else {
                break;
            }
        }
    }

    public void inputDataForSearch(){
        Scanner inputFromConsole = new Scanner(System.in);
        System.out.println("Введите ФИО");
        this.FIO=inputFromConsole.nextLine();
        System.out.println("Введите номер группы");
        this.group=inputFromConsole.nextLine();
    }

    @Override
    public String toString() {
        return "Student{" +
                "FIO='" + FIO + '\'' +
                ", group='" + group + '\'' +
                ", scholarship='" + scholarship + '\'' +
                ", marks=" + marks +
                '}';
    }
}
