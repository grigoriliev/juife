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

import java.awt.event.ActionListener;


/**
 * This interface defines a suitable data model for a <code>Wizard</code>.
 * @author  Grigor Iliev
 */
public interface WizardModel {
	/**
	 * Registers the specified <code>ActionListener</code> to be
	 * notified when the current page is changed.
	 * @param l The <code>ActionListener</code> to register.
	 */
	public void
	addActionListener(ActionListener l);
	
	/**
	 * Removes the specified listener.
	 * @param l The <code>ActionListener</code> to remove.
	 */
	public void
	removeActionListener(ActionListener l);	
	
	/**
	 * Determines whether there is a next page in this wizard.
	 * @return <code>true</code> if there is a next page in this wizard,
	 * <code>false</code> otherwise.
	 * @see #next
	 */
	public boolean hasNext();
	
	/**
	 * Moves to the next page in the wizard.
	 * @return The next page in the wizard.
	 * @see #hasNext
	 */
	public WizardPage next();
	
	/**
	 * Determines whether there is a previous page in this wizard.
	 * @return <code>true</code> if there is a previous page in this wizard,
	 * <code>false</code> otherwise.
	 * @see #previous
	 */
	public boolean hasPrevious();
	
	/**
	 * Moves to the previous page in the wizard.
	 * @return The previous page in the wizard.
	 * @see #hasPrevious
	 */
	public WizardPage previous();
	
	/**
	 * Determines whether the last page is specified.
	 * If the last page is specified the 'Last' button in the wizard will be enabled.
	 * @return <code>true</code> if last page is specified,
	 * <code>false</code> otherwise.
	 * @see #last
	 */
	public boolean hasLast();
	
	/**
	 * Moves to the last page in the wizard.
	 * Last page is the page which will be displayed when the user clicks the 'Last' button.
	 * Note that the last page in the wizard is not necessarily the last page in the model.
	 * @return The page specified as last in the wizard.
	 * @see #hasLast
	 */
	public WizardPage last();
	
	/**
	 * Gets the current page.
	 * @return The current page or <code>null</code> if there isn't current page yet.
	 */
	public WizardPage getCurrentPage();
	
	/**
	 * Gets a <code>String</code> array providing the current list of steps.
	 * @return A <code>String</code> array providing the current list of steps or
	 * <code>null</code> if the list of steps is not available.
	 */
	public String[] getSteps();
	
	/**
	 * Gets the current step in the wizard.
	 * @return The current step in the wizard or
	 * <code>null</code> if there is no current step.
	 */
	public String getCurrentStep();
}
