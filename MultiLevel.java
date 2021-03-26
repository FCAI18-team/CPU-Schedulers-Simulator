import java.util.ArrayList;
import java.util.Collections;

public class MultiLevel {
    private ArrayList<Process> processes = new ArrayList<>();
    private ArrayList<Process> firstQueue = new ArrayList<>();
    private ArrayList<Process> secondQueue = new ArrayList<>();
    private ArrayList<myPair> endTimes = new ArrayList<>();
    private final int quantum;

    public MultiLevel(ArrayList<Process> processes, int quantum)
    {
        for(Process p : processes)
            this.processes.add(new Process(p));
        this.quantum = quantum;
        System.out.println("\nMULTI LEVEL SCHEDULER:");
    }

    public void printExecutionOrder(){
        for(Process p : processes){
            if(p.getQueueNum() == 1){
                firstQueue.add(new Process(p));
            }
            else{
                secondQueue.add(new Process(p));
            }
        }
        Collections.sort(firstQueue);
        Collections.sort(secondQueue);
        int currTime = 0;
        while(true){
            if(!firstQueue.isEmpty()){
                if(firstQueue.get(0).getArrivalTime() <= currTime){
                    if(firstQueue.get(0).getBurstTime() > 0 && firstQueue.get(0).getBurstTime() <= quantum){
                        System.out.print(currTime + " " + firstQueue.get(0).getName() + " ");
                        currTime += firstQueue.get(0).getBurstTime();
                        System.out.print(currTime + "\n");
                        endTimes.add(new myPair(firstQueue.get(0).getName() , currTime));
                        firstQueue.remove(0);
                        continue;
                    }
                    else if (firstQueue.get(0).getBurstTime() > quantum){
                        System.out.print(currTime + " " + firstQueue.get(0).getName() + " ");
                        currTime += quantum;
                        System.out.print(currTime + "\n");
                        firstQueue.get(0).setBurstTime(firstQueue.get(0).getBurstTime() - quantum);
                        firstQueue.add(firstQueue.remove(0));
                        continue;
                    }
                }
                //System.out.println("not Empty but not arrived yet " + firstQueue.get(0).getName() +  " " +currTime + " " + firstQueue.get(0).getArrivalTime());
                Process tmp = new Process(firstQueue.get(0));
                Collections.sort(firstQueue);
                if (tmp.getName().compareTo(firstQueue.get(0).getName()) != 0 && firstQueue.size() != 1) {
                    continue;
                }
            }
            if(!secondQueue.isEmpty()){
                if(secondQueue.get(0).getArrivalTime() <= currTime && secondQueue.get(0).getBurstTime() > 0){
                    System.out.print(currTime + " " + secondQueue.get(0).getName() + " ");
                    currTime++;
                    System.out.print(currTime + "\n");
                    secondQueue.get(0).setBurstTime(secondQueue.get(0).getBurstTime() - 1);
                    if(secondQueue.get(0).getBurstTime() == 0) {
                        endTimes.add(new myPair(secondQueue.get(0).getName() , currTime));
                        secondQueue.remove(0);
                    }
                }
                else{
                    currTime++;
                }
                continue;
            }
            if(!firstQueue.isEmpty()){
                currTime = firstQueue.get(0).getArrivalTime();
            }
            else
                break;
        }
    }

    private int getBurstTime(String name){
        for(Process p : processes){
            if(name.compareTo(p.getName()) == 0)
                return p.getBurstTime();
        }
        return -1;
    }

    private int getArrivalTime(String name){
        for(Process p : processes){
            if(name.compareTo(p.getName()) == 0)
                return p.getArrivalTime();
        }
        return -1;
    }

    public void printWaitingTime()
    {
        int[] waitingTimes = new int[endTimes.size()];
        System.out.println("Waiting Time:");
        for(int i = 0; i < endTimes.size(); i++){
            int arrTime = getArrivalTime((String) endTimes.get(i).first);
            int burTime = getBurstTime((String) endTimes.get(i).first);
            waitingTimes[i] = (Integer) endTimes.get(i).second - arrTime - burTime;
            System.out.println(endTimes.get(i).first + " " + waitingTimes[i]);
        }
    }

    public void printTurnAroundTime()
    {
        int[] turnAroundTimes = new int[endTimes.size()];
        System.out.println("Turnaround Time:");
        for(int i = 0; i < endTimes.size(); i++){
            int arrTime = getArrivalTime((String) endTimes.get(i).first);
            turnAroundTimes[i] = (Integer) endTimes.get(i).second - arrTime;
            System.out.println(endTimes.get(i).first + " " + turnAroundTimes[i]);
        }
    }

    public void printAverageWaiting()
    {
        double avgWaitingTime = 0.0;
        for (myPair endTime : endTimes) {
            int arrTime = getArrivalTime((String) endTime.first);
            int burTime = getBurstTime((String) endTime.first);
            avgWaitingTime += (Integer) endTime.second - arrTime - burTime;
        }
        System.out.println("Average waiting Time = " + avgWaitingTime / processes.size());
    }

    public void printAverageTurnaround()
    {
        double avgTurnaround = 0.0;
        for (myPair endTime : endTimes) {
            int arrTime = getArrivalTime((String) endTime.first);
            avgTurnaround += (Integer) endTime.second - arrTime;
        }
        System.out.println("Average turnaround Time = " + avgTurnaround / processes.size());
    }
}