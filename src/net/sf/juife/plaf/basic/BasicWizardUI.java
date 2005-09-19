/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2005 Grigor Kirilov Iliev
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

package net.sf.juife.plaf.basic;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JSeparator;

import javax.swing.plaf.ComponentUI;

import net.sf.juife.Wizard;

import net.sf.juife.plaf.WizardUI;

import net.sf.juife.wizard.WizardModel;
import net.sf.juife.wizard.WizardPage;


/**
 * Basic L&F implementation of <code>WizardUI</code>.
 * @author Grigor Iliev
 */
public class BasicWizardUI extends WizardUI {
	private final static String propertyPrefix = "Wizard" + ".";
	
	private Wizard wizard;
	
	private LayoutManager oldLayoutManager;
	
	private BasicWizardLeftPane leftPane;
	private BasicWizardRightPane rightPane;
	private JSeparator separator;
	private BasicWizardBottomPane bottomPane;
	
	private final Handler handler = new Handler();
	
	
	private
	BasicWizardUI() { }
	
	/**
	 * Gets the property prefix.
	 * @return The property prefix.
	 */
	protected String
	getPropertyPrefix() { return propertyPrefix; }
	
	/**
	 * Creates a new instance of <code>BasicWizardUI</code>.
	 * @return A new instance of <code>BasicWizardUI</code>.
	 */
	public static ComponentUI
	createUI(JComponent c) { return new BasicWizardUI(); }
	
	/**
	 * Configures the specified component appropriate for the look and feel.
	 * This method is invoked when the ComponentUI instance is being
	 * installed as the UI delegate on the specified component.
	 * @param c The component where this UI delegate is being installed.
	 */
	public void
	installUI(JComponent c) {
		wizard = (Wizard) c;
		
		installDefaults();
		installListeners();
	}
	
	/** Installs the UI defaults. */
	protected void
	installDefaults() {
		leftPane = new BasicWizardLeftPane();
		rightPane = new BasicWizardRightPane();
		bottomPane = new BasicWizardBottomPane();
		
		displayLastButton(false);
		displayHelpButton(false);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		oldLayoutManager = wizard.getLayout();
		wizard.setLayout(gridbag);
		
		leftPane.setPreferredSize(new Dimension(190, 300));
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		gridbag.setConstraints(leftPane, c);
		wizard.add(leftPane); 
		
		rightPane.setPreferredSize(new Dimension(400, 300));
		c.gridx = 1;
		c.gridy = 0;
		gridbag.setConstraints(rightPane, c);
		wizard.add(rightPane);
		
		separator = new JSeparator();
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(separator, c);
		wizard.add(separator);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		gridbag.setConstraints(bottomPane, c);
		wizard.add(bottomPane);
	}
	
	/** Installs the event listeners for the UI. */
	protected void
	installListeners() {
		wizard.addPropertyChangeListener(getHandler());
		
		bottomPane.nextButton().addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				WizardModel model = wizard.getModel();
				if(!model.getCurrentPage().mayGoToNext()) return;
				
				model.next();
			}
		});
		
		bottomPane.backButton().addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				WizardModel model = wizard.getModel();
				if(!model.getCurrentPage().mayGoToPrevious()) return;
				
				model.previous();
			}
		});
		
		bottomPane.lastButton().addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				WizardModel model = wizard.getModel();
				if(!model.getCurrentPage().mayGoToLast()) return;
				
				model.last();
			}
		});
		
		bottomPane.finishButton().addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) { wizard.finishWizard(); }
		});
		
		bottomPane.cancelButton().addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) { wizard.cancelWizard(); }
		});
		
		bottomPane.closeButton().addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) { wizard.closeWizard(); }
		});
		
		bottomPane.helpButton().addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				wizard.getModel().getCurrentPage().showHelp();
			}
		});
	}
	
	/**
	 * Reverses configuration which was done on the specified component
	 * during <code>installUI</code>. This method is invoked when this
	 * <code>BasicWizardUI</code> instance is being removed as
	 * the UI delegate for the specified component.
	 * @param c The component from which this UI delegate is being removed.
	 */
	public void
	uninstallUI(JComponent c) {
		uninstallListeners();
		uninstallDefaults();
	}
	
	/** Uninstalls the UI defaults. */
	protected void
	uninstallDefaults() {
		wizard.remove(leftPane);
		wizard.remove(rightPane);
		wizard.remove(separator);
		wizard.remove(bottomPane);
		
		wizard.setLayout(oldLayoutManager);
		oldLayoutManager = null;
		
		leftPane = null;
		rightPane = null;
		separator = null;
		bottomPane = null;
	}
	
	/** Uninstalls the event listeners for the UI. */
	protected void
	uninstallListeners() {
		wizard.removePropertyChangeListener(getHandler());
	}
	
	private Handler
	getHandler() { return handler; }
	
	private class Handler implements ActionListener, PropertyChangeListener {
		/** Invoked when the current page of the wizard is changed */
		public void
		actionPerformed(ActionEvent e) {
			setCurrentPage(wizard.getModel().getCurrentPage());
		}
		
		public void
		propertyChange(PropertyChangeEvent e) {
			String name = e.getPropertyName();
			
			if(e.getPropertyName() == "model") {
				WizardModel old1 = (WizardModel)e.getOldValue();
				WizardModel new1 = (WizardModel)e.getNewValue();
				
				if(old1 != null) old1.removeActionListener(getHandler());
				if(new1 != null) new1.addActionListener(getHandler());
			}
		}
	}
	
	/**
	 * Sets the current page of the wizard.
	 * @param page The wizard page to be set as current.
	 */
	public void
	setCurrentPage(WizardPage page) {
		WizardModel model = wizard.getModel();
		
		if(model.getSteps() != null)
			leftPane.setSteps(model.getSteps(), model.getCurrentStep());
		
		rightPane.setWizardPage(page);
		
		switch(page.getOptionalButtons()) {
		case NONE:
			displayLastButton(false);
			displayHelpButton(false);
			break;
		case HELP:
			displayLastButton(false);
			enableHelpButton(true);
			displayHelpButton(true);
			break;
		case LAST:
			enableLastButton(true);
			displayLastButton(true);
			displayHelpButton(false);
			break;
		case HELP_AND_LAST:
			enableLastButton(true);
			displayLastButton(true);
			enableHelpButton(true);
			displayHelpButton(true);
			break;
		default:
			throw new IllegalArgumentException("Unknown OptionalButtons value");	
		}
		
		switch(page.getPageType()) {
		case OVERVIEW_PAGE:
			enableBackButton(false);
			displayBackButton(true);
			enableNextButton(true);
			displayNextButton(true);
			displayFinishButton(false);
			displayCancelButton(true);
			enableCancelButton(true);
			displayCloseButton(false);
			break;
		case REQUIREMENTS_PAGE:
		case USER_INPUT_PAGE:
			enableBackButton(true);
			displayBackButton(true);
			enableNextButton(true);
			displayNextButton(true);
			displayFinishButton(false);
			displayCancelButton(true);
			enableCancelButton(true);
			displayCloseButton(false);
			break;
		case CONFIRMATION_PAGE:
			enableBackButton(true);
			displayBackButton(true);
			displayNextButton(false);
			displayFinishButton(true);
			displayCancelButton(true);
			enableCancelButton(true);
			displayCloseButton(false);
			break;
		case CONFIRMATION_PAGE_EX:
			enableBackButton(true);
			displayBackButton(true);
			enableNextButton(false);
			displayNextButton(true);
			displayFinishButton(true);
			displayCancelButton(true);
			displayCancelButton(true);
			enableCancelButton(true);
			displayCloseButton(false);
			break;
		case PROGRESS_PAGE:
			enableBackButton(false);
			displayBackButton(true);
			enableNextButton(false);
			displayNextButton(true);
			displayFinishButton(false);
			displayCancelButton(true);
			enableCancelButton(false);
			displayCloseButton(false);
			break;
		case SUMMARY_PAGE:
			displayBackButton(false);
			displayNextButton(false);
			displayFinishButton(false);
			displayCancelButton(false);
			displayCloseButton(true);
			break;
		default:
			throw new IllegalArgumentException("Unknown wizard page type");	
		}
		
		enableBackButton(model.hasPrevious());
		enableNextButton(model.hasNext());
		enableLastButton(model.hasLast());
	}
	
	/**
	 * Determines whether to display the 'Back' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Back' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Back' button.
	 */
	public void
	displayBackButton(boolean b) { bottomPane.backButton().setVisible(b); }
	
	/**
	 * Determines whether to display the 'Nex't button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Next' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Next' button.
	 */
	public void
	displayNextButton(boolean b) { bottomPane.nextButton().setVisible(b); }
	
	/**
	 * Determines whether to display the 'Last' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Last' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Last' button.
	 */
	public void
	displayLastButton(boolean b) { bottomPane.lastButton().setVisible(b); }
	
	/**
	 * Determines whether to display the 'Finish' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Finish' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Finish' button.
	 */
	public void
	displayFinishButton(boolean b) { bottomPane.finishButton().setVisible(b); }
	
	/**
	 * Determines whether to display the 'Cancel' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Cancel' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Cancel' button.
	 */
	public void
	displayCancelButton(boolean b) { bottomPane.cancelButton().setVisible(b); }
	
	/**
	 * Determines whether to display the 'Help' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Help' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Help' button.
	 */
	public void
	displayHelpButton(boolean b) { bottomPane.helpButton().setVisible(b); }
	
	/**
	 * Determines whether to display the 'Close' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Close' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Close' button.
	 */
	public void
	displayCloseButton(boolean b) { bottomPane.closeButton().setVisible(b); }
	
	/**
	 * Determines whether to enable the 'Back' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Back' button in the Bottom Pane;
	 * <code>false</code> to disable the 'Back' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public void
	enableBackButton(boolean enabled) { bottomPane.backButton().setEnabled(enabled); }
	
	/**
	 * Determines whether to enable the 'Nex't button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Next' button in the Bottom Pane;
	 * <code>false</code> to disable the 'Next' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public void
	enableNextButton(boolean enabled) { bottomPane.nextButton().setEnabled(enabled); }

	/**
	 * Determines whether to enable the 'Last' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Last button' in the Bottom Pane;
	 * <code>false</code> to disable the 'Last' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public void
	enableLastButton(boolean enabled) { bottomPane.lastButton().setEnabled(enabled); }

	/**
	 * Determines whether to enable the 'Finish' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Finish' button
	 * in the Bottom Pane; <code>false</code> to disable the 'Finish' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public void
	enableFinishButton(boolean enabled) { bottomPane.finishButton().setEnabled(enabled); }
	
	/**
	 * Determines whether to enable the 'Cancel' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Cancel' button
	 * in the Bottom Pane; <code>false</code> to disable the 'Cancel' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public void
	enableCancelButton(boolean enabled) { bottomPane.cancelButton().setEnabled(enabled); }
	
	/**
	 * Determines whether to enable the 'Help' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Help' button in the Bottom Pane;
	 * <code>false</code> to disable the 'Help' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public void
	enableHelpButton(boolean enabled) { bottomPane.helpButton().setEnabled(enabled); }
	
	/**
	 * Determines whether the wizard can be cancelled.
	 * @return <code>true</code> if the wizard can be cancelled, <code>false</code> otherwise.
	 */
	public boolean
	mayCancelWizard() {
		return bottomPane.cancelButton().isVisible() &&
			bottomPane.cancelButton().isEnabled();
	}
}
