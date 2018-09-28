package logicmk2;

import logic.ExpressionTxt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LogicParserTxt {
    private Set<String> variables = new TreeSet<String>();
    private List<ExpressionTxt> expressions = new ArrayList<>();
    private boolean status = true;
    public LogicParserTxt(){

    }

    public void parseFile(ArrayList<String> readFile){
        int lineCount = readFile.size();
        String[][] parsedFileByArrow = new String[lineCount][2];
        int expressionLines = 0;
        TreeSet<Integer> skipLines = new TreeSet<>();
        for (String line : readFile){
            String delimiter = "----------------------------------------------------------------";
            if (line.equals(delimiter)) break;
            if (!line.equals("")){
                Scanner lineParser = new Scanner(line);
                lineParser.useDelimiter("->");
                try{
                    parsedFileByArrow[expressionLines][0] = lineParser.next().trim();
                    if (lineParser.hasNext()){
                        String ruleVariable = lineParser.next().trim();
                        if (ruleVariable.matches("[a-zA-Z_]*[\\w]")){
                            parsedFileByArrow[expressionLines][1] = ruleVariable;
                        }
                        else{
                            System.out.println("Error in rule "+line+" (line "+expressionLines+")");
                            System.out.println("Invalid fact "+ ruleVariable);
                            System.out.println("Unsupported symbol");
                            System.out.println("Check for missing operation and fact correctness");
                            System.out.println();
                            skipLines.add(expressionLines);
                            status = false;
                        }
                    }
                    else{
                        parsedFileByArrow[expressionLines][1] = "";
                        System.out.println("Error in rule "+line+" (line "+expressionLines+")");
                        System.out.println("Missing equation");
                        System.out.println();
                        skipLines.add(expressionLines);
                        status = false;
                    }
                }
                catch (NoSuchElementException e){
                    System.out.println("No equation in line"+expressionLines);
                    status = false;
                }
            }
            else {
                parsedFileByArrow[expressionLines][0] = "";
                parsedFileByArrow[expressionLines][1] = "";
            }
            expressionLines++;

        }

        for (int currentLine = 0; currentLine < expressionLines; currentLine++){
            if (skipLines.contains(currentLine)){
                continue;
            }
            if (parsedFileByArrow[currentLine][0].equals("")) continue;
            int variableStart = 0;
            char[] delimiter = {'&', '|'};
            char[] expressionArray = parsedFileByArrow[currentLine][0].toCharArray();
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
                                    System.out.println("Missing fact in rule "+parsedFileByArrow[currentLine][0]+" (line "+currentLine+")");
                                    System.out.println();
                                    skipLines.add(currentLine);
                                    status = false;
                                }else{
                                    System.out.println("Error in rule "+parsedFileByArrow[currentLine][0]+" (line "+currentLine+")");
                                    System.out.println("Invalid fact "+addVariable);
                                    System.out.println("Unsupported symbol");
                                    System.out.println("Check for missing operation and fact correctness");
                                    System.out.println();
                                    skipLines.add(currentLine);
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
                                skipLines.add(currentLine);
                                System.out.println("Invalid operation "+expressionArray[i]+" in rule "
                                        +parsedFileByArrow[currentLine][0]+" (line "+currentLine+")");
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
                                System.out.println("Missing fact in rule "+parsedFileByArrow[currentLine][0]+" (line "+currentLine+")");
                                System.out.println();
                                skipLines.add(currentLine);
                                status = false;
                            }else{
                                System.out.println("Invalid fact "+addVariable+" in rule "+parsedFileByArrow[currentLine][0]+" (line "+currentLine+")");
                                System.out.println("Unsupported symbol");
                                System.out.println("Check for missing operation and fact correctness");
                                System.out.println();
                                skipLines.add(currentLine);
                                status = false;
                            }

                        }
                    }
                    expressions.add(new ExpressionTxt(variableList, expressionList, parsedFileByArrow[currentLine][1]));
                }
            }
            catch (IndexOutOfBoundsException ignored){

            }
        }

    }

    public void parseVariablesLine(String variablesLine){
        Scanner line = new Scanner(variablesLine).useDelimiter(",");
        while (line.hasNext()) {
            String current = line.next().trim();
            if (current.matches("[a-zA-Z_]*[\\w]"))
                variables.add(current);
            else{
                System.out.println("Error in fact: "+current);
                System.out.println("Unsupported symbol");
                System.out.println();
                status = false;
            }
        }

    }

    private String buildVariable(char[] characters, int first, int last){
        StringBuilder result = new StringBuilder();
        for (int i = first; i <= last; i++)
            result.append("").append(characters[i]);
        return result.toString();
    }

    public Set<String> getVariables() {
        return variables;
    }

    public List<ExpressionTxt> getExpressions() {
        return expressions;
    }

    public boolean isStatus() {
        return status;
    }
}
