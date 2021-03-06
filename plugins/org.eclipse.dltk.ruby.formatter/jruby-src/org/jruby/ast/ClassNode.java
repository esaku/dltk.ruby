/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2001-2002 Jan Arne Petersen <jpetersen@uni-bonn.de>
 * Copyright (C) 2001-2002 Benoit Cerrina <b.cerrina@wanadoo.fr>
 * Copyright (C) 2002 Anders Bengtsson <ndrsbngtssn@yahoo.se>
 * Copyright (C) 2004 Thomas E Enebo <enebo@acm.org>
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/
package org.jruby.ast;

import java.util.List;

import org.jruby.ast.visitor.NodeVisitor;
import org.jruby.evaluator.Instruction;
import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.lexer.yacc.ISourcePositionHolder;
import org.jruby.parser.StaticScope;

/**
 * A class statement. A class statement is defined by its name, its supertype
 * and its body. The body is a separate naming scope. This node is for a regular
 * class definition, Singleton classes get their own node, the SClassNode
 * 
 * @author jpetersen
 */
public class ClassNode extends Node implements IScopingNode {
	static final long serialVersionUID = -1369424045737867587L;

	private final Colon3Node cpath;
	private final StaticScope scope;
	private final Node bodyNode;
	private final Node superNode;
	private final ISourcePositionHolder classKeyword;

	private final ISourcePositionHolder end;

	public ClassNode(ISourcePosition position, Colon3Node cpath,
			StaticScope scope, Node bodyNode, Node superNode,
			ISourcePositionHolder classKeyword, ISourcePositionHolder endNode) {
		super(position, NodeTypes.CLASSNODE);
		this.cpath = cpath;
		this.scope = scope;
		this.bodyNode = bodyNode;
		this.superNode = superNode;
		this.classKeyword = classKeyword;
		this.end = endNode;
	}

	/**
	 * Accept for the visitor pattern.
	 * 
	 * @param iVisitor
	 *            the visitor
	 **/
	public Instruction accept(NodeVisitor iVisitor) {
		return iVisitor.visitClassNode(this);
	}

	/**
	 * Gets the body of this class.
	 * 
	 * @return the contents
	 */
	public Node getBodyNode() {
		return bodyNode;
	}

	/**
	 * Get the static scoping information.
	 * 
	 * @return the scoping info
	 */
	public StaticScope getScope() {
		return scope;
	}

	/**
	 * Gets the className.
	 * 
	 * @return Returns representation of class path+name
	 */
	public Colon3Node getCPath() {
		return cpath;
	}

	/**
	 * Gets the superNode.
	 * 
	 * @return Returns a Node
	 */
	public Node getSuperNode() {
		return superNode;
	}

	public ISourcePositionHolder getClassKeyword() {
		return classKeyword;
	}

	public ISourcePositionHolder getEnd() {
		return end;
	}

	public List childNodes() {
		return Node.createList(cpath, bodyNode, superNode);
	}

	public String toString() {
		return "ClassNode [" + cpath + "]";
	}

}
