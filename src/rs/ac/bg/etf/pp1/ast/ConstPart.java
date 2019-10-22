// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class ConstPart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String constName;
    private SomeConstant SomeConstant;

    public ConstPart (String constName, SomeConstant SomeConstant) {
        this.constName=constName;
        this.SomeConstant=SomeConstant;
        if(SomeConstant!=null) SomeConstant.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public SomeConstant getSomeConstant() {
        return SomeConstant;
    }

    public void setSomeConstant(SomeConstant SomeConstant) {
        this.SomeConstant=SomeConstant;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SomeConstant!=null) SomeConstant.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SomeConstant!=null) SomeConstant.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SomeConstant!=null) SomeConstant.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstPart(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(SomeConstant!=null)
            buffer.append(SomeConstant.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstPart]");
        return buffer.toString();
    }
}
