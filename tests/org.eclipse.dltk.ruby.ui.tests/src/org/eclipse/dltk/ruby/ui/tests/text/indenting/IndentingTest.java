/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.ruby.ui.tests.text.indenting;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ruby.internal.ui.RubyPreferenceConstants;
import org.eclipse.dltk.ruby.internal.ui.text.IRubyPartitions;
import org.eclipse.dltk.ruby.internal.ui.text.RubyAutoEditStrategy;
import org.eclipse.dltk.ruby.ui.tests.internal.TestUtils;
import org.eclipse.dltk.ui.CodeFormatterConstants;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.text.DocCmd;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.ui.PlatformUI;

public class IndentingTest extends TestCase {

	private static final String PATH = "resources/indenting/";
	private static final String DELIMITER = TextUtilities
			.getDefaultLineDelimiter(new Document());
	private RubyAutoEditStrategy tabStrategy, spaceStrategy;

	private void waitWorkbenchCreated() throws InterruptedException {
		for (int i = 0; i < 40; i++) {
			if (PlatformUI.isWorkbenchRunning()) {
				return;
			}
			Thread.sleep(1000);
		}
		throw new RuntimeException(
				"Workbench did not intitialized during a minute.");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		waitWorkbenchCreated();
		tabStrategy = createStrategy(true);
		spaceStrategy = createStrategy(false);
	}

	private RubyAutoEditStrategy createStrategy(boolean useTabs) {
		PreferenceStore store = new PreferenceStore();
		RubyPreferenceConstants.initializeDefaultValues(store);
		store.setValue(CodeFormatterConstants.FORMATTER_TAB_CHAR,
				(useTabs ? CodeFormatterConstants.TAB
						: CodeFormatterConstants.SPACE));
		store.setValue(PreferenceConstants.EDITOR_CLOSE_BRACES, false);
		String partitioning = IRubyPartitions.RUBY_PARTITIONING;
		RubyAutoEditStrategy result = new RubyAutoEditStrategy(partitioning,
				store);
		return result;
	}

	public void doTest(String data, RubyAutoEditStrategy strategy)
			throws Exception {
		final String START_TAG = "#s#";
		final String END_TAG = "#e#";
		final String NEW_LINE_TAG = "#n#";
		final String REPLACE_TAG = "#r#";
		String EXPECTED_TAG = "###";
		
		String delim = TextUtilities.determineLineDelimiter(data, DELIMITER);
		data = data.replaceAll(NEW_LINE_TAG, START_TAG + delim + END_TAG);

		int startPos = data.indexOf(START_TAG);
		Assert.isLegal(startPos >= 0);
		data = data.substring(0, startPos) + data.substring(startPos + START_TAG.length());

		int replacePos = data.indexOf(REPLACE_TAG);
		int insertionStartPos = startPos;
		if (replacePos >= 0) {
			Assert.isLegal(replacePos >= startPos);
			data = data.substring(0, replacePos)
					+ data.substring(replacePos + REPLACE_TAG.length());
			insertionStartPos = replacePos;
		}

		int endPos = data.indexOf(END_TAG);
		Assert.isLegal(endPos >= 0);
		Assert.isLegal(replacePos < 0 || endPos >= replacePos);
		String insertion = data.substring(insertionStartPos, endPos);
		data = data.substring(0, insertionStartPos)
				+ data.substring(endPos + END_TAG.length());

		int expectedPos = data.indexOf(EXPECTED_TAG);
		Assert.isLegal(expectedPos >= 0);
		String expected = data.substring(expectedPos + EXPECTED_TAG.length());
		data = data.substring(0, expectedPos);

		Document doc = new Document(data);
		TestUtils.installStuff(doc);

		// remove the leading line break from expected
		String[] legalLineDelimiters = doc.getLegalLineDelimiters();
		int index = TextUtilities.startsWith(legalLineDelimiters, expected);
		Assert.isLegal(index >= 0);
		expected = expected.substring(legalLineDelimiters[index].length());

		int replaceLength = (replacePos < 0 ? 0 : replacePos - startPos);
		DocCmd cmd = new DocCmd(startPos, replaceLength, insertion);

		strategy.customizeDocumentCommand(doc, cmd);
		if (cmd.doit) {
			// for (Iterator iter = cmd.getCommandIterator(); iter.hasNext(); )
			// {
			// Object command = iter.next();
			// Method method = command.getClass().getMethod("execute", new
			// Class[] {IDocument.class});
			// method.invoke(command, new Object[] {doc});
			// }
			doc.replace(cmd.offset, cmd.length, cmd.text);
		}

		assertEquals(expected, doc.get());
	}

	private void magic() throws Exception {
		String name = getName();
		String fileName = name.substring(4, 5).toLowerCase()
				+ name.substring(5) + ".rb";
		String data = TestUtils.getData(PATH + fileName);
		String moreData = data.replaceAll("\t", "  ");
		if (!moreData.equals(data))
			doTest(moreData, spaceStrategy);
		doTest(data, tabStrategy);
	}

	public void testNewLineInDef() throws Exception {
		magic();
	}

	public void testEnterBeforeClass() throws Exception {
		magic();
	}

	public void testEnterOpensClass() throws Exception {
		magic();
	}

	public void testIfStatement() throws Exception {
		magic();
	}

	public void testIfModifier() throws Exception {
		magic();
	}

	public void testMovingEndToNewLine() throws Exception {
		magic();
	}

	public void testMovingEndWithWhitespaceToNewLine() throws Exception {
		magic();
	}

	public void testDeindentingEnd() throws Exception {
		magic();
	}

	public void testClassNotKeyword() throws Exception {
		magic();
	}

	public void testNewLineAfterEmptyIndentedLine() throws Exception {
		magic();
	}

	public void testNewLineInRegularFunction() throws Exception {
		magic();
	}

	public void testIndentAfterNewLineBeforeParen() throws Exception {
		magic();
	}

	public void testIndentOnUnclosedParen() throws Exception {
		magic();
	}

	public void testIndentOnFirstExplicitContinuation() throws Exception {
		magic();
	}

	public void testIndentOnFirstImplicitContinuation() throws Exception {
		magic();
	}

	public void testNoIndentOnSubsequentExplicitContinuation() throws Exception {
		magic();
	}

	public void testNoIndentOnSubsequentImplicitContinuationAfterExplicitOne()
			throws Exception {
		magic();
	}

	public void testNoIndentOnSubsequentImplicitContinuationAfterImplicitOne()
			throws Exception {
		magic();
	}

}
