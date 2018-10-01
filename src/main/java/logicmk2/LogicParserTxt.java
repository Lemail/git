package logicmk2;

import java.util.*;

public class LogicParserTxt {
    private Set<String> inputFacts = new TreeSet<>();
    private List<ExpressionTxt> rules = new ArrayList<>();
    private boolean status = true;
    public LogicParserTxt(){

    }

    public void parseRules(ArrayList<String> readFile){
        int lineCount = readFile.size();
        String[][] parsedFileByArrow = new String[lineCount][2];
        int expressionLines = 0;
        TreeSet<Integer> skipLines = new TreeSet<>();
        for (String line : readFile){
            String delimiter = "----------------------------------------------------------------";
            if (line.equals(delimiter)) break;
            if (!line.equals("") && !line.matches("[\\s]+")){
                if (line.substring(0, line.indexOf(">")+1).trim().equals("->")){
                    System.out.println("Error in rule "+line.trim()+" (line "+(expressionLines + 1)+")");
                    System.out.println("Invalid rule");
                    System.out.println();
                    skipLines.add(expressionLines);
                    status = false;
                    expressionLines++;
                    continue;
                }
                Scanner lineParser = new Scanner(line);
                lineParser.useDelimiter("->");
                try{
                    String ruleVariable = lineParser.next().trim();
                    if (!ruleVariable.equals("")){
                        parsedFileByArrow[expressionLines][0] = ruleVariable;
                    }
                    else{
                        parsedFileByArrow[expressionLines][0] = "";
                        System.out.println("Error in rule "+line.trim()+" (line "+(expressionLines + 1)+")");
                        System.out.println("Missing fact");
                        System.out.println();
                        skipLines.add(expressionLines);
                        status = false;
                    }
                    if (lineParser.hasNext()){
                        ruleVariable = lineParser.next().trim();
                        lineParser.useDelimiter("");
                        if (lineParser.hasNext()){
                            System.out.println("Error in rule "+line.trim()+" (line "+(expressionLines + 1)+")");
                            System.out.println("Invalid rule");
                            System.out.println();
                            skipLines.add(expressionLines);
                            status = false;
                            continue;
                        }

                        if (ruleVariable.matches("([_]+[a-zA-Z]+[\\w]*)|([a-zA-Z]+[\\w]*)")){
                            parsedFileByArrow[expressionLines][1] = ruleVariable;
                        }
                        else{
                            if (ruleVariable.equals("")){
                                System.out.println("Error in rule "+line.trim()+" (line "+(expressionLines + 1)+")");
                                System.out.println("Missing fact ");
                                System.out.println();
                                skipLines.add(expressionLines);
                                status = false;
                            }
                            else {
                                System.out.println("Error in rule "+line.trim()+" (line "+(expressionLines + 1)+")");
                                System.out.println("Invalid fact "+ ruleVariable);
                                System.out.println("Unsupported symbol");
                                System.out.println();
                                skipLines.add(expressionLines);
                                status = false;
                            }

                        }
                    }
                    else{
                        parsedFileByArrow[expressionLines][1] = "";
                        System.out.println("Error in rule "+line.trim()+" (line "+(expressionLines + 1)+")");
                        System.out.println("Invalid rule");
                        System.out.println();
                        skipLines.add(expressionLines);
                        status = false;
                    }
                }
                catch (NoSuchElementException e){
                    System.out.println("No rule in line"+(expressionLines+1));
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
            List<String> expression = new ArrayList<>();
            List<String> operatorList = new ArrayList<>();
            try{
                for (int i = 0; i < expressionArray.length; i++){
                    if (i != expressionArray.length - 1){
                        if (((expressionArray[i+1] == delimiter[0]) || (expressionArray[i+1] == delimiter[1])) &&
                                ((expressionArray[i] == delimiter[0]) || (expressionArray[i] == delimiter[1]))){
                            String addVariable = buildVariable(expressionArray, variableStart, i - 1).trim();
                            if (addVariable.matches("([_]+[a-zA-Z]+[\\w]*)|([a-zA-Z]+[\\w]*)")){
                                expression.add(addVariable);
                            }
                            else{
                                if (addVariable.equals("")){
                                    System.out.println("Missing fact in rule "+readFile.get(currentLine).trim()+" (line "+currentLine+")");
                                    System.out.println();
                                    skipLines.add(currentLine);
                                    status = false;
                                }else{
                                    System.out.println("Error in rule "+readFile.get(currentLine).trim()+" (line "+currentLine+")");
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
                                operatorList.add(buildVariable(expressionArray,  i, i + 1).trim());
                                i++;
                            }
                            else{
                                skipLines.add(currentLine);
                                System.out.println("Invalid operation "+expressionArray[i]+" in rule "
                                        +readFile.get(currentLine).trim()+" (line "+currentLine+")");
                                System.out.println("Supported operations are \"&&\" and \"||\"");
                                System.out.println();
                                status = false;
                                break;
                            }

                        }
                    }
                    else{
                        String addVariable = buildVariable(expressionArray, variableStart, expressionArray.length - 1).trim();
                        if (addVariable.matches("([_]+[a-zA-Z]+[\\w]*)|([a-zA-Z]+[\\w]*)")){
                            expression.add(buildVariable(expressionArray, variableStart, expressionArray.length - 1).trim());
                        }
                        else{
                            if (addVariable.equals("")){
                                System.out.println("Missing fact in rule "+readFile.get(currentLine).trim()+" (line "+currentLine+")");
                                System.out.println();
                                skipLines.add(currentLine);
                                status = false;
                            }else{
                                System.out.println("Invalid fact "+addVariable+" in rule "+readFile.get(currentLine).trim()+" (line "+currentLine+")");
                                System.out.println("Unsupported symbol");
                                System.out.println("Check for missing operation and fact correctness");
                                System.out.println();
                                skipLines.add(currentLine);
                                status = false;
                            }

                        }
                    }
                    rules.add(new ExpressionTxt(expression, operatorList, parsedFileByArrow[currentLine][1]));
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
            if (current.matches("[_]*[\\p{Alpha}]+[\\w]*"))
                inputFacts.add(current);
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
        return inputFacts;
    }

    public List<ExpressionTxt> getRules() {
        return rules;
    }

    public boolean isStatus() {
        return status;
    }
}
