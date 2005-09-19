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

package net.sf.juife.wizard;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



/**
 * Provides default implementation of typical user-input page.
 * If You want to customize your User-Input pages override <code>WizardPage</code> and set 
 * the appropriate page type using {@link WizardPage#setPageType} method.
 *
 * @author  Grigor Iliev
 */
public class UserInputPage extends WizardPage {
	private JPanel mainInstructionsPane = null;
	private JLabel lMainInstructions = null;
	
	private JPanel mainPane;
	
	private JPanel additionalInstructionsPane = new JPanel();
	private JLabel lAdditionalInstructions = new JLabel();
	
	private JPanel navigationInstructionsPane = new JPanel();
	private JLabel lNavigationInstructions = new JLabel();
	
	
	/**
	 * Creates a new <code>UserInputPage</code> with empty subtitle and description.
	 */
	public
	UserInputPage() { this(null); }
	
	/**
	 * Creates a <code>UserInputPage</code> with
	 * the specified subtitle and empty description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 */
	public
	UserInputPage(String subtitle) { this(subtitle, null); }
	
	/**
	 * Creates a <code>UserInputPage</code> with the specified subtitle and description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 */
	public
	UserInputPage(String subtitle, String description) {
		this(subtitle, description, OptionalButtons.NONE);
	}
	
	/**
	 * Creates a <code>UserInputPage</code> with the
	 * specified subtitle, description and optional buttons.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 * @param optionalButtons Specifies the optional buttons
	 * to be shown in the wizard when this wizard page becomes current.
	 */
	public
	UserInputPage(String subtitle, String description, OptionalButtons optionalButtons) {
		this(subtitle, description, optionalButtons, new JPanel());
	}
	
	/**
	 * Creates a <code>UserInputPage</code> with the specified subtitle, description,
	 * optional button(s) and main pane. In this case the main pane must be 
	 * the User-Input pane.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 * @param optionalButtons Specifies the optional buttons
	 * to be shown in the wizard when this wizard page becomes current.
	 * @param mainPane The main pane of this User-Input page.
	 */
	public
	UserInputPage (
		String subtitle,
		String description,
		OptionalButtons optionalButtons,
		JPanel mainPane
	) {
		super(subtitle, description, Type.USER_INPUT_PAGE, optionalButtons);
		
		this.mainPane = mainPane;
		initUserInputPage();
	}
	
	private void
	initUserInputPage() {
		initInstructionPanes();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		mainInstructionsPane.setAlignmentX(LEFT_ALIGNMENT);
		add(mainInstructionsPane);
		
		add(Box.createRigidArea(new Dimension(0, 12)));
		
		mainPane.setAlignmentX(LEFT_ALIGNMENT);
		add(mainPane);
		
		add(Box.createRigidArea(new Dimension(0, 12)));
		
		additionalInstructionsPane.setAlignmentX(LEFT_ALIGNMENT);
		add(additionalInstructionsPane);
		
		add(Box.createGlue());
		
		navigationInstructionsPane.setAlignmentX(LEFT_ALIGNMENT);
		add(navigationInstructionsPane);
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
		mainInstructionsPane.add(Box.createRigidArea(new Dimension(0, 12)));
		
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
		
		
		lNavigationInstructions.setAlignmentX(LEFT_ALIGNMENT);
		lNavigationInstructions.setFont (
			lNavigationInstructions.getFont().deriveFont(Font.PLAIN)
		);
		lNavigationInstructions.setOpaque(false);
		
		navigationInstructionsPane.setLayout (
			new BoxLayout(navigationInstructionsPane, BoxLayout.Y_AXIS)
		);
		navigationInstructionsPane.add(lNavigationInstructions);
		
		navigationInstructionsPane.setVisible(false);
	}
	
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
	
	/**
	 * Sets the navigation instructions for this page.
	 * @param text the text with the navigation insturctions.
	 */
	public void
	setNavigationInstructions(String text) {
		if(text == null || text.length() == 0) {
			navigationInstructionsPane.setVisible(false);
			lNavigationInstructions.setText("");
			return;
		}
		
		navigationInstructionsPane.setVisible(true);
		lNavigationInstructions.setText(text);
	}
	
	/**
	 * Sets the main pane for this page.
	 * @param pane the main pane for this page.
	 */
	public void
	setMainPane(JPanel pane) {
		remove(mainPane);
		mainPane = pane;
		mainPane.setAlignmentX(LEFT_ALIGNMENT);
		add(mainPane, 2);
	}
	
	/**
	 * Sets main instructions for this page with default content type - plain text.
	 * @param text the text with the main insturctions.
	 *
	public void
	setAdditionalInstructions(String text) {
		setAdditionalInstructions(text, "text/plain");
	}
	
	/**
	 * Sets the additional instructions for this page with the specified content type.
	 * By default the following types of content are known:
	 * <ul>
	 *  <li>text/plain
	 *  <li>text/html
	 *  <li>text/rtf
	 * @param text the text with the additional insturctions.
	 * @param contentType the mime type for the content. Must be non-<code>null</code>.
	 * @throws NullPointerException if the <code>contentType</code> prameter is 
	 * <code>null<code>.
	 *
	public void
	setAdditionalInstructions(String text, String contentType) {
		epAdditionalInstructions.setContentType(contentType);
		epAdditionalInstructions.setText(text);
	}*/
}
