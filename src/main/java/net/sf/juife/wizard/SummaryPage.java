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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;

import static net.sf.juife.JuifeI18n.i18n;


/**
 * Provides default implementation of typical summary page.
 * @author  Grigor Iliev
 */
public class SummaryPage extends WizardPage {
	JEditorPane epSummary = new JEditorPane();
	
	/**
	 * Creates a new <code>SummaryPage</code> with default subtitle and description.
	 * Default subtitle - 'Summary'. Default description - 'Summary.'.
	 */
	public
	SummaryPage() { this(i18n.getLabel("SummaryPage.subtitle")); }
	
	/**
	 * Creates a <code>SummaryPage</code> with the specified subtitle and
	 * default description. The default description is 'Summary.'.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 */
	public
	SummaryPage(String subtitle) {
		this(subtitle, i18n.getLabel("SummaryPage.description"));
	}
	
	/**
	 * Creates a <code>SummaryPage</code> with the specified subtitle and description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 */
	public
	SummaryPage(String subtitle, String description) {
		super(subtitle, description, Type.SUMMARY_PAGE, OptionalButtons.NONE);
		
		initSummaryPage();
	}
	
	private void
	initSummaryPage() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		epSummary.setEditable(false);
		epSummary.setOpaque(false);
		epSummary.setAlignmentX(LEFT_ALIGNMENT);
		epSummary.setContentType("text/html");
		add(epSummary);
		
	}
	
	/**
	 * Sets the text for the Overview Page. The default content type is <code>text/html</code>.
	 */
	public void
	setSummaryText(String text) {
		setSummaryText(text, "text/html");
	}
	
	/**
	 * Sets the text for the Summary Page with the specified content type.
	 * By default the following types of content are known:
	 * <ul>
	 *  <li>text/plain
	 *  <li>text/html
	 *  <li>text/rtf
	 * @param text the summary text.
	 * @param contentType the mime type for the content. Must be non-<code>null</code>.
	 * @throws NullPointerException if the <code>contentType</code> prameter is 
	 * <code>null<code>.
	 */
	public void
	setSummaryText(String text, String contentType) {
		epSummary.setContentType(contentType);
		epSummary.setText(text);
		epSummary.setCaretPosition(0);
	}
}
