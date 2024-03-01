package crz.SchedulingSim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * 
 */
public class Main 
{
    
    public static Process[] readInputFile(File file)
    {

        BufferedReader reader;
        try 
        {
            reader = new BufferedReader( new FileReader(file));
        } 
        catch (FileNotFoundException e) 
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
                int[] processInfo = new int[4];
                String[] list = line.split("\s*");
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
                    System.err.printf("Format Error. %nError Line %s %nOn Line %d, %s is not an integer. %n", line, i, list[i]);
                    return new Process[0];
                }
                finally
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Process[] processesArray = new Process[processessStack.size()];
        int i = 0;
        while (!processessStack.isEmpty()) 
        {
            processesArray[i] = processessStack.pop();
            i += 1;       
        }
        return processesArray;

    }

    public static void main( String[] args )
    {   

    }

}
