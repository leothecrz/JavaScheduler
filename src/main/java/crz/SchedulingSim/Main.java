package crz.SchedulingSim;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;


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
 */
public class Main 
{

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
		
		Process[] pArray = readInputFile(new File(path));
		System.out.println( Arrays.toString(pArray) );

    }

}
