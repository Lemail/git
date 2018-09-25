package logic;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class LogicReader{
    BufferedReader reader;
    String cutLine = "----------------------------------------------------------------";
    HashMap<String, Boolean> variables;
    String input;

    public LogicReader(String inputFile) throws IOException{
        input = inputFile;
        reader = new BufferedReader(new FileReader(inputFile));
        variables = new HashMap<String, Boolean>();
    }

    public void readInput() throws IOException{
        String readLine;
        this.setVariables(this.input);
        try{
            while (!(readLine = reader.readLine()).equals(cutLine)){
                Scanner scanner = new Scanner(readLine);
                String expression = scanner.useDelimiter("->").next();
                int variableStart = 0;
                char[] delimeter = {'&', '|'};
                char[] expressionArray = expression.toCharArray();
                List<String> variableList = new ArrayList<String>();
                List<String> expressionList = new ArrayList<String>();
                try{
                    for (int i = 0; i < expressionArray.length; i++){
                        if (((expressionArray[i+1] == delimeter[0]) || (expressionArray[i+1] == delimeter[1])) &&
                                ((expressionArray[i] == delimeter[0]) || (expressionArray[i] == delimeter[1]))){
                            variableList.add(buildVariable(expressionArray, variableStart, i - 1));
                            variableStart = i + 2;
                        }
                        if (((expressionArray[i] == delimeter[0]) && (expressionArray[i+1] == delimeter[0])) ||
                                ((expressionArray[i] == delimeter[1]) && (expressionArray[i+1] == delimeter[1]))){
                            expressionList.add(buildVariable(expressionArray,  i, i + 1));
                        }
                    }
                }
                catch (IndexOutOfBoundsException ignored){

                }
                variableList.add(buildVariable(expressionArray, variableStart, expressionArray.length - 1));
                if (checkExpression(variableList, expressionList).equals("true")){
                    variables.put(scanner.next(), true);
                }

            }
        }
        catch (NullPointerException e){
            System.out.println("Error while reading file in method readInput()");
            System.out.println("Invalid delimiter in file"+this.input);
            System.out.println("Expected: "+this.cutLine);
        }
        reader.close();
    }

    private void setVariables(String input) throws IOException{
        String readLine;
        BufferedReader variableReader = new BufferedReader(new FileReader(input));
        try{
            while (!(variableReader.readLine()).equals(cutLine)){
            }
            while ((readLine = variableReader.readLine()) != null){
                Scanner line = new Scanner(readLine).useDelimiter(",");
                while (line.hasNext()){
                    String current = line.next();
                    variables.put(current, true);
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Error while reading file in method setVariables(): ");
            System.out.println("Invalid delimiter in file"+this.input);
            System.out.println("Expected: "+this.cutLine);
        }

        variableReader.close();
    }

    private String buildVariable(char[] characters, int first, int last){
        String result = new String();
        for (int i = first; i <= last; i++)
            result += ""+characters[i];
        return result;
    }

    public String checkExpression(List<String> variableList, List<String> expressionList){

        StringBuilder expression = new StringBuilder("");

        for (int i = 0; i < variableList.size(); i++){
            if (variables.get(variableList.get(i))!= null) {
                expression.append(variables.get(variableList.get(i)).toString());
            } else expression.append("false");

            if (expressionList.size() > i)
                expression.append(expressionList.get(i));
        }

        try {
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine se = sem.getEngineByName("JavaScript");
            return (se.eval(expression.toString()).toString());
        } catch (ScriptException e) {
            System.out.println("Invalid Expression");
        }
        return null;
    }

    public HashMap<String, Boolean> getVariables(){return variables;}
}