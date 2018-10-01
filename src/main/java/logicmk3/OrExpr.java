package logicmk3;

import java.util.ArrayList;
import java.util.List;

public class OrExpr implements IExpr{
    private List<IExpr> parts = new ArrayList<>();
    private boolean evalState = false;

    public OrExpr(){
    }

    public boolean eval() {
        for (IExpr part : parts){
            evalState |= part.eval();
        }
        return evalState;
    }

    public void addPart(IExpr expression){
        parts.add(expression);
    }
}
