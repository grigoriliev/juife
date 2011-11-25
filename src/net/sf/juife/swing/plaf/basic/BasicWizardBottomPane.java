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

package net.sf.juife.swing.plaf.basic;

import java.awt.Component;
import java.awt.Dimension;

import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.sf.juife.swing.JuifeUtils;

import static net.sf.juife.JuifeI18n.i18n;


/**
 * Basic L&F implementation of the wizard's bottom pane.
 * @author Grigor Iliev
 */
public class BasicWizardBottomPane extends JPanel {
	private final static Icon iconBack;
	private final static Icon iconForward;
	
	static {
		String s = "net/sf/juife/icons/navigation/Back16.gif";
		URL url = ClassLoader.getSystemClassLoader().getResource(s);
		iconBack = new ImageIcon(url);
		
		s = "net/sf/juife/icons/navigation/Forward16.gif";
		url = ClassLoader.getSystemClassLoader().getResource(s);
		iconForward = new ImageIcon(url);
	}
	
	private final JButton btnBack = new JButton(i18n.getButtonLabel("back"));
	private JButton btnNext;
	private final JButton btnLast = new JButton(i18n.getButtonLabel("last"));
	private final JButton btnFinish = new JButton(i18n.getButtonLabel("finish"));
	private final JButton btnCancel = new JButton(i18n.getButtonLabel("cancel"));
	private final JButton btnHelp = new JButton(i18n.getButtonLabel("help"));
	private final JButton btnClose = new JButton(i18n.getButtonLabel("close"));
	
	
	/** Creates new instance of <code>BasicWizardBottomPane</code>. */
	BasicWizardBottomPane() {
		initBottomPane();
	}
	
	private void
	initBottomPane() {
		btnBack.setIcon(iconBack);
		
		final Component ra = Box.createRigidArea(new Dimension(5, 0));
		
		// TODO: Find another, more clear way to do this.
		btnNext = new JButton(i18n.getButtonLabel("next")) {
			public void
			setVisible(boolean b) {
				super.setVisible(b);
				ra.setVisible(b);
			}
		};
		
		btnNext.setIcon(iconForward);
		btnNext.setHorizontalTextPosition(JButton.LEFT);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Dimension d = JuifeUtils.getUnionSize(btnBack, btnNext);
		d = JuifeUtils.getUnionSize(JuifeUtils.getUnionSize(btnLast, btnFinish), d);
		d = JuifeUtils.getUnionSize(JuifeUtils.getUnionSize(btnCancel, btnHelp), d);
		
		btnBack.setPreferredSize(d);
		btnNext.setPreferredSize(d);
		btnLast.setPreferredSize(d);
		btnFinish.setPreferredSize(d);
		btnCancel.setPreferredSize(d);
		btnHelp.setPreferredSize(d);
				
		add(btnBack);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(btnNext);
		add(ra);
		add(btnLast);
		// There is NO space here because ...
		add(btnFinish);
		
		add(Box.createGlue());
		
		add(btnCancel);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(btnHelp);
		add(btnClose);
		
		setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 11));
	}
	
	/**
	 * Gets the 'Back' button of the wizard's bottom pane.
	 * @return The 'Back' button of the wizard's bottom pane.
	 */
	public JButton
	backButton() { return btnBack; }
	
	/**
	 * Gets the 'Next' button of the wizard's bottom pane.
	 * @return The 'Next' button of the wizard's bottom pane.
	 */
	public JButton
	nextButton() { return btnNext; }
	
	/**
	 * Gets the 'Last' button of the wizard's bottom pane.
	 * @return The 'Last' button of the wizard's bottom pane.
	 */
	public JButton
	lastButton() { return btnLast; }
	
	/**
	 * Gets the 'Finish' button of the wizard's bottom pane.
	 * @return The 'Finish' button of the wizard's bottom pane.
	 */
	public JButton
	finishButton() { return btnFinish; }
	
	/**
	 * Gets the 'Cancel' button of the wizard's bottom pane.
	 * @return The 'Cancel' button of the wizard's bottom pane.
	 */
	public JButton
	cancelButton() { return btnCancel; }
	
	/**
	 * Gets the 'Help' button of the wizard's bottom pane.
	 * @return The 'Help' button of the wizard's bottom pane.
	 */
	public JButton
	helpButton() { return btnHelp; }
	
	/**
	 * Gets the 'Close' button of the wizard's bottom pane.
	 * @return The 'Close' button of the wizard's bottom pane.
	 */
	public JButton
	closeButton() { return btnClose; }
}
