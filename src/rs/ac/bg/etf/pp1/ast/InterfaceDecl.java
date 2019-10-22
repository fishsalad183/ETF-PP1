// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class InterfaceDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private InterfaceName InterfaceName;
    private InterfaceMethodDeclList InterfaceMethodDeclList;

    public InterfaceDecl (InterfaceName InterfaceName, InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.InterfaceName=InterfaceName;
        if(InterfaceName!=null) InterfaceName.setParent(this);
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.setParent(this);
    }

    public InterfaceName getInterfaceName() {
        return InterfaceName;
    }

    public void setInterfaceName(InterfaceName InterfaceName) {
        this.InterfaceName=InterfaceName;
    }

    public InterfaceMethodDeclList getInterfaceMethodDeclList() {
        return InterfaceMethodDeclList;
    }

    public void setInterfaceMethodDeclList(InterfaceMethodDeclList InterfaceMethodDeclList) {
        this.InterfaceMethodDeclList=InterfaceMethodDeclList;
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
        if(InterfaceName!=null) InterfaceName.accept(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InterfaceName!=null) InterfaceName.traverseTopDown(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InterfaceName!=null) InterfaceName.traverseBottomUp(visitor);
        if(InterfaceMethodDeclList!=null) InterfaceMethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceDecl(\n");

        if(InterfaceName!=null)
            buffer.append(InterfaceName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethodDeclList!=null)
            buffer.append(InterfaceMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceDecl]");
        return buffer.toString();
    }
}
