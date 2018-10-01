package logicmk2;

import java.util.List;

public class AndExpr implements IExpr{
    List<IExpr> parts;

    public boolean eval() {
        return false;
    }
}
