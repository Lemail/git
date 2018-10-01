package logicmk3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        LogicAbstractTxtParser parser = new LogicAbstractTxtParser();
        try{
            BufferedReader file = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line=file.readLine()) != null){
                parser.parseLine(line);
            }
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found \""+args[0]+"\"");
            return;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Fatal error");
            System.out.println("Missing file parameter");
           return;
        }
        if (parser.isStatus()){
            for (int i = 0; i < parser.getRules().size(); i++){
                for (IExpr expression : parser.getRules()){
                    expression.eval();
                }
            }
            for (String result : parser.getFacts())
            System.out.println(result);
        }
        else System.out.println("Logical error(s) detected\nCheck the log above");
    }
}
