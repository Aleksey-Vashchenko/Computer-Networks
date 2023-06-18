package Simple_MultyThreading_TCP_Server_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedServer {
    public static ArrayList<Student> studentArrayList = new ArrayList<>();
    public static int onlineCount=0;

    public static void main(String[] args) throws IOException,ClassNotFoundException {
        studentArrayList.add(new Student("Ващенко Алексей Александрович","110101","113,9",  new ArrayList(Arrays.asList("10", "9", "8", "7"))));
        studentArrayList.add(new Student("Виракоон Налин Чинтанович","110102","113,9",  new ArrayList(Arrays.asList("7", "7", "7", "7"))));
        studentArrayList.add(new Student("Бобков Кирилл ","110103","113,9",  new ArrayList(Arrays.asList("10", "10", "10", "10"))));
        studentArrayList.add(new Student("Позняк Степан Анатольевич","110104","113,9",  new ArrayList(Arrays.asList("2", "2", "2", "2"))));
        ServerSocket server=new ServerSocket(10000);
        System.out.println("Server Started ....");
        int client=0;
        ExecutorService pool = Executors.newCachedThreadPool();
        while(true) {
            Socket serverClient = server.accept();
            client++;
            int finalClient = client;
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    System.out.println("Пользователь №" + finalClient + " был принят в новом потоке "+serverClient.getInetAddress()+" "+serverClient.getPort());
                    onlineCount++;
                    System.out.printf("Число активных клиентов "+onlineCount+"\n");
                    try {
                        DataOperator operator = new DataOperator(serverClient);
                        boolean isWorking = true;
                        while (isWorking) {
                            String operation = operator.accept(finalClient);
                            switch (operation){
                                case ("1"):{
                                    Student newStudent = operator.acceptStudent(finalClient);
                                    if (isStudentExist(newStudent)) {
                                        operator.send("0",finalClient);
                                    } else {
                                        synchronized (studentArrayList){
                                            studentArrayList.add(newStudent);
                                        }
                                        operator.send("1",finalClient);
                                    }
                                    continue;
                                }
                                case ("2"):{
                                    Student studentToRemove = operator.acceptStudent(finalClient);
                                    if (isStudentExist(studentToRemove)) {
                                        synchronized (studentArrayList){
                                            studentArrayList.removeIf(w -> Objects.equals(w.FIO, studentToRemove.FIO) && Objects.equals(w.group, studentToRemove.group));
                                        }
                                        operator.send("1",finalClient);
                                    } else {
                                        operator.send("false",finalClient);
                                    }
                                    continue;
                                }
                                case ("3"):{
                                    Student studentToView = operator.acceptStudent();
                                    Student studentToSend = new Student();
                                    String response = "0";
                                    for (Student student : studentArrayList) {
                                        if (Objects.equals(student.FIO,studentToView.FIO)&& Objects.equals(student.group, studentToView.group)) {
                                            studentToSend = student;
                                            response = "1";
                                            break;
                                        }
                                    }
                                    if (response.equals("1")) {
                                        operator.send(response,finalClient);
                                        operator.sendStudent(studentToSend,finalClient);
                                    } else {
                                        operator.send(response,finalClient);
                                    }
                                    continue;
                                }
                                case ("4"):{
                                    ArrayList<Student> students = new ArrayList<>();
                                    String firstChar = operator.accept(finalClient);
                                    for (Student student : studentArrayList) {
                                        if (student.FIO.substring(0, 1).equals(firstChar)) {
                                            students.add(student);
                                        }
                                    }
                                    operator.sendStudents(students,finalClient);
                                    continue;
                                }
                                case ("5"):{
                                    serverClient.close();
                                    operator.close();
                                    isWorking = false;
                                }
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    } finally {
                        System.out.println("Client №" + finalClient +" "+serverClient.getInetAddress()+" "+serverClient.getPort() +" exit!! ");
                        onlineCount--;
                        System.out.printf("Число активных клиентов "+onlineCount+"\n");
                    }
                }
            });
            pool.execute(thread);
        }
    }

    public static boolean isStudentExist(Student student){
        boolean isExist = false;
        for(Student student1:studentArrayList){
            if(Objects.equals(student1.FIO, student.FIO) && Objects.equals(student.group, student1.group)){
                isExist=true;
            }
        }
        return isExist;
    }
}
