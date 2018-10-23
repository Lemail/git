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
    private List<IExpr> bracketExprs = new ArrayList<>();
    private final String bracketConst = "#BRACKET_EXPR#";



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

                String expression = line.substring(0,lastIndexOfArrow).trim();
                String resultingFact = line.substring(lastIndexOfArrow + 2,line.length()).trim();

                if (!resultingFact.matches(correctFactRegexp)){
                    System.out.println("Error in resulting fact "+resultingFact+" (line"+lines+")");
                    System.out.println("Unsupported symbol");
                    System.out.println();
                    errorFound = false;
                    break;
                }

                Rule rule = new Rule(model.getFacts(), resultingFact);
                parseRule(rule, expression);

            }
        }
    }

    private void parseRule(Rule rule, String expression){
        bracketExprs = new ArrayList<>();
        if (expression.contains("(")) {
            rule.setRule(parseByBracket(expression));
            if ((errorFound) && (rule.getRule() != null)) model.getRules().add(rule);
            return;
        }

        if (expression.contains("||")){
            rule.setRule(parseByOr(expression));
            if ((errorFound) && (rule.getRule() != null)) model.getRules().add(rule);
            return;
        }

        if (expression.contains("&&")){
            rule.setRule(parseByAnd(expression));
            if ((errorFound) && (rule.getRule() != null)) model.getRules().add(rule);
            return;
        }

        rule.setRule(parseByFact(expression));
        if (errorFound) model.getRules().add(rule);
        return;
    }

    private IExpr parseByBracket(String line){
        BracketExpr bracketExpr = new BracketExpr();
        if ((line.contains("(")) && (!line.contains(")"))){
            System.out.println("Error in rule "+line+" (line "+lines+")");
            System.out.println("Missing closing bracket");
            System.out.println();
            errorFound = false;
            return null;
        }
        else {
            int openBrackets = 0;
            int closeBrackets = 0;
            String current;
            String bracketExpression;
            while (line.contains("(")){
                int i = 0;
                openBrackets = closeBrackets = 0;
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter("");
                while (scanner.hasNext()){
                    current = scanner.next();
                    i++;
                    if (current.equals("(")) openBrackets++;
                    if (current.equals(")")) closeBrackets++;
                    if (((openBrackets & closeBrackets) != 0) && (openBrackets == closeBrackets)){
                        bracketExpression = line.substring(line.indexOf("("), i);
                        line = line.replace(bracketExpression, bracketConst);
                        bracketExpression = bracketExpression.substring(1, bracketExpression.length() - 1);
                        bracketExprs.add(parseByBracket(bracketExpression));
                        break;
                    }
                }
            }
            if (line.equals(bracketConst)){
                bracketExpr.addPart(bracketExprs.get(bracketExprs.size() - 1));
                bracketExprs.remove(bracketExprs.size() - 1);
            }
            if (line.contains("||")){
                bracketExpr.addPart(parseByOr(line));
            }
            else if (line.contains("&&")){
                bracketExpr.addPart(parseByAnd(line));
            }
            else bracketExpr.addPart(parseByFact(line));


            if (bracketExpr.getParts().size() != 0)
                return bracketExpr;
            return null;
        }
    }

    private OrExpr parseByOr(String line){
        OrExpr orExpr = new OrExpr();
        String part;
        int orCount = count(line, "||");
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter("\\|\\|");
        while (scanner.hasNext()){
            part = scanner.next().trim();
            if (part.contains("&&")){
                orExpr.addPart(parseByAnd(part));
            }
            else {
                if (part.equals(bracketConst)){
                    orExpr.addPart(bracketExprs.get(0));
                    bracketExprs.remove(0);
                    continue;
                }
                orExpr.addPart(parseByFact(part));
            }
        }
        if (orExpr.getParts().size() - orCount != 1){
            System.out.println("Error in rule "+line+" (line "+lines+")");
            System.out.println("Missing fact");
            System.out.println();
            errorFound = false;
            return null;
        }

        if (orExpr.getParts().size() != 0)
            return orExpr;
        return null;
    }

    private AndExpr parseByAnd(String line){
        AndExpr andExpr = new AndExpr();
        String fact;
        int andCount = count(line, "&&");
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter("&&");
        while (scanner.hasNext()){
            fact = scanner.next().trim();
            if (fact.equals(bracketConst)){
                andExpr.addPart(bracketExprs.get(0));
                bracketExprs.remove(0);
                continue;
            }
            andExpr.addPart(parseByFact(fact));
        }
        if (andExpr.getParts().size() - andCount != 1){
            System.out.println("Error in rule "+line+" (line "+lines+")");
            System.out.println("Missing fact");
            System.out.println();
            errorFound = false;
            return null;
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

    private static int count(String text, String find) {
        int index = 0, count = 0, length = find.length();
        while( (index = text.indexOf(find, index)) != -1 ) {
            index += length; count++;
        }
        return count;
    }
}
