// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class DeclPartConst extends DeclPart {

    private ConstsDecl ConstsDecl;

    public DeclPartConst (ConstsDecl ConstsDecl) {
        this.ConstsDecl=ConstsDecl;
        if(ConstsDecl!=null) ConstsDecl.setParent(this);
    }

    public ConstsDecl getConstsDecl() {
        return ConstsDecl;
    }

    public void setConstsDecl(ConstsDecl ConstsDecl) {
        this.ConstsDecl=ConstsDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstsDecl!=null) ConstsDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstsDecl!=null) ConstsDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstsDecl!=null) ConstsDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclPartConst(\n");

        if(ConstsDecl!=null)
            buffer.append(ConstsDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclPartConst]");
        return buffer.toString();
    }
}
