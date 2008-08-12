/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.formatter.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.ruby.formatter.RubyFormatterConstants;
import org.eclipse.dltk.ui.CodeFormatterConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class RubyFormatterPreferenceInitializer extends
		AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = RubyFormatterPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(RubyFormatterConstants.FORMATTER_TAB_CHAR,
				CodeFormatterConstants.TAB);
		store.setDefault(RubyFormatterConstants.FORMATTER_INDENTATION_SIZE, 1);
		//
		store.setDefault(RubyFormatterConstants.INDENT_CLASS, true);
		store.setDefault(RubyFormatterConstants.INDENT_MODULE, true);
		store.setDefault(RubyFormatterConstants.INDENT_METHOD, true);
		store.setDefault(RubyFormatterConstants.INDENT_BLOCKS, true);
		store.setDefault(RubyFormatterConstants.INDENT_CASE, false);
		store.setDefault(RubyFormatterConstants.INDENT_WHEN, true);
		store.setDefault(RubyFormatterConstants.INDENT_IF, true);
		//
		store.setDefault(RubyFormatterConstants.LINES_BEFORE_CLASS, 1);
		store.setDefault(RubyFormatterConstants.LINES_AFTER_CLASS, 1);
		store.setDefault(RubyFormatterConstants.LINES_BEFORE_METHOD, 1);
		store.setDefault(RubyFormatterConstants.LINES_AFTER_METHOD, 1);
		store.setDefault(RubyFormatterConstants.LINES_PRESERVE, 1);
	}
}