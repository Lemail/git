package logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LogicParser {

    private LogicReader logicReader;
    private BufferedReader reader;
    private String delimiter;
    private String fileName;
    private boolean status;
    private LogicEvaluator evaluator;
    private HashMap<String, Boolean> variables;

    public LogicParser(LogicReader logicReader){
        this.logicReader = logicReader;
        this.delimiter = logicReader.getDelimiter();
        this.status = logicReader.getStatus();
        this.fileName = logicReader.getFileName();
        this.evaluator = new LogicEvaluator(this.logicReader);
        this.variables = this.logicReader.getVariables();
    }
    
    
    public void parseFile() throws IOException{
        String readLine;
        TreeSet<Integer> skipLines = new TreeSet<>();
        skipLines.add(logicReader.getLines());
        int currentString;
        for (int string = 0; string < logicReader.getLines() - 2; string++){
            currentString = 0;
            reader = new BufferedReader(new FileReader(fileName));
            try{
                while (!(readLine = reader.readLine()).equals(delimiter)){
                    if (skipLines.contains(currentString + 1)){
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
                            if (i != expressionArray.length - 1){
                                if (((expressionArray[i+1] == delimiter[0]) || (expressionArray[i+1] == delimiter[1])) &&
                                        ((expressionArray[i] == delimiter[0]) || (expressionArray[i] == delimiter[1]))){
                                    String addVariable = buildVariable(expressionArray, variableStart, i - 1).trim();
                                    if (addVariable.matches("[a-zA-Z_]*[\\w]")){
                                        variableList.add(addVariable);
                                    }
                                    else{
                                        if (addVariable.equals("")){
                                            System.out.println("Missing fact in rule "+readLine+" (line "+currentString+")");
                                            System.out.println();
                                            skipLines.add(currentString);
                                            status = false;
                                        }else{
                                            System.out.println("Error in rule "+readLine+" (line "+currentString+")");
                                            System.out.println("Invalid fact "+addVariable);
                                            System.out.println("Unsupported symbol");
                                            System.out.println("Check for missing operation and fact correctness");
                                            System.out.println();
                                            skipLines.add(currentString);
                                            status = false;
                                        }
                                    }
                                    variableStart = i + 2;
                                }
                                if ((expressionArray[i] == delimiter[0])  || ((expressionArray[i] == delimiter[1]))){
                                    if (((expressionArray[i] == delimiter[0]) && (expressionArray[i+1] == delimiter[0])) ||
                                            ((expressionArray[i] == delimiter[1]) && (expressionArray[i+1] == delimiter[1]))){
                                        expressionList.add(buildVariable(expressionArray,  i, i + 1).trim());
                                        i++;
                                    }
                                    else{
                                        skipLines.add(currentString);
                                        System.out.println("Invalid operation "+expressionArray[i]+" in rule "
                                                +readLine+" (line "+currentString+")");
                                        System.out.println("Supported operations are \"&&\" and \"||\"");
                                        System.out.println();
                                        status = false;
                                        break;
                                    }

                                }
                            }
                            else{
                                String addVariable = buildVariable(expressionArray, variableStart, expressionArray.length - 1).trim();
                                if (addVariable.matches("[a-zA-Z_]*[\\w]")){
                                    variableList.add(buildVariable(expressionArray, variableStart, expressionArray.length - 1).trim());
                                }
                                else{
                                    if (addVariable.equals("")){
                                        System.out.println("Missing fact in rule "+readLine+" (line "+currentString+")");
                                        System.out.println();
                                        skipLines.add(currentString);
                                        status = false;
                                    }else{
                                        System.out.println("Invalid fact "+addVariable+" in rule "+readLine+" (line "+currentString+")");
                                        System.out.println("Unsupported symbol");
                                        System.out.println("Check for missing operation and fact correctness");
                                        System.out.println();
                                        skipLines.add(currentString);
                                        status = false;
                                    }

                                }
                            }
                        }
                    }
                    catch (IndexOutOfBoundsException ignored){

                    }



                    if (!skipLines.contains(currentString) && evaluator.evaluateExpression(variableList, expressionList)){
                        String insertFact = scanner.next().trim();
                        if (insertFact.matches("[a-zA-Z_]*[\\w]"))
                            variables.put(insertFact, true);
                        else{
                            System.out.println("Error in fact "+insertFact+" (line "+currentString+")");
                            System.out.println("Unsupported symbol");
                            System.out.println();
                            skipLines.add(currentString);
                            status = false;
                        }
                    }
                }
            }
            catch (NullPointerException e){
                System.out.println("Fatal error while reading file");
                System.out.println("Invalid delimiter in file "+this.fileName);
                System.out.println("Expected: "+this.delimiter);
                System.out.println("Terminating application");
                reader.close();
                System.exit(1);
            }
            reader.close();
        }
    }

    private String buildVariable(char[] characters, int first, int last){
        StringBuilder result = new StringBuilder();
        for (int i = first; i <= last; i++)
            result.append("").append(characters[i]);
        return result.toString();
    }

    public Set<String> getKeys(){
        return variables.keySet();
    }

    public boolean getStatus(){
        return status;
    }
    
}
