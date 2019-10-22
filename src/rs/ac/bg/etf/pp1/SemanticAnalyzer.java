package rs.ac.bg.etf.pp1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ActualParam;
import rs.ac.bg.etf.pp1.ast.AddExpr;
import rs.ac.bg.etf.pp1.ast.ArrDecl;
import rs.ac.bg.etf.pp1.ast.Assignment;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.BoolConstDecl;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.CharConstDecl;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.ConstPart;
import rs.ac.bg.etf.pp1.ast.ConstsDeclSuccess;
import rs.ac.bg.etf.pp1.ast.Decrement;
import rs.ac.bg.etf.pp1.ast.DesignatorArray;
import rs.ac.bg.etf.pp1.ast.DesignatorDot;
import rs.ac.bg.etf.pp1.ast.NumConst;
import rs.ac.bg.etf.pp1.ast.NumConstDecl;
import rs.ac.bg.etf.pp1.ast.OptargYes;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.DesignatorSimple;
import rs.ac.bg.etf.pp1.ast.EnumDecl;
import rs.ac.bg.etf.pp1.ast.EnumExplicit;
import rs.ac.bg.etf.pp1.ast.EnumImplicit;
import rs.ac.bg.etf.pp1.ast.EnumName;
import rs.ac.bg.etf.pp1.ast.ExprInParentheses;
import rs.ac.bg.etf.pp1.ast.ExtendsType;
import rs.ac.bg.etf.pp1.ast.FormalParamDeclArr;
import rs.ac.bg.etf.pp1.ast.FormalParamDeclVar;
import rs.ac.bg.etf.pp1.ast.FuncCall;
import rs.ac.bg.etf.pp1.ast.FuncName;
import rs.ac.bg.etf.pp1.ast.ImplementsType;
import rs.ac.bg.etf.pp1.ast.Increment;
import rs.ac.bg.etf.pp1.ast.InterfaceDecl;
import rs.ac.bg.etf.pp1.ast.InterfaceMethodDecl;
import rs.ac.bg.etf.pp1.ast.InterfaceMethodName;
import rs.ac.bg.etf.pp1.ast.InterfaceName;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.NegExpr;
import rs.ac.bg.etf.pp1.ast.NewArray;
import rs.ac.bg.etf.pp1.ast.NewClass;
import rs.ac.bg.etf.pp1.ast.ProcCall;
import rs.ac.bg.etf.pp1.ast.ProcName;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.ReturnExpr;
import rs.ac.bg.etf.pp1.ast.ReturnNoExpr;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermExpr;
import rs.ac.bg.etf.pp1.ast.TermMulop;
import rs.ac.bg.etf.pp1.ast.TermSimple;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.Var;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VarsDeclaration;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidReturn;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	int nVars;
	
	boolean returnFound = false;
	Obj currentConst = null;
	Obj currentEnum = null;
	int nextEnumVal = 0;
	Integer currentConstVal = null;
	Obj currentInterface = null;
	Obj currentClass = null;
	Obj currentType = null;
	Obj currentMethod = null;
	ArrayList<Obj> extendedAndImplemented = null;
	Obj currentExpression = null;
	Deque<ArrayList<Struct>> stackOfActParLists = new ArrayDeque<>();
//	ArrayList<Struct> actualParams = new ArrayList<>();
	
	SymbolTableVisitor stv;
	
	

	/* logger and error handling --- begin */
	
	boolean errorDetected = false;
	
	public boolean passed() {
		return !errorDetected;
	}
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	/* logger and error handling --- end */
	
	/* Program --- begin */
	
	public void visit(Program program) {
		Obj main = Tab.currentScope().findSymbol("main");
		if (main.equals(Tab.noObj)) {
			report_error("Greska: ne postoji main metoda", null);
		} else if (main.getKind() != Obj.Meth) {
			report_error("Greska: main mora biti globalna void funkcija bez argumenata", null);
		} else {
			if (main.getType().getKind() != Struct.None) {
				report_error("Greska: main mora biti tipa void", null);
			}
			if (main.getLevel() != 0) {
				report_error("Greska: main mora biti bez argumenata", null);
			}
		}
			
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getPName(), Tab.noType);
		Tab.openScope();     	
	}
	
	/* Program --- end */
	
	/* Type --- begin */
	
	public void visit(Type type) {
		currentType = Tab.find(type.getTypeName());
		if (currentType == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", null);
			type.struct = Tab.noType;
		}
		else {
			if (Obj.Type == currentType.getKind()) {
				type.struct = currentType.getType();
			} 
			else {
				report_error("Greska: " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
		}
	}
	
	/* Type --- end */
	
	/* variables --- begin */
	
	public void visit(VarDecl varDecl) {
		Obj obj = Tab.currentScope().findSymbol(varDecl.getVarName());
		if (obj != null) {
			report_error("Greska na liniji " + varDecl.getLine() + ": vec postoji ime " + varDecl.getVarName() + " deklarisano pre unutar istog opsega", null);
		} else {
			Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), currentType.getType());
//			report_info("Deklarisana promenljiva " + varDecl.getVarName(), varDecl);
			stv = new DumpSymbolTableVisitorExtended();
			varNode.accept(stv);
			report_info("Deklaracija " + stv.getOutput(), varDecl);
		}
	}
	
	public void visit(ArrDecl arrDecl) {
		Obj obj = Tab.currentScope().findSymbol(arrDecl.getArrName());
		if (obj != null) {
			report_error("Greska na liniji " + arrDecl.getLine() + ": vec postoji ime " + arrDecl.getArrName() + " deklarisano pre unutar istog opsega", null);
		} else {
			Struct arrType = new Struct(Struct.Array, currentType.getType());
			Obj arrNode = Tab.insert(Obj.Var, arrDecl.getArrName(), arrType);
//			report_info("Deklarisan niz promenljivih " + arrDecl.getArrName(), arrDecl);
			stv = new DumpSymbolTableVisitorExtended();
			arrNode.accept(stv);
			report_info("Deklaracija " + stv.getOutput(), arrDecl);
		}
	}
	
	public void visit(VarsDeclaration varsDeclaration) {
		currentType = null;
	}
	
	/* variables --- end */
	
	/* constants --- begin */
	
	public void visit(ConstPart constPart) {
		Obj obj = Tab.currentScope().findSymbol(constPart.getConstName());
		if (obj != null) {
			report_error("Greska na liniji " + constPart.getLine() + ": vec postoji ime " + constPart.getConstName() + " deklarisano pre unutar istog opsega", null);
		} else {
			Struct currentConstType = currentType.getType();
			if (!(currentConstType.equals(Tab.intType) || currentConstType.equals(Tab.charType) || currentConstType.equals(TabExtended.boolType))) {
				report_error("Tip konstante mora biti int, char ili bool", constPart);
			} else {
				currentConst = Tab.insert(Obj.Con, constPart.getConstName(), currentConstType);
				if (currentConstVal != null) {
					currentConst.setAdr(currentConstVal);
				}
				
				stv = new DumpSymbolTableVisitorExtended();
				currentConst.accept(stv);
				report_info("Deklaracija konstante " + stv.getOutput(), constPart);
				currentConstVal = null;
			}
		}
	}
	
	public void visit(BoolConstDecl boolConstDecl) {
		if (currentType.getType().equals(TabExtended.boolType)) {
			currentConstVal = boolConstDecl.getVal() ? 1 : 0;
		} else {
			report_error("Ne moze se dodeliti navedena vrednost tipu deklarinsane konstante", boolConstDecl);
			currentConstVal = null;
		}
	}
	
	public void visit(CharConstDecl charConstDecl) {
		if (currentType.getType().equals(Tab.charType)) {
			currentConstVal = (int) charConstDecl.getVal().charValue();
		} else {
			report_error("Ne moze se dodeliti navedena vrednost tipu deklarinsane konstante", charConstDecl);
			currentConstVal = null;
		}
	}
	
	public void visit(NumConstDecl numConstDecl) {
		if (currentType.getType().equals(Tab.intType)) {
			int val = numConstDecl.getVal();
			currentConstVal = val;
		} else {
			report_error("Ne moze se dodeliti navedena vrednost tipu deklarinsane konstante", numConstDecl);
			currentConstVal = null;
		}
	}
	
	public void visit(ConstsDeclSuccess cds) {
		currentType = null;
	}
	
	/* constants --- end */
	
	/* enum --- begin */
	
	public void visit(EnumName enumName) {
		Obj obj = Tab.currentScope().findSymbol(enumName.getEnumName());
		if (obj != null) {
			report_error("Greska na liniji " + enumName.getLine() + ": vec postoji ime " + enumName.getEnumName() + " deklarisano pre unutar istog opsega", null);
		} else {
			currentEnum = Tab.insert(Obj.Type, enumName.getEnumName(), new Struct(Struct.Enum));
			nextEnumVal = 0;
			Tab.openScope();
		}
	}
	
	private boolean enumHasVal(Integer val) {
		for (Obj valObj : Tab.currentScope().values()) {
			if (valObj.getAdr() == val) {
				return true;
			}
		}
		return false;
	}
	
	public void visit(EnumExplicit enumExplicit) {
		Obj obj = Tab.currentScope().findSymbol(enumExplicit.getEnumConstName());
		if (obj != null) {
			report_error("Greska na liniji " + enumExplicit.getLine() + ": vec postoji enum konstanta " + currentEnum.getName() + "." + enumExplicit.getEnumConstName(), null);
		} else {
			if (enumHasVal(enumExplicit.getEnumConstVal())) {
				report_error("Greska na liniji " + enumExplicit.getLine() + ": ne moze se dodeliti eksplicitna vrednost " + enumExplicit.getEnumConstVal() + " konstanti " + currentEnum.getName() + "." + enumExplicit.getEnumConstName() + " jer vec postoji", null);
			}
			Obj currentEnumConst = Tab.insert(Obj.Con, enumExplicit.getEnumConstName(), Tab.intType);
			currentEnumConst.setAdr(enumExplicit.getEnumConstVal());
			nextEnumVal = enumExplicit.getEnumConstVal() + 1;
		}
	}
	
	public void visit(EnumImplicit enumImplicit) {
		Obj obj = Tab.currentScope().findSymbol(enumImplicit.getEnumConstName());
		if (obj != null) {
			report_error("Greska na liniji " + enumImplicit.getLine() + ": vec postoji enum konstanta " + currentEnum.getName() + "." + enumImplicit.getEnumConstName(), null);
		} else {
			if (enumHasVal(nextEnumVal)) {
				report_error("Greska na liniji " + enumImplicit.getLine() + ": ne moze se dodeliti implicitna vrednost " + nextEnumVal + " konstanti " + currentEnum.getName() + "." + enumImplicit.getEnumConstName() + " jer vec postoji", null);
			}
			Obj currentEnumConst = Tab.insert(Obj.Con, enumImplicit.getEnumConstName(), Tab.intType);
			currentEnumConst.setAdr(nextEnumVal);
			++nextEnumVal;
		}
	}
	
	public void visit(EnumDecl enumDecl) {
		currentEnum.getType().setMembers(Tab.currentScope().getLocals());
		Tab.closeScope();
		currentEnum = null;
	}
	
	/* enum --- end */
	
	/* interface --- begin */
	
	public void visit(InterfaceName interfaceName) {
		Obj obj = Tab.find(interfaceName.getName());
		if (obj != Tab.noObj) {
			report_error("Greska na liniji " + interfaceName.getLine() + " pri deklaraciji interfejsa: ime " + interfaceName.getName() + " vec postoji", null);
		} else {
			currentInterface = Tab.insert(Obj.Type, interfaceName.getName(), new Struct(Struct.Interface));
			Tab.openScope();
		}
	}
	
	public void visit(InterfaceDecl interfaceDecl) {
		Tab.chainLocalSymbols(currentInterface.getType());	// currentInterface.getType().setMembers(...);
		Tab.closeScope();
		currentInterface = null;
	}
	
	public void visit(VoidReturn voidReturn) {
		currentType = new Obj(Obj.Type, "void", Tab.noType);
	}
	
	public void visit(InterfaceMethodName imn) {
		Obj obj = Tab.currentScope().findSymbol(imn.getName());
		if (obj != null) {
			report_error("Greska na liniji " + imn.getLine() + " pri deklaraciji metode interfejsa: ime " + imn.getName() + " vec postoji", null);
		} else {
			currentMethod = Tab.insert(Obj.Meth, imn.getName(), currentType.getType());
			currentMethod.setLevel(0);
			Tab.openScope();
		}
	}
	
	public void visit(InterfaceMethodDecl imd) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		if (imd.getOptargOption() instanceof OptargYes) {
			if (currentMethod.getLevel() == 0) {
				report_error("Greska na liniji " + imd.getInterfaceMethodName().getLine() + ": metoda (" + currentMethod.getName() + ") ne sme biti oznacena kao OPTARG ako nema nijedan formalan argument", null);
			} else {
				TabExtended.optargMethods.add(currentMethod);
			}
		}
		
		currentMethod = null;
	}
	
	/* interface --- end */
	
	/* class --- begin */
	
	public void visit(ClassName className) {
		Obj obj = Tab.find(className.getName());
		if (!obj.equals(Tab.noObj)) {
			report_error("Greska na liniji " + className.getLine() + " pri deklaraciji klase: ime " + className.getName() + " vec postoji", null);
		} else {
			currentClass = Tab.insert(Obj.Type, className.getName(), new Struct(Struct.Class));
			Tab.openScope();
			extendedAndImplemented = new ArrayList<Obj>();
		}
	}
	
	public void visit(ExtendsType extendsType) {
		if (currentType != null) {
			if (currentType.getType().getKind() == Struct.Class) {
				currentClass.getType().setElementType(currentType.getType());
				extendedAndImplemented.add(currentType);
			} else {
				report_error("Greska na liniji " + extendsType.getLine() + " prilikom izvodjenja: tip " + currentType.getName() + " nije klasa", null);
			}
		} else {
			System.err.println("currentType je null prilikom ulaska u metodu void visit(ExtendsType extendsType)");
		}
	}
	
	public void visit(ImplementsType implementsType) {
		if (currentType != null) {
			if (currentType.getType().getKind() == Struct.Interface) {
				boolean alreadyImplemented = false;
				for (Struct implementedInterface : currentClass.getType().getImplementedInterfaces()) {
					if (implementedInterface.equals(currentType.getType())) {
						report_error("Greska na liniji " + implementsType.getLine() + ": vec je implementiran interfejs " + currentType.getName(), null);
						alreadyImplemented = true;
					}
				}
				if (!alreadyImplemented) {
					currentClass.getType().addImplementedInterface(currentType.getType());
					extendedAndImplemented.add(currentType);
				}
			} else {
				report_error("Greska na liniji " + implementsType.getLine() + " prilikom implementiranja interfejsa: tip " + currentType.getName() + " nije interfejs", null);
			}
		} else {
			System.err.println("currentType je null prilikom ulaska u metodu void visit(ImplementsType implementsType)");
		}
	}
	
	private boolean equalPrototypes(Obj method1, Obj method2) {
		if (!(Obj.Meth == method1.getKind() && method2.getKind() == method1.getKind()
				&& method1.getName().contentEquals(method2.getName())
				&& method1.getType().equals(method2.getType())
				&& method1.getAdr() == method2.getAdr()	// ?
				&& method1.getLevel() == method2.getLevel())) {
			return false;
		}
		for (Obj member1 : method1.getLocalSymbols()) {
			for (Obj member2 : method2.getLocalSymbols()) {
				if (member1.getFpPos() > 0 && member1.getFpPos() == member2.getFpPos()
						&& !member1.equals(member2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void visit(ClassDecl classDecl) {
		for (Obj eai : extendedAndImplemented) {
			for (Obj member : eai.getType().getMembers()) {
				Obj obj = Tab.currentScope().findSymbol(member.getName());
				if (obj != null) {
					if (member.getKind() == Obj.Meth && eai.getType().getKind() == Struct.Interface
							&& !equalPrototypes(obj, member)) {
						report_error("Greska: implementirana metoda " + obj.getName() + " ne odgovara potpisu one iz implementiranog interfejsa " + eai.getName(), null);
					}
				} else {
					if (member.getKind() == Obj.Fld) {
						Tab.insert(member.getKind(), member.getName(), member.getType());
					} else if (member.getKind() == Obj.Meth) {
						if (eai.getType().getKind() == Struct.Class) {
							Obj inheritedMethod = Tab.insert(member.getKind(), member.getName(), member.getType());
							Tab.openScope();
							for (Obj sym : member.getLocalSymbols()) {
								Tab.insert(sym.getKind(), sym.getName(), sym.getType());
							}
							Tab.chainLocalSymbols(inheritedMethod);
							Tab.closeScope();
						} else if (eai.getType().getKind() == Struct.Interface) {
							report_error("Greska: klasa " + currentClass.getName() + " mora da implementira metodu " + member.getName() + " iz interfejsa " + eai.getName(), null);
						}
					}
				}
			}
		}
		
		Tab.chainLocalSymbols(currentClass.getType());
		Tab.closeScope();
		currentClass = null;
		extendedAndImplemented = null;
	}
	
	/* class --- end */
	
	/* methods --- begin */
	
	public void visit(MethodName methodName) {
		Obj obj = Tab.currentScope().findSymbol(methodName.getName());
		if (obj != null) {
			report_error("Greska na liniji " + methodName.getLine() + " pri deklarisanju metode: ime " + methodName.getName() + " vec postoji", null);
		}
		currentMethod = Tab.insert(Obj.Meth, methodName.getName(), currentType.getType());
		currentMethod.setLevel(0);
		methodName.obj = currentMethod;
		Tab.openScope();
		report_info("Obradjuje se funkcija " + methodName.getName(), methodName);
	}
	
	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funcija " + currentMethod.getName() + " nema return iskaz!", null);
		}
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		if (methodDecl.getOptargOption() instanceof OptargYes) {
			if (currentMethod.getLevel() == 0) {
				report_error("Greska na liniji " + methodDecl.getMethodName().getLine() + ": metoda (" + currentMethod.getName() + ") ne sme biti oznacena kao OPTARG ako nema nijedan formalan argument", null);
			} else {
				TabExtended.optargMethods.add(currentMethod);
			}
		}
		
		returnFound = false;
		currentMethod = null;
		currentType = null;
	}
	
	public void visit(ReturnExpr returnExpr){	// TREBA DA SE PROVERAVA U RUNTIME?
		returnFound = true;
		if (currentMethod == null) {
			report_error("Greska: return naredba se ne nalazi u telu funkcije", returnExpr);
		} else if (!currentMethod.getType().compatibleWith(returnExpr.getExpr().struct)) {
			report_error("Greska: tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), returnExpr);
		}
	}
	
	public void visit(ReturnNoExpr returnNoExpr) {
		returnFound = true;	// nebitno?
		if (currentMethod == null) {
			report_error("Greska: return naredba se ne nalazi u telu funkcije", returnNoExpr);
		} else if (currentMethod.getType() != Tab.noType) {
			report_error("Greska: funkcija " + currentMethod.getName() + " nije void, u return naredbi se ocekuje vrednost", returnNoExpr);
		}
	}
	
	public void visit(FormalParamDeclVar fpdv) {
		Obj obj = Tab.currentScope().findSymbol(fpdv.getName());
		if (obj != null) {
			report_error("Greska na liniji " + fpdv.getLine() + ": vec postoji ime " + fpdv.getName() + " medju formalnim parametrima", null);
		} else {
			Obj formalParam = Tab.insert(Obj.Var, fpdv.getName(), fpdv.getType().struct);
			currentMethod.setLevel(currentMethod.getLevel() + 1);
			formalParam.setFpPos(currentMethod.getLevel());	// fpPos je >= 1 za formalne parametre
		}
	}
	
	public void visit(FormalParamDeclArr fpda) {
		Obj obj = Tab.currentScope().findSymbol(fpda.getName());
		if (obj != null) {
			report_error("Greska na liniji " + fpda.getLine() + ": vec postoji ime " + fpda.getName() + " medju formalnim parametrima", null);
		} else {
			Obj formalParam = Tab.insert(Obj.Var, fpda.getName(), new Struct(Struct.Array, fpda.getType().struct));
			currentMethod.setLevel(currentMethod.getLevel() + 1);
			formalParam.setFpPos(currentMethod.getLevel());	// fpPos je >= 1 za formalne parametre
		}
	}
	
	 public void visit(ActualParam actualParam) {
//		 actualParams.add(actualParam.getExpr().struct);
		 stackOfActParLists.getFirst().add(actualParam.getExpr().struct);
	 }

	/* methods --- end */
	
	/* read, print --- begin */
	
	public void visit(ReadStmt read) {
		Obj obj = read.getDesignator().obj;
		if (!(obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem || obj.getKind() == Obj.Fld)) {
			report_error("Greska na liniji " + read.getLine() + ": designator u read naredbi mora biti promenljiva, element niza ili polje unutar objekta", null);
		} else {
			Struct s = obj.getType();
			if (!(s.equals(Tab.intType) || s.equals(Tab.charType) || s.equals(TabExtended.boolType))) {
				report_error("Greska na liniji " + read.getLine() + ": designator u read naredbi mora biti tipa int, char ili bool", null);
			}
		}
	}
	
	public void visit(PrintStmt print) {
		Struct s = print.getExpr().struct;
		if (!(s.equals(Tab.intType) || s.equals(Tab.charType) || s.equals(TabExtended.boolType) || s.getKind() == Struct.Enum)) {
			report_error("Greska na liniji " + print.getLine() + ": izraz unutar print naredbe mora biti int, char ili bool", null);
		}
	}
	
	/* read, print --- end */
	
	/* DesignatorStatement --- begin */
	
	private boolean isEnumInt(Struct dest, Struct src) {
		return dest.getKind() == Struct.Enum && src.getKind() == Struct.Int
				|| dest.getKind() == Struct.Int && src.getKind() == Struct.Enum
				|| dest.getKind() == Struct.Enum && src.getKind() == Struct.Enum;
	}
	
//	private boolean isEnumIntOkay(Struct dest, Struct src) {
//		if (dest.getKind() == Struct.Enum) {
//			if (dest.getMembers().) {
//				return true;
//			}
//		} else if (dest.getKind() == Struct.Int) {
//			return true;
//		}
//		return false;
//	}
	
	public void visit(Assignment assignment) {
		int kind = assignment.getDesignator().obj.getKind();
		if (!(kind == Obj.Var || kind == Obj.Fld || kind == Obj.Elem)) {
			report_error("Greska na liniji " + assignment.getLine() + ": destinacija dodele vrednosti mora biti promenljiva, element niza ili polje unutar objekta", null);
		} else if (!assignment.getExpr().struct.assignableTo(assignment.getDesignator().obj.getType())) {
			Struct desStruct = assignment.getDesignator().obj.getType();
			Struct exprStruct = assignment.getExpr().struct;
			if (isEnumInt(desStruct, exprStruct)) {
//				if (!isEnumIntOkay(desStruct, exprStruct)) {
//					report_error("Greska na liniji " + assignment.getLine() + ": nekompatibilni tipovi pri dodeli vrednosti int/enum", null);
//				}
			} else {
				report_error("Greska na liniji " + assignment.getLine() + ": nekompatibilni tipovi u dodeli vrednosti", null);
			}
		}
	}
	
	public void visit(Increment inc) {
		Obj obj = inc.getDesignator().obj;
		if (!(obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem || obj.getKind() == Obj.Fld)) {
			report_error("Greska na liniji " + inc.getLine() + ": " + obj.getName() + " nije promenljiva, element niza ili polje objekta unutrasnje klase", null);
		} else if (!obj.getType().equals(Tab.intType)) {
			report_error("Greska na liniji " + inc.getLine() + ": " + obj.getName() + " nije tipa int", null);
		}
	}
	
	public void visit(Decrement dec) {
		Obj obj = dec.getDesignator().obj;
		if (!(obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem || obj.getKind() == Obj.Fld)) {
			report_error("Greska na liniji " + dec.getLine() + ": " + obj.getName() + " nije promenljiva, element niza ili polje objekta unutrasnje klase", null);
		} else if (!obj.getType().equals(Tab.intType)) {
			report_error("Greska na liniji " + dec.getLine() + ": " + obj.getName() + " nije tipa int", null);
		}
	}
	
	private void checkParams(Obj method, int line) {	// povratni tip da bude boolean?
		ArrayList<Obj> formalParams = new ArrayList<>();
		ArrayList<Struct> actualParams = stackOfActParLists.getFirst();
		for (Obj obj : method.getLocalSymbols()) {
			if (obj.getFpPos() > 0) {
				formalParams.add(obj);
			} else if (method.getName().contentEquals("chr")
						|| method.getName().contentEquals("len")
						|| method.getName().contentEquals("ord")) {
				formalParams.add(obj);
			}
		}
		if (!TabExtended.optargMethods.contains(method) && actualParams.size() != formalParams.size()) {
			report_error("Greska na liniji " + line + ": broj stvarnih parametara ne odgovara broju formalnih", null);
		} else if (TabExtended.optargMethods.contains(method) && !(actualParams.size() == formalParams.size() || actualParams.size() == 0)) {
			report_error("Greska na liniji " + line + ": broj stvarnih parametara OPTARG metode mora biti 0 ili jednak broju formalnih", null);
		} else {
			formalParams.sort((Obj o1, Obj o2) -> {
				return Integer.valueOf(o1.getFpPos()).compareTo(o2.getFpPos());
			});
			for (int i = 0; i < actualParams.size(); i++) {
				if (!actualParams.get(i).assignableTo(formalParams.get(i).getType())) {
					report_error("Greska na liniji " + line + ": tip " + (i + 1) + ". stvarnog argumenta se ne poklapa sa formalnim pri pozivu metode " + method.getName(), null);
				}
			}
		}
	}
	
	public void visit(ProcName procName) {
		stackOfActParLists.push(new ArrayList<Struct>());
	}

	public void visit(ProcCall procCall){
//		Obj func = procCall.getDesignator().obj;
		Obj func = procCall.getProcName().getDesignator().obj;
		if (func.getKind() == Obj.Meth) {
			checkParams(func, procCall.getLine());
//			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + procCall.getLine(), null);
			//RESULT = func.getType();
		} 
		else {
			report_error("Greska na liniji " + procCall.getLine()+" : ime " + func.getName() + " nije funkcija", null);
			//RESULT = Tab.noType;
		}
//		actualParams.clear();
		stackOfActParLists.pop();
	}    

	/* DesignatorStatement --- end */
	
	/* Expr --- begin */
	
	public void visit(TermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
	}
	
	public void visit(NegExpr negExpr) {
		Struct s = negExpr.getTerm().struct;
		if (!(s.equals(Tab.intType) || s.getKind() == Struct.Enum
				|| (s.getKind() == Struct.Array && (s.getElemType().equals(Tab.intType) || s.getElemType().getKind() == Struct.Enum))
				)) {
			report_error("Greska na liniji " + negExpr.getLine() + ": izraz posle unarnog operatora '-' mora biti int", null);
		}
		negExpr.struct = negExpr.getTerm().struct;
	}
	
	public void visit(AddExpr addExpr) {
		Struct te = addExpr.getExpr().struct;
		Struct t = addExpr.getTerm().struct;
		if ((te.equals(t) && te == Tab.intType) || isEnumInt(te, t)) {
			// isEnumIntOkay(...)
			addExpr.struct = te;
		} else {
			report_error("Greska na liniji " + addExpr.getLine() + ": nekompatibilni tipovi u izrazu za sabiranje/oduzimanje", null);
			addExpr.struct = Tab.noType;
		} 
	}
	
	/* Expr --- end */
	
	/* Term --- begin */

	public void visit(TermSimple term) {
		term.struct = term.getFactor().struct;    	
	}
	
	public void visit(TermMulop termMulop) {
		Struct s1 = termMulop.getFactor().struct;
		Struct s2 = termMulop.getTerm().struct;
		if ((s1.equals(Tab.intType) && s2.equals(s1)) || isEnumInt(s1, s2)) {
			// isEnumIntOkay(...)
			termMulop.struct = Tab.intType;
		} else {
			report_error("Greska na liniji " + termMulop.getLine() + ": tipovi prilikom mnozenja i deljenja moraju biti int", null);
			termMulop.struct = Tab.noType;
		}
		// Deo za kombinovane aritmeticke operatore?!
	}
	
	/* Term --- end */

	/* Factor --- begin */
	
	public void visit(NumConst numConst) {
		numConst.struct = Tab.intType;    	
	}
	
	public void visit(CharConst charConst) {
		charConst.struct = Tab.charType;
	}
	
	public void visit(BoolConst boolConst) {
		boolConst.struct = TabExtended.boolType;
	}
	
	public void visit(Var var) {
		var.struct = var.getDesignator().obj.getType();
	}
	
	public void visit(ExprInParentheses exprParen) {
		exprParen.struct = exprParen.getExpr().struct;
	}

	public void visit(FuncName funcName) {
		stackOfActParLists.push(new ArrayList<Struct>());
	}
	
	public void visit(FuncCall funcCall) {
//		Obj func = funcCall.getDesignator().obj;
		Obj func = funcCall.getFuncName().getDesignator().obj;
		if (func.getKind() == Obj.Meth) {
			checkParams(func, funcCall.getLine());
//			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = func.getType();
		} 
		else {
			report_error("Greska na liniji " + funcCall.getLine() + ": ime " + func.getName() + " nije funkcija", null);
			funcCall.struct = Tab.noType;
		}
//		actualParams.clear();
		stackOfActParLists.pop();
	}
	
	public void visit(NewArray newArray) {
		if (!newArray.getExpr().struct.equals(Tab.intType)) {
			report_error("Greska na liniji " + newArray.getLine() + ": izraz u uglastim zagradama mora biti tipa int", null);
		}
//		Struct s = new Struct(Struct.Array, newArray.getType().struct);
		newArray.struct = new Struct(Struct.Array, currentType.getType());
	}

	public void visit(NewClass newClass) {
		if (currentType.getKind() == Obj.Type && currentType.getType().getKind() == Struct.Class) {
			newClass.struct = currentType.getType();
		} else {
			report_error("Greska na liniji " + newClass.getLine() + ": navedeni tip nije unutrasnja klasa", null);
			newClass.struct = Tab.noType;
		}
	}
	
	/* Factor --- end */
	
	/* Designator --- begin */
	
	public void visit(DesignatorSimple designator){
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) {
			report_error("Greska na liniji " + designator.getLine() + ": ime " + designator.getName() + " nije deklarisano", null);
		} else {
			if (obj.getKind() == Obj.Con) {
				report_info("Upotreba simbolicke konstante " + designator.getName(), designator);
			} else if (obj.getKind() == Obj.Var) {
				if (DesignatorArray.class != designator.getParent().getClass()) {
					report_info("Upotreba " + (obj.getLevel() == 0 ? "globalne" : "lokalne") + " promenljive " + designator.getName(), designator);
				}
//				if (DesignatorDot.class == designator.getParent().getClass()) {
//					DesignatorDot parent = (DesignatorDot) designator.getParent();
//					if (parent.obj.getType().getKind() == Struct.Enum) {
//						report_info("Upotreba enum promenljive " + ((DesignatorDot) designator.getParent()).obj + "." + designator.getName(), designator);
//					}
//				} else {
//					report_info("Upotreba promenljive " + designator.getName(), designator);
//				}
//				if (DesignatorDot.class != designator.getParent().getClass()) {
//					report_info("Upotreba promenljive " + designator.getName(), designator);
//				}
			}
		}
		designator.obj = obj;
	}
	
	public void visit(DesignatorDot designatorDot) {
		Obj obj = designatorDot.getDesignator().obj;
		String designatorName = obj.getName();
		String memberName = designatorDot.getMember();
		boolean found = false;
		if (obj.getType().getKind() == Struct.Class) {
			for (Obj sym : obj.getLocalSymbols()) {
				if (sym.getName().contentEquals(memberName)
						&& (sym.getKind() == Obj.Fld || sym.getKind() == Obj.Meth)) {
					found = true;
					designatorDot.obj = sym;
					break;
				}
			}
			if (!found) {
				report_error("Greska na liniji " + designatorDot.getLine() + ": " + memberName + " nije polje ili metoda klase " + designatorName, null);
				designatorDot.obj = Tab.noObj;
			}
		} else if (obj.getType().getKind() == Struct.Interface) {
			for (Obj sym : obj.getLocalSymbols()) {
				if (sym.getName().contentEquals(memberName) && sym.getKind() == Obj.Meth) {
					found = true;
					designatorDot.obj = sym;
					break;
				}
			}
			if (!found) {
				report_error("Greska na liniji " + designatorDot.getLine() + ": " + memberName + " nije metoda interfejsa " + designatorName, null);
				designatorDot.obj = Tab.noObj;
			}
		} else if (obj.getType().getKind() == Struct.Enum) {
			for (Obj sym : obj.getType().getMembers()) {
				if (sym.getName().contentEquals(memberName)) {
					found = true;
					designatorDot.obj = sym;
					break;
				}
			}
			if (!found) {
				report_error("Greska na liniji " + designatorDot.getLine() + ": " + memberName + " nije konstanta u okviru nabrajanja " + designatorName, null);
				designatorDot.obj = Tab.noObj;
			}
		} else {
			report_error("Greska na liniji " + designatorDot.getLine() + ": " + designatorName + " nije unutrasnja klasa, interfejs ili nabrajanje", null);
			designatorDot.obj = Tab.noObj;
		}
	}
	
	public void visit(DesignatorArray designatorArray) {
		Obj obj = designatorArray.getDesignator().obj;
		Struct exprStruct = designatorArray.getExpr().struct;
		if (obj.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji " + designatorArray.getLine() + ": " + obj.getName() + " nije niz", null);
			designatorArray.obj = obj;
		} else if (!(exprStruct.equals(Tab.intType) || exprStruct.getKind() == Struct.Enum)) {
			report_error("Greska na liniji " + designatorArray.getLine() + ": izraz u uglastim zagradama mora biti tipa int", null);
			designatorArray.obj = new Obj(Obj.Elem, "[bilo koji element iz niza " + obj.getName() + "]", obj.getType().getElemType());
			report_info("Pristup elementu niza " + obj.getName(), designatorArray);
		} else {
			designatorArray.obj = new Obj(Obj.Elem, "[element iz niza " + obj.getName() + "]", obj.getType().getElemType());
			report_info("Pristup elementu niza " + obj.getName(), designatorArray);
		}
	}
	
	/* Designator --- end */
	
}

