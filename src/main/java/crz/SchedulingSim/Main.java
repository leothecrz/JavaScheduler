package crz.SchedulingSim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * 
 */
public class Main 
{

	enum ALGORITHM
	{
		FCFS,
		SJF,
		RR,
		PRIORITY,
		UNSET
	}

    /**
	* 
	* @param file
	* @return
    */ 
    public static Process[] readInputFile(File file)
    {

        BufferedReader reader;
        try 
        {
            reader = new BufferedReader( new FileReader(file));
        } 
        catch (FileNotFoundException e) 
        //Only accesible if file is deleted while the program passes file to function.
        {
            System.err.printf("File '%s' Not Found", file.getAbsolutePath());
            return new Process[0];
        }

        Stack<Process> processessStack = new Stack<>();

        try 
        {
            String line;
            while ( (line = reader.readLine()) != null ) 
            {
                if(line.isBlank())
                    continue;

                String[] list = line.split("\s+");
                int[] processInfo = new int[list.length];
                int i = 0;

                try 
                {
                    while (i < list.length) 
                    {
                        processInfo[i] = Integer.valueOf(list[i]);
                        i +=1;    
                    }                
                }
                catch (NumberFormatException e) 
                {
					try {
                        reader.close();
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                    System.err.printf("Format Error. %nError Line %s %nOn Line %d, %s is not an integer. %n", line, i, list[i]);
                    return new Process[0];
                }
                
                processessStack.push(new Process(processInfo[0], processInfo[1], processInfo[2], processInfo[3]));

            }
        } 
        catch (IOException e) 
        {
            System.err.println("IO ERROR " + e.toString());
            return new Process[0];
        }
        finally
        {
            try 
            {
                reader.close();
            } 
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }

        int stackSize = processessStack.size();
        Process[] processesArray = new Process[stackSize];

        int i = 0;
        stackSize += -1;
        while (!processessStack.isEmpty()) 
        {
            processesArray[stackSize - i] = processessStack.pop();
            i += 1;
        }
        return processesArray;

    }

	/**
	 * 
	 */
	public static void printMenu()
	{
		System.out.print("\nSelect an algorithm (enter either the number or name):\n 0)FCFS\n 1)SJF\n 2)RR\n 3)Priority\n\n  >>");
	}

	/**
	 * 
	 * @return
	 */
	public static ALGORITHM selectAlgorithm()
	{
		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
		int attempts = 0;
		try 
		{
			while (attempts < 3) 
			{
				
				printMenu();

				switch (reader.readLine().toLowerCase()) 
				{
					case "fcfs", "0":
						return ALGORITHM.FCFS;
				
					case "sjf", "1":		
						return ALGORITHM.SJF;

					case "rr", "2":					
						return ALGORITHM.RR;

					case "priority", "3":						
						return ALGORITHM.PRIORITY;

					default:
						System.out.println("\nNot an option try again.");
						attempts += 1;
						break;
				}
				
			}
			System.out.println("Too many attempts. Goodbye.");
			return ALGORITHM.UNSET;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return ALGORITHM.UNSET;
		}
	}

	/**
	 * TODO: Confirm given path
	 * @return
	 */
	public static String getFilePath()
	{
		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
		int attempts = 0;
		try 
		{
			while (attempts < 3) 
			{
				
				System.out.print("\nEnter the path to the processes file:\n >>");
				String path = reader.readLine();
				File test = new File(path);
				if(test.exists())
					return path;
				
				System.out.print("\nThere is no file at: " + path + ". Try again.");
				attempts += 1;
				
			}
			return null;
		} 
		catch (NullPointerException | IOException e ) 
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	public static Long getTimeQuantumSize()
	{
		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
		int attempts = 0;
		try 
		{
			while (attempts < 3) 
			{
				
				System.out.print("\nEnter the size for a TIME QUANTUM:\n >>");
				String input = reader.readLine();
				
				try 
				{
					long value = Long.valueOf(input);
					return value;
				} 
				catch (NumberFormatException e) 
				{
					System.out.print("\n" + input + " is not a valid time quantum.");
					attempts += 1;
				}
			}
			return null;
		} 
		catch (IOException e ) 
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param processes
	 */
	public static void printProcessData(Process[] processes)
	{
		long waitingSum = 0;
		long responceSum = 0;
		long turnAroundSum = 0;

		for (Process process : processes) 
		{
			waitingSum += process.getWaitingTime();
			responceSum += process.getResponceTime();
			turnAroundSum += process.getTurnAroundTime();	
		}

		double waitingAvg = (double) waitingSum / processes.length;
		double responceAvg = (double) responceSum / processes.length;
		double turnAvg = (double) turnAroundSum / processes.length;

		System.out.println(String.format("Waiting Avg %f", waitingAvg));
		System.out.println(String.format("Responce Avg %f", responceAvg));
		System.out.println(String.format("Turn Avg %f", turnAvg));
	}

	/**
	 * 
	 */
	public static void FCFS(Process[] processes)
	{
		Arrays.sort(processes, Comparator.comparingLong(Process::getArrivalTime));
		long currentTime = 0;
		for (Process process : processes) 
		{
			if(currentTime < process.getArrivalTime())
				currentTime = process.getArrivalTime();
			process.setStartTime(currentTime);		

			process.setWaitingTime(process.getStartTime() - process.getArrivalTime());
			
			currentTime += process.getBurstTime();
			process.setFinishTime(currentTime);
		}
		printProcessData(processes);
	}

	/**
	 * 
	 */
	public static void SJF(Process[] processes)
	{
		Arrays.sort(processes, Comparator.comparingLong(Process::getArrivalTime));
		PriorityQueue<Process> runningQueue = new PriorityQueue<>(Comparator.comparingLong(Process::getBurstTime));

		int lastCheckedIndex = 1;
		long currentTime = 0;
		runningQueue.add(processes[0]);
		while (!runningQueue.isEmpty()) 
		{
			Process active = runningQueue.poll();

			if(currentTime < active.getArrivalTime())
				currentTime = active.getArrivalTime();
			active.setStartTime(currentTime);

			active.setWaitingTime(active.getStartTime() - active.getArrivalTime());
			
			currentTime += active.getBurstTime();
			active.setFinishTime(currentTime);

			while (lastCheckedIndex < processes.length) 
			{
				if(processes[lastCheckedIndex].getArrivalTime() <= currentTime)
					runningQueue.add(processes[lastCheckedIndex]);
				else
					break;
				lastCheckedIndex += 1;
			}
			
		}
		printProcessData(processes);
	}

	/**
	 * 
	 */
	public static void RR(Process[] processes, long quantum)
	{
		Arrays.sort(processes, Comparator.comparingLong(Process::getArrivalTime));
		Queue<Process> queue = new LinkedList<>();
		for (Process process : processes)
			queue.add(process);

		long currentTime = 0;
		while (!queue.isEmpty()) 
		{
			Process active = queue.poll();

			System.out.println(active.toString());

			//Wait until next process arives. Idle Time.
			if(currentTime < active.getArrivalTime())
				currentTime = active.getArrivalTime();
			
			//Set start
			if(!active.isStartTimeSet())
				active.setStartTime(currentTime);

			//Update Wait Time
			active.setWaitingTime(active.getWaitingTime() + currentTime - active.getLastArrivalTime());

			// Adjust current time and remaining time
			if(active.getRemainingTime() <= quantum)
			{
				currentTime += active.getRemainingTime();
				active.setFinishTime(currentTime);
			}
			else
			{
				currentTime += quantum;
				active.decrementRemainingTime(quantum);
				active.setLastArrivalTime(currentTime);
				queue.add(active);
			}
		}
		printProcessData(processes);
	}

	/**
	 * Preemptive
	 * Interup when a new process enterns and compare priority
	 */
	public static void Priority(Process[] processes)
	{
		Arrays.sort(processes, Comparator.comparingLong(Process::getArrivalTime));
		PriorityQueue<Process> pQueue = new PriorityQueue<>(Comparator.comparingLong(Process::getPriority));

		int lastCheckedIndex = 1;
		long currentTime = 0;
		pQueue.add(processes[0]);

		while (!pQueue.isEmpty()) 
		{
			Process active = pQueue.poll();

			if(currentTime < active.getArrivalTime())
				currentTime = active.getArrivalTime();

			if(!active.isStartTimeSet())
				active.setStartTime(currentTime);

			active.setWaitingTime(active.getWaitingTime() + currentTime - active.getLastArrivalTime());

			// Check Interups
			boolean wasInterupted = false;
			while (lastCheckedIndex < processes.length) 
			{

				Process innerActive = processes[lastCheckedIndex];
				if(innerActive.getArrivalTime() > currentTime + active.getRemainingTime())
					break;

				if(active.getPriority() < innerActive.getPriority())
				{
					long passedTime = innerActive.getLastArrivalTime() - currentTime;

					

					wasInterupted = true;
				}
				else
					pQueue.add(processes[lastCheckedIndex]);

				lastCheckedIndex += 1;
			}

			if(!wasInterupted)
			{
				currentTime += active.getRemainingTime();
				active.setFinishTime(currentTime);
			}


		}

		printProcessData(processes);

	}

	/**
	 * 
	 * @param args
	 */
    public static void main( String[] args )
    {   
		
		String path = getFilePath();
		if(path == null)
			return;
		
		ALGORITHM algo = selectAlgorithm();
		if(algo == ALGORITHM.UNSET)
			return;
		
		Long timeQLong = null;
		if(algo == ALGORITHM.RR)
		{
			timeQLong = getTimeQuantumSize();
			
			if(timeQLong == null)
				return;
		}
			
		Process[] pArray = readInputFile(new File(path));
		if (pArray.length < 1)
			return;

		System.out.println("Process List:");
		for (Process process : pArray) 
			System.out.println(process.toString());
		System.out.println();

		switch (algo) 
		{
			case PRIORITY -> Priority(pArray);

			case FCFS -> FCFS(pArray);
			case SJF -> SJF(pArray); 
			case RR -> RR(pArray, timeQLong);
			case UNSET -> {}
		}
		
		System.out.println();
		System.out.println("Final List:");
		for (Process process : pArray)
		{
			System.out.println(process.toString());
			System.out.println(process.toStatString());
			System.out.println();
		} 


    }

}
