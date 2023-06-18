package Simple_MultyThreading_TCP_Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MultyThreadedServer {
    public static ArrayList<Worker> workerArrayList=DataReader.main();
    public static int onlineCount=0;

    public static void main(String[] args) throws IOException,ClassNotFoundException {
        ServerSocket server=new ServerSocket(10000);
        System.out.println("Server Started ....");
        int client=0;
        ExecutorService pool = Executors.newFixedThreadPool(5);
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
                            Operator operator = new Operator(serverClient);
                            boolean isWorking = true;
                            while (isWorking) {
                                String operation = operator.accept(finalClient);
                                switch (operation) {
                                    case "1": {
                                        Worker newWorker = operator.acceptWorker(finalClient);
                                        if (isWorkerExist(newWorker.getNumber())) {
                                            operator.send("0",finalClient);
                                        } else {
                                            synchronized (workerArrayList){
                                                MultyThreadedServer.workerArrayList.add(newWorker);
                                            }
                                            operator.send("1",finalClient);
                                        }
                                        continue;
                                    }
                                    case "2": {
                                        String numberToRemove = operator.accept(finalClient);
                                        if (isWorkerExist(numberToRemove)) {
                                            synchronized (workerArrayList){
                                                MultyThreadedServer.workerArrayList.removeIf(w -> Objects.equals(w.getNumber(), numberToRemove));

                                            }
                                            operator.send("1",finalClient);
                                        } else {
                                            operator.send("false",finalClient);
                                        }
                                        continue;
                                    }
                                    case "3": {
                                        String number = operator.accept(finalClient);
                                        if (isWorkerExist(number)) {
                                            synchronized (workerArrayList){
                                                MultyThreadedServer.workerArrayList.removeIf(w -> Objects.equals(w.getNumber(), number));

                                            }
                                            operator.send("1",finalClient);
                                            while (true) {
                                                Worker workerToChange = operator.acceptWorker(finalClient);
                                                if (isWorkerExist(workerToChange.getNumber())) {
                                                    operator.send("2",finalClient);
                                                } else {
                                                    synchronized (workerArrayList){                                                    MultyThreadedServer.workerArrayList.add(workerToChange);
                                                        MultyThreadedServer.workerArrayList.add(workerToChange);
                                                    }
                                                    operator.send("1",finalClient);
                                                    break;
                                                }
                                            }
                                        } else {
                                            operator.send("0",finalClient);
                                        }
                                        continue;
                                    }
                                    case "4": {
                                        String number = operator.accept(finalClient);
                                        Worker workerToSend = new Worker("");
                                        String response = "0";
                                        for (Worker worker : MultyThreadedServer.workerArrayList) {
                                            if (Objects.equals(worker.getNumber(), number)) {
                                                workerToSend = worker;
                                                response = "1";
                                                break;
                                            }
                                        }
                                        if (response.equals("1")) {
                                            operator.send(response,finalClient);
                                            operator.sendWorker(workerToSend,finalClient);
                                        } else {
                                            operator.send(response,finalClient);
                                        }
                                        continue;
                                    }
                                    case "5": {
                                        ArrayList<Worker> workers = new ArrayList<>();
                                        String salary = operator.accept(finalClient);
                                        for (Worker worker : MultyThreadedServer.workerArrayList) {
                                            if (worker.getSalary() < Double.parseDouble(salary)) {
                                                workers.add(worker);
                                            }
                                        }
                                        operator.sendWorkers(workers,finalClient);
                                        continue;
                                    }
                                    case "6": {
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

    public static boolean isWorkerExist(String number){
        for (Worker worker:workerArrayList){
            if (Objects.equals(worker.getNumber(), number)){
                return true;
            }
        }
        return false;
    }
}












/*    Socket serverClient = server.accept();
 сервер принимает запрос на подключение клиента
            counter++;
                    int finalCounter = counter;
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(serverClient.getInputStream()));
                        String dataFromClient = reader.readLine();
                        System.out.println(dataFromClient);
                        ArrayList<String> dataToWords = new ArrayList<>(Arrays.asList(dataFromClient.split(" ")));
                        Worker workerToAdd = new Worker(dataToWords.get(0),dataToWords.get(1),dataToWords.get(2),dataToWords.get(3));
                        System.out.println(workerToAdd.toString());
                        boolean isExist=false;
                        /*for(Worker workerInArray:workerArrayList){
                            if(Objects.equals(workerInArray.getNumber(), workerToAdd.getNumber())){
                                isExist=true;
                                break;
                            }
                        }
                        char command = dataFromClient.charAt(dataFromClient.length()-1);
                        switch (command) {
                            case ('1'): {
                                if (isExist) {
                                    operator.send("0");
                                } else {
                                    workerArrayList.add(workerToAdd);
                                    operator.send("1");
                                }
                                break;
                            }
                            case ('2'): {
                                if (isExist) {
                                    workerArrayList.remove(workerArrayList.indexOf(workerToAdd));
                                    operator.send("0");
                                } else {
                                    operator.send("1");
                                }
                                break;
                            }
                            case ('3'): {
                                if (isExist) {
                                    int index = 0;
                                    for (Worker changedWorker : MultyThreadedServer.workerArrayList) {
                                        if (Objects.equals(changedWorker.getNumber(), dataToWords.get(0))) {
                                            index = workerArrayList.indexOf(changedWorker);
                                            break;
                                        }
                                    }
                                    workerArrayList.set(index, workerToAdd);
                                    operator.send("1");
                                } else {
                                    operator.send("0");
                                }
                                break;
                            }
                            case ('4'): {
                                if (isExist) {
                                    Worker workerToSend = new Worker();
                                    for (Worker workerInCycle : workerArrayList) {
                                        if (Objects.equals(workerInCycle.getNumber(), dataToWords.get(0))) {
                                            workerToSend = workerInCycle;
                                            break;
                                        }
                                    }
                                    operator.send("1" + workerToSend.toString());
                                } else {
                                    operator.send("0");
                                }
                                break;
                            }
                            case ('5'): {
                                if (isExist) {
                                    operator.send("0");
                                } else {
                                    operator.send("1");
                                }
                                break;
                            }
                            case ('6'): {
                                operator.close();
                                serverClient.close();
                                break;
                            }
                        }
                    }
                    catch (IOException ioException){
                       System.out.println(ioException);
                    }
                    catch (Exception exception){
                        System.out.println(exception);
                    }
                    finally {
                        System.out.println("Client №" + finalCounter + " exit!! ");
                    }
                }
            }).start();*/