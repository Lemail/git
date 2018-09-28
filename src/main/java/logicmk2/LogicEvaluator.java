package logicmk2;

import logic.ExpressionTxt;

import java.util.List;
import java.util.Set;

public class LogicEvaluator {
    private Set<String> result;
    private boolean evaluationResult;

    public LogicEvaluator(Set<String> variables){
        this.result = variables;
    }

    public void evaluateExpression(ExpressionTxt expression){
        List<String> variableList = expression.getVariableList();
        List<String> expressionList = expression.getExpressionList();
        boolean[] logic = new boolean[expressionList.size()+1];
        boolean endOfCheck = false;
        try{
            while (!endOfCheck){
                if (expressionList.size() == 0 && !(variableList.size() == 0)){
                    evaluationResult = result.contains(variableList.get(0));
                    break;
                }
                if (!(variableList.size() == 0)){
                    for (int i = 0; i < expressionList.size(); i++){
                        if (i != 0 && expressionList.get(i-1).equals("&&")){
                            if (!expressionList.get(i-1).equals("&&")){
                                logic[i] = (result.contains(variableList.get(i)) && result.contains(variableList.get(i + 1)));
                            }
                            else{
                                logic[i] = logic[i-1] && result.contains(variableList.get(i));
                                logic[expressionList.size()] = logic[i];
                            }
                        }
                        else logic[i] = result.contains(variableList.get(i));
                        if (i == expressionList.size() - 1) logic[expressionList.size()] = logic[i] && result.contains((variableList.get(i + 1)));

                    }
                    for (int i = 0; i < expressionList.size(); i++){
                        if (expressionList.get(i).equals("||")){
                            if (i != 0){
                                if (logic[expressionList.size()]){
                                    evaluationResult = true;
                                    break;
                                }
                                if (expressionList.get(i+1).equals("||")){
                                    if (result.contains(variableList.get(i + 1))){
                                        logic[expressionList.size()] = true;
                                        evaluationResult = logic[expressionList.size()];
                                        break;
                                    }
                                }
                                else {
                                    logic[expressionList.size()] = logic[i-1];
                                    break;
                                }
                            }
                            else logic[i] = result.contains(variableList.get(i));
                            if (i == expressionList.size() -1) logic[expressionList.size()] = logic[i];
                        }
                    }
                }
                endOfCheck = true;
                evaluationResult = logic[expressionList.size()];
            }


        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        if (evaluationResult){
            result.add(expression.getCheckedFact());
        }
    }

    public Set<String> getResult() {
        return result;
    }

}
