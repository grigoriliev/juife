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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import static net.sf.juife.JuifeI18n.i18n;



/**
 * Provides default implementation of typical conformation page.
 * @author  Grigor Iliev
 */
public class ConfirmationPage extends WizardPage {
	JEditorPane epConfig = new JEditorPane();
	
	/**
	 * Creates a new <code>ConfirmationPage</code> with default subtitle and description.
	 * Default subtitle - 'Confirmation'. Default description - 'Confirm choices.'.
	 */
	public
	ConfirmationPage() { this(i18n.getLabel("ConfirmationPage.subtitle")); }
	
	/**
	 * Creates a <code>ConfirmationPage</code> with the specified subtitle and
	 * default description. The default description is 'Confirm choices.'.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 */
	public
	ConfirmationPage(String subtitle) {
		this(subtitle, i18n.getLabel("ConfirmationPage.description"));
	}
	
	/**
	 * Creates a <code>ConfirmationPage</code> with the specified subtitle and description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 */
	public
	ConfirmationPage(String subtitle, String description) {
		this(subtitle, description, OptionalButtons.NONE);
	}
	
	/**
	 * Creates a <code>ConfirmationPage</code> with the
	 * specified subtitle, description and optional buttons.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 * @param optionalButtons Specifies the optional buttons
	 * to be shown in the wizard when this wizard page becomes current.
	 */
	public
	ConfirmationPage(String subtitle, String description, OptionalButtons optionalButtons) {
		super(subtitle, description, Type.CONFIRMATION_PAGE_EX, optionalButtons);
		
		initConfirmationPage();
	}
	
	private void
	initConfirmationPage() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(Box.createRigidArea(new java.awt.Dimension(0, 11)));
		
		epConfig.setEditable(false);
		epConfig.setOpaque(false);
		epConfig.setAlignmentX(LEFT_ALIGNMENT);
		
		JScrollPane sp = new JScrollPane(epConfig);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.setOpaque(false);
		sp.getViewport().setOpaque(false);
		sp.setAlignmentX(LEFT_ALIGNMENT);
		add(sp);
		
		add(Box.createGlue());
		
		//setBorder(BorderFactory.createEmptyBorder(11, 11, 11, 11));
	}
	
	/**
	 * Sets the text for the Confirmation Page. 
	 * The default content type is <code>text/html</code>.
	 */
	public void
	setConfirmationText(String text) {
		setConfirmationText(text, "text/html");
	}
	
	/**
	 * Sets the text for the Confirmation Page with the specified content type.
	 * By default the following types of content are known:
	 * <ul>
	 *  <li>text/plain
	 *  <li>text/html
	 *  <li>text/rtf
	 * @param text the confirmation text.
	 * @param contentType the mime type for the content. Must be non-<code>null</code>.
	 * @throws NullPointerException if the <code>contentType</code> prameter is 
	 * <code>null<code>.
	 */
	public void
	setConfirmationText(String text, String contentType) {
		epConfig.setContentType(contentType);
		epConfig.setText(text);
		epConfig.setCaretPosition(0);
	}
}
