package logicmk2;

import logic.ExpressionTxt;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class IncorrectFactInRuleTest {
    @Test
    public void incorrectFactInRuleTest(){
        LogicReader reader = new LogicReader("factruleerror.txt");
        LogicParserTxt parser = new LogicParserTxt();
        try{
            reader.readFromFile();
            parser.parseFile(reader.getReadFile());
            parser.parseVariablesLine(reader.getVariablesLine());
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found \""+reader.getFileName()+"\"");
        }
        catch (NullPointerException e){
            System.out.println("Fatal error");
            System.out.println("File name missing");
        }
        LogicEvaluator evaluator = new LogicEvaluator(parser.getVariables());
        for (int i = 0; i < reader.getReadFile().size(); i++){
            for (ExpressionTxt expression : parser.getExpressions()){
                evaluator.evaluateExpression(expression);
            }
        }
        Set<String> returnedResult = evaluator.getResult();
        assertEquals(returnedResult.toString(), "[THIS]");
    }
}
