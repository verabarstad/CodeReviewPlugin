package review.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class MyVisitor extends ASTVisitor {
  List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
  List<SimpleName> names = new ArrayList<SimpleName>();
  List<VariableDeclarationFragment> variables = new ArrayList<VariableDeclarationFragment>();
  
  List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
  
  @Override
  public boolean visit(TypeDeclaration node) {
    types.add(node);
    return super.visit(node);
  }
  
  public List<TypeDeclaration> getTypes() {
	    return types;
	  }
  
  @Override
  public boolean visit(MethodDeclaration node) {
    methods.add(node);
    return super.visit(node);
  }
  
  public List<MethodDeclaration> getMethods() {
	    return methods;
	  }
  
  @Override
  public boolean visit(SimpleName node) {
    names.add(node);
    return super.visit(node);
  }

  public List<SimpleName> getNames() {
	    return names;
	  }
  
	  
  @Override
  public boolean visit(VariableDeclarationFragment node) {
    variables.add(node);
    return super.visit(node);
  }

  public List<VariableDeclarationFragment> getVariables() {
	    return variables;
	  }
}