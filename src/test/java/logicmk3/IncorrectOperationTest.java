package logicmk3;

import logicmk3.IExpr;
import logicmk3.LogicAbstractTxtParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class IncorrectOperationTest {
    @Test
    public void incorrectOperationTest(){
        LogicAbstractTxtParser parser = new LogicAbstractTxtParser();
        try{
            BufferedReader file = new BufferedReader(new FileReader("operatorerror.txt"));
            String line;
            while ((line=file.readLine()) != null){
                parser.parseLine(line);
            }
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found ");
            return;
        }
        catch (NullPointerException e){
            System.out.println("Fatal error");
            System.out.println("Missing file parameter");
            return;
        }
        if (parser.isStatus()){
            for (int i = 0; i < parser.getRules().size(); i++){
                for (IExpr expression : parser.getRules()){
                    expression.eval();
                }
            }
            for (String result : parser.getFacts())
                System.out.println(result);
        }
        else System.out.println("Logical error(s) detected\nCheck the log above");
        Set<String> returnedResult = parser.getFacts();
        assertEquals(returnedResult.toString(), "[A, B]");

    }
}
