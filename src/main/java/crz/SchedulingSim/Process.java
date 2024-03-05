package crz.SchedulingSim;

public class Process 
{
 
    private int PID;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime;
    private int priority;
    private int startTime;
    private int finishTime;
    private int waitingTime;
    private int responceTime;

    public Process(int pid, int arrival, int burst, int priority)
    {

        PID = pid;
        arrivalTime = arrival;
        burstTime = burst;
        remainingTime = burst;
        this.priority = priority;

        startTime = 0;
        finishTime = 0;
        waitingTime = 0;
        responceTime = -1;

    }

    public void setStartTime(int startTime) {this.startTime = startTime;}
    public void setFinishTime(int finishTime) {this.finishTime = finishTime;}
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime;}
    public void setRemainingTime(int remainingTime) { this.remainingTime = remainingTime;}

    public int getArrivalTime() {return arrivalTime;}
    public int getBurstTime() {return burstTime;}
    public int getFinishTime() {return finishTime;}
    public int getPID() {return PID;}
    public int getPriority() {return priority;}
    public int getRemainingTime() {return remainingTime;}
    public int getResponceTime() {return responceTime;}
    public int getStartTime() {return startTime;}
    public int getWaitingTime() {return waitingTime;}

	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("PID: ");
			sb.append(String.valueOf(PID));
		sb.append("  Arrival: ");
			sb.append(String.valueOf(arrivalTime));
		sb.append("  Burst: ");
			sb.append(String.valueOf(burstTime));
		sb.append("  Priority: ");
			sb.append(String.valueOf(priority));
		sb.append("\n");
		return sb.toString();
	}

}
