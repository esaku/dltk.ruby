package org.eclipse.dltk.ruby.formatter.tests;

import junit.framework.TestSuite;

public class RDocTest extends ScriptedTest {

	public static TestSuite suite() {
		return new RDocTest().createScriptedSuite("scripts/rdoc.rb");
	}
}
