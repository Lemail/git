package logicmk2;

import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class InputFactCheck {
    @Test
    public void inputFactCheck(){
        LogicReader reader = new LogicReader("inputfactcheck.txt");
        LogicParserTxt parser = new LogicParserTxt();
        try{
            reader.readFromFile();
            parser.parseRules(reader.getReadFile());
            parser.parseVariablesLine(reader.getVariablesLine());
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found \""+reader.getFileName()+"\"");
            return;
        }
        catch (NullPointerException e){
            System.out.println("Fatal error");
            System.out.println("Missing file parameter");
            return;
        }
        LogicEvaluator evaluator = new LogicEvaluator(parser.getVariables());
        for (int i = 0; i < parser.getExpressions().size(); i++){
            for (ExpressionTxt expression : parser.getExpressions()){
                evaluator.evaluateExpression(expression);
            }
        }
        Set<String> returnedResult = evaluator.getResult();
        assertEquals(returnedResult.toString(), "[A, A3, Aa3, Aaaaa3, ____________________a, _________a3, _a3, a3]");
    }
}
