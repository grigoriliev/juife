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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import static net.sf.juife.JuifeI18n.i18n;


/**
 * This class can be used to simplify the creation of information dialogs
 * with or without 'Close' button.
 * To create a dialog you only need to specify a <code>Container</code>
 * that will be used as a main pane in the dialog.
 * This can be done either by using one of the following constructors to create the dialog
 * <ul>
 *  <li>{@link #InformationDialog(Frame owner, Container mainPane)}
 *  <li>{@link #InformationDialog(Frame owner, String title, Container mainPane)}
 *  <li>{@link #InformationDialog(Frame owner, String title, Container mainPane, boolean modal)}
 *  <li>{@link #InformationDialog(Dialog owner, Container mainPane)}
 *  <li>{@link #InformationDialog(Dialog owner, String title, Container mainPane)}
 *  <li>{@link #InformationDialog(Dialog owner, String title, Container mainPane, boolean modal)}
 * </ul>
 * or by using the {@link #setMainPane} method.
 *
 * @author Grigor Iliev
 */
public class InformationDialog extends EnhancedDialog {
	private final JPanel pane = new JPanel(new BorderLayout());
	private final JPanel btnPane = new JPanel();
	private final JButton btnClose = new JButton(i18n.getButtonLabel("close"));
	
	/**
	 * Creates a modal dialog without a title and with the specified
	 * <code>Frame</code> as its owner.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 */
	public
	InformationDialog(Frame owner) { this(owner, new Container()); }
	
	/**
	 * Creates a modal dialog with the specified title and owner <code>Frame</code>.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 */
	public
	InformationDialog(Frame owner, String title) { this(owner, title, new Container()); }
	
	/**
	 * Creates a modal dialog without a title and with the specified owner and main pane.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 * @param mainPane A non-null <code>Container</code> instance that will be used
	 * as a main pane for this dialog.
	 * @throws IllegalArgumentException If <code>mainPane</code> is <code>null</code>.
	 */
	public
	InformationDialog(Frame owner, Container mainPane) { this(owner, "", mainPane); }
	
	/**
	 * Creates a modal dialog with the specified title, owner and main pane.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 * @param mainPane A non-null <code>Container</code> instance that will be used
	 * as a main pane for this dialog.
	 * @throws IllegalArgumentException If <code>mainPane</code> is <code>null</code>.
	 */
	public
	InformationDialog(Frame owner, String title, Container mainPane) {
		this(owner, title, mainPane, true);
	}
	
	/**
	 * Creates a modal or non-modal dialog with the specified title, owner and main pane.
	 * @param owner Specifies the <code>Frame</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 * @param mainPane A non-null <code>Container</code> instance that will be used
	 * as a main pane for this dialog.
	 * @param modal Specifies whether this dialog should be modal or not.
	 * @throws IllegalArgumentException If <code>mainPane</code> is <code>null</code>.
	 */
	public
	InformationDialog(Frame owner, String title, Container mainPane, boolean modal) {
		super(owner, title, modal);
		
		initInformationDialog();
		setMainPane(mainPane);
		addWindowListener(new WindowAdapter() {
			public void
			windowActivated(WindowEvent e) { btnClose.requestFocusInWindow(); }
		});
	}
	
	/**
	 * Creates a modal dialog without a title and with the specified
	 * <code>Dialog</code> as its owner.
	 * @param owner Specifies the <code>Dialog</code> from which this dialog is displayed.
	 */
	public
	InformationDialog(Dialog owner) { this(owner, new Container()); }
	
	/**
	 * Creates a modal dialog with the specified title and owner <code>Dialog</code>.
	 * @param owner Specifies the <code>Dialog</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 */
	public
	InformationDialog(Dialog owner, String title) { this(owner, title, new Container()); }
	
	/**
	 * Creates a modal dialog without a title and with the specified owner and main pane.
	 * @param owner Specifies the <code>Dialog</code> from which this dialog is displayed.
	 * @param mainPane A non-null <code>Container</code> instance that will be used
	 * as a main pane for this dialog.
	 * @throws IllegalArgumentException If <code>mainPane</code> is <code>null</code>.
	 */
	public
	InformationDialog(Dialog owner, Container mainPane) { this(owner, "", mainPane); }
	
	/**
	 * Creates a modal dialog with the specified title, owner and main pane.
	 * @param owner Specifies the <code>Dialog</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 * @param mainPane A non-null <code>Container</code> instance that will be used
	 * as a main pane for this dialog.
	 * @throws IllegalArgumentException If <code>mainPane</code> is <code>null</code>.
	 */
	public
	InformationDialog(Dialog owner, String title, Container mainPane) {
		this(owner, title, mainPane, true);
	}
	
	/**
	 * Creates a modal or non-modal dialog with the specified title, owner and main pane.
	 * @param owner Specifies the <code>Dialog</code> from which this dialog is displayed.
	 * @param title The text to be displayed in the dialog's title bar.
	 * @param mainPane A non-null <code>Container</code> instance that will be used
	 * as a main pane for this dialog.
	 * @param modal Specifies whether this dialog should be modal or not.
	 * @throws IllegalArgumentException If <code>mainPane</code> is <code>null</code>.
	 */
	public
	InformationDialog(Dialog owner, String title, Container mainPane, boolean modal) {
		super(owner, title, modal);
		
		initInformationDialog();
		setMainPane(mainPane);
	}
	
	/** Used for initial initialization of the dialog */
	private void
	initInformationDialog() {
		pane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		
		add(pane, BorderLayout.CENTER);
		
		btnPane.add(btnClose);
		
		btnPane.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		
		pane.add(btnPane, BorderLayout.SOUTH);
		
		pack();
		
		setLocation(JuifeUtils.centerLocation(this, getOwner()));
		
		btnClose.addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) { onCancel(); }
		});
	}
	
	/**
	 * Sets the main pane of this dialog and centers the dialog relatvely to its owner.
	 * @param mainPane A non-null <code>Container</code> instance that will be used
	 * as a main pane for this dialog.
	 * @throws IllegalArgumentException If <code>mainPane</code> is <code>null</code>.
	 */
	public void
	setMainPane(Container mainPane) {
		if(mainPane == null)
			throw new IllegalArgumentException("mainPane must be non null");
		
		pane.add(mainPane, BorderLayout.CENTER);
		pack();
		setLocation(JuifeUtils.centerLocation(this, getOwner()));
	}
	
	/**
	 * Sets whether the 'Close' button should be displayed.
	 * @param show If <code>true</code> the 'Close' button is displayed else
	 * the dialog is displayed without 'Close' button.
	 */
	public void
	showCloseButton(boolean show) { btnPane.setVisible(show); }
	
	protected void
	onOk() { }
	
	protected void
	onCancel() { setVisible(false); }
}
