package logicmk2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LogicReader {
    private String fileName;
    private ArrayList<String> readFile = new ArrayList<>();

    public LogicReader(String fileName){
        this.fileName = fileName;
    }

    public void readFromFile(){
        try {
            BufferedReader file = new BufferedReader(new FileReader(fileName));
            String readLine;
            while ((readLine=file.readLine()) != null){
                this.readFile.add(readLine);
            }
        }
        catch (IOException e){
            System.out.println("No such file found \""+fileName+"\"");
        }
        catch (NullPointerException e){
            System.out.println("File not found");
            System.exit(1);
        }
    }

    public ArrayList<String> getReadFile() {
        return readFile;
    }
}
