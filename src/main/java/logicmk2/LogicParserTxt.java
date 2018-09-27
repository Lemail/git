package logicmk2;

import java.util.*;

public class LogicParserTxt {
    private int lineCount;
    private String delimiter = "----------------------------------------------------------------";
    private List<String> ruleVariables = new ArrayList<String>();
    private Set<String> variables = new TreeSet<String>();

    public LogicParserTxt(){

    }

    public void parseFile(ArrayList<String> readFile){
        lineCount = readFile.size();
        String[][] parsedFileByArrow = new String[lineCount][2];
        int i = 0;
        for (String line : readFile){
            if (!line.equals("")){
                Scanner lineParser = new Scanner(line);
                lineParser.useDelimiter("->");
                try{
                    parsedFileByArrow[i][0] = lineParser.next().trim();
                    if (lineParser.hasNext())
                        parsedFileByArrow[i][1] = lineParser.next().trim();
                    else parsedFileByArrow[i][1] = "";
                }
                catch (NoSuchElementException e){
                    System.out.println("No equation in line"+i);
                }
            }
            else {
                parsedFileByArrow[i][0] = "";
                parsedFileByArrow[i][1] = "";
            }
            i++;

        }

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
        System.out.println(Arrays.deepToString(parsedFileByArrow));
    }
}
