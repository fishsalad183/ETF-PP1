package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class TabExtended extends Tab {
	
	public static final Struct boolType = new Struct(Struct.Bool);
//								enumType = new Struct(Struct.Enum);
	
	public static final ArrayList<Obj> optargMethods = new ArrayList<>();
	
	public static void init() {
		Tab.init();
		
		Scope universe = Tab.currentScope();
		universe.addToLocals(new Obj(Obj.Type, "bool", boolType));
//		universe.addToLocals(new Obj(Obj.Type, "enum", enumType));
	}
	
	public static void dump(SymbolTableVisitor stv) {
		System.out.println("=====================SYMBOL TABLE DUMP=========================");
		if (stv == null)
			stv = new DumpSymbolTableVisitorExtended();
		for (Scope s = currentScope; s != null; s = s.getOuter()) {
			s.accept(stv);
		}
		System.out.println(stv.getOutput());
	}

}
