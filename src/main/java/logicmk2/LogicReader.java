package logicmk2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LogicReader {
    private String fileName;
    private ArrayList<String> readFile = new ArrayList<>();
    private String factLine;

    public LogicReader(String fileName){
        this.fileName = fileName;
    }

    public void readFromFile() throws IOException, NullPointerException{
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        String readLine;
        String delimiter = "----------------------------------------------------------------";
        try{
            while ((readLine=file.readLine()) != null && !readLine.equals(delimiter)){
                this.readFile.add(readLine);
            }

            while ((readLine=file.readLine()) != null && !readLine.equals("")){
                factLine = readLine;
            }
            if (factLine == null) throw new NullPointerException();
        }
        catch (NullPointerException e){
            System.out.println("Fatal error while reading file");
            System.out.println("Invalid delimiter in file "+this.fileName);
            System.out.println("Expected: "+ delimiter);
            System.out.println("Terminating application");
            file.close();
            System.exit(1);
        }

        file.close();

    }

    public ArrayList<String> getReadFile() {
        return readFile;
    }

    public String getFactLine() {
        return factLine;
    }

    public String getFileName() {
        return fileName;
    }
}
