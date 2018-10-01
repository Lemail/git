package logicmk2;

import java.util.List;

public class ExpressionTxt {
    private List<String> factList;
    private List<String> operatorList;
    private String checkedFact;

    public ExpressionTxt(List<String> factList, List<String> operatorList, String checkedFact){
        this.factList = factList;
        this.operatorList = operatorList;
        this.checkedFact = checkedFact;
    }

    public List<String> getOperatorList() {
        return operatorList;
    }

    public List<String> getFactList() {
        return factList;
    }

    public String getCheckedFact() {
        return checkedFact; }
}
