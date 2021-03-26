import java.util.ArrayList;

public class PriorityScheduling {
    private final ArrayList<Process> processes = new ArrayList<>();
    private ArrayList<myPair> endTimes = new ArrayList<>();

    public PriorityScheduling(ArrayList<Process> processes){
        for(Process p : processes)
            this.processes.add(new Process(p));
        System.out.println("\nPRIORITY SCHEDULER:");
        for(int i = 0; i < processes.size(); i++){
            endTimes.add(new myPair<>());
        }
    }

    private int getMinArrivalIndex(ArrayList<Process> ProcessesList){
        int min = 0;
        boolean flag = true;
        for(int i = 1 ; i < ProcessesList.size(); i++){
            if (ProcessesList.get(i).getArrivalTime() < ProcessesList.get(min).getArrivalTime()) {
                min = i;
                flag = false;
            }
        }
        if(flag)
            return getHighestPriority(ProcessesList);
        return min;
    }
    private int getHighestPriority(ArrayList<Process> processesList){
        int min = 0;
        for(int i = 1 ; i < processesList.size(); i++){
            if (processesList.get(i).getPriority() < processesList.get(min).getPriority())
                min = i;
        }
        return min;
    }
    private void decrementLowestPriority(ArrayList<Process> processesList){
        int max = 0;
        for(int i = 1 ; i < processesList.size(); i++){
            if (processesList.get(i).getPriority() > processesList.get(max).getPriority())
                max = i;
        }
        int lowestPriority=processesList.get(max).getPriority();
        processesList.get(max).setPriority(lowestPriority-1);
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

    public void printExecutionOrder() {
        int cnt = 0;
        ArrayList<Process> processesList = new ArrayList<>(processes.size());
        ArrayList<Process> tempProcesses = new ArrayList<>();
        for (Process process : processes) {
            tempProcesses.add(new Process(process));
        }
        int realTime=tempProcesses.get(getMinArrivalIndex(tempProcesses)).getArrivalTime();
        int indexOfFirstArrival;
        while(tempProcesses.size()!=0) {
            indexOfFirstArrival = getMinArrivalIndex(tempProcesses);
            if(!processesList.contains(tempProcesses.get(indexOfFirstArrival))&& tempProcesses.get(indexOfFirstArrival).getArrivalTime()<=realTime) {
                processesList.add(tempProcesses.remove(indexOfFirstArrival));
            }
            int highestPriorityIndex= getHighestPriority(processesList);
            System.out.println(realTime+" "+processesList.get(highestPriorityIndex).getName()+" "+(realTime+1));
            realTime++;
            int burstTime=processesList.get(highestPriorityIndex).getBurstTime();
            processesList.get(highestPriorityIndex).setBurstTime(burstTime-1);
           // decrementLowestPriority(processesList);
            if(processesList.get(highestPriorityIndex).getBurstTime()<=0) {
                endTimes.get(cnt).first = processesList.get(highestPriorityIndex).getName();
                endTimes.get(cnt++).second = realTime;
                processesList.remove(highestPriorityIndex);
            }
        }
        while(processesList.size()!=0)
        {
            int shortestJobIndex = getHighestPriority(processesList);
            System.out.println(realTime+" "+processesList.get(shortestJobIndex).getName()+" "+(realTime+1));
            realTime++;
            int burstTime=processesList.get(shortestJobIndex).getBurstTime();
            processesList.get(shortestJobIndex).setBurstTime(burstTime-1);
            //decrementLowestPriority(processesList);
            if(processesList.get(shortestJobIndex).getBurstTime()<=0) {
                endTimes.get(cnt).first = processesList.get(shortestJobIndex).getName();
                endTimes.get(cnt++).second = realTime;
                processesList.remove(shortestJobIndex);
            }
        }
        System.out.println();
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