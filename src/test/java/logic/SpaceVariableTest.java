package logic;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class SpaceVariableTest {
    @Test
    public void spaceVariableTest(){
        try{
            LogicReader reader = new LogicReader("facterror.txt");
            reader.readFile();
            LogicParser parser = new LogicParser(reader);
            parser.parseFile();
            Set<String> returnedResult = parser.getKeys();
            assertEquals(returnedResult.toString(), "[A]");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
