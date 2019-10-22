// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class DeclPartVar extends DeclPart {

    private VarsDecl VarsDecl;

    public DeclPartVar (VarsDecl VarsDecl) {
        this.VarsDecl=VarsDecl;
        if(VarsDecl!=null) VarsDecl.setParent(this);
    }

    public VarsDecl getVarsDecl() {
        return VarsDecl;
    }

    public void setVarsDecl(VarsDecl VarsDecl) {
        this.VarsDecl=VarsDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarsDecl!=null) VarsDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarsDecl!=null) VarsDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarsDecl!=null) VarsDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclPartVar(\n");

        if(VarsDecl!=null)
            buffer.append(VarsDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclPartVar]");
        return buffer.toString();
    }
}
