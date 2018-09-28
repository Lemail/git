package logicmk2;


import org.junit.Test;

import java.io.IOException;

public class NullArgumentTest {

    @Test
    public void nullArgumentTest(){
        LogicReader reader = new LogicReader(null);
        LogicParserTxt parser = new LogicParserTxt();
        try{
            reader.readFromFile();
            parser.parseRules(reader.getReadFile());
            parser.parseVariablesLine(reader.getVariablesLine());
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found \""+reader.getFileName()+"\"");
        }
        catch (NullPointerException e){
            System.out.println("Fatal error");
            System.out.println("File name missing");
        }

    }
}
