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

import javax.swing.ListModel;

import java.awt.Component;


/**
 * This interface defines the data model for <code>ComponentList</code>.
 * @author Grigor Iliev
 */
public interface ComponentListModel<C extends Component> extends ListModel {
	/**
	 * Adds the specified component at the end of the list.
	 * @param c The component to be added.
	 */
	public void add(C c);
	
	/**
	 * Inserts the specified component at the specified index.
	 * @param c The component to be inserted.
	 * @param index The position of the new component.
	 * @throws ArrayIndexOutOfBoundsException  If the index is invalid.
	 */
	public void insert(C c, int index);
	
	/**
	 * Removes the specified component.
	 * @param c The component to be removed.
	 * @return <code>true</code> if the list contained the specified component,
	 * <code>false</code> otherwise.
	 */
	public boolean remove(C c);
	
	/**
	 * Removes the component at the specified position.
	 * @param index The index of the component to be removed.
	 * @return The removed component.
	 * @throws ArrayIndexOutOfBoundsException If the index is out of range.
	 */
	public C remove(int index);
	
	/**
	 * Determines whether there are known upcoming changes to the 
	 * component list, which should be considered as part of a single action.
	 */
	public boolean getComponentListIsAdjusting();
	
	/**
	 * Sets whether there are upcoming changes to the 
	 * component list which should be considered part of a single action.
	 */
	public void setComponentListIsAdjusting(boolean b);
	
	/**
	 * Gets the component at the specified index.
	 * @param index The requested index.
	 * @return The component at the specified index.
	 */
	public C get(int index);
	
	/**
	 * Replaces the component at the specified position with the specified component.
	 * @param index The index of the component to replace.
	 * @param c The component to be stored at the specified position.
	 * @return The previous component at the specified position.
	 */
	public C set(int index, C c);
	
	/**
	 * Moves the specified component one position up in the list.
	 * @param c The component to be moved up.
	 */
	public void moveUp(C c);
	
	/**
	 * Moves the specified component one position down in the list.
	 * @param c The component to be moved down.
	 */
	public void moveDown(C c);
	
	/**
	 * Gets the length of the list.
	 * @return The length of the list.
	 */
	public int size();
}
