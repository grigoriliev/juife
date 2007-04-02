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

package net.sf.juife.plaf.basic;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.sf.juife.wizard.WizardPage;


/**
 *  Basic L&F implementation of the wizard's right pane.
 * @author Grigor Iliev
 */
public class BasicWizardRightPane extends JPanel {
	private final JLabel lSubtitle = new JLabel();
	private WizardPage currentPage = null;
	
	/**
	 * Creates new instance of <code>BasicWizardRightPane</code>.
	 */
	BasicWizardRightPane() { this(""); }
	
	/**
	 * Creates new instance of <code>BasicWizardRightPane</code> with the specified subtitle.
	 * @param subtitle The subtitle of wizard's right pane.
	 */
	BasicWizardRightPane(String subtitle) {
		setSubtitle(subtitle);
		
		
		initRightPane();
	}
	
	private void
	initRightPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		lSubtitle.setFont(lSubtitle.getFont().deriveFont(Font.PLAIN));
		lSubtitle.setAlignmentX(LEFT_ALIGNMENT);
		add(lSubtitle);
		
		JSeparator sep = new JSeparator();
		sep.setAlignmentX(LEFT_ALIGNMENT);
		sep.setMaximumSize (
			new Dimension(sep.getMaximumSize().width, sep.getPreferredSize().height)
		);
		add(sep);
		
		setBorder(BorderFactory.createEmptyBorder(12,12, 12, 11));
	}
	
	/**
	 * Sets the subtitle of wizard's right pane.
	 * @param subtitle The subtitle of wizard's right pane.
	 */
	protected void
	setSubtitle(String subtitle) { lSubtitle.setText(subtitle); }
	
	/**
	 * Sets the current page in the wizard's right pane.
	 * @param page The page to be set as current.
	 */
	protected void
	setWizardPage(WizardPage page) {
		if(currentPage != null) this.remove(currentPage);
		currentPage = page;
		
		currentPage.setAlignmentX(LEFT_ALIGNMENT);
		add(currentPage);
		setSubtitle(currentPage.getSubtitle());
		revalidate();
		repaint();
	}
}
