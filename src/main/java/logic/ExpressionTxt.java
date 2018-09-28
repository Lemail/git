package logic;

import java.util.List;

public class ExpressionTxt {
    private List<String> variableList;
    private List<String> expressionList;
    private String checkedFact;

    public ExpressionTxt(List<String> variableList, List<String> expressionList, String checkedFact){
        this.variableList = variableList;
        this.expressionList = expressionList;
        this.checkedFact = checkedFact;
    }

    public List<String> getExpressionList() {
        return expressionList;
    }

    public List<String> getVariableList() {
        return variableList;
    }

    public String getCheckedFact() {
        return checkedFact; }
}
