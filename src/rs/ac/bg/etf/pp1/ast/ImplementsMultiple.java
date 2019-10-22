// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class ImplementsMultiple extends ImplementsList {

    private ImplementsList ImplementsList;
    private ImplementsPart ImplementsPart;

    public ImplementsMultiple (ImplementsList ImplementsList, ImplementsPart ImplementsPart) {
        this.ImplementsList=ImplementsList;
        if(ImplementsList!=null) ImplementsList.setParent(this);
        this.ImplementsPart=ImplementsPart;
        if(ImplementsPart!=null) ImplementsPart.setParent(this);
    }

    public ImplementsList getImplementsList() {
        return ImplementsList;
    }

    public void setImplementsList(ImplementsList ImplementsList) {
        this.ImplementsList=ImplementsList;
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
        if(ImplementsList!=null) ImplementsList.accept(visitor);
        if(ImplementsPart!=null) ImplementsPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ImplementsList!=null) ImplementsList.traverseTopDown(visitor);
        if(ImplementsPart!=null) ImplementsPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ImplementsList!=null) ImplementsList.traverseBottomUp(visitor);
        if(ImplementsPart!=null) ImplementsPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ImplementsMultiple(\n");

        if(ImplementsList!=null)
            buffer.append(ImplementsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ImplementsPart!=null)
            buffer.append(ImplementsPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ImplementsMultiple]");
        return buffer.toString();
    }
}
