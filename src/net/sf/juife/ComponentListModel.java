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

package net.sf.juife;

import javax.swing.ListModel;

import java.awt.Component;


/**
 * This interface defines the data model for <code>ComponentList</code>.
 * @author Grigor Iliev
 */
public interface ComponentListModel extends ListModel {
	/**
	 * Adds the specified component at the end of the list.
	 * @param c The component to be added.
	 */
	public void add(Component c);
	
	/**
	 * Inserts the specified component at the specified index.
	 * @param c The component to be inserted.
	 * @param index The position of the new component.
	 * @throws ArrayIndexOutOfBoundsException  If the index is invalid.
	 */
	public void insert(Component c, int index);
	
	/**
	 * Removes the specified component.
	 * @param c The component to be removed.
	 * @return <code>true</code> if the list contained the specified component,
	 * <code>false</code> otherwise.
	 */
	public boolean remove(Component c);
	
	/**
	 * Removes the component at the specified position.
	 * @param index The index of the component to be removed.
	 * @return The removed component.
	 * @throws ArrayIndexOutOfBoundsException If the index is out of range.
	 */
	public Component remove(int index);
	
	/**
	 * Gets the component at the specified index.
	 * @param index The requested index.
	 * @return The component at the specified index.
	 */
	public Component get(int index);
	
	/**
	 * Replaces the component at the specified position with the specified component.
	 * @param index The index of the component to replace.
	 * @param c The component to be stored at the specified position.
	 * @return The previous component at the specified position.
	 */
	public Component set(int index, Component c);
	
	/**
	 * Gets the length of the list.
	 * @return The length of the list.
	 */
	public int size();
}
