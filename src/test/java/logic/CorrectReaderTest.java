package logic;

import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class CorrectReaderTest {
    @Test
    public void correctReaderTest(){
        try{
            LogicReader reader = new LogicReader("correctinput.txt");
            reader.readFile();
            LogicParser parser = new LogicParser(reader);
            parser.parseFile();
            Set<String> returnedResult = parser.getKeys();
            assertEquals(returnedResult.toString(), "[VV, A, C, S, D, E]");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
