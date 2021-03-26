import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class RoundRobin {
    private final ArrayList<Process> processes = new ArrayList<>();
    private final int quantumTime;
    private final int contextSwitchTime;
    public RoundRobin(ArrayList<Process> processes, int quantumTime , int contextSwitchTime){
        for(Process p : processes)
            this.processes.add(new Process(p));
        this.quantumTime = quantumTime;
        this.contextSwitchTime = contextSwitchTime;
        System.out.println("\nROUND ROBIN SCHEDULER:");
    }

    private void calculateCompletionTime(int[] completionTime, int[] executionCount){
        int[] burstTime = new int[processes.size()];
        for(int i=0;i< processes.size();i++){
            burstTime[i]= processes.get(i).getBurstTime();
            executionCount[i] = 0;
        }
        int currentTime= 0;
        int arrivalTime = 0;
        while(true){
            boolean cpuFinished = true;
            for(int i=0;i< processes.size();i++){
                if(burstTime[i]>0){
                    executionCount[i]++;
                    cpuFinished = false;
                    if(burstTime[i]>quantumTime && processes.get(i).getArrivalTime() <= arrivalTime){
                        currentTime+=quantumTime+contextSwitchTime;
                        burstTime[i]-=quantumTime;
                    }
                    else{
                        if(processes.get(i).getArrivalTime() <= arrivalTime){
                            arrivalTime++;
                            currentTime+=burstTime[i]+contextSwitchTime;
                            burstTime[i]=0;
                            completionTime[i]=currentTime;
                        }
                    }
                    arrivalTime++;
                }
            }
            if(cpuFinished) {
                break;
            }
        }
        for(int i = 0; i < processes.size(); i++){
            completionTime[i] -= contextSwitchTime;
        }
    }

    public void printExecutionOrder() {
        ArrayBlockingQueue<Process> processesQueue = new ArrayBlockingQueue<>(processes.size());
        ArrayList<Process> tempProcesses = new ArrayList<>();
        for (Process process : processes) {
            tempProcesses.add(new Process(process));
        }
        int realTime=processes.get(getMinArrivalIndex(tempProcesses)).getArrivalTime();
        int indexOfFirstArrival;
        while(tempProcesses.size()!=0) {
            indexOfFirstArrival = getMinArrivalIndex(tempProcesses);
            processesQueue.add(tempProcesses.remove(indexOfFirstArrival));
            realTime = getRealTime(processesQueue, realTime);
            System.out.println(realTime + " Context Switch " + (realTime+=contextSwitchTime));
            checkIfProcessArrived(realTime,tempProcesses,processesQueue);
            Process p1=processesQueue.remove();
            if(p1.getBurstTime()>0)
                processesQueue.add(p1);
        }
        while(processesQueue.size()!=0)
        {
            realTime = getRealTime(processesQueue, realTime);
            Process p1=processesQueue.remove();
            if(processesQueue.size()>0 && p1.getBurstTime()<=0)
                System.out.println(realTime + " Context Switch " + (realTime+=contextSwitchTime));

            if(p1.getBurstTime()>0) {
                processesQueue.add(p1);
                System.out.println(realTime + " Context Switch " + (realTime+=contextSwitchTime));
            }
        }
    }

    private int getRealTime(ArrayBlockingQueue<Process> processesQueue, int realTime) {
        assert processesQueue.peek() != null;
        int burstTime=processesQueue.peek().getBurstTime();
        assert processesQueue.peek() != null;
        processesQueue.peek().setBurstTime(burstTime-quantumTime);
        assert processesQueue.peek() != null;
        System.out.print(realTime + "\t" + processesQueue.peek().getName()+"\t");
        realTime+=(Math.min(quantumTime, burstTime));
        System.out.print(realTime + "\n");
        return realTime;
    }

    private void checkIfProcessArrived(int realTime, ArrayList<Process> processes, Queue<Process> processesQueue)
    {
        int i=0;
        int size=processes.size();
        while(i<size)
        {
            if(processes.get(i).getArrivalTime() < realTime){
                processesQueue.add(processes.remove(i));
                i=0;
                size--;
                continue;
            }
            i++;
        }
    }

    private int getMinArrivalIndex(ArrayList<Process> ProcessesList){
        int min = 0;
        for(int i = 1 ; i < ProcessesList.size(); i++){
            if (ProcessesList.get(i).getArrivalTime() < ProcessesList.get(min).getArrivalTime())
                min = i;
        }
        return min;
    }

    public void printWaitingTime(){
        int[] waitingTime = new int[processes.size()];
        int[] completionTime = new int[processes.size()];
        int[] executionCount = new int[processes.size()];
        calculateCompletionTime(completionTime, executionCount);
        System.out.println("Waiting Time:");
        for(int i = 0; i < processes.size(); i++){
            waitingTime[i] = completionTime[i] - processes.get(i).getBurstTime();
            System.out.println(processes.get(i).getName() + " " + waitingTime[i]);
        }
    }

    public void printTurnaroundTime(){
        int[] turnaroundTime = new int[processes.size()];
        int[] completionTime = new int[processes.size()];
        int[] executionCount = new int[processes.size()];
        calculateCompletionTime(completionTime, executionCount);
        System.out.println("Turnaround Time:");
        for(int i = 0; i < processes.size(); i++){
            turnaroundTime[i] = completionTime[i] - processes.get(i).getArrivalTime();
            System.out.println(processes.get(i).getName() + " " + turnaroundTime[i]);
        }

    }

    public void printAvgWaitingTime(){
        int[] waitingTime = new int[processes.size()];
        int[] completionTime = new int[processes.size()];
        int[] executionCount = new int[processes.size()];
        calculateCompletionTime(completionTime, executionCount);
        for(int i = 0; i < processes.size(); i++){
            waitingTime[i] = completionTime[i] - processes.get(i).getBurstTime();
        }
        double avgWaiting = 0.0;
        for(int n : waitingTime)
            avgWaiting += n;
        avgWaiting /= processes.size();
        System.out.println("Average Waiting Time: " + avgWaiting);
    }

    public void printAvgTurnaroundTime(){
        int[] turnaroundTime = new int[processes.size()];
        int[] completionTime = new int[processes.size()];
        int[] executionCount = new int[processes.size()];
        calculateCompletionTime(completionTime, executionCount);
        for(int i = 0; i < processes.size(); i++){
            turnaroundTime[i] = completionTime[i] - processes.get(i).getArrivalTime();
        }
        double avgTurnaround = 0.0;
        for(int n : turnaroundTime)
            avgTurnaround += n;
        avgTurnaround /= processes.size();
        System.out.println("Average Turnaround Time: " + avgTurnaround);
    }
}
