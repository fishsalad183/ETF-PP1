// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class VoidReturn extends ReturnType {

    public VoidReturn () {
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
        buffer.append("VoidReturn(\n");

        buffer.append(tab);
        buffer.append(") [VoidReturn]");
        return buffer.toString();
    }
}
