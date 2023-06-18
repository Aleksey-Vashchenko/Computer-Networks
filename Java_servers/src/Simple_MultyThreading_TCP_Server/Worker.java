package Simple_MultyThreading_TCP_Server;

import java.io.Serializable;
import java.util.Scanner;

public class Worker implements Serializable {
    String fio;
    String number;
    String hours;
    String tariff;

    public Worker(String string){
    }

    public Worker(){
        inputData();
    }

    public Worker(String fio, String number, String hours, String tariff) {
        this.fio = fio;
        this.number = number;
        this.hours = hours;
        this.tariff = tariff;
    }

    public void inputData(){
        Scanner inputFromConsole = new Scanner(System.in);
        System.out.println("¬ведите ‘»ќ");
        this.fio=inputFromConsole.nextLine();
        System.out.println("¬ведите табельный номер");
        this.number=inputFromConsole.nextLine();
        System.out.println("¬ведите кол-во часов");
        this.hours=inputFromConsole.nextLine();
        System.out.println("¬ведите тариф");
        this.tariff=inputFromConsole.nextLine();
    }


    @Override
    public String toString() {
        return this.fio + " " + this.number + " " + this.hours + " " + this.tariff;
    }

    public String getNumber() {
        return number;
    }

    public double getSalary(){
        return Double.valueOf(this.hours)*Double.valueOf(this.tariff);
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

}
