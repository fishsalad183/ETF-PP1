// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class VarDeclSingle extends VarList {

    private VarPart VarPart;

    public VarDeclSingle (VarPart VarPart) {
        this.VarPart=VarPart;
        if(VarPart!=null) VarPart.setParent(this);
    }

    public VarPart getVarPart() {
        return VarPart;
    }

    public void setVarPart(VarPart VarPart) {
        this.VarPart=VarPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarPart!=null) VarPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarPart!=null) VarPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarPart!=null) VarPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclSingle(\n");

        if(VarPart!=null)
            buffer.append(VarPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclSingle]");
        return buffer.toString();
    }
}
