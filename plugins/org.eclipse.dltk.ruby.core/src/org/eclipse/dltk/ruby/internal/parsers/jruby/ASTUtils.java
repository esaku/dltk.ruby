package org.eclipse.dltk.ruby.internal.parsers.jruby;

import java.util.Stack;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ruby.ast.ColonExpression;

public class ASTUtils {

	private ASTUtils() {
		throw new AssertionError("Cannot instantiate utility class");
	}

	public static void setVisibility(MethodDeclaration methodDeclaration, int newVisibility) {
		int modifiers = methodDeclaration.getModifiers();
		modifiers = modifiers & ~(Modifiers.AccPublic | Modifiers.AccProtected | Modifiers.AccPrivate | Modifiers.AccDefault);
		methodDeclaration.setModifiers(modifiers | newVisibility);
	}
	
	public static String getTypeNameFromColon (ColonExpression e) {
		String name = "";
		Expression expr = e;
		while (expr instanceof ColonExpression) {
			ColonExpression colonExpression = (ColonExpression) expr;
			if (name.length() > 0) 
				name = "::" + name;
			name = colonExpression.getName() + name;
			if (colonExpression.isFull()) {
				name = "::" + name;
				break;
			} else
				expr = colonExpression.getLeft();
		} 
		if (expr instanceof ConstantReference) {
			ConstantReference constantReference = (ConstantReference) expr;
			if (name.length() > 0) 
				name = "::" + name;
			name = constantReference.getName() + name;
		}		
		return name;
	}
	
	public static ASTNode[] restoreWayToNode (ModuleDeclaration module, final ASTNode nde) {
		final Stack stack = new Stack();
		
		ASTVisitor visitor = new ASTVisitor() {
			boolean found = false;

			public boolean visitGeneral(ASTNode node) throws Exception {
				if (found)
					return super.visitGeneral(node);
				stack.push(node);
				if (node == nde)
					found = true;
				return super.visitGeneral(node);
			}

			public void endvisitGeneral(ASTNode node) throws Exception {
				super.endvisitGeneral(node);
				if (found) 
					return;
				stack.pop();
			}			
		};
		
		try {
			module.traverse(visitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ASTNode[]) stack.toArray(new ASTNode[stack.size()]);
	}
	
	private static ASTNode getEnclosingElement(Class element, ASTNode[] wayToNode, ASTNode node, boolean considerGiven) {
		int pos = -1;
		for (int i = wayToNode.length - 1; i >= 0; i--) {
			if (wayToNode[i] == node) {
				pos = i;
				break;
			}
		}
		Assert.isLegal(pos != -1);
		if (!considerGiven)
			pos--;
		for (int i = pos; i >= 0; i--) {		
			if (element.isInstance(wayToNode[i]))
				return wayToNode[i];
		}
		
		return null;
	}
	
	public static TypeDeclaration getEnclosingType(ASTNode[] wayToNode, ASTNode node, boolean considerGiven) {
		return (TypeDeclaration) getEnclosingElement(TypeDeclaration.class, wayToNode, node, considerGiven);
	}
	
	public static CallExpression getEnclosingCallNode(ASTNode[] wayToNode, ASTNode node, boolean considerGiven) {
		return (CallExpression) getEnclosingElement(CallExpression.class, wayToNode, node, considerGiven);
	}
	
	public static MethodDeclaration getEnclosingMethod(ASTNode[] wayToNode, ASTNode node, boolean considerGiven) {
		return (MethodDeclaration) getEnclosingElement(MethodDeclaration.class, wayToNode, node, considerGiven);
	}
	
}
