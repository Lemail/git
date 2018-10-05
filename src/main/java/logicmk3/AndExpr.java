package logicmk3;

import java.util.ArrayList;
import java.util.List;

public class AndExpr implements IExpr{
    List<IExpr> parts = new ArrayList<>();

    public AndExpr(){
    }

    public boolean eval() {
        boolean evalState = true;
        for (IExpr part : parts){
            evalState &= part.eval();
        }
        return evalState;
    }

    public void addPart(IExpr expression){
        parts.add(expression);
    }

    public List<IExpr> getParts() {
        return parts;
    }
}
