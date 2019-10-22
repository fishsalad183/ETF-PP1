// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class ImplementsSingle extends ImplementsList {

    private ImplementsPart ImplementsPart;

    public ImplementsSingle (ImplementsPart ImplementsPart) {
        this.ImplementsPart=ImplementsPart;
        if(ImplementsPart!=null) ImplementsPart.setParent(this);
    }

    public ImplementsPart getImplementsPart() {
        return ImplementsPart;
    }

    public void setImplementsPart(ImplementsPart ImplementsPart) {
        this.ImplementsPart=ImplementsPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ImplementsPart!=null) ImplementsPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ImplementsPart!=null) ImplementsPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ImplementsPart!=null) ImplementsPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ImplementsSingle(\n");

        if(ImplementsPart!=null)
            buffer.append(ImplementsPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ImplementsSingle]");
        return buffer.toString();
    }
}
