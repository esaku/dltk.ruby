package org.eclipse.dltk.ruby.debug.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.dltk.ruby.debug.tests.console.RubyFilenameLinenumberTests;
import org.eclipse.dltk.ruby.debug.tests.launching.RubyLaunchingTests;

public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("org.eclipse.dltk.ruby.debug");
		// $JUnit-BEGIN$

		// Launching
		suite.addTest(RubyLaunchingTests.suite());
		suite.addTest(RubyFilenameLinenumberTests.suite());
		// $JUnit-END$
		return suite;
	}
}
