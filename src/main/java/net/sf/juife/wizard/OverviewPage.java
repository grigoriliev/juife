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
 * Provides default implementation of typical overview page.
 * @author  Grigor Iliev
 */
public class OverviewPage extends WizardPage {
	JEditorPane epOverview = new JEditorPane();
	
	/**
	 * Creates a new <code>OverviewPage</code> with default subtitle and description.
	 * Default subtitle - 'Overview'. Default description - 'Overview.'.
	 */
	public
	OverviewPage() { this(i18n.getLabel("OverviewPage.subtitle")); }
	
	/**
	 * Creates a <code>OverviewPage</code> with the specified subtitle and
	 * default description. The default description is 'Overview.'.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 */
	public
	OverviewPage(String subtitle) {
		this(subtitle, i18n.getLabel("OverviewPage.description"));
	}
	
	/**
	 * Creates a <code>OverviewPage</code> with the specified subtitle and description.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 */
	public
	OverviewPage(String subtitle, String description) {
		this(subtitle, description, OptionalButtons.NONE);
	}
	
	/**
	 * Creates a <code>OverviewPage</code> with the
	 * specified subtitle, description and optional buttons.
	 * @param subtitle The subtitle text to be shown
	 * in the wizard when this page becomes current.
	 * @param description A brief description of the page.
	 * @param optionalButtons Specifies the optional buttons
	 * to be shown in the wizard when this wizard page becomes current.
	 */
	public
	OverviewPage(String subtitle, String description, OptionalButtons optionalButtons) {
		super(subtitle, description, Type.OVERVIEW_PAGE, optionalButtons);
		
		initOverviewPage();
	}
	
	private void
	initOverviewPage() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		epOverview.setEditable(false);
		epOverview.setOpaque(false);
		epOverview.setAlignmentX(LEFT_ALIGNMENT);
		epOverview.setContentType("text/html");
		add(epOverview);
		
	}
	
	/**
	 * Sets the text for the Overview Page. The default content type is <code>text/plain</code>.
	 */
	public void
	setOverviewText(String text) {
		setOverviewText(text, "text/plain");
	}
	
	/**
	 * Sets the text for the Overview Page with the specified content type.
	 * By default the following types of content are known:
	 * <ul>
	 *  <li>text/plain
	 *  <li>text/html
	 *  <li>text/rtf
	 * @param text the overview text.
	 * @param contentType the mime type for the content. Must be non-<code>null</code>.
	 * @throws NullPointerException if the <code>contentType</code> prameter is 
	 * <code>null<code>.
	 */
	public void
	setOverviewText(String text, String contentType) {
		epOverview.setContentType(contentType);
		epOverview.setText(text);
		epOverview.setCaretPosition(0);
	}
}
