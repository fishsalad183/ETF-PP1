// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class EnumSingle extends EnumList {

    private EnumPart EnumPart;

    public EnumSingle (EnumPart EnumPart) {
        this.EnumPart=EnumPart;
        if(EnumPart!=null) EnumPart.setParent(this);
    }

    public EnumPart getEnumPart() {
        return EnumPart;
    }

    public void setEnumPart(EnumPart EnumPart) {
        this.EnumPart=EnumPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumPart!=null) EnumPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumPart!=null) EnumPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumPart!=null) EnumPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumSingle(\n");

        if(EnumPart!=null)
            buffer.append(EnumPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumSingle]");
        return buffer.toString();
    }
}
