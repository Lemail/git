package logicmk2;

import logic.LogicParser;

public class Application {
    public static void main(String[] args) {
        LogicReader reader = new LogicReader("test.txt");
        LogicParserTxt parser = new LogicParserTxt();
        reader.readFromFile();
        parser.parseFile(reader.getReadFile());

    }
}
