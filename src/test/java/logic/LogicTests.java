package logic;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LogicTests {
    @Test
    public void readerTest(){
        try{
            LogicReader reader = new LogicReader("input.txt");
            assertEquals(reader.input, "input.txt");
            assertEquals(reader.cutLine, "----------------------------------------------------------------");
            reader.readInput();
            Set<String> expectedResult = new HashSet<>(Arrays.asList("Dell", "Action", "Bad", "Eval", "Cell"));
            Set<String> returnedResult = reader.getVariables().keySet();
            assertEquals(expectedResult, reader.getVariables().keySet());
            assertSame(returnedResult, reader.getVariables().keySet());
            System.out.println("Connection successful");
        }
        catch (IOException e){
            System.out.println("Connection failed");
        }
    }

}
