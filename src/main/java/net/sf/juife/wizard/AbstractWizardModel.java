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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;


/**
 * This class provides a skeletal implementation of the <code>WizardModel</code>
 * interface to minimize the effort required to implement this interface.
 * @author Grigor Iliev
 */
public abstract class AbstractWizardModel implements WizardModel {
	private final Vector<ActionListener> listeners = new Vector<ActionListener>();
		
	/**
	 * Registers the specified <code>ActionListener</code> to be
	 * notified when the current page is changed.
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
	
	/** Notifies registered listeners that the current page has changed. */
	protected void
	fireActionPerformed() {
		ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
		for(ActionListener l : listeners) if(l != null) l.actionPerformed(e);
	}
}
