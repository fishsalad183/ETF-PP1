package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ArrDecl;
import rs.ac.bg.etf.pp1.ast.FormalParamDeclArr;
import rs.ac.bg.etf.pp1.ast.FormalParamDeclVar;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor {

		@Override
		public void visit(FormalParamDeclVar fpdv) {
			count++;
		}
		
		@Override
		public void visit(FormalParamDeclArr fpda) {
			count++;
		}
	}
	
	public static class VarCounter extends CounterVisitor {		
		@Override
		public void visit(VarDecl vd) {
			count++;
		}
		
		@Override
		public void visit(ArrDecl ad) {
			count++;
		}
	}
}
