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
    public void correctReaderTest(){
        try{
            LogicReader test = new LogicReader("test.txt");
            test.readInput();
            LogicReader reader = new LogicReader("input.txt");
            reader.readInput();
            Set<String> returnedResult = reader.getKeys();
            assertSame(returnedResult, reader.getKeys());
            System.out.println("Connection successful");
        }
        catch (IOException e){
            System.out.println("Connection failed");
        }
    }

}
