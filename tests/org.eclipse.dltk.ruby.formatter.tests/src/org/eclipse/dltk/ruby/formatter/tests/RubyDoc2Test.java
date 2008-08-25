/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.tests;

import java.util.Map;

import junit.framework.TestSuite;

import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.ruby.formatter.RubyFormatter;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;

public class RubyDoc2Test extends ScriptedTest {

	public static TestSuite suite() {
		return new RubyDoc2Test().createScriptedSuite("scripts/rubydocs2.rb");
	}

	protected RubyFormatter createFormatter() {
		Map preferences = TestRubyFormatter.createTestingPreferences();
		preferences.put(RubyFormatterConstants.LINES_BEFORE_FIRST, "1");
		preferences.put(RubyFormatterConstants.LINES_BEFORE_METHOD, "2");
		return new TestRubyFormatter(Util.LINE_SEPARATOR, preferences);
	}

}
