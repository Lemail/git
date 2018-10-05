package logicmk3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        Model model;
        LogicTxtParser parser = new LogicTxtParser();
        try{
            BufferedReader file = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line=file.readLine()) != null){
                parser.parseLine(line);
            }
            file.close();
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

        if ((model = parser.getResults()) != null){
            model.eval();
            String result = "";
            for (String fact : model.getFacts()){
                if (result.equals("")) result += fact;
                else result += ","+fact;
            }
                System.out.println(result);

        }
        else System.out.println("Logical error(s) detected\nCheck the log above");
    }
}
