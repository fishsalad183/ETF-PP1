// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class EnumExplicit extends EnumPart {

    private String enumConstName;
    private Integer enumConstVal;

    public EnumExplicit (String enumConstName, Integer enumConstVal) {
        this.enumConstName=enumConstName;
        this.enumConstVal=enumConstVal;
    }

    public String getEnumConstName() {
        return enumConstName;
    }

    public void setEnumConstName(String enumConstName) {
        this.enumConstName=enumConstName;
    }

    public Integer getEnumConstVal() {
        return enumConstVal;
    }

    public void setEnumConstVal(Integer enumConstVal) {
        this.enumConstVal=enumConstVal;
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
        buffer.append("EnumExplicit(\n");

        buffer.append(" "+tab+enumConstName);
        buffer.append("\n");

        buffer.append(" "+tab+enumConstVal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumExplicit]");
        return buffer.toString();
    }
}
