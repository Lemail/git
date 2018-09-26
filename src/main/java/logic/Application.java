package logic;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try{
            LogicReader reader = new LogicReader(args[0]);
            System.out.println("If you do not get expected results, check the following parameters:");
            System.out.println("Supported operations are && and || exactly as given");
            System.out.println("Delimiter of rules and facts is expected to be 64 dashes");
            System.out.println("Rule equation is implemented by ->");
            System.out.println();
            reader.readInput();
            System.out.println("Result:");
            for (String test : reader.getKeys()){
                System.out.println(test);
            }
        }
        catch (IOException e){
            System.out.println("Error while opening file "+args[0]);
            System.out.println("Check input of file name and its existence");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("File name missing");
            System.out.println("Please check console command for missing argument (should contain input file)");
        }


    }
}
