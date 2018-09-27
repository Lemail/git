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
        System.out.println(Arrays.deepToString(parsedFileByArrow));
    }
}
