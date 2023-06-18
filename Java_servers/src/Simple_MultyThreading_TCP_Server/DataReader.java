package Simple_MultyThreading_TCP_Server;

import java.io.*;
import java.util.ArrayList;

public class DataReader {
    public static ArrayList main() {
        try {
            File file = new File("C:/Users/lehav/Data.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            ArrayList<Worker> workers = new ArrayList<>();
            while (reader.readLine() != null) {
                Worker workerToAdd = new Worker("null");
                workerToAdd.setFio(reader.readLine()+" "+reader.readLine()+" "+reader.readLine());
                workerToAdd.setNumber(reader.readLine());
                workerToAdd.setHours(reader.readLine());
                workerToAdd.setTariff(reader.readLine());
                workers.add(workerToAdd);
            }
            return  workers;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
