package logic;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LogicReader{
    //private BufferedReader reader;
    private String cutLine = "----------------------------------------------------------------";
    private HashMap<String, Boolean> variables;
    private String input;

    public LogicReader(String inputFile) throws IOException{
        input = inputFile;
        //reader = new BufferedReader(new FileReader(inputFile));
        variables = new HashMap<>();
    }

    public void readInput() throws IOException{
        String readLine;
        this.setVariables(this.input);
        int lines = 0;
        BufferedReader reader = new BufferedReader(new FileReader(input));
        while(reader.readLine() != null) lines++;
        reader.close();
        int skipString = lines;
        int currentString;
        for (int string = 0; string <= lines - 2; string++){
            currentString = 0;
            reader = new BufferedReader(new FileReader(input));
            try{
                while (!(readLine = reader.readLine()).equals(cutLine)){
                    if (currentString + 1 == skipString){
                        currentString++;
                        continue;
                    }
                    currentString++;
                    if (readLine.equals("")) continue;
                    Scanner scanner = new Scanner(readLine);
                    String expression = scanner.useDelimiter("->").next();
                    int variableStart = 0;
                    char[] delimiter = {'&', '|'};
                    char[] expressionArray = expression.toCharArray();
                    List<String> variableList = new ArrayList<>();
                    List<String> expressionList = new ArrayList<>();
                    try{
                        for (int i = 0; i < expressionArray.length; i++){
                            if (expressionArray[i] == " ".toCharArray()[0]
                                    && expressionArray[i+1] != expressionArray[i]
                                    && expressionArray[i+1] != delimiter[0]
                                    && expressionArray[i+1] != delimiter[1]){
                                skipString = currentString;
                                System.out.println("Invalid factor in  rule "+readLine+" (string "+currentString+")");
                                System.out.println("Spaces in factor names not allowed");
                                System.out.println();

                            }
                            if (((expressionArray[i+1] == delimiter[0]) || (expressionArray[i+1] == delimiter[1])) &&
                                    ((expressionArray[i] == delimiter[0]) || (expressionArray[i] == delimiter[1]))){
                                variableList.add(buildVariable(expressionArray, variableStart, i - 1).trim());
                                variableStart = i + 2;
                            }
                            if (((expressionArray[i] == delimiter[0]) && (expressionArray[i+1] == delimiter[0])) ||
                                    ((expressionArray[i] == delimiter[1]) && (expressionArray[i+1] == delimiter[1]))){
                                expressionList.add(buildVariable(expressionArray,  i, i + 1).trim());
                            }
                        }
                    }
                    catch (IndexOutOfBoundsException ignored){

                    }
                    variableList.add(buildVariable(expressionArray, variableStart, expressionArray.length - 1).trim());

                    if (checkExpression(variableList, expressionList)){
                        variables.put(scanner.next().trim(), true);
                    }
                }
            }
            catch (NullPointerException e){
                System.out.println("Error while reading file");
                System.out.println("Invalid delimiter in file "+this.input);
                System.out.println("Expected: "+this.cutLine);
                reader.close();
                System.exit(1);
            }
            reader.close();
        }

    }

    private void setVariables(String input) throws IOException{
        String readLine;
        BufferedReader variableReader = new BufferedReader(new FileReader(input));
        try {
            while (!(variableReader.readLine()).equals(cutLine)) {
            }
            while ((readLine = variableReader.readLine()) != null) {
                Scanner line = new Scanner(readLine).useDelimiter(",");
                while (line.hasNext()) {
                    String current = line.next().trim();
                    if (current.matches("[a-zA-Z_]*[\\w]"))
                        variables.put(current, true);
                    else{
                        System.out.println("Error in factor: "+current);
                        System.out.println("Spaces in factor names are not allowed");
                        System.out.println();
                    }
                }
            }
        }
        catch (NullPointerException e){
            System.out.println("Error while reading file");
            System.out.println("Invalid delimiter in file "+this.input);
            System.out.println("Expected: "+this.cutLine);
            variableReader.close();
            System.exit(1);
        }

        variableReader.close();
    }

    private String buildVariable(char[] characters, int first, int last){
        StringBuilder result = new StringBuilder();
        for (int i = first; i <= last; i++)
            result.append("").append(characters[i]);
        return result.toString();
    }

    private boolean checkExpression(List<String> variableList, List<String> expressionList){
        boolean[] logics = new boolean[expressionList.size()+1];
        try{
            if (expressionList.size() == 0){
                return variables.containsKey(variableList.get(0));
            }
            for (int i = 0; i < expressionList.size(); i++){
                if (expressionList.get(i).equals("&&")){
                    if (i != 0){
                        if (!expressionList.get(i-1).equals("&&")){
                            logics[i] = (variables.containsKey(variableList.get(i)) && variables.containsKey(variableList.get(i + 1)));
                        }
                        else{
                            logics[i] = logics[i-1] && variables.containsKey(variableList.get(i + 1));
                            logics[expressionList.size()] = logics[i];
                        }
                        if (i == expressionList.size() - 1) logics[expressionList.size()] = logics[i];
                    }
                    else logics[i] = variables.containsKey(variableList.get(i));
                    if (i == expressionList.size() - 1) logics[expressionList.size()] = logics[i];

                }

            }
            for (int i = 0; i < expressionList.size(); i++){
                if (expressionList.get(i).equals("||")){
                    if (i != 0){
                        if (logics[expressionList.size()]) return true;
                        if (expressionList.get(i+1).equals("||")){
                            if (variables.containsKey(variableList.get(i + 1))){
                                logics[expressionList.size()] = true;
                                return logics[expressionList.size()];
                            }
                        }
                    }
                    else logics[i] = variables.containsKey(variableList.get(i));
                    if (i == expressionList.size() -1) logics[expressionList.size()] = logics[i];
                }
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        /*StringBuilder expression = new StringBuilder("");

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
        return null;*/
        return logics[expressionList.size()];
    }

    public boolean checkVariable(String name){return variables.containsKey(name);}
    public Set<String> getKeys(){
        return variables.keySet();
    }
}