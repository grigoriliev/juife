/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2005-2011 Grigor Iliev <grigor@grigoriliev.com>
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

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.UIManager;

import net.sf.juife.swing.plaf.NavigationPaneUI;


/**
 * A component that allows the user to navigate through set of pages.
 * @author Grigor Iliev
 */
public class NavigationPane extends JPanel {
	static {
		// TODO: In future this must be done the right way
		UIManager.put("NavigationPaneUI", "net.sf.juife.swing.plaf.basic.BasicNavigationPaneUI");
	}
	private static final String uiClassID = "NavigationPaneUI";
	
	private NavigationHistoryModel model;
	private NavigationPage[] pages = new NavigationPage[0];
	
	private Color titleBackground;
	
	/**
	 * Creates a new instance of <code>NavigationPane</code>.
	 */
	public
	NavigationPane() { this(new DefaultNavigationHistoryModel<NavigationPage>()); }
	
	/**
	 * Creates a new instance of <code>NavigationPane</code>
	 * using the specified non-<code>null</code> model.
	 * @param model The navigation history model to be used by this
	 * <code>NavigationPane</code>.
	 * @throws IllegalArgumentException If <code>model</code> is <code>null</code>.
	 */
	public
	NavigationPane(NavigationHistoryModel model) {
		setModel(model);
	}
	
	/**
	 * Gets a string that specifies the name
	 * of the L&F class that renders this component.
	 * @return the string "NavigationPaneUI"
	 */
	public String
	getUIClassID() { return uiClassID; }
	
	/**
	 * Gets the L&F object that renders this component.
	 * @return The L&F object that renders this component.
	 */
	public NavigationPaneUI
	getUI() { return (NavigationPaneUI)ui; }
	
	/**
	 * Sets the L&F object that renders this component.
	 * @param ui The new UI delegate.
	 */
	public void
	setUI(NavigationPaneUI ui) { super.setUI(ui); }
	
	/**
	 * Resets the UI property to a value from the current look and feel.
	 */
	public void
	updateUI() { setUI((NavigationPaneUI)UIManager.getUI(this)); }
	
	/**
	 * Sets the pages that this <code>NavigationPane</code> will offer.
	 * @param pages The list of pages this <code>NavigationPane</code> will offer.
	 * @throws IllegalArgumentException If <code>pages</code> is <code>null</code>.
	 */
	public void
	setPages(NavigationPage[] pages) {
		if(pages == null) throw new IllegalArgumentException("pages must be non null");
		NavigationPage[] oldPages = this.pages;
		this.pages = pages;
		
		firePropertyChange("pages", oldPages, this.pages);
	}
	
	/**
	 * Gets the list of pages this <code>NavigationPane</code> offers.
	 * @return A list of <code>NavigationPage</code>s this <code>NavigationPane</code> offers.
	 */
	public NavigationPage[]
	getPages() { return pages; }
	
	/**
	 * Gets the navigation history model of this pane.
	 * @return The navigation history model of this pane.
	 */
	public NavigationHistoryModel<NavigationPage>
	getModel() { return model; }
	
	/**
	 * Sets the navigation history model of this pane.
	 * @param model A non-<code>null NavigationHistoryModel</code> instance.
	 * @throws IllegalArgumentException If <code>model</code> is <code>null</code>.
	 */
	public void
	setModel(NavigationHistoryModel model) {
		if(model == null) throw new IllegalArgumentException("model must be non null");
		
		NavigationHistoryModel oldModel = this.model;
		this.model = model;
		firePropertyChange("model", oldModel, this.model);
	}
	
	/**
	 * Gets the current page in this navigation pane.
	 * @return The current page in this navigation pane or
	 * <code>null</code> if the history list is empty.
	 */
	public NavigationPage
	getCurrentPage() { return getModel().getCurrentPage(); }
	
	/**
	 * Gets the title's background color of this <code>NavigationPane</code>.
	 * @return The title's background color of this <code>NavigationPane</code>.
	 */
	public Color
	getTitleBackground() { return titleBackground; }
	
	/**
	 * Sets the title's background color of this <code>NavigationPane</code>.
	 * @param c The new title's background color of this <code>NavigationPane</code>.
	 */
	public void
	setTitleBackground(Color c) {
		Color oldColor = titleBackground;
		titleBackground = c;
		firePropertyChange("titleBackground", oldColor, titleBackground);
	}
	
	/**
	 * Determines whether the titlebar should be visible.
	 */
	public void
	setTitlebarVisiblie(boolean b) {
		getUI().setTitlebarVisiblie(b);
	}
}
