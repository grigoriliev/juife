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

package net.sf.juife;

import java.awt.event.ActionListener;


/**
 * This interface defines the data model for the
 * navigation history list in <code>NavigationPane</code>.
 *
 * <p>The change of the current page can be handled by registering
 * an <code>ActionListener</code> using the {@link #addActionListener} method.</p>
 *
 * @author Grigor Iliev
 */
public interface NavigationHistoryModel {
	/**
	 * Registers the specified <code>ActionListener</code> to be
	 * notified about changes of the history list.
	 * @param l The <code>ActionListener</code> to register.
	 */
	public void addActionListener(ActionListener l);
	
	/**
	 * Removes the specified listener.
	 * @param l The <code>ActionListener</code> to remove.
	 */
	public void
	removeActionListener(ActionListener l);
	
	/**
	 * Adds the specified page to the history list after the current page.
	 * All pages after the the current page are removed from the history list and
	 * the added page becomes current.
	 * @param page The page to be added to the history list.
	 */
	public void addPage(NavigationPage page);
	
	/**
	 * Goes to the previous page in the history list.
	 * This means that the previous page becomes the current page of the history list.
	 * @return The new current page or <code>null</code> if
	 * there is no previous page in the history list.
	 * @see #hasBack
	 */
	public NavigationPage goBack();
	
	/**
	 * Determines whether there is at least one
	 * page before the current page in the history list.
	 * @return <code>true</code> if there is at least one page before
	 * the current page in the history list, <code>false</code> otherwise.
	 */
	public boolean hasBack();
	
	
	/**
	 * Goes to the next page in the history list.
	 * This means that the next page becomes the current page of the history list.
	 * @return The new current page or <code>null</code> if
	 * there is no next page in the history list.
	 * @see #hasForward
	 */
	public NavigationPage goForward();
	
	/**
	 * Determines whether there is at least one
	 * page after the current page in the history list.
	 * @return <code>true</code> if there is at least one page after
	 * the current page in the history list, <code>false</code> otherwise.
	 */
	public boolean hasForward();
	
	/**
	 * Goes to the first page in the history list.
	 * This means that the first page becomes the current page of the history list.
	 */
	public void goFirst();
	
	/**
	 * Goes to the last page in the history list.
	 * This means that the last page becomes the current page of the history list.
	 */
	public void goLast();
	
	/**
	 * Gets the current page in the history list.
	 * @return The current page in the history list or
	 * <code>null</code> if the history list is empty.
	 */
	public NavigationPage getCurrentPage();
	
	/**
	 * Gets the current number of pages in the history list.
	 * @return The current number of pages in the history list.
	 */
	public int getPageCount();
	
	/** Removes all pages from history list except the current page. */
	public void clearHistory();
}
