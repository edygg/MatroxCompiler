package Tree;

import Temp.Temp;
import Temp.Label;

public class EXPG extends Stm {

    public Exp exp;

    public EXPG(Exp e) {
        exp = e;
    }

    public ExpList kids() {
        return new ExpList(exp, null);
    }

    public Stm build(ExpList kids) {
        return new EXPG(kids.head);
    }
}
