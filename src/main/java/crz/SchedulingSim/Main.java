package crz.SchedulingSim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.io.InputStreamReader;

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
                    System.err.printf("Format Error. %nError Line %s %nOn Line %d, %s is not an integer. %n", line, i, list[i]);
                    
                    try 
                    {
                        reader.close();
                    } 
                    catch (IOException ex) 
                    {
                        ex.printStackTrace();
                    }

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

    public static String askForFileName()
    {
        System.out.print("Enter the path to the file to be analysed. \n >>");
        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            
            File fCheck;

            String input = in.readLine();

            

            
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public static void showMenu()
    {

    }

    public static void main( String[] args )
    {
        System.out.println(Arrays.toString( readInputFile(new File(args[0])) ));
    }

}
