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

import javax.swing.JPanel;


/**
 * This class represents a page that can be displayed in <code>NavigationPane</code>.
 * @author Grigor Iliev
 */
public class NavigationPage extends JPanel {
	private String title;
	
	/** Creates a new instance of <code>NavigationPage</code> with empty title */
	public
	NavigationPage() { this(""); }
	
	/**
	 * Creates a new instance of <code>NavigationPage</code> with the specified title.
	 * @param title	The title text.
	 */
	public
	NavigationPage(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the title of this navigation page.
	 * @return The title of this navigation page.
	 */
	public String
	getTitle() { return title; }
	
	/**
	 * Sets the title of this navigation page.
	 * @param title Specifies the title text of this navigation page.
	 */
	public void
	setTitle(String title) { this.title = title; }
}
