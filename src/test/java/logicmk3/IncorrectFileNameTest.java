package logicmk3;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;


public class IncorrectFileNameTest {
    @Test
    public void incorrectFileNameTest() {
        Model model;
        LogicTxtParser parser = new LogicTxtParser();
        try{
            BufferedReader file = new BufferedReader(new FileReader("nosuchfile.txt"));
            String line;
            while ((line=file.readLine()) != null){
                parser.parseLine(line);
            }
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found");
            return;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Fatal error");
            System.out.println("Missing file parameter");
            return;
        }
    }
}
