package logicmk3;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class IncorrectOperationTest {
    @Test
    public void incorrectOperationTest(){
        Model model;
        LogicTxtParser parser = new LogicTxtParser();
        try{
            BufferedReader file = new BufferedReader(new FileReader("operationerror.txt"));
            String line;
            while ((line=file.readLine()) != null){
                parser.parseLine(line);
            }
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found");
            return;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Fatal error");
            System.out.println("Missing file parameter");
            return;
        }

        if ((model = parser.getResults()) != null){
            model.eval();
            Set<String> returnedResult = model.getFacts();
            assertEquals(returnedResult.toString(), "[A, B]");
        }
        else System.out.println("Logical error(s) detected\nCheck the log above");
        assertEquals(model, null);

    }
}
