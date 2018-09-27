package logic;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class IncorrectOperationTest {
    @Test
    public void incorrectOperationTest(){
        try{
            LogicReader reader = new LogicReader("operationerror.txt");
            reader.readFile();
            LogicParser parser = new LogicParser(reader);
            parser.parseFile();
            Set<String> returnedResult = parser.getKeys();
            assertEquals(returnedResult.toString(), "[A, B]");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
