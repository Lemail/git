package logic;

import java.util.List;

public class LogicEvaluator {
    private LogicReader logicReader;
    
    public LogicEvaluator(LogicReader logicReader){
        this.logicReader = logicReader;
    }
    
    public boolean evaluateExpression(List<String> variableList, List<String> expressionList){
        boolean[] logic = new boolean[expressionList.size()+1];
        try{
            if (expressionList.size() == 0){
                return logicReader.getVariables().containsKey(variableList.get(0));
            }
            for (int i = 0; i < expressionList.size(); i++){
                if (i != 0 && expressionList.get(i-1).equals("&&")){
                    if (!expressionList.get(i-1).equals("&&")){
                        logic[i] = (logicReader.getVariables().containsKey(variableList.get(i)) && logicReader.getVariables().containsKey(variableList.get(i + 1)));
                    }
                    else{
                        logic[i] = logic[i-1] && logicReader.getVariables().containsKey(variableList.get(i + 1));
                        logic[expressionList.size()] = logic[i];
                    }
                }
                else logic[i] = logicReader.getVariables().containsKey(variableList.get(i));
                if (i == expressionList.size() - 1) logic[expressionList.size()] = logic[i];

            }
            for (int i = 0; i < expressionList.size(); i++){
                if (expressionList.get(i).equals("||")){
                    if (i != 0){
                        if (logic[expressionList.size()]) return true;
                        if (expressionList.get(i+1).equals("||")){
                            if (logicReader.getVariables().containsKey(variableList.get(i + 1))){
                                logic[expressionList.size()] = true;
                                return logic[expressionList.size()];
                            }
                        }
                    }
                    else logic[i] = logicReader.getVariables().containsKey(variableList.get(i));
                    if (i == expressionList.size() -1) logic[expressionList.size()] = logic[i];
                }
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return logic[expressionList.size()];
    }
}
