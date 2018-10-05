package logicmk3;

import java.util.ArrayList;
import java.util.List;

public class OrExpr implements IExpr{
    private List<IExpr> parts = new ArrayList<>();

    public OrExpr(){
    }

    public boolean eval() {
        boolean evalState = false;
        for (IExpr part : parts){
            evalState |= part.eval();
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
