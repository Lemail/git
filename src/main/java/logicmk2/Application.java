package logicmk2;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        LogicReader reader = new LogicReader(args[0]);
        LogicParserTxt parser = new LogicParserTxt();
        try{
            reader.readFromFile();
            parser.parseRules(reader.getReadFile());
            parser.parseVariablesLine(reader.getFactLine());
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
        for (int i = 0; i < parser.getRules().size(); i++){
            for (ExpressionTxt expression : parser.getRules()){
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
