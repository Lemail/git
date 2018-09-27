package logic;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class NullArgumentTest {
    @Test
    public void nullArgumentTest(){
        try{
            LogicReader reader = new LogicReader(null);
            reader.readFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            System.out.println("Null console argument");
            e.printStackTrace();
        }
    }
}
