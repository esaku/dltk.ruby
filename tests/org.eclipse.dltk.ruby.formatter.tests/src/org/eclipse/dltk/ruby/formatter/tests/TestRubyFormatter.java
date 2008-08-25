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

import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.ruby.formatter.RubyFormatter;

public class TestRubyFormatter extends RubyFormatter {

	public TestRubyFormatter() {
		super(Util.LINE_SEPARATOR, RubyFormatter.createTestingPreferences());
	}

	public TestRubyFormatter(String lineDelimiter, Map preferences) {
		super(lineDelimiter, preferences);
	}

	protected boolean isValidation() {
		return false;
	}

}