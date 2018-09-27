package logic;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class IncorrectFileNameTest {
    @Test
    public void incorrectFileNameTest(){
        try{
            LogicReader reader = new LogicReader("nosuchfile.txt");
            reader.readFile();
            LogicParser parser = new LogicParser(reader);
            parser.parseFile();
        }
        catch (IOException e){
            System.out.println("File not found");
            e.printStackTrace();
        }
    }
}
