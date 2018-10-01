package logicmk3;

import java.util.Set;

public class FactExpr implements IExpr {
    private Set<String> facts;
    private String factName;

    public FactExpr(Set<String> facts, String factName){
        this.facts = facts;
        this.factName = factName;
    }

    public boolean eval(){
        return facts.contains(factName);
    }
}
