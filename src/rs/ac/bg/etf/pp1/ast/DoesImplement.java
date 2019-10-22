// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class DoesImplement extends ImplementsInterfaces {

    private ImplementsList ImplementsList;

    public DoesImplement (ImplementsList ImplementsList) {
        this.ImplementsList=ImplementsList;
        if(ImplementsList!=null) ImplementsList.setParent(this);
    }

    public ImplementsList getImplementsList() {
        return ImplementsList;
    }

    public void setImplementsList(ImplementsList ImplementsList) {
        this.ImplementsList=ImplementsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ImplementsList!=null) ImplementsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ImplementsList!=null) ImplementsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ImplementsList!=null) ImplementsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoesImplement(\n");

        if(ImplementsList!=null)
            buffer.append(ImplementsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoesImplement]");
        return buffer.toString();
    }
}
