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

package net.sf.juife.swing.plaf;

import javax.swing.plaf.PanelUI;

/**
 * Pluggable look and feel interface for <code>Wizard</code>.
 * @author Grigor Iliev
 */
public abstract class WizardUI extends PanelUI {
	/**
	 * Determines whether the wizard can be cancelled.
	 * @return <code>true</code> if the wizard can be cancelled, <code>false</code> otherwise.
	 */
	public abstract boolean mayCancelWizard();
	
	/**
	 * Determines whether to display the 'Back' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Back' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Back' button.
	 */
	public abstract void displayBackButton(boolean b);
	
	/**
	 * Determines whether to display the 'Nex't button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Next' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Next' button.
	 */
	public abstract void displayNextButton(boolean b);
	
	/**
	 * Determines whether to display the 'Last' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Last' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Last' button.
	 */
	public abstract void displayLastButton(boolean b);
	
	/**
	 * Determines whether to display the 'Finish' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Finish' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Finish' button.
	 */
	public abstract void displayFinishButton(boolean b);
	
	/**
	 * Determines whether to display the 'Cancel' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Cancel' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Cancel' button.
	 */
	public abstract void displayCancelButton(boolean b);
	
	/**
	 * Determines whether to display the 'Help' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Help' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Help' button.
	 */
	public abstract void displayHelpButton(boolean b);
	
	/**
	 * Determines whether to display the 'Close' button in the Bottom Pane.
	 * @param b Specify <code>true</code> to display the 'Close' button in the Bottom Pane;
	 * <code>false</code> to hide the 'Close' button.
	 */
	public abstract void displayCloseButton(boolean b);
	
	/**
	 * Determines whether to enable the 'Back' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Back' button in the Bottom Pane;
	 * <code>false</code> to disable the 'Back' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public abstract void enableBackButton(boolean enabled);
	
	/**
	 * Determines whether to enable the 'Next' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Next' button in the Bottom Pane;
	 * <code>false</code> to disable the 'Next' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public abstract void enableNextButton(boolean enabled);

	/**
	 * Determines whether to enable the 'Last' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Last button' in the Bottom Pane;
	 * <code>false</code> to disable the 'Last' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public abstract void enableLastButton(boolean enabled);

	/**
	 * Determines whether to enable the 'Finish' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Finish' button
	 * in the Bottom Pane; <code>false</code> to disable the 'Finish' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public abstract void enableFinishButton(boolean enabled);
	
	/**
	 * Determines whether to enable the 'Cancel' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Cancel' button
	 * in the Bottom Pane; <code>false</code> to disable the 'Cancel' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public abstract void enableCancelButton(boolean enabled);
	
	/**
	 * Determines whether to enable the 'Help' button in the Bottom Pane.
	 * @param enabled Specify <code>true</code> to enable the 'Help' button in the Bottom Pane;
	 * <code>false</code> to disable the 'Help' button.
	 * @see javax.swing.JComponent#setEnabled
	 */
	public abstract void enableHelpButton(boolean enabled);
}
