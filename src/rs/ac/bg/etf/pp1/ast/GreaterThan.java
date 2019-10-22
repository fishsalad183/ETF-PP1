// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:38


package rs.ac.bg.etf.pp1.ast;

public class GreaterThan extends Relop {

    public GreaterThan () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GreaterThan(\n");

        buffer.append(tab);
        buffer.append(") [GreaterThan]");
        return buffer.toString();
    }
}
