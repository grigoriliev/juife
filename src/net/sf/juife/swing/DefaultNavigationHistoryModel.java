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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;


/**
 * Provides default implementation of the <code>NavigationHistoryModel</code> interface.
 * <b>Note</b> that this implementation of {@link NavigationHistoryModel} is not synchronized.
 * @author Grigor Iliev
 */
public class DefaultNavigationHistoryModel<P> implements NavigationHistoryModel<P> {
	private final LinkedList<P> histList = new LinkedList<P>();
	private ListIterator<P> listIt = histList.listIterator();
	
	private final Vector<ActionListener> listeners = new Vector<ActionListener>();
	
	
	/**
	 * Creates a new instance of <code>DefaultNavigationHistoryModel</code>.
	 */
	public
	DefaultNavigationHistoryModel() {
		
	}
	
	/**
	 * Registers the specified <code>ActionListener</code> to be
	 * notified about changes of the history list.
	 * @param l The <code>ActionListener</code> to register.
	 */
	public void
	addActionListener(ActionListener l) { listeners.add(l); }
	
	/**
	 * Removes the specified listener.
	 * @param l The <code>ActionListener</code> to remove.
	 */
	public void
	removeActionListener(ActionListener l) { listeners.remove(l); }
	
	/** Notifies registered listeners that the history list has changed. */
	protected void
	fireActionPerformed() {
		ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
		for(ActionListener l : listeners) if(l != null) l.actionPerformed(e);
	}
	
	/**
	 * Adds the specified page to the history list after the current page.
	 * All pages after the the current page are removed from the history list and
	 * the added page becomes current. Note that this method does nothing if the
	 * current page is <code>null</code> or is equal to the page to be added.
	 * @param page The page to be added to the history list.
	 */
	public void
	addPage(P page) {
		if(page == null) return;
		if(page.equals(getCurrentPage())) return;
		
		while(listIt.hasNext()) {
			listIt.next();
			listIt.remove();
		}
		
		listIt.add(page);
		fireActionPerformed();
	}
	
	/**
	 * Goes to the previous page in the history list.
	 * This means that the previous page becomes the current page of the history list.
	 * @return The new current page or <code>null</code> if
	 * there is no previous page in the history list.
	 * @see #hasBack
	 */
	public P
	goBack() {
		if(!hasBack()) return null;
		
		listIt.previous();
		fireActionPerformed();
		return getCurrentPage();
	}
	
	/**
	 * Determines whether there is at least one
	 * page before the current page in the history list.
	 * @return <code>true</code> if there is at least one page before
	 * the current page in the history list, <code>false</code> otherwise.
	 */
	public boolean
	hasBack() { return listIt.previousIndex() > 0; }
	
	
	/**
	 * Goes to the next page in the history list.
	 * This means that the next page becomes the current page of the history list.
	 * @return The new current page or <code>null</code> if
	 * there is no next page in the history list.
	 * @see #hasForward
	 */
	public P
	goForward() {
		if(!listIt.hasNext()) return null;
		
		listIt.next();
		fireActionPerformed();
		return getCurrentPage();
	}
	
	/**
	 * Determines whether there is at least one
	 * page after the current page in the history list.
	 * @return <code>true</code> if there is at least one page after
	 * the current page in the history list, <code>false</code> otherwise.
	 */
	public boolean
	hasForward() { return listIt.hasNext(); }
	
	/**
	 * Goes to the first page in the history list.
	 * This means that the first page becomes the current page of the history list.
	 */
	public void
	goFirst() {
		// We don't want to notify about changes if the history list is not modified.
		if(listIt.previousIndex() < 1) return;
		
		while(listIt.previousIndex() > 0) listIt.previous();
		fireActionPerformed();
	}
	
	/**
	 * Goes to the last page in the history list.
	 * This means that the last page becomes the current page of the history list.
	 */
	public void
	goLast() {
		// We don't want to notify about changes if the history list is not modified.
		if(!listIt.hasNext()) return;
		
		while(listIt.hasNext()) listIt.next();
		fireActionPerformed();
	}
	
	/**
	 * Gets the current page in the history list.
	 * @return The current page in the history list or
	 * <code>null</code> if the history list is empty.
	 */
	public P
	getCurrentPage() {
		int idx = listIt.previousIndex();
		return (idx == -1) ? null : histList.get(idx);
	}
	
	/**
	 * Gets the current number of pages in the history list.
	 * @return The current number of pages in the history list.
	 */
	public int
	getPageCount() { return histList.size(); }
	
	/** Removes all pages from history list except the current page. */
	public void
	clearHistory() {
		P p = getCurrentPage();
		if(p == null) return;
		
		histList.clear();
		listIt = histList.listIterator();
		listIt.add(p);
		fireActionPerformed();
	}
	
	/**
	 * Removes from the model the last page that was returned by
	 * <code>goBack</code> or <code>goForward</code>.
	 * @see java.util.ListIterator#remove
	 */
	protected void
	removePage() { listIt.remove(); }
}
