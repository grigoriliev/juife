/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2005-2008 Grigor Iliev <grigor@grigoriliev.com>
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

import java.awt.Component;

import java.util.Vector;

import javax.swing.AbstractListModel;

/**
 * This class provides default implementation of the <code>ComponentListModel</code> interface.
 * @author Grigor Iliev
 */
public class DefaultComponentListModel<C extends Component>
			extends AbstractListModel implements ComponentListModel<C> {
	
	private final Vector<C> list = new Vector<C>();
	private boolean componentListIsAdjusting = false;
	
	/** Creates a new instance of DefaultComponentListModel */
	public DefaultComponentListModel() {
	}
	
	/**
	 * Gets the component at the specified index.
	 * @param index The requested index.
	 * @return The component at the specified index.
	 * @throws ArrayIndexOutOfBoundsException If the index is out of range.
	 */
	@Override
	public C
	get(int index) { return list.get(index); }
	
	/**
	 * Gets the value at the specified index.
	 * <blockquote>
	 * <b>Note:</b> The preferred method to use is {@link #get}.
	 * </blockquote>
	 * @param index The requested index.
	 * @return The value at the specified index.
	 */
	@Override
	public Object
	getElementAt(int index) { return list.get(index); }
	
	/**
	 * Inserts the specified component at the specified index.
	 * @param c The component to be inserted.
	 * @param index The position of the new component.
	 * @throws ArrayIndexOutOfBoundsException  If the index is invalid.
	 */
	@Override
	public void
	insert(C c, int index) {
		list.insertElementAt(c, index);
		fireIntervalAdded(this, index, index);
	}
	
	/**
	 * Adds the specified component at the end of the list.
	 * @param c The component to be added.
	 */
	@Override
	public void
	add(C c) {
		int idx = list.size();
		list.add(c);
		fireIntervalAdded(this, idx, idx);
	}
	
	/**
	 * Replaces the component at the specified position with the specified component.
	 * @param index The index of the component to replace.
	 * @param c The component to be stored at the specified position.
	 * @return The previous component at the specified position.
	 */
	@Override
	public C
	set(int index, C c) {
		c = list.set(index, c);
		fireContentsChanged(this, index, index);
		
		return c;
	}
	
	/**
	 * Moves the specified component one position up in the list.
	 * @param c The component to be moved up.
	 */
	public void
	moveUp(C c) {
		int idx = indexOf(c);
		if(idx < 1) return;
		
		C c2 = get(idx - 1);
			
		list.set(idx, c2);
		list.set(idx - 1, c);
		
		fireContentsChanged(this, idx - 1, idx);
	}
	
	/**
	 * Moves the specified component one position down in the list.
	 * @param c The component to be moved down.
	 */
	public void
	moveDown(C c) {
		int idx = indexOf(c);
		if(idx == -1 || idx >= size() - 1) return;
		
		C c2 = get(idx + 1);
			
		list.set(idx, c2);
		list.set(idx + 1, c);
		
		fireContentsChanged(this, idx, idx + 1);
	}
	
	/**
	 * Removes the specified component.
	 * @param c The component to be removed.
	 * @return <code>true</code> if the list contained the specified component,
	 * <code>false</code> otherwise.
	 */
	@Override
	public boolean
	remove(C c) {
		int idx = list.indexOf(c);
		boolean b = list.remove(c);
		
		if(idx >= 0) fireIntervalRemoved(this, idx, idx);
		
		return b;
	}
	
	/**
	 * Removes the component at the specified position.
	 * @param index The index of the component to be removed.
	 * @return The removed component.
	 * @throws ArrayIndexOutOfBoundsException If the index is out of range.
	 */
	@Override
	public C
	remove(int index) {
		C c = list.remove(index);
		fireIntervalRemoved(this, index, index);
		
		return c;
	}
	
	/**
	 * Determines whether there are known upcoming changes to the 
	 * component list, which should be considered as part of a single action.
	 */
	@Override
	public boolean
	getComponentListIsAdjusting() {
		return componentListIsAdjusting;
	}
	
	/**
	 * Sets whether there are upcoming changes to the 
	 * component list which should be considered part of a single action.
	 */
	@Override
	public void
	setComponentListIsAdjusting(boolean b) {
		componentListIsAdjusting = b;
	}
	
	/**
	 * Gets the index of the first occurrence of the given argument, testing
	 * for equality using the <code>equals</code> method.
	 * @param o An object.
	 * @return The index of the first occurrence of the given argument or -1
	 * if the object is not found.
	 */
	public int
	indexOf(Object o) { return list.indexOf(o); }
	
	/**
	 * Gets the length of the list.
	 * @return The length of the list.
	 */
	@Override
	public int
	getSize() { return list.size(); }
	
	/**
	 * Gets the length of the list.
	 * @return The length of the list.
	 */
	@Override
	public int
	size() { return list.size(); }
}
