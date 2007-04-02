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

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import static net.sf.juife.JuifeI18n.i18n;


/**
 * Provides default implementation of typical progress page.
 * @author  Grigor Iliev
 */
public class ProgressPage extends WizardPage {
	private final JPanel progressPane = new JPanel();
	private final JProgressBar progress = new JProgressBar();
	private final JButton btnStop = new JButton(i18n.getButtonLabel("stop"));
	
	private JPanel mainInstructionsPane = null;
	private JLabel lMainInstructions = null;
	
	private JPanel additionalInstructionsPane = new JPanel();
	private JLabel lAdditionalInstructions = new JLabel();
	
	
	/**
	 * Creates a new <code>ProgressPage</code> with empty subtitle and description.
	 */
	public
	ProgressPage() { this(null); }
	
	/**
	 * Creates a <code>ProgressPage</code> with the specified subtitle and empty description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 */
	public
	ProgressPage(String subtitle) { this(subtitle, null); }
	
	/**
	 * Creates a <code>ProgressPage</code> with the specified subtitle and description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 */
	public
	ProgressPage(String subtitle, String description) {
		this(subtitle, description, OptionalButtons.NONE);
	}
	
	/**
	 * Creates a <code>ProgressPage</code> with the
	 * specified subtitle, description and optional buttons.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 * @param optionalButtons Specifies the optional buttons
	 * to be shown in the wizard when this wizard page becomes current.
	 */
	public
	ProgressPage(String subtitle, String description, OptionalButtons optionalButtons) {
		super(subtitle, description, Type.PROGRESS_PAGE, optionalButtons);
		
		initProgressPage();
	}
	
	private void
	initProgressPage() {
		initInstructionPanes();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new Dimension(0, 24)));
		
		mainInstructionsPane.setAlignmentX(LEFT_ALIGNMENT);
		add(mainInstructionsPane);
		
		progressPane.setLayout(new BoxLayout(progressPane, BoxLayout.X_AXIS));
		progressPane.add(progress);
		progressPane.add(Box.createRigidArea(new Dimension(11, 0)));
		progressPane.add(btnStop);
		
		progressPane.setAlignmentX(LEFT_ALIGNMENT);
		add(progressPane);
		
		additionalInstructionsPane.setAlignmentX(LEFT_ALIGNMENT);
		add(additionalInstructionsPane);
		
		add(Box.createGlue());
	}
	
	private void
	initInstructionPanes() {
		mainInstructionsPane = new JPanel();
		mainInstructionsPane.setOpaque(false);
		mainInstructionsPane.setAlignmentX(LEFT_ALIGNMENT);
		mainInstructionsPane.setLayout (
			new BoxLayout(mainInstructionsPane, BoxLayout.Y_AXIS)
		);
		
		lMainInstructions = new JLabel();
		lMainInstructions.setOpaque(false);
		lMainInstructions.setFont(lMainInstructions.getFont().deriveFont(Font.PLAIN));
		lMainInstructions.setAlignmentX(LEFT_ALIGNMENT);
		
		mainInstructionsPane.add(Box.createRigidArea(new Dimension(0, 12)));
		mainInstructionsPane.add(lMainInstructions);
		
		mainInstructionsPane.setVisible(false);
		
		lAdditionalInstructions.setAlignmentX(LEFT_ALIGNMENT);
		lAdditionalInstructions.setFont (
			lAdditionalInstructions.getFont().deriveFont(Font.PLAIN)
		);
		lAdditionalInstructions.setOpaque(false);
		
		additionalInstructionsPane.setLayout (
			new BoxLayout(additionalInstructionsPane, BoxLayout.Y_AXIS)
		);
		additionalInstructionsPane.add(lAdditionalInstructions);
		
		additionalInstructionsPane.setVisible(false);
	}
	
	public JProgressBar
	getProgressBar() { return progress; }
	
	/**
	 * Sets the additional instructions for this page.
	 * @param text the text with the additional insturctions.
	 */
	public void
	setAdditionalInstructions(String text) {
		if(text == null || text.length() == 0) return;
		
		additionalInstructionsPane.setVisible(true);
		lAdditionalInstructions.setText(text);
	}
	
	/**
	 * Sets the main instructions for this page.
	 * @param text the text with the main insturctions.
	 */
	public void
	setMainInstructions(String text) {
		if(text == null || text.length() == 0) return;
		
		lMainInstructions.setText(text);
		mainInstructionsPane.setVisible(true);
		mainInstructionsPane.setMaximumSize (
			new Dimension(30000, mainInstructionsPane.getPreferredSize().height)
		);
	}
}
