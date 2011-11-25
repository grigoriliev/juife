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

package net.sf.juife.wizard;

import java.awt.Dialog;

import javax.swing.JPanel;

import net.sf.juife.swing.JuifeUtils;
import net.sf.juife.swing.Wizard;


/**
 * The base class for all types of wizard pages.
 * Override this class if you want to create a custom wizard page of a specific type.
 * @see WizardPage.Type
 * @author  Grigor Iliev
 */
public class WizardPage extends JPanel {
	private String description;
	private String subtitle;
	
	private Wizard wizard = null;
	
	/** Represents the type of a wizard page. */
	public static enum Type {
		/** Represents an overview page. */
		OVERVIEW_PAGE,
		
		/** Represents a requirements page. */
		REQUIREMENTS_PAGE,
		
		/** Represents an user-input page. */
		USER_INPUT_PAGE,
		
		/**
		 * Represents a conformation page.
		 * Use <code>CONFIRMATION_PAGE_EX</code> if
		 * you want the 'Next' button to be displayed.
		 */
		CONFIRMATION_PAGE,
		
		/**
		 * Represents a conformation page.
		 * The difference between <code>CONFIRMATION_PAGE</code> and
		 * <code>CONFIRMATION_PAGE_EX</code> is the visibility of the 'Next' button.
		 * Use <code>CONFIRMATION_PAGE</code> if
		 * you don't want the 'Next' button to be displayed.
		 */
		CONFIRMATION_PAGE_EX,
		
		/** Represents a progress page. */
		PROGRESS_PAGE,
		
		/** Represents a summary page. */
		SUMMARY_PAGE
	}
	
	/** Specifies the type of this page. */
	private Type pageType;
	
	/**
	 * Represents the optional buttons to be shown
	 * in the wizard when the page becomes current.
	 */
	public static enum OptionalButtons {
		/** Indicates that none of the optional buttons will be shown. */
		NONE,
		
		/**
		 * Indicates that only the 'Help' button from the optional buttons will be shown.
		 */
		HELP,
		
		/**
		 * Indicates that only the 'Last' button from the optional buttons will be shown.
		 */
		LAST,
		
		/** Indicates that both 'Help' and 'Last' buttons will be shown. */
		HELP_AND_LAST
	}
	
	/**
	 * Specifies the optional buttons that will be shown
	 * in the wizard when this wizard page becomes current.
	 */
	private OptionalButtons optionalButtons;
	
	
	/**
	 * Creates a new <code>WizardPage</code> with <code>null</code> subtitle and description.
	 */
	public
	WizardPage() { this(null); }
	
	/**
	 * Creates a <code>WizardPage</code> with the specified subtitle and 
	 * <code>null</code> description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 */
	public
	WizardPage(String subtitle) { this(subtitle, null); }
	
	/**
	 * Creates a <code>WizardPage</code> with the specified subtitle and description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 */
	public
	WizardPage(String subtitle, String description) {
		this(subtitle, description, Type.USER_INPUT_PAGE);
	}
	
	/**
	 * Creates a <code>WizardPage</code> with the
	 * specified subtitle, description and page type.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 * @param pageType Specifies the page type.
	 */
	public
	WizardPage(String subtitle, String description, Type pageType) {
		this(subtitle, description, pageType, OptionalButtons.NONE);
	}
	
	/**
	 * Creates a <code>WizardPage</code> with the specified
	 * subtitle, description, page type and optional buttons.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 * @param pageType Specifies the page type.
	 * @param optionalButtons Specifies the optional buttons
	 * to be shown in the wizard when this wizard page becomes current.
	 */
	public
	WizardPage (
		String subtitle,
		String description,
		Type pageType,
		OptionalButtons optionalButtons
	) {
		setSubtitle(subtitle);
		setDescription(description);
		setPageType(pageType);
		setOptionalButtons(optionalButtons);
	}
	
	/**
	 * Gets a brief description about this page.
	 * @return Returns a brief description about this page or <code>null</code>
	 * if this is not the first page in current step
	 */
	public String
	getDescription() { return description; }
	
	/**
	 * Sets a brief description about this page. This description is used by Wizard to
	 * create a list of steps in his Left Pane.
	 * <p> Notice that each step in the list can correspond to more than one wizard pages.
	 * To achieve this set the description text only in the first page and <code>null</code> 
	 * in next pages of current step</p>
	 */
	public void
	setDescription(String s) { description = s; }
	
	/**
	 * Gets the subtitle for this page.
	 * @return the subtitle for this page.
	 */
	public String
	getSubtitle() { return subtitle; }
	
	/**
	 * Sets the subtitle for this page.
	 */
	public void
	setSubtitle(String subtitle) { this.subtitle = subtitle; }
	
	/**
	 * Gets the type of this page.
	 * @return The type of this page.
	 */
	public Type
	getPageType() { return pageType; }
	
	/**
	 * Sets the type of this page.
	 * @param pageType The new type of this page.
	 */
	public void
	setPageType(Type pageType) { this.pageType = pageType; }
	
	/**
	 * Determines the optional buttons that will be shown
	 * in the wizard when this page becomes current.
	 * @return An <code>OptionalButtons</code> instance specifying the optional
	 * buttons that will be shown in the wizard when this wizard page becomes current.
	 */
	public OptionalButtons
	getOptionalButtons() { return optionalButtons; }
	
	/**
	 * Sets the optional buttons to be displayed
	 * in the wizard when this page becomes current.
	 * @param optionalButtons Specifies the optional buttons
	 * to be shown in the wizard when this wizard page becomes current.
	 */
	public void
	setOptionalButtons(OptionalButtons optionalButtons) {
		this.optionalButtons = optionalButtons;
	}
	
	/**
	 * Gets the <code>Wizard</code> to which this page belongs to.
	 * Note that this method will return <code>null</code> until the
	 * page becomes current in the wizard.
	 * @return the <code>Wizard</code> to which this page belongs to,
	 * or <code>null</code>.
	 * @see #setWizard
	 */
	public Wizard
	getWizard() { return wizard; }
	
	/**
	 * Sets the <code>Wizard</code> to which this page belongs to.
	 * @param wizard the <code>Wizard</code> to which this page belongs to.
	 */
	public void
	setWizard(Wizard wizard) { this.wizard = wizard; }
	
	/**
	 * Gets the <code>WizardModel</code> to which this page belongs to.
	 * @return the <code>WizardModel</code> to which this page belongs to,
	 * or <code>null</code> if <code>getWizard()</code> returns <code>null</code>.
	 * @see #getWizard
	 */
	public WizardModel
	getWizardModel() { return getWizard() == null ? null : getWizard().getModel(); }
	
	/**
	 * Gets the wizard's dialog.
	 * This method can be used to change the wizard's size, location, etc.
	 * @return The wizard's dialog.
	 */
	public Dialog
	getWizardDialog() { return (Dialog)JuifeUtils.getWindow(this); }
	
	/**
	 * Invoked when the page is going to become current in the wizard.
	 * Override this method to do some initializations before the page is shown in the wizard.
	 * The execution of this method should not be time consuming.
	 * The current implementation does nothing.
	 */
	public void
	preinitPage() { }
	
	/**
	 * Invoked when the page becomes current in the wizard.
	 * Override this method to do some initializations after the page is shown in the wizard.
	 * The execution of this method should not be time consuming.
	 * The current implementation does nothing.
	 */
	public void
	postinitPage() { }
	
	/**
	 * Invoked when the user clicks the 'Back' button
	 * while this page is the current page of the wizard.
	 * Override this method to cancel the movement to previous page, or
	 * to do some additional tasks before that. The return value determines
	 * whether the movement to previous page should be done.
	 * You should return <code>true</code> to allow the wizard to go to
	 * previous page, and <code>false</code> to cancel this operation.
	 * @return <code>true</code>
	 */
	public boolean
	mayGoToPrevious() { return true; }
	
	/**
	 * Invoked when the user clicks the 'Next' button
	 * while this page is the current page of the wizard.
	 * Override this method to cancel the movement to next page, or
	 * to do some additional tasks before that. The return value determines
	 * whether the movement to next page should be done.
	 * You should return <code>true</code> to allow the wizard to go to
	 * next page, and <code>false</code> to cancel this operation.
	 * @return <code>true</code>
	 */
	public boolean
	mayGoToNext() { return true; }
	
	/**
	 * Invoked when the user clicks the 'Last' button
	 * while this page is the current page of the wizard.
	 * Override this method to cancel the movement to last page, or
	 * to do some additional tasks before that. The return value determines
	 * whether the movement to last page should be done.
	 * You should return <code>true</code> to allow the wizard to go to
	 * last page, and <code>false</code> to cancel this operation.
	 * @return <code>true</code>
	 */
	public boolean
	mayGoToLast() { return true; }
	
	/**
	 * Invoked when the user clicks the 'Finish' button
	 * while this page is the current page of the wizard.
	 * Override this method to cancel this operation, or to do some
	 * additional tasks before that. The return value determines
	 * whether this operation should be done.
	 * You should return <code>true</code> to allow the wizard to
	 * finish, and <code>false</code> to cancel this operation.
	 * @return <code>true</code>
	 */
	public boolean
	mayFinish() { return true; }
	
	/**
	 * Invoked when the user clicks the 'Cancel' button
	 * while this page is the current page of the wizard.
	 * Override this method to cancel this operation, or to do some
	 * additional tasks before that. The return value determines
	 * whether this operation should be done.
	 * You should return <code>true</code> to allow the wizard to
	 * cancel, and <code>false</code> otherwise.
	 * @return <code>true</code>
	 */
	public boolean
	mayCancel() { return true; }
	
	/**
	 * Invoked when the user clicks the 'Close' button
	 * while this page is the current page of the wizard.
	 * Override this method to cancel this operation, or to do some
	 * additional tasks before that. The return value determines
	 * whether this operation should be done.
	 * You should return <code>true</code> to allow the wizard to
	 * close, and <code>false</code> otherwise.
	 * @return <code>true</code>
	 */
	public boolean
	mayClose() { return true; }
	
	/**
	 * Invoked when the user clicks the Help button of the wizard.
	 * Override this method to show some additional help to the user about this page.
	 */
	public void
	showHelp() { }
}
