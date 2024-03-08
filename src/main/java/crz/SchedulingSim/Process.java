package crz.SchedulingSim;

import java.lang.StringBuilder;

public class Process 
{
 
    private long PID;
    private long arrivalTime;
	private long lastArrival;
    private long burstTime;
    private long remainingTime;
    private long priority;
    private long startTime;
    private long finishTime;
    private long waitingTime;
    private long responceTime;
	private long turnAroundTime;

	private boolean startTimeSet;

    public Process(long pid, long arrival, long burst, long priority)
    {

        this.PID = pid;
        this.arrivalTime = arrival;
		this.lastArrival = arrival;
        this.burstTime = burst;
        this.remainingTime = burst;
        this.priority = priority;

		this.startTimeSet = false;

        this.startTime = 0;
        this.finishTime = 0;
        this.waitingTime = 0;		
        this.responceTime = -1;
		this.turnAroundTime = 0;

    }

	public Process(int pid, int arrival, int burst, int priority)
	{
	 	this((long)pid,(long)arrival,(long)burst,(long)priority);
	}

	public void decrementRemainingTime(long time)
	{
		remainingTime -= time;
		if(remainingTime < 0)
			remainingTime = 0;
	}

    public void setStartTime(long startTime) 
	{
		this.startTime = startTime;
		this.responceTime = startTime - arrivalTime;
		startTimeSet = true;
	}

    public void setFinishTime(long finishTime) 
	{
		this.finishTime = finishTime;
		this.turnAroundTime = finishTime - arrivalTime;
	}

    public void setWaitingTime(long waitingTime) { this.waitingTime = waitingTime;}

    public void setRemainingTime(long remainingTime) { this.remainingTime = remainingTime;}

	public void setLastArrivalTime(long arrivalTime) { this.lastArrival = arrivalTime;}

	public boolean isStartTimeSet() {return startTimeSet;}

    public long getArrivalTime() {return arrivalTime;}
    public long getBurstTime() {return burstTime;}
    public long getFinishTime() {return finishTime;}
    public long getPID() {return PID;}
    public long getPriority() {return priority;}
    public long getRemainingTime() {return remainingTime;}
    public long getResponceTime() {return responceTime;}
    public long getStartTime() {return startTime;}
    public long getWaitingTime() {return waitingTime;}
	public long getTurnAroundTime() {return turnAroundTime;}
	public long getLastArrivalTime() {return lastArrival;}

	public String toStatString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("StartTime: ");
			sb.append(String.valueOf(startTime));
		sb.append("  FinishTime: ");
			sb.append(String.valueOf(finishTime));
		sb.append("  WaitingTime");
			sb.append(String.valueOf(waitingTime));
		return sb.toString();
	}

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
		return sb.toString();
	}

	

}
