package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class DumpSymbolTableVisitorExtended extends DumpSymbolTableVisitor {
	
	@Override
	public void visitObjNode(Obj objToVisit) {
		//output.append("[");
		switch (objToVisit.getKind()) {
		case Obj.Con:  output.append("Con "); break;
		case Obj.Var:  output.append("Var "); break;
		case Obj.Type: output.append("Type "); break;
		case Obj.Meth: output.append("Meth "); break;
		case Obj.Fld:  output.append("Fld "); break;
		case Obj.Prog: output.append("Prog "); break;
		}
		
		output.append(objToVisit.getName());
		output.append(": ");
		
		if ((Obj.Var == objToVisit.getKind()) && "this".equalsIgnoreCase(objToVisit.getName()))
			output.append("");
		else
			objToVisit.getType().accept(this);
		
		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel() + " ");
				
		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
			output.append("\n");
			nextIndentationLevel();
		}
		
		if (objToVisit.getType().getKind() == Struct.Enum) {
			output.append("\n");
			nextIndentationLevel();
		}

		for (Obj o : objToVisit.getLocalSymbols()) {
			output.append(currentIndent.toString());
			o.accept(this);
			output.append("\n");
		}
		
		if (objToVisit.getKind() == Obj.Meth) {
			for (Obj o : objToVisit.getType().getMembers()) {
				output.append(currentIndent.toString());
				o.accept(this);
				output.append("\n");
			}
		}
		
		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
			previousIndentationLevel();
		} 

		if (objToVisit.getType().getKind() == Struct.Enum) {
			previousIndentationLevel();
//			output.append(currentIndent.toString());
		}

		//output.append("]");
		
	}
	
	@Override
	public void visitStructNode(Struct structToVisit) {
		switch (structToVisit.getKind()) {
		case Struct.None:
			output.append("notype");
			break;
		case Struct.Int:
			output.append("int");
			break;
		case Struct.Char:
			output.append("char");
			break;
		case Struct.Bool:
			output.append("bool");
			break;
		case Struct.Enum:
			output.append("enum [\n");
			nextIndentationLevel();
			for (Obj obj : structToVisit.getMembers()) {
				output.append(currentIndent.toString());
				obj.accept(this);
				output.append("\n");
			}
			previousIndentationLevel();
			output.append(currentIndent.toString());
			output.append("]");
			break;
		case Struct.Array:
			output.append("Arr of ");
			
			switch (structToVisit.getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Bool:
				output.append("bool");
				break;
			case Struct.Enum:
				output.append("enum");
				break;
			case Struct.Class:
				output.append("Class");
				break;
			case Struct.Interface:
				output.append("interface");
				break;
			}
			break;
		case Struct.Class:
			output.append("Class [\n");
			nextIndentationLevel();
			for (Obj obj : structToVisit.getMembers()) {
				output.append(currentIndent.toString());
				obj.accept(this);
			}
			previousIndentationLevel();
			output.append(currentIndent.toString());
			output.append("]");
			break;
		case Struct.Interface:
			output.append("Interface [\n");
			nextIndentationLevel();
			for (Obj obj : structToVisit.getMembers()) {
				output.append(currentIndent.toString());
				obj.accept(this);
			}
			previousIndentationLevel();
			output.append(currentIndent.toString());
			output.append("]");
			break;
		}
	}
	
}
