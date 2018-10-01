package logicmk2;

import logicmk3.LogicAbstractTxtParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class IncorrectFileNameTest {
    @Test
    public void incorrectFileNameTest() {
        LogicAbstractTxtParser parser = new LogicAbstractTxtParser();
        try{
            BufferedReader file = new BufferedReader(new FileReader("nosuchfile.txt"));
            String line;
            while ((line=file.readLine()) != null){
                parser.parseLine(line);
            }
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found ");
            return;
        }
        catch (NullPointerException e){
            System.out.println("Fatal error");
            System.out.println("Missing file parameter");
            return;
        }
    }
}
