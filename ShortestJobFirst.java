import java.util.ArrayList;

public class ShortestJobFirst {
    private final ArrayList<Process> processes = new ArrayList<>();
    private final int contextSwitchTime;
    private ArrayList<myPair> endTimes = new ArrayList<>();

    public ShortestJobFirst(ArrayList<Process> processes, int contextSwitchTime){
        for(Process p : processes)
            this.processes.add(new Process(p));
        this.contextSwitchTime = contextSwitchTime;
        System.out.println("\nSHORTEST-JOB FIRST SCHEDULER:");
        for(int i = 0; i < processes.size(); i++){
            endTimes.add(new myPair<>());
        }
    }


    public void printExecutionOrder() {
        ArrayList<Process> processesList = new ArrayList<>(processes.size());
        ArrayList<Process> tempProcesses = new ArrayList<>();
        for (Process process : processes) {
            tempProcesses.add(new Process(process));
        }
        int realTime=tempProcesses.get(getMinArrivalIndex(tempProcesses)).getArrivalTime();
        int indexOfFirstArrival;
        int cnt = 0;
        String processName=tempProcesses.get(getMinArrivalIndex(tempProcesses)).getName();
        while(tempProcesses.size()!=0) {
            indexOfFirstArrival = getMinArrivalIndex(tempProcesses);
            if(!processesList.contains(tempProcesses.get(indexOfFirstArrival))&& tempProcesses.get(indexOfFirstArrival).getArrivalTime()<=realTime)
                processesList.add(tempProcesses.remove(indexOfFirstArrival));
            int shortestJobIndex=getShortestJobIndex(processesList);
            if(processesList.get(shortestJobIndex).getName().compareToIgnoreCase(processName) != 0)
            {
                processName = processesList.get(shortestJobIndex).getName();
                System.out.println(realTime +" Context Switch "+ (realTime+contextSwitchTime));
                realTime+=contextSwitchTime;
            }
            System.out.println(realTime+" "+processesList.get(shortestJobIndex).getName()+" "+(realTime+1));
            realTime++;
            int burstTime=processesList.get(shortestJobIndex).getBurstTime();
            processesList.get(shortestJobIndex).setBurstTime(burstTime-1);
            if(processesList.get(shortestJobIndex).getBurstTime()<=0)
            {
                endTimes.get(cnt).first = processesList.remove(shortestJobIndex).getName();
                endTimes.get(cnt++).second = realTime;
            }
        }
        while(processesList.size()!=0) {
            int shortestJobIndex = getShortestJobIndex(processesList);
            if (processesList.get(shortestJobIndex).getName().compareToIgnoreCase(processName) != 0) {
                processName = processesList.get(shortestJobIndex).getName();
                System.out.println(realTime + " Context Switch " + (realTime + contextSwitchTime));
                realTime += contextSwitchTime;
            }
            System.out.println(realTime + " " + processesList.get(shortestJobIndex).getName() + " " + (realTime + 1));
            realTime++;
            int burstTime = processesList.get(shortestJobIndex).getBurstTime();
            processesList.get(shortestJobIndex).setBurstTime(burstTime - 1);
            if (processesList.get(shortestJobIndex).getBurstTime() <= 0) {
                endTimes.get(cnt).first = processesList.remove(shortestJobIndex).getName();
                endTimes.get(cnt++).second = realTime;
            }
        }
        System.out.println();
    }

    public void printCompletionTime(){
        System.out.println("Completion time:");
        for(myPair p : endTimes)
            System.out.println(p.first + " " + p.second);
    }

    private int getMinArrivalIndex(ArrayList<Process> ProcessesList){
        int min = 0;
        for(int i = 1 ; i < ProcessesList.size(); i++){
            if (ProcessesList.get(i).getArrivalTime() < ProcessesList.get(min).getArrivalTime())
                min = i;
        }
        return min;
    }
    private int getShortestJobIndex(ArrayList<Process> ProcessesList){
        int min = 0;
        for(int i = 1 ; i < ProcessesList.size(); i++){
            if (ProcessesList.get(i).getBurstTime() < ProcessesList.get(min).getBurstTime())
                min = i;
        }
        return min;
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
