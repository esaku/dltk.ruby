package org.eclipse.dltk.ruby.tests.parser.ast;

import org.eclipse.dltk.ruby.ast.RubyVariableKind;
import org.eclipse.dltk.ruby.tests.Activator;
import org.eclipse.dltk.ruby.tests.parser.AbstractASTTest;
			
public class RubyVariableKindTest extends AbstractASTTest {

	public RubyVariableKindTest(String name) {
		super(Activator.PLUGIN_ID, name);
	}

	protected Class getExpectedClass () {
		return RubyVariableKind.class;	
	}
	
	public void test0 () throws Exception {
		String text = "";
		checkNode (text, 0, text.length());
	}		
	
}
