package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.ActualParamSingle;
import rs.ac.bg.etf.pp1.ast.ActualPars;
import rs.ac.bg.etf.pp1.ast.Actuals;
import rs.ac.bg.etf.pp1.ast.Add;
import rs.ac.bg.etf.pp1.ast.AddExpr;
import rs.ac.bg.etf.pp1.ast.Addop;
import rs.ac.bg.etf.pp1.ast.Assignment;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.Decrement;
import rs.ac.bg.etf.pp1.ast.NumConst;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.PrintWithNum;
import rs.ac.bg.etf.pp1.ast.ProcCall;
import rs.ac.bg.etf.pp1.ast.ProcName;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorArray;
import rs.ac.bg.etf.pp1.ast.DesignatorDot;
import rs.ac.bg.etf.pp1.ast.DesignatorSimple;
import rs.ac.bg.etf.pp1.ast.Div;
import rs.ac.bg.etf.pp1.ast.FuncCall;
import rs.ac.bg.etf.pp1.ast.FuncName;
import rs.ac.bg.etf.pp1.ast.Increment;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.Mod;
import rs.ac.bg.etf.pp1.ast.Mul;
import rs.ac.bg.etf.pp1.ast.Mulop;
import rs.ac.bg.etf.pp1.ast.NegExpr;
import rs.ac.bg.etf.pp1.ast.NewArray;
import rs.ac.bg.etf.pp1.ast.NoActuals;
import rs.ac.bg.etf.pp1.ast.ReturnExpr;
import rs.ac.bg.etf.pp1.ast.ReturnNoExpr;
import rs.ac.bg.etf.pp1.ast.Sub;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermMulop;
import rs.ac.bg.etf.pp1.ast.Var;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	public CodeGenerator() {
		{	// chr
			Tab.chrObj.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
		{	// ord
			Tab.ordObj.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
		{	// len
			Tab.lenObj.setAdr(Code.pc);
			Code.put(Code.enter);
			Code.put(1);
			Code.put(1);
			Code.put(Code.load_n);
			Code.put(Code.arraylength);
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
	}
	
//	@Override
//	public void visit(ActualParam actPar) {	// for chr, ord, len ?
//		
//	}
	
	@Override
	public void visit(MethodName methodName) {
		if ("main".equalsIgnoreCase(methodName.getName())) {
			mainPc = Code.pc;
		}
		
		methodName.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = methodName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	@Override
	public void visit(MethodDecl methodDecl) {
//		Code.put(Code.trap);	// According to the requirements, a lack of a return statement is a runtime error?
		
		// These statements below are unneccessary in case Code.put(Code.trap) is above.
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnExpr returnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnNoExpr returnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(Assignment assignment) {
		Designator d = assignment.getDesignator();
		Struct elemType = d.obj.getType().getElemType();
		if (d instanceof DesignatorArray && elemType != null) {
			if (elemType.equals(Tab.charType)) {
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}
		} else {
			Code.store(d.obj);
		}
	}
	
	private void checkChrOrdLen(SyntaxNode call) {	// hm?
		if (call instanceof FuncCall) {
			FuncCall fc = (FuncCall) call;
			String name = fc.getFuncName().getDesignator().obj.getName();
			if (name.contentEquals("chr") || name.contentEquals("ord")) {
//				if (fc.getActualPars() instanceof Actuals) {
//					Actuals a = (Actuals) fc.getActualPars();
//					if (a.getActualParamList() instanceof ActualParamSingle) {
//						ActualParamSingle aps = (ActualParamSingle) a.getActualParamList();
//						if (name.contentEquals("chr")) {
//							aps.getActualParam().getExpr().struct = Tab.charType;
//						} else if (name.contentEquals("ord")) {
//							aps.getActualParam().getExpr().struct = Tab.intType;
//						}
//					}
//				}
				Actuals a = (Actuals) fc.getActualPars();
				ActualParamSingle aps = (ActualParamSingle) a.getActualParamList();
				if (name.contentEquals("chr")) {
					aps.getActualParam().getExpr().struct = Tab.charType;
				} else if (name.contentEquals("ord")) {
					aps.getActualParam().getExpr().struct = Tab.intType;
				}
			}
		} /*else if (call instanceof ProcCall) {	// cemu?
			ProcCall pc = (ProcCall) call;
//			... kao i za FuncCall ...
		}*/
	}
	
	private void generateArgsIfOptarg(Obj meth, ActualPars actualPars) {
		if (TabExtended.optargMethods.contains(meth) && actualPars instanceof NoActuals) {
			ArrayList<Obj> formalParams = new ArrayList<>();
			for (Obj obj : meth.getLocalSymbols()) {
				if (obj.getFpPos() > 0) {
					formalParams.add(obj);
				}
			}
			formalParams.sort((Obj o1, Obj o2) -> {
				return Integer.valueOf(o1.getFpPos()).compareTo(o2.getFpPos());
			});
			for (int i = 0; i < formalParams.size(); i++) {
				if (formalParams.get(i).getType().isRefType()) {
					Code.load(Tab.noObj);
				} else {
					Code.loadConst(0);
				}
			}
		}
	}
	
	@Override
	public void visit(FuncCall funcCall) {
		Obj functionObj = funcCall.getFuncName().getDesignator().obj;
		generateArgsIfOptarg(functionObj, funcCall.getActualPars());
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		checkChrOrdLen(funcCall);
	}
	
	@Override
	public void visit(ProcCall procCall) {
		Obj functionObj = procCall.getProcName().getDesignator().obj;
		generateArgsIfOptarg(functionObj, procCall.getActualPars());
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
//		checkChrOrdLen(procCall);	// ne treba?
	}
	
	@Override
	public void visit(NewArray newArray) {
		Code.put(Code.newarray);
		Code.put(newArray.getType().struct.equals(Tab.charType) ? 0 : 1);
	}
	
//	private void generateCodeForDesignator(Designator des) {
//		
//	}
	
	@Override
	public void visit(DesignatorSimple des) {
		SyntaxNode parent = des.getParent();
		if (Assignment.class != parent.getClass()
				/*&& FuncCall.class != parent.getClass()*/
				&& FuncName.class != parent.getClass()
				/*&& ProcCall.class != parent.getClass()*/
				&& ProcName.class != parent.getClass()
				&& DesignatorDot.class != parent.getClass()
				&& DesignatorArray.class != parent.getClass()
				/*&& Var.class != parent.getClass()*/
				&& ReadStmt.class != parent.getClass()) {
			Code.load(des.obj);
		} else if (DesignatorArray.class == parent.getClass()) {
			Code.load(des.obj);
		}
	}
	
//	@Override
//	public void visit(DesignatorArray des) {	// moze da se izbrise ako se izbrise i uslov za DesignatorSimple?
//		Code.load(des.obj);
//	}
	
	@Override
	public void visit(DesignatorDot des) {
		Code.loadConst(des.obj.getAdr());	// samo za enum
	}
	
	@Override
	public void visit(Var var) {
		if (var.getDesignator() instanceof DesignatorArray) {
			Code.load(var.getDesignator().obj);
		}
	}
	
	@Override
	public void visit(NumConst numConst) {
		Code.load(new Obj(Obj.Con, "dummy numconst", numConst.struct, numConst.getN1(), 0));
	}
	
	@Override
	public void visit(CharConst charConst) {
		Code.load(new Obj(Obj.Con, "dummy charconst", charConst.struct, charConst.getC1(), 0));
	}
	
	@Override
	public void visit(BoolConst boolConst) {
		Code.load(new Obj(Obj.Con, "dummy boolconst", boolConst.struct, boolConst.getB1() ? 1 : 0, 0));
	}
	
	@Override
	public void visit(ReadStmt readStmt) {
		Struct s = readStmt.getDesignator().obj.getType();
		if (s.getElemType() != null) {	// za nizove; provera da li je vrste Array?
			s = s.getElemType();
		}
		
		if (s.equals(Tab.intType) || s.equals(TabExtended.boolType)) {
			Code.put(Code.read);
		} else if (s.equals(Tab.charType)) {
			Code.put(Code.bread);
		}
		Code.store(readStmt.getDesignator().obj);
	}
	
	@Override
	public void visit(PrintStmt printStmt) {
		Struct s = printStmt.getExpr().struct;
		if (s.getElemType() != null) {
			s = s.getElemType();
		}
		if (s.equals(Tab.intType) || s.equals(TabExtended.boolType) || s.getKind() == Struct.Enum) {
//			Code.put(Code.const_5);
			if (printStmt.getPrintNum() instanceof PrintWithNum) {
				Code.loadConst(((PrintWithNum) printStmt.getPrintNum()).getN1());
			} else {
				Code.loadConst(5);
			}
			Code.put(Code.print);
		} else if (s.equals(Tab.charType)) {
//			Code.put(Code.const_1);
			if (printStmt.getPrintNum() instanceof PrintWithNum) {
				Code.loadConst(((PrintWithNum) printStmt.getPrintNum()).getN1());
			} else {
				Code.loadConst(1);
			}
			Code.put(Code.bprint);
		}
	}
	
	@Override
	public void visit(NegExpr negExpr) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(AddExpr addExpr) {
		Addop addop = addExpr.getAddop();
		if (addop instanceof Add) {
			Code.put(Code.add);
		} else if (addop instanceof Sub) {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(TermMulop termMulop) {
		Mulop mulop = termMulop.getMulop();
		if (mulop instanceof Mul) {
			Code.put(Code.mul);
		} else if (mulop instanceof Div) {
			Code.put(Code.div);
		} else if (mulop instanceof Mod) {
			Code.put(Code.rem);
		}
	}
	
	@Override
	public void visit(Increment inc) {
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(inc.getDesignator().obj);
	}
	
	@Override
	public void visit(Decrement dec) {
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(dec.getDesignator().obj);
	}
	
}
