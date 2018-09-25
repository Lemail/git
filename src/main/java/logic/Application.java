package logic;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try{
            LogicReader reader = new LogicReader(args[0]);
            System.out.println("Connection successful");
            System.out.println("If you do not get expected results check the following parameters:");
            System.out.println("Supported operations are && and || exactly as given");
            System.out.println("Delimiter of expressions and data is expected to be 64 dashes");
            System.out.println("Expression equation is implemented by ->");
            System.out.println();
            reader.readInput();
            System.out.println("Result:");
            for (String test : reader.getVariables().keySet()){
                System.out.println(test);
            }
        }
        catch (IOException e){
            System.out.println("Connection failed");
            System.out.println("Check input of file name and its existence");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Error while reading input file");
            System.out.println("Please check console command for missing argument (should contain input file)");
        }


    }
}
