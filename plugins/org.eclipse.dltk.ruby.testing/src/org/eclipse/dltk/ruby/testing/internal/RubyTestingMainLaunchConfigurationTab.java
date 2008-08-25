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
package org.eclipse.dltk.ruby.testing.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.debug.ui.messages.DLTKLaunchConfigurationsMessages;
import org.eclipse.dltk.ruby.internal.debug.ui.launchConfigurations.RubyMainLaunchConfigurationTab;
import org.eclipse.dltk.ruby.testing.IRubyTestingEngine;
import org.eclipse.dltk.testing.IDLTKTestingConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class RubyTestingMainLaunchConfigurationTab extends
		RubyMainLaunchConfigurationTab {

	private Button detect;
	private Combo engineType;
	private Map nameToId = new HashMap();

	public RubyTestingMainLaunchConfigurationTab(String mode) {
		super(mode);
	}

	protected void doCreateControl(Composite composite) {
		createMainModuleEditor(composite,
				DLTKLaunchConfigurationsMessages.mainTab_mainModule);
		createVerticalSpacer(composite, 1);
		createTestEngineEditor(composite, "Ruby Testing engine");
	}

	protected void createTestEngineEditor(Composite parent, String text) {
		Font font = parent.getFont();
		Group mainGroup = new Group(parent, SWT.NONE);
		mainGroup.setText(text);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		mainGroup.setLayoutData(gd);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		mainGroup.setLayout(layout);
		mainGroup.setFont(font);
		engineType = new Combo(mainGroup, SWT.SINGLE | SWT.BORDER
				| SWT.DROP_DOWN);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		engineType.setLayoutData(gd);
		engineType.setFont(font);
		engineType.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		detect = createPushButton(mainGroup, "Detect", null);

		IRubyTestingEngine[] engines = RubyTestingEngineManager.getEngines();
		for (int i = 0; i < engines.length; i++) {
			String name = engines[i].getName();
			this.engineType.add(name);
			nameToId.put(name, engines[i].getId());
		}
		detect.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleDetectButtonSelected();
			}
		});
		handleDetectButtonSelected();
	}

	private void handleDetectButtonSelected() {
		IRubyTestingEngine[] engines = RubyTestingEngineManager.getEngines();
		// this.engineType.select(0);
		ISourceModule module = getSourceModule();
		if (module != null && module.exists()) {
			for (int i = 0; i < engines.length; i++) {
				if (engines[i].isValidModule(module)) {
					this.engineType.select(i);
				}
			}
		}
	}

	private ISourceModule getSourceModule() {
		IScriptProject project = this.getProject();
		if (project == null) {
			return null;
		}
		IProject prj = project.getProject();
		String scriptName = this.getScriptName();
		ISourceModule module = null;
		IResource res = prj.getFile(scriptName);
		module = (ISourceModule) DLTKCore.create(res);
		return module;
	}

	protected boolean doCanSave() {
		return validateScript() && validateEngine();
	}

	private boolean validateEngine() {
		ISourceModule module = getSourceModule();
		if (module != null) {
			IRubyTestingEngine[] engines = RubyTestingEngineManager.getEngines();
			for (int i = 0; i < engines.length; i++) {
				String selectedEngine = this.getEngineId();
				if (engines[i].getId().equals(selectedEngine)
						&& engines[i].isValidModule(module)) {
					return true;
				}
			}
		}
		setErrorMessage("Testing engine not support specified script");
		return true;
	}

	protected void doPerformApply(ILaunchConfigurationWorkingCopy config) {
		super.doPerformApply(config);
		config.setAttribute(IDLTKTestingConstants.ENGINE_ID_ATR, getEngineId());
	}

	private String getEngineId() {
		return (String) this.nameToId.get(this.engineType.getText());
	}

	protected void doInitializeForm(ILaunchConfiguration config) {
		super.doInitializeForm(config);
		IRubyTestingEngine[] engines = RubyTestingEngineManager.getEngines();
		String id = null;
		try {
			id = config.getAttribute(IDLTKTestingConstants.ENGINE_ID_ATR,
					Util.EMPTY_STRING);
		} catch (CoreException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		if (id == null || id.length() == 0) {
			handleDetectButtonSelected();
		} else {
			// this.engineType.select(0);
			for (int i = 0; i < engines.length; i++) {
				if (engines[i].getId().equals(id)) {
					this.engineType.select(i);
				}
			}
		}
		// handleDetectButtonSelected();
	}
}