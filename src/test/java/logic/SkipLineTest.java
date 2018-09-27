package logic;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class SkipLineTest {
    @Test
    public void skipLineTest(){
        try{
            LogicReader reader = new LogicReader("skipline.txt");
            reader.readFile();
            LogicParser parser = new LogicParser(reader);
            parser.parseFile();
            Set<String> returnedResult = parser.getKeys();
            assertEquals(returnedResult.toString(), "[A, B, T, D, E, F, H, THIS, O]");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}