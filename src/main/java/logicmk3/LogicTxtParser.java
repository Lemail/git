package logicmk3;

import java.util.*;

enum State{
        RULES, FACTS, FINAL
}

public class LogicTxtParser {
    private final String delimiter = "----------------------------------------------------------------";
    private boolean errorFound =  true;
    private int lines = 0;
    private final String correctFactRegexp = "[_]*[\\p{Alpha}]+[\\w]*";
    private Model model = new Model();
    private State state;



    public LogicTxtParser(){
        state = State.RULES;
    }


    public void parseLine(String line){
        lines++;
        if (line.equals(""))
            return;
        Scanner scanner = new Scanner(line);
        switch (state){
            case FINAL:{
                System.out.println("Error in file structure");
                System.out.println("More than one line of input facts");
                System.out.println();
                errorFound = false;
                break;
            }
            case FACTS:{
                scanner.useDelimiter(",");

                if (!line.equals("")){
                    while (scanner.hasNext()) {
                        String current = scanner.next().trim();
                        if (current.matches(correctFactRegexp))
                            model.getFacts().add(current);
                        else{
                            System.out.println("Error in fact: "+current);
                            System.out.println("Unsupported symbol");
                            System.out.println();
                            errorFound = false;
                        }
                    }
                    state = State.FINAL;
                }
                break;
            }
            case RULES:{

                if (line.equals(delimiter)) {
                    state = State.FACTS;
                    break;
                }

                int lastIndexOfArrow = line.lastIndexOf("->");

                if (lastIndexOfArrow == -1){
                    System.out.println("Error in "+line+"(line "+lines+")");
                    System.out.println("Missing \"->\"");
                    System.out.println();
                    errorFound = false;
                    break;
                }

                String expression = line.substring(0,lastIndexOfArrow);
                String resultingFact = line.substring(lastIndexOfArrow + 2,line.length());

                if (!resultingFact.matches(correctFactRegexp)){
                    System.out.println("Error in resulting fact "+resultingFact+" (line"+lines+")");
                    System.out.println("Unsupported symbol");
                    System.out.println();
                    errorFound = false;
                    break;
                }

                Rule rule = new Rule(model.getFacts(), resultingFact);

                if (expression.contains("||")){
                    rule.setRule(parseByOr(expression));
                    if ((errorFound) && (rule.getRule() != null)) model.getRules().add(rule);
                    break;
                }

                if (expression.contains("&&")){
                    rule.setRule(parseByAnd(expression));
                    if ((errorFound) && (rule.getRule() != null)) model.getRules().add(rule);
                    break;
                }

                rule.setRule(parseByFact(expression));
                if (errorFound) model.getRules().add(rule);
                break;
            }
        }
    }

    private OrExpr parseByOr(String line){
        OrExpr orExpr = new OrExpr();
        Scanner scanner = new Scanner(line);
        String part;
        scanner.useDelimiter("\\|\\|");
        while (scanner.hasNext()){
            part = scanner.next().trim();
            if (part.contains("&&")){
                orExpr.addPart(parseByAnd(part));
            }
            else {
                orExpr.addPart(parseByFact(part));
            }
        }
        if (orExpr.getParts().size() != 0)
            return orExpr;
        return null;
    }

    private AndExpr parseByAnd(String line){
        AndExpr andExpr = new AndExpr();
        Scanner scanner = new Scanner(line);
        String fact;
        scanner.useDelimiter("&&");
        while (scanner.hasNext()){
            fact = scanner.next().trim();
            andExpr.addPart(parseByFact(fact));
        }
        if (andExpr.getParts().size() != 0)
            return andExpr;
        return null;
    }

    private FactExpr parseByFact(String fact){
        FactExpr factExpr;
        if (fact.matches(correctFactRegexp)){
            factExpr = new FactExpr(model.getFacts(), fact);
        }
        else {
            if (fact.equals("")){
                System.out.println("Missing fact (line "+lines+")");
                System.out.println();
                errorFound = false;
                factExpr = new FactExpr(model.getFacts(), null);
            }
            else {
                System.out.println("Error in fact "+fact+" (line "+lines+")");
                System.out.println("Unsupported symbol");
                System.out.println();
                errorFound = false;
                factExpr = new FactExpr(model.getFacts(), null);
            }
        }
        return factExpr;
    }

    public Model getResults(){
        if ((state == State.FINAL) && (errorFound) ){
            return model;
        }
        if (state == State.RULES){
            System.out.println("Reading error");
            System.out.println("Invalid delimiter in file");
            System.out.println("Expected delimiter:");
            System.out.println(delimiter);
            System.out.println();
           }
        return null;
    }
}
