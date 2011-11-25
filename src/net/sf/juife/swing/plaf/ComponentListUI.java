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

package net.sf.juife.swing.plaf;

import java.awt.Component;
import javax.swing.plaf.PanelUI;


/**
 * Pluggable look and feel interface for <code>ComponentList</code>.
 * @author Grigor Iliev
 */
public abstract class ComponentListUI extends PanelUI {
	/**
	 * Scrolls the viewport to make the specified component visible.
	 * @param index The index of the component to make visible.
	 */
	public abstract void ensureIndexIsVisible(int index);
	
	/** Updates the component list UI. */
	public abstract void updateList();
	
	/**
	 * Process a selection event.
	 * @param c The newly selected component.
	 * @param controlDown Specifies whether the control key is held down during selection.
	 * @param shiftDown Specifies whether the shift key is held down during selection.
	 */
	public abstract void processSelectionEvent(Component c, boolean controlDown, boolean shiftDown);
}
