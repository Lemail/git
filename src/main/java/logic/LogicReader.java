package logic;

import javax.lang.model.element.NestingKind;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LogicReader{
    private String delimiter = "----------------------------------------------------------------";
    private HashMap<String, Boolean> variables;
    private String fileName;
    private boolean status = true;
    private BufferedReader reader;
    private int lines;
    
    public int getLines(){
        return lines;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getFileName() {
        return fileName;
    }

    public LogicReader(String reader){
        fileName = reader;
        variables = new HashMap<>();
        lines = 0;
    }
    
    public void countLines() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        while(reader.readLine() != null) lines++;
        reader.close();
    }
    

    public void readFile() throws IOException{
        this.readVariables(this.fileName);
        countLines();
    }

    private void readVariables(String fileName) throws IOException{
        String readLine;
        BufferedReader variableReader = new BufferedReader(new FileReader(fileName));
        try {
            while (!(variableReader.readLine()).equals(delimiter)) {
            }
            while ((readLine = variableReader.readLine()) != null) {
                Scanner line = new Scanner(readLine).useDelimiter(",");
                while (line.hasNext()) {
                    String current = line.next().trim();
                    if (current.matches("[a-zA-Z_]*[\\w]"))
                        variables.put(current, true);
                    else{
                        System.out.println("Error in fact: "+current);
                        System.out.println("Unsupported symbol");
                        System.out.println();
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
            variableReader.close();
            System.exit(1);
        }

        variableReader.close();
    }

   public HashMap<String, Boolean> getVariables(){
        return variables;
   }
    public boolean getStatus(){
        return status;
    }
}