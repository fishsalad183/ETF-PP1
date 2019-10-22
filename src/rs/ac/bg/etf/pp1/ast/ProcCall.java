// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:38


package rs.ac.bg.etf.pp1.ast;

public class ProcCall extends DesignatorStatement {

    private ProcName ProcName;
    private ActualPars ActualPars;

    public ProcCall (ProcName ProcName, ActualPars ActualPars) {
        this.ProcName=ProcName;
        if(ProcName!=null) ProcName.setParent(this);
        this.ActualPars=ActualPars;
        if(ActualPars!=null) ActualPars.setParent(this);
    }

    public ProcName getProcName() {
        return ProcName;
    }

    public void setProcName(ProcName ProcName) {
        this.ProcName=ProcName;
    }

    public ActualPars getActualPars() {
        return ActualPars;
    }

    public void setActualPars(ActualPars ActualPars) {
        this.ActualPars=ActualPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProcName!=null) ProcName.accept(visitor);
        if(ActualPars!=null) ActualPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProcName!=null) ProcName.traverseTopDown(visitor);
        if(ActualPars!=null) ActualPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProcName!=null) ProcName.traverseBottomUp(visitor);
        if(ActualPars!=null) ActualPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProcCall(\n");

        if(ProcName!=null)
            buffer.append(ProcName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActualPars!=null)
            buffer.append(ActualPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProcCall]");
        return buffer.toString();
    }
}
