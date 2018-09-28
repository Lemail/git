package logicmk2;

import logic.ExpressionTxt;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        LogicReader reader = new LogicReader(args[0]);
        LogicParserTxt parser = new LogicParserTxt();
        try{
            reader.readFromFile();
            parser.parseRules(reader.getReadFile());
            parser.parseVariablesLine(reader.getVariablesLine());
        }
        catch (IOException e){
            System.out.println("Fatal error");
            System.out.println("No such file found \""+reader.getFileName()+"\"");
            System.exit(-1);
        }
        catch (NullPointerException e){
            System.out.println("Fatal error");
            System.out.println("Missing file parameter");
            System.exit(-1);
        }
        LogicEvaluator evaluator = new LogicEvaluator(parser.getVariables());
        for (int i = 0; i < reader.getReadFile().size() - 1; i++){
            for (ExpressionTxt expression : parser.getExpressions()){
                evaluator.evaluateExpression(expression);
            }
        }
        if (parser.isStatus()){
            for (String result : evaluator.getResult())
            System.out.println(result);
        }
        else System.out.println("Logical error(s) detected\nCheck the log above");


    }
}
