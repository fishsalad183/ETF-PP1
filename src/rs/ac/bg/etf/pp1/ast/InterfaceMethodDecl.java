// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:37


package rs.ac.bg.etf.pp1.ast;

public class InterfaceMethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private OptargOption OptargOption;
    private ReturnType ReturnType;
    private InterfaceMethodName InterfaceMethodName;
    private FormPars FormPars;

    public InterfaceMethodDecl (OptargOption OptargOption, ReturnType ReturnType, InterfaceMethodName InterfaceMethodName, FormPars FormPars) {
        this.OptargOption=OptargOption;
        if(OptargOption!=null) OptargOption.setParent(this);
        this.ReturnType=ReturnType;
        if(ReturnType!=null) ReturnType.setParent(this);
        this.InterfaceMethodName=InterfaceMethodName;
        if(InterfaceMethodName!=null) InterfaceMethodName.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
    }

    public OptargOption getOptargOption() {
        return OptargOption;
    }

    public void setOptargOption(OptargOption OptargOption) {
        this.OptargOption=OptargOption;
    }

    public ReturnType getReturnType() {
        return ReturnType;
    }

    public void setReturnType(ReturnType ReturnType) {
        this.ReturnType=ReturnType;
    }

    public InterfaceMethodName getInterfaceMethodName() {
        return InterfaceMethodName;
    }

    public void setInterfaceMethodName(InterfaceMethodName InterfaceMethodName) {
        this.InterfaceMethodName=InterfaceMethodName;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
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
        if(OptargOption!=null) OptargOption.accept(visitor);
        if(ReturnType!=null) ReturnType.accept(visitor);
        if(InterfaceMethodName!=null) InterfaceMethodName.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptargOption!=null) OptargOption.traverseTopDown(visitor);
        if(ReturnType!=null) ReturnType.traverseTopDown(visitor);
        if(InterfaceMethodName!=null) InterfaceMethodName.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptargOption!=null) OptargOption.traverseBottomUp(visitor);
        if(ReturnType!=null) ReturnType.traverseBottomUp(visitor);
        if(InterfaceMethodName!=null) InterfaceMethodName.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InterfaceMethodDecl(\n");

        if(OptargOption!=null)
            buffer.append(OptargOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ReturnType!=null)
            buffer.append(ReturnType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InterfaceMethodName!=null)
            buffer.append(InterfaceMethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InterfaceMethodDecl]");
        return buffer.toString();
    }
}
