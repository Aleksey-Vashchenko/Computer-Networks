package Simple_MultyThreading_TCP_Server_2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class DataOperator {
    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public DataOperator(Socket socket) throws IOException {
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

    public Student acceptStudent() throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            Student newStudent =(Student) oos.readObject();
            System.out.println("Был принят студент: "+newStudent.toString());
            return  newStudent;
        }
        catch (ClassNotFoundException e){
            System.out.println("Ошибка");
            return null;
        }
    }
    public Student acceptStudent(int client) throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            Student newStudent =(Student) oos.readObject();
            System.out.println("Был принят студент от клиента "+client+" :"+newStudent.toString());
            return  newStudent;
        }
        catch (ClassNotFoundException e){
            System.out.println("Ошибка");
            return null;
        }
    }

    public ArrayList<Student> acceptStudents() throws IOException{
        try{
            ObjectInputStream oos = new ObjectInputStream(dataInputStream);
            ArrayList<Student>  students =(ArrayList<Student>) oos.readObject();
            System.out.println("Были приняты студенты:");
            for (Student student:students){
                System.out.println(student.toString());
            }
            return  students;
        }
        catch (ClassNotFoundException e){
            System.out.println("Ошибка");
            return null;
        }
    }

    public void sendStudent(Student student) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(student);
        System.out.println("Был отправлен студент: "+student.toString());
        dataOutputStream.flush();
    }

    public void sendStudent(Student student,int client) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(student);
        System.out.println("Клиенту "+client+" был отправлен студент: "+student.toString());
        dataOutputStream.flush();
    }

    public void sendStudents(ArrayList<Student> students,int client) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
        oos.writeObject(students);
        if(students==null){
            System.out.println("Был отправлен пустой массив");
        }
        else {
            System.out.println("Клиенту "+client+" были отправлены студенты:");
            for(Student student:students){
                System.out.println(student.toString());
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
