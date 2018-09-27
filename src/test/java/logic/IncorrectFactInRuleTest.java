package logic;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class IncorrectFactInRuleTest {
    @Test
    public void incorrectFactInRuleTest(){
        try{
            LogicReader reader = new LogicReader("factruleerror.txt");
            reader.readFile();
            LogicParser parser = new LogicParser(reader);
            parser.parseFile();
            Set<String> returnedResult = parser.getKeys();
            assertEquals(returnedResult.toString(), "[THIS]");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
