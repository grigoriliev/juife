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

package net.sf.juife.swing;

import java.awt.Dialog;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Adds common dialog behaviors that are missing in <code>JDialog</code>.
 * This class can be used to:
 * <ul>
 *  <li> Facilitate the handling of user actions like accepting or canceling
 *       a dialog by using the Enter and Escape keys
 *  <li> Easily show or hide a diloag form thread other then the event-dispatching thread
 * </ul>
 * @author Grigor Iliev
 */
public abstract class EnhancedDialog extends JDialog {
	private boolean cancelled = true;
	
	/**
	 * Creates a modal dialog without a title and with the specified
	 * <code>Dialog</code> as its owner.
	 * @param owner Specifies the dialog from which this dialog is displayed.
	 */
	public
	EnhancedDialog(Dialog owner) { this(owner, ""); }
	
	/**
	 * Creates a modal dialog with the specified title and owner <code>Dialog</code>.
	 * @param owner Specifies the dialog from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 */
	public
	EnhancedDialog(Dialog owner, String title) { this(owner, title, true); }
	
	/**
	 * Creates a modal or non-model dialog with the
	 * specified title and owner <code>Dialog</code>.
	 * @param owner Specifies the dialog from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 * @param modal Specifies whether this dialog should be modal or not.
	 */
	public
	EnhancedDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
		initEnhancedDialog();
	}
	
	/**
	 * Creates a modal dialog without a title and with the specified
	 * <code>Frame</code> as its owner.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 */
	public
	EnhancedDialog(Frame owner) { this(owner, ""); }
	
	/**
	 * Creates a modal dialog with the specified title and owner <code>Frame</code>.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 */
	public
	EnhancedDialog(Frame owner, String title) { this(owner, title, true); }
	
	/**
	 * Creates a modal or non-modal dialog with the
	 * specified title and owner <code>Frame</code>.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 * @param modal Specifies whether this dialog should be modal or not.
	 */
	public
	EnhancedDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		initEnhancedDialog();
	}
	
	/**
	 * This method is invoked when the user presses the Enter key.
	 */
	protected abstract void onOk();
	
	/**
	 * This method is invoked when the user presses the Escape key.
	 */
	protected abstract void onCancel();
	
	/** Used for initial initialization of the dialog */
	private void
	initEnhancedDialog() {
		
		
		getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put (
			KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
			"Applying..."
		);
		
		getRootPane().getActionMap().put ("Applying...", new AbstractAction() {
			public void
			actionPerformed(ActionEvent e) {
				setCancelled(false);
				onOk();
			}
		});
		
		
		getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put (
			KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
			"Canceling..."
		);
		
		getRootPane().getActionMap().put ("Canceling...", new AbstractAction() {
			public void
			actionPerformed(ActionEvent e) {
				setCancelled(true);
				onCancel();
			}
		});
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void
			windowClosing(WindowEvent we) {
				setCancelled(true);
				onCancel();
			}
		});
	}
	
	/**
	 * This method can be used when the user closes the
	 * dialog to determine whether the dialog was cancelled.
	 * @return <code>true</code> if the user cancels the dialog, <code>false</code> otherwise.
	 */
	public boolean
	isCancelled() { return cancelled; }
	
	/**
	 * Sets whether the dialog was cancelled.
	 * @param b Specify <code>true</code> to indicate that this dialog was cancelled;
	 * <code>false</code> otherwise.
	 */
	public void
	setCancelled(boolean b) { cancelled = b; }
	
	/**
	 * Shows or hides this dialog.
	 * This method queues the execution of the <code>setVisible</code> method
	 * on the event-dispatching thread so it can be safely invoked also from
	 * threads other then the event-dispatching thread.
	 * @param b Specify <code>true</code> to show the dialog; <code>false</code>
	 * to hide the dialog.
	 */
	public void
	makeVisible(final boolean b) {
		if(SwingUtilities.isEventDispatchThread()) setVisible(b);
		else try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void
				run() {  setVisible(b); }
			});
		} catch(Exception x) { x.printStackTrace(); }
	}
}
