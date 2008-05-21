/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2005-2007 Grigor Iliev <grigor@grigoriliev.com>
 *
 *   This file is part of juife.
 *
 *   juife is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License version 2.1 as published by the Free Software Foundation.
 *
 *   juife is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with juife; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *   MA  02110-1301, USA
 */

package net.sf.juife;

import java.awt.Dialog;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;
import javax.swing.UIManager;

import net.sf.juife.plaf.WizardUI;

import net.sf.juife.wizard.DefaultWizardModel;
import net.sf.juife.wizard.WizardModel;
import net.sf.juife.wizard.WizardPage;


/**
 * The main class for creating wizards that conforms to the wizard's
 * <a href="http://java.sun.com/products/jlf/at/book/Wizards.html">guidelines</a>
 * provided in
 * <a href="http://java.sun.com/products/jlf/at/book/index.html">
 * Java Look and Feel Design Guidelines: Advanced Topics</a>
 * book.
 * <p>Note that by default the wizard is diplayed in modal dialog.
 * Use {@link #setModal} to change the modal state of the wizard.</p>
 * @author Grigor Iliev
 */
public class Wizard extends JPanel {
	static {
		// TODO: In future this must be done the right way
		UIManager.put("WizardUI", "net.sf.juife.plaf.basic.BasicWizardUI");
	}
	private static final String uiClassID = "WizardUI";
	
	/** Property for setting the icon of the <b>Back</b> button. */
	public static final String BACK_BUTTON_ICON = "Wizard.backButtonIcon";
	
	/** Property for setting the icon of the <b>Next</b> button. */
	public static final String NEXT_BUTTON_ICON = "Wizard.nextButtonIcon";
	
	/** Property for setting the background color of the left pane. */
	public static final String LEFT_PANE_BACKGROUND_COLOR = "Wizard.leftPaneBackgroundColor";
	
	/** Property for setting the foreground color of the left pane. */
	public static final String LEFT_PANE_FOREGROUND_COLOR = "Wizard.leftPaneForegroundColor";
	
	private final WizardDialog wizardDialog;
	
	private WizardModel model = new DefaultWizardModel();
	
	
	/**
	 * Creates a new wizard with the specified title and owner.
	 * @param owner Specifies the <code>Frame</code> from which this wizard is displayed.
	 * @param title The text to be displayed in the wizard's title bar.
	 */
	public
	Wizard(Frame owner, String title) {
		this(owner, title, new DefaultWizardModel());
	}
	
	/**
	 * Creates a new wizard with the specified title, owner and model.
	 * @param owner Specifies the <code>Frame</code> from which this wizard is displayed.
	 * @param title The text to be displayed in the wizard's title bar.
	 * @param model The non-<code>null</code> model to be used by this wizard.
	 * @throws IllegalArgumentException If <code>model</code> is <code>null</code>.
	 */
	public
	Wizard(Frame owner, String title, WizardModel model) {
		wizardDialog = new WizardDialog(owner, title, true);
		
		initWizard();
		setModel(model);
	}
	
	/**
	 * Creates a new wizard with the specified title and owner.
	 * @param owner Specifies the dialog from which this wizard is displayed.
	 * @param title The text to be displayed in the wizard's title bar.
	 */
	public
	Wizard(Dialog owner, String title) {
		this(owner, title, new DefaultWizardModel());
	}
	
	/**
	 * Creates a new wizard with the specified title, owner and model.
	 * @param owner Specifies the dialog from which this wizard is displayed.
	 * @param title The text to be displayed in the wizard's title bar.
	 * @param model The non-<code>null</code> model to be used by this wizard.
	 * @throws IllegalArgumentException If <code>model</code> is <code>null</code>.
	 */
	public
	Wizard(Dialog owner, String title, WizardModel model) {
		wizardDialog = new WizardDialog(owner, title, true);
		
		initWizard();
		setModel(model);
	}
	
	private void
	initWizard() {
		wizardDialog.getContentPane().add(this);
		wizardDialog.pack();
	}
	
	/**
	 * Gets a string that specifies the name
	 * of the L&F class that renders this component.
	 * @return the string "NavigationPaneUI"
	 */
	public String
	getUIClassID() { return uiClassID; }
	
	/**
	 * Gets the L&F object that renders this component.
	 * @return The L&F object that renders this component.
	 */
	public WizardUI
	getUI() { return (WizardUI)ui; }
	
	/**
	 * Sets the L&F object that renders this component.
	 * @param ui The new UI delegate.
	 */
	public void
	setUI(WizardUI ui) { super.setUI(ui); }
	
	/**
	 * Resets the UI property to a value from the current look and feel.
	 */
	public void
	updateUI() { setUI((WizardUI)UIManager.getUI(this)); }
	
	/**
	 * Specifies whether this wizard should be modal.
	 * @param modal If <code>true</code> the wizard will be displayed in modal dialog.
	 */
	public void
	setModal(Boolean modal) { wizardDialog.setModal(modal); }
	
	/**
	 * Gets the model of this wizard.
	 * @return The model of this wizard.
	 */
	public WizardModel
	getModel() { return model; }
	
	/**
	 * Sets the model of this wizard.
	 * @param model A non-<code>null WizardModel</code> instance.
	 * @throws IllegalArgumentException If <code>model</code> is <code>null</code>.
	 */
	public void
	setModel(WizardModel model) {
		if(model == null) throw new IllegalArgumentException("model must be non null");
		
		WizardModel oldModel = this.model;
		this.model = model;
		
		if(oldModel != null) oldModel.removeActionListener(getHandler());
		model.addActionListener(getHandler());
		
		firePropertyChange("model", oldModel, this.model);
	}
	
	/** Closes the wizard. */
	public void
	closeWizard() {
		if(!getCurrentPage().mayClose()) return;
				
		wizardDialog.setVisible(false);
	}
	
	/** Cancels the wizard. */
	public void
	cancelWizard() {
		if(!getCurrentPage().mayCancel()) return;
		
		wizardDialog.setVisible(false);
	}
	
	/** Finishes the wizard task. */
	public void
	finishWizard() {
		if(!getModel().getCurrentPage().mayFinish()) return;
			
		if(!getModel().hasNext()) wizardDialog.setVisible(false);
		else getModel().next();
	}
	
	/**
	 * Gets the current page in the Wizard.
	 * @return the current page in the Wizard.
	 */
	public WizardPage
	getCurrentPage() { return getModel().getCurrentPage(); }
	
	/**
	 * Starts the Wizard.
	 */
	public void
	showWizard() {
		if(getModel().getCurrentPage() == null) getModel().next();
		
		wizardDialog.setLocation (
			JuifeUtils.centerLocation(wizardDialog, wizardDialog.getParent())
		);
		
		wizardDialog.setVisible(true);
	}
	
	/**
	 * Enables/disables the <code>Next</code>button.
	 * Use this method to manually enable/disable the
	 * <code>Next</code> button when needed.
	 */
	public void
	enableNextButton(boolean enable) {
		getUI().enableNextButton(enable);
	}
	
	/**
	 * Enables/disables the <code>Finish</code>button.
	 * Use this method to manually enable/disable the
	 * <code>Finish</code> button when needed.
	 */
	public void
	enableFinishButton(boolean enable) {
		getUI().enableFinishButton(enable);
	}
	
	/**
	 * Gets the wizard's dialog.
	 * This method can be used to change the wizard's size, location, etc.
	 * @return The wizard's dialog.
	 */
	public javax.swing.JDialog
	getWizardDialog() { return wizardDialog; }
	
	private class WizardDialog extends EnhancedDialog {
		WizardDialog(Frame owner, String title, boolean modal) {
			super(owner, title, modal);
			
			initWizardDialog();
		}
		
		WizardDialog(Dialog owner, String title, boolean modal) {
			super(owner, title, modal);
			
			initWizardDialog();
		}
		
		private void
		initWizardDialog() {
			setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				public void
				windowClosing(WindowEvent we) { onCancel(); }
			});
		}
		
		protected void
		onOk() { }
	
		protected void
		onCancel() {
			if(getCurrentPage().getPageType() == WizardPage.Type.SUMMARY_PAGE) {
				closeWizard();
				return;
			}
		
			if(getUI().mayCancelWizard()) cancelWizard();
		}
	}
	
	private final Handler handler = new Handler();
	
	private Handler
	getHandler() { return handler; }
	
	private class Handler implements ActionListener {
		/** Invoked when the current page of the wizard is changed */
		public void
		actionPerformed(ActionEvent e) {
			getCurrentPage().setWizard(Wizard.this);
			getCurrentPage().preinitPage();
		}
	}
}
