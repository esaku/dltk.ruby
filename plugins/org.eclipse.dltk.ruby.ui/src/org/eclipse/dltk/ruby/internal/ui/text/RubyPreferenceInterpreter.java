/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.internal.ui.text;

import org.eclipse.dltk.ui.CodeFormatterConstants;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.util.AutoEditUtils;
import org.eclipse.dltk.ui.text.util.ITabPreferencesProvider;
import org.eclipse.dltk.ui.text.util.TabStyle;
import org.eclipse.jface.preference.IPreferenceStore;

public class RubyPreferenceInterpreter implements ITabPreferencesProvider {

	private final IPreferenceStore store;

	public RubyPreferenceInterpreter(IPreferenceStore store) {
		this.store = store;
	}

	public boolean isSmartMode() {
		return store.getBoolean(PreferenceConstants.EDITOR_SMART_INDENT);
	}

	public boolean isSmartPaste() {
		return store.getBoolean(PreferenceConstants.EDITOR_SMART_PASTE);
	}

	public boolean closeBlocks() {
		return closeBraces();
	}

	public boolean closeBraces() {
		return store.getBoolean(PreferenceConstants.EDITOR_CLOSE_BRACES);
	}

	public int getIndentSize() {
		return store.getInt(CodeFormatterConstants.FORMATTER_INDENTATION_SIZE);
	}

	public int getTabSize() {
		return store.getInt(CodeFormatterConstants.FORMATTER_TAB_SIZE);
	}

	public TabStyle getTabStyle() {
		return TabStyle.forName(store
				.getString(CodeFormatterConstants.FORMATTER_TAB_CHAR),
				TabStyle.TAB);
	}

	public String getIndent() {
		if (getTabStyle() == TabStyle.SPACES) {
			return AutoEditUtils.getNSpaces(getIndentSize());
		} else
			return "\t"; //$NON-NLS-1$
	}

	public String getIndentByVirtualSize(int size) {
		if (getTabStyle() == TabStyle.SPACES) {
			return AutoEditUtils.getNSpaces(size);
		} else {
			int tabs = size / getTabSize();
			int leftover = size - tabs * getTabSize();
			return AutoEditUtils.getNChars(tabs, '\t')
					+ AutoEditUtils.getNSpaces(leftover);
		}
	}

	public String getIndent(int count) {
		String indent = getIndent();
		StringBuffer result = new StringBuffer(indent.length() * count);
		for (int i = 0; i < count; i++)
			result.append(indent);
		return result.toString();
	}
}
