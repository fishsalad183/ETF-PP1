// generated with ast extension for cup
// version 0.8
// 18/9/2019 0:35:38


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(ReturnType ReturnType);
    public void visit(Mulop Mulop);
    public void visit(InterfaceMethodDeclList InterfaceMethodDeclList);
    public void visit(Relop Relop);
    public void visit(VarsDecl VarsDecl);
    public void visit(Assignop Assignop);
    public void visit(FormalParamDecl FormalParamDecl);
    public void visit(StatementList StatementList);
    public void visit(OptargOption OptargOption);
    public void visit(VarPart VarPart);
    public void visit(ConstsDecl ConstsDecl);
    public void visit(Addop Addop);
    public void visit(DeclPart DeclPart);
    public void visit(ImplementsPart ImplementsPart);
    public void visit(Factor Factor);
    public void visit(VarList VarList);
    public void visit(ConstList ConstList);
    public void visit(DeclList DeclList);
    public void visit(Designator Designator);
    public void visit(EnumPart EnumPart);
    public void visit(Term Term);
    public void visit(ImplementsList ImplementsList);
    public void visit(EnumList EnumList);
    public void visit(ActualParamList ActualParamList);
    public void visit(ImplementsInterfaces ImplementsInterfaces);
    public void visit(VarDeclList VarDeclList);
    public void visit(FormalParamList FormalParamList);
    public void visit(Expr Expr);
    public void visit(ClassMethodDecls ClassMethodDecls);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ActualPars ActualPars);
    public void visit(Statement Statement);
    public void visit(PrintNum PrintNum);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(SomeConstant SomeConstant);
    public void visit(ExtendsClass ExtendsClass);
    public void visit(FormPars FormPars);
    public void visit(Mod Mod);
    public void visit(Div Div);
    public void visit(Mul Mul);
    public void visit(Sub Sub);
    public void visit(Add Add);
    public void visit(LessThanOrEqualTo LessThanOrEqualTo);
    public void visit(LessThan LessThan);
    public void visit(GreaterThanOrEqualTo GreaterThanOrEqualTo);
    public void visit(GreaterThan GreaterThan);
    public void visit(NotEqualTo NotEqualTo);
    public void visit(EqualTo EqualTo);
    public void visit(Assign Assign);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(DesignatorDot DesignatorDot);
    public void visit(DesignatorSimple DesignatorSimple);
    public void visit(FuncName FuncName);
    public void visit(NewClass NewClass);
    public void visit(NewArray NewArray);
    public void visit(FuncCall FuncCall);
    public void visit(ExprInParentheses ExprInParentheses);
    public void visit(Var Var);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(TermMulop TermMulop);
    public void visit(TermSimple TermSimple);
    public void visit(AddExpr AddExpr);
    public void visit(NegExpr NegExpr);
    public void visit(TermExpr TermExpr);
    public void visit(ProcName ProcName);
    public void visit(ErrAssignment ErrAssignment);
    public void visit(ProcCall ProcCall);
    public void visit(Decrement Decrement);
    public void visit(Increment Increment);
    public void visit(Assignment Assignment);
    public void visit(PrintWithoutNum PrintWithoutNum);
    public void visit(PrintWithNum PrintWithNum);
    public void visit(PrintStmt PrintStmt);
    public void visit(ReadStmt ReadStmt);
    public void visit(ReturnNoExpr ReturnNoExpr);
    public void visit(ReturnExpr ReturnExpr);
    public void visit(StatementDes StatementDes);
    public void visit(NoStmt NoStmt);
    public void visit(Statements Statements);
    public void visit(OptargNo OptargNo);
    public void visit(OptargYes OptargYes);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParamSingle ActualParamSingle);
    public void visit(ActualParams ActualParams);
    public void visit(NoActuals NoActuals);
    public void visit(Actuals Actuals);
    public void visit(FormalParamDeclArr FormalParamDeclArr);
    public void visit(FormalParamDeclVar FormalParamDeclVar);
    public void visit(SingleFormalParamDecl SingleFormalParamDecl);
    public void visit(FormalParamDecls FormalParamDecls);
    public void visit(NoFormParam NoFormParam);
    public void visit(FormParams FormParams);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(VoidReturn VoidReturn);
    public void visit(TypeReturn TypeReturn);
    public void visit(NoClassMethodDeclarations NoClassMethodDeclarations);
    public void visit(ClassMethodDeclarations ClassMethodDeclarations);
    public void visit(ImplementsType ImplementsType);
    public void visit(ImplementsSingle ImplementsSingle);
    public void visit(ImplementsMultiple ImplementsMultiple);
    public void visit(DoesNotImplement DoesNotImplement);
    public void visit(DoesImplement DoesImplement);
    public void visit(NoExtends NoExtends);
    public void visit(ExtendsType ExtendsType);
    public void visit(ClassName ClassName);
    public void visit(ClassDecl ClassDecl);
    public void visit(InterfaceMethodName InterfaceMethodName);
    public void visit(InterfaceMethodDecl InterfaceMethodDecl);
    public void visit(NoInterfaceMethods NoInterfaceMethods);
    public void visit(InterfaceMethods InterfaceMethods);
    public void visit(InterfaceName InterfaceName);
    public void visit(InterfaceDecl InterfaceDecl);
    public void visit(EnumImplicit EnumImplicit);
    public void visit(EnumExplicit EnumExplicit);
    public void visit(EnumSingle EnumSingle);
    public void visit(EnumMultiple EnumMultiple);
    public void visit(EnumName EnumName);
    public void visit(EnumDecl EnumDecl);
    public void visit(Type Type);
    public void visit(NoVarDecls NoVarDecls);
    public void visit(VarDecls VarDecls);
    public void visit(VarPartError VarPartError);
    public void visit(ArrDecl ArrDecl);
    public void visit(VarDecl VarDecl);
    public void visit(VarDeclSingle VarDeclSingle);
    public void visit(VarDeclMultiple VarDeclMultiple);
    public void visit(VarType VarType);
    public void visit(VarsDeclErr VarsDeclErr);
    public void visit(VarsDeclaration VarsDeclaration);
    public void visit(BoolConstDecl BoolConstDecl);
    public void visit(CharConstDecl CharConstDecl);
    public void visit(NumConstDecl NumConstDecl);
    public void visit(ConstPart ConstPart);
    public void visit(ConstDeclSingle ConstDeclSingle);
    public void visit(ConstDeclMultiple ConstDeclMultiple);
    public void visit(ConstType ConstType);
    public void visit(ConstsDeclError ConstsDeclError);
    public void visit(ConstsDeclSuccess ConstsDeclSuccess);
    public void visit(DeclPartClass DeclPartClass);
    public void visit(DeclPartInterface DeclPartInterface);
    public void visit(DeclPartEnum DeclPartEnum);
    public void visit(DeclPartVar DeclPartVar);
    public void visit(DeclPartConst DeclPartConst);
    public void visit(NoDecl NoDecl);
    public void visit(Declarations Declarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
