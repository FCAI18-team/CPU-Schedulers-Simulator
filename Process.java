public class Process implements Comparable<Process>{
    private String name;
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private int queueNum;

    public Process(String[] inputs){
        this.name = inputs[0];
        burstTime = Integer.parseInt(inputs[1]);
        arrivalTime = Integer.parseInt(inputs[2]);
        priority = Integer.parseInt(inputs[3]);
        queueNum = Integer.parseInt(inputs[4]);
    }
    public Process(Process p){
        this.name = p.name;
        this.burstTime = p.burstTime;
        this.arrivalTime = p.arrivalTime;
        this.priority = p.priority;
        this.queueNum = p.queueNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(int queueNum) {
        this.queueNum = queueNum;
    }

    @Override
    public int compareTo(Process rhs) {
        if (this.arrivalTime > rhs.arrivalTime)
            return 1;
        else if (this.arrivalTime < rhs.arrivalTime)
            return -1;
        return 0;
    }
}
