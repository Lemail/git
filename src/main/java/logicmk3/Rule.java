package logicmk3;

import java.util.Set;

public class Rule implements IExpr{
    private Set<String> facts;
    private IExpr rule;
    private String resultingFact;
    private boolean evalState = false;

    public boolean eval() {
        evalState = rule.eval();
        if (evalState){
            facts.add(resultingFact);
        }
        return evalState;
    }

    public void setResultingFact(String resultingFact) {
        this.resultingFact = resultingFact;
    }

    public void setRule(IExpr rule) {
        this.rule = rule;
    }

    public void setFacts(Set<String> facts) {
        this.facts = facts;
    }
}
