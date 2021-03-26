import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("src\\RR.txt");
        Scanner reader = new Scanner(input);
        int numOfProc = Integer.parseInt(reader.nextLine());
        ArrayList<Process> processes = new ArrayList<>();
        String[] inputs = new String[]{"0","0","0","0","0"};
        for(int i = 0; i < numOfProc; i++){
            inputs[0] =  reader.nextLine();
            inputs[1] = reader.nextLine();
            inputs[2] = reader.nextLine();
            inputs[3] = reader.nextLine();
            inputs[4] = reader.nextLine();
            processes.add(new Process(inputs));
        }
        int conSwitchTime = Integer.parseInt(reader.nextLine());
        int quantumTime = Integer.parseInt(reader.nextLine());
        RoundRobin roundRobin = new RoundRobin(processes, quantumTime, conSwitchTime);
        roundRobin.printExecutionOrder();
        roundRobin.printWaitingTime();
        roundRobin.printTurnaroundTime();
        roundRobin.printAvgWaitingTime();
        roundRobin.printAvgTurnaroundTime();
        System.out.println("------------------------------------------------------------");
        input = new File("src\\SJF.txt");
        reader = new Scanner(input);
        numOfProc = Integer.parseInt(reader.nextLine());
        processes = new ArrayList<>();
        inputs = new String[]{"0","0","0","0","0"};
        for(int i = 0; i < numOfProc; i++){
            inputs[0] =  reader.nextLine();
            inputs[1] = reader.nextLine();
            inputs[2] = reader.nextLine();
            inputs[3] = reader.nextLine();
            inputs[4] = reader.nextLine();
            processes.add(new Process(inputs));
        }
        conSwitchTime = Integer.parseInt(reader.nextLine());
        quantumTime = Integer.parseInt(reader.nextLine());
        ShortestJobFirst shortestJobFirst = new ShortestJobFirst(processes, conSwitchTime);
        shortestJobFirst.printExecutionOrder();
        shortestJobFirst.printWaitingTime();
        shortestJobFirst.printTurnAroundTime();
        shortestJobFirst.printAverageWaiting();
        shortestJobFirst.printAverageTurnaround();
        System.out.println("------------------------------------------------------------");
        input = new File("src\\PS.txt");
        reader = new Scanner(input);
        numOfProc = Integer.parseInt(reader.nextLine());
        processes = new ArrayList<>();
        inputs = new String[]{"0","0","0","0","0"};
        for(int i = 0; i < numOfProc; i++){
            inputs[0] =  reader.nextLine();
            inputs[1] = reader.nextLine();
            inputs[2] = reader.nextLine();
            inputs[3] = reader.nextLine();
            inputs[4] = reader.nextLine();
            processes.add(new Process(inputs));
        }
        conSwitchTime = Integer.parseInt(reader.nextLine());
        quantumTime = Integer.parseInt(reader.nextLine());
        PriorityScheduling priorityScheduling = new PriorityScheduling(processes);
        priorityScheduling.printExecutionOrder();
        priorityScheduling.printTurnAroundTime();
        priorityScheduling.printWaitingTime();
        priorityScheduling.printAverageWaiting();
        priorityScheduling.printAverageTurnaround();
        System.out.println("------------------------------------------------------------");
        input = new File("src\\ML.txt");
        reader = new Scanner(input);
        numOfProc = Integer.parseInt(reader.nextLine());
        processes = new ArrayList<>();
        inputs = new String[]{"0","0","0","0","0"};
        for(int i = 0; i < numOfProc; i++){
            inputs[0] =  reader.nextLine();
            inputs[1] = reader.nextLine();
            inputs[2] = reader.nextLine();
            inputs[3] = reader.nextLine();
            inputs[4] = reader.nextLine();
            processes.add(new Process(inputs));
        }
        conSwitchTime = Integer.parseInt(reader.nextLine());
        quantumTime = Integer.parseInt(reader.nextLine());
        MultiLevel multiLevel = new MultiLevel(processes, quantumTime);
        multiLevel.printExecutionOrder();
        multiLevel.printWaitingTime();
        multiLevel.printTurnAroundTime();
        multiLevel.printAverageWaiting();
        multiLevel.printAverageTurnaround();
    }
}