package logicmk3;

import java.util.ArrayList;
import java.util.List;

public class AndExpr implements IExpr{
    List<IExpr> parts = new ArrayList<>();
    private boolean evalState = true;

    public AndExpr(){
    }

    public boolean eval() {
        for (IExpr part : parts){
            evalState &= part.eval();
        }
        return evalState;
    }

    public void addPart(IExpr expression){
        parts.add(expression);
    }
}
