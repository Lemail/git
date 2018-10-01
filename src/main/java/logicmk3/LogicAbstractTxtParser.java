package logicmk3;

import java.util.*;

public class LogicAbstractTxtParser {
    private final String delimiter = "----------------------------------------------------------------";
    private boolean delimiterFound = false;
    private Set<String> facts =  new TreeSet<>();
    private boolean status =  true;
    private List<IExpr> rules = new ArrayList<>();
    private int lines = 0;

    public void parseLine(String line){
        lines++;
        Scanner scanner = new Scanner(line);
        if (delimiterFound){
            scanner.useDelimiter(",");
            if (!line.equals("")){
                while (scanner.hasNext()) {
                    String current = scanner.next().trim();
                    if (current.matches("[_]*[\\p{Alpha}]+[\\w]*"))
                        facts.add(current);
                    else{
                        System.out.println("Error in fact: "+current);
                        System.out.println("Unsupported symbol");
                        System.out.println();
                        status = false;
                    }
                }
            }
        }
        else{
            if (line.equals(delimiter)){
                delimiterFound = true;
            }
            else{
                if (!line.equals("")){
                    if (!line.contains("->")){
                        System.out.println("Error in "+line+"(line "+lines+")");
                        System.out.println("Missing \"->\"");
                        System.out.println();
                        status = false;
                    }
                    else{
                        String ruleString = line.substring(0,line.lastIndexOf("->"));
                        if (ruleString.contains("->")){
                            System.out.println("Error in rule "+ruleString+"(line "+lines+")");
                            System.out.println("Invalid rule");
                            System.out.println();
                            status = false;
                        }
                        else{
                            String resultingFact = line.substring(line.lastIndexOf("->")+2,line.length());
                            if (!resultingFact.matches("[_]*[\\p{Alpha}]+[\\w]*")){
                                System.out.println("Error in resulting fact "+resultingFact+" (line"+lines+")");
                                System.out.println("Unsupported symbol");
                                System.out.println();
                                status = false;
                            }
                            else {
                                Rule rule = new Rule();
                                rules.add(rule);
                                rule.setResultingFact(resultingFact);
                                rule.setFacts(facts);
                                if (ruleString.contains("|")){
                                    if (ruleString.contains("||")){
                                        OrExpr orExpr;
                                        orExpr = parseByOr(ruleString);
                                        rule.setRule(orExpr);
                                    }
                                    else {
                                        System.out.println("Error in rule "+ruleString+"(line "+lines+")");
                                        System.out.println("Invalid operator");
                                        System.out.println("Expected: \"||\", got \"|\"");
                                        System.out.println();
                                        status = false;
                                    }

                                } else {
                                    if (ruleString.contains("&")){
                                        if (ruleString.contains("&&")){
                                            List<IExpr> facts;
                                            facts = parseByAnd(ruleString);
                                            if (status){
                                                AndExpr andExpr = new AndExpr();
                                                for (IExpr fact : facts){
                                                    andExpr.addPart(fact);
                                                }
                                                rule.setRule(andExpr);
                                            }
                                        }
                                        else {
                                            System.out.println("Error in rule "+ruleString+"(line "+lines+")");
                                            System.out.println("Invalid operator");
                                            System.out.println("Expected: \"&&\", got \"&\"");
                                            System.out.println();
                                            status = false;
                                        }

                                    }
                                    else {
                                        if (ruleString.matches("[_]*[\\p{Alpha}]+[\\w]*"))
                                            rule.setRule(new FactExpr(this.facts, ruleString));
                                        else {
                                            System.out.println("Error in fact "+ruleString+"(line "+lines+")");
                                            System.out.println("Unsupported symbol");
                                            System.out.println();
                                            status = false;
                                        }
                                    }
                                }
                            }

                        }
                    }

                }
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
            if (!part.contains("&")){
                if (part.matches("[_]*[\\p{Alpha}]+[\\w]*")){
                    orExpr.addPart(new FactExpr(facts, part));
                }
                else {
                    System.out.println("Error in fact "+part+" (line "+lines+")");
                    System.out.println("Unsupported symbol");
                    System.out.println();
                    status = false;
                }
            }
            else{
                if (part.contains("&&")){
                    AndExpr andExpr = new AndExpr();
                    orExpr.addPart(andExpr);
                    List<IExpr> facts;
                    facts = parseByAnd(part);
                    for (IExpr fact : facts){
                        andExpr.addPart(fact);
                    }
                }
                else {
                    System.out.println("Error in rule "+part+"(line "+lines+")");
                    System.out.println("Invalid operator");
                    System.out.println("Expected: \"&&\", got \"&\"");
                    System.out.println();
                    status = false;
                }
            }

        }
        return orExpr;
    }

    private List<IExpr> parseByAnd(String line){
        List<IExpr> parts = new ArrayList<>();
        Scanner scanner = new Scanner(line);
        String part;
        scanner.useDelimiter("&&");
        while (scanner.hasNext()){
            part = scanner.next().trim();
            if (part.matches("[_]*[\\p{Alpha}]+[\\w]*")){
                parts.add(new FactExpr(facts, part));
            }
            else {
                System.out.println("Error in fact "+part+" (line "+lines+")");
                System.out.println("Unsupported symbol");
                System.out.println();
                status = false;
            }
        }
        return parts;
    }

    public boolean isStatus() {
        return status;
    }

    public Set<String> getFacts() {
        return facts;
    }

    public List<IExpr> getRules() {
        return rules;
    }

    public boolean isDelimiterFound() {
        return delimiterFound;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
