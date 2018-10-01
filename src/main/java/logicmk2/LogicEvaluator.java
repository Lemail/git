package logicmk2;

import java.util.List;
import java.util.Set;

public class LogicEvaluator {
    private Set<String> result;
    private boolean evaluationResult;

    public LogicEvaluator(Set<String> inputFacts){
        this.result = inputFacts;
    }

    public void evaluateExpression(ExpressionTxt expression){
        List<String> factList = expression.getFactList();
        List<String> operatorList = expression.getOperatorList();
        boolean[] logic = new boolean[operatorList.size()+1];
        boolean endOfCheck = false;
        try{
            while (!endOfCheck){
                if (operatorList.size() == 0 && !(factList.size() == 0)){
                    evaluationResult = result.contains(factList.get(0));
                    break;
                }
                if (!(factList.size() == 0)){
                    for (int i = 0; i < operatorList.size(); i++){
                        if (i != 0 && operatorList.get(i-1).equals("&&")){
                            if (!operatorList.get(i-1).equals("&&")){
                                logic[i] = (result.contains(factList.get(i)) && result.contains(factList.get(i + 1)));
                            }
                            else{
                                logic[i] = logic[i-1] && result.contains(factList.get(i));
                                logic[operatorList.size()] = logic[i];
                            }
                        }
                        else logic[i] = result.contains(factList.get(i));
                        if (i == operatorList.size() - 1) logic[operatorList.size()] = logic[i] && result.contains((factList.get(i + 1)));

                    }
                    for (int i = 0; i < operatorList.size(); i++){
                        if (operatorList.get(i).equals("||")){
                            if (i != 0){
                                if (logic[operatorList.size()]){
                                    evaluationResult = true;
                                    break;
                                }
                                if (operatorList.get(i+1).equals("||")){
                                    if (result.contains(factList.get(i + 1))){
                                        logic[operatorList.size()] = true;
                                        evaluationResult = logic[operatorList.size()];
                                        break;
                                    }
                                }
                                else {
                                    logic[operatorList.size()] = logic[i-1];
                                    break;
                                }
                            }
                            else logic[i] = result.contains(factList.get(i));
                            if (i == operatorList.size() -1) logic[operatorList.size()] = logic[i];
                        }
                    }
                }
                endOfCheck = true;
                evaluationResult = logic[operatorList.size()];
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
