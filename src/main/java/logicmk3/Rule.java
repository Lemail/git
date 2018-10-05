package logicmk3;

import java.util.Set;

public class Rule{
    private Set<String> facts;
    private IExpr rule = null;
    private String resultingFact;

    public Rule(Set<String> facts, String resultingFact){
        this.facts = facts;
        this.resultingFact = resultingFact;
    }

    public void eval() {
        boolean evalState = rule.eval();
        if (evalState){
            facts.add(resultingFact);
        }
    }

    public void setRule(IExpr rule) {
        this.rule = rule;
    }

    public IExpr getRule() {
        return rule;
    }
}
