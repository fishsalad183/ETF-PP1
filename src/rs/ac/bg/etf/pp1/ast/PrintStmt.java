// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:38


package rs.ac.bg.etf.pp1.ast;

public class PrintStmt extends Statement {

    private Expr Expr;
    private PrintNum PrintNum;

    public PrintStmt (Expr Expr, PrintNum PrintNum) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.PrintNum=PrintNum;
        if(PrintNum!=null) PrintNum.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public PrintNum getPrintNum() {
        return PrintNum;
    }

    public void setPrintNum(PrintNum PrintNum) {
        this.PrintNum=PrintNum;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(PrintNum!=null) PrintNum.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(PrintNum!=null) PrintNum.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(PrintNum!=null) PrintNum.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintStmt(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PrintNum!=null)
            buffer.append(PrintNum.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintStmt]");
        return buffer.toString();
    }
}
