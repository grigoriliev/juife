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

import java.awt.Component;
import java.awt.BorderLayout;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sf.juife.swing.plaf.ComponentListUI;


/**
 * A component that contains a list of components
 * and allows the user to select one or more of them.
 *
 * <p>Note that if you want to create a list of non-interactive
 * components that just show some kind of information consider using
 * <code>JList</code>. In <code>JList</code> this can be done
 * by setting a custom delegate to paint the cells using
 * {@link javax.swing.JList#setCellRenderer} method.</p>
 *
 * @see ComponentListModel
 * @see DefaultComponentListModel
 * @author Grigor Iliev
 */
public class ComponentList extends JPanel {
	static {
		// TODO: In future this must be done the right way
		UIManager.put("ComponentListUI", "net.sf.juife.swing.plaf.basic.BasicComponentListUI");
	}
	private static final String uiClassID = "ComponentListUI";
	
	private ComponentListModel dataModel;
	private ListSelectionModel selectionModel;
	private final ListSelectionListener selectionHandler;
	
	private boolean autoUpdate = true;
	
	/** Creates a new instance of <code>ComponentList</code> */
	public
	ComponentList() { this(new DefaultComponentListModel()); }
	
	/** 
	 * Creates a new instance of <code>ComponentList</code> with the specified data model.
	 * @param model The data model for this <code>ComponentList</code> instance.
	 */
	public
	ComponentList(ComponentListModel model) {
		super(new BorderLayout());
		
		dataModel = model;
		selectionModel = new DefaultListSelectionModel();
		
		selectionHandler = new ListSelectionListener() {
			public void
			valueChanged(ListSelectionEvent e) { fireSelectionChanged(e); }
		};
		
		getSelectionModel().addListSelectionListener(selectionHandler);
		
		addPropertyChangeListener(new PropertyChangeListener() {
			public void
			propertyChange(PropertyChangeEvent e) {
				String name = e.getPropertyName();
			
				if(e.getPropertyName() == "selectionModel") {
					ListSelectionModel old1 =
						(ListSelectionModel)e.getOldValue();
					ListSelectionModel new1 =
						(ListSelectionModel)e.getNewValue();
				
					if(old1 != null)
						old1.removeListSelectionListener(selectionHandler);
					
					if(new1 != null)
						new1.addListSelectionListener(selectionHandler);
				}
			}
		});
	}
	
	/**
	 * Gets a string that specifies the name
	 * of the L&F class that renders this component.
	 * @return the string "ComponentListUI"
	 */
	public String
	getUIClassID() { return uiClassID; }
	
	/**
	 * Gets the L&F object that renders this component.
	 * @return The L&F object that renders this component.
	 */
	public ComponentListUI
	getUI() { return (ComponentListUI)ui; }
	
	/**
	 * Sets the L&F object that renders this component.
	 * @param ui The new UI delegate.
	 */
	public void
	setUI(ComponentListUI ui) { super.setUI(ui); }
	
	/**
	 * Resets the UI property to a value from the current look and feel.
	 */
	public void
	updateUI() { setUI((ComponentListUI)UIManager.getUI(this)); }
	
	/**
	 * Registers the specified listener for receiving list selection events.
	 * @param listener The <code>ListSelectionListener</code> to register.
	 */
	public void
	addListSelectionListener(ListSelectionListener listener) {
		listenerList.add(ListSelectionListener.class, listener);
	}
	
	/**
	 * Removes the specified listener.
	 * @param listener The <code>ListSelectionListener</code> to remove.
	 */
	public void
	removeListSelectionListener(ListSelectionListener listener) {
		listenerList.remove(ListSelectionListener.class, listener);
	}
	
	/**
	 * Notifies <code>ComponentList</code> <code>ListSelectionListener</code>s that
	 * the selection model has changed.
	 */
	protected void
	fireSelectionChanged(ListSelectionEvent e) {
		ListSelectionEvent e2 = null;
		Object[] listeners = listenerList.getListenerList();
		
		for(int i = listeners.length - 2; i >= 0; i -= 2) {
			if(listeners[i] == ListSelectionListener.class) {
				if(e2 == null) e2 = new ListSelectionEvent (
					this,
					e.getFirstIndex(),
					e.getLastIndex(),
					e.getValueIsAdjusting()
				);
				
				((ListSelectionListener)listeners[i + 1]).valueChanged(e2);
			}
		}
			
	}
	
	/**
	 * Gets the data model of this list.
	 * @return The data model of this list.
	 */
	public ComponentListModel
	getModel() { return dataModel; }
	
	
	/**
	 * Sets the data model of the list.
	 * @param model The new data model.
	 */
	public void
	setModel(ComponentListModel model) {
		if(model == null) throw new IllegalArgumentException("model can't be null");
		ComponentListModel oldModel = dataModel;
		dataModel = model;
		firePropertyChange("model", oldModel, dataModel);
		clearSelection();
	}
	
	/**
	 * Gets the current selection model of this list.
	 * @return The current selection model of this list.
	 */
	public ListSelectionModel
	getSelectionModel() { return selectionModel; }
	
	/**
	 * Sets the selection model of this list.
	 * @param model The new selection model.
	 */
	public void
	setSelectionModel(ListSelectionModel model) {
		if(model == null)
			throw new IllegalArgumentException("selectionModel can't be null");
		
		ListSelectionModel oldModel = selectionModel;
		selectionModel = model;
		firePropertyChange("selectionModel", oldModel, selectionModel);
	}
	
	/**
	 * Gets the selection mode.
	 * @return The selection mode.
	 * @see #setSelectionMode
	 * @see javax.swing.ListSelectionModel#getSelectionMode
	 */
	public int
	getSelectionMode() { return getSelectionModel().getSelectionMode(); }
	
	/**
	 * Sets the selection mode.
	 * @see javax.swing.ListSelectionModel#setSelectionMode
	 * @see #getSelectionMode
	 */
	public void
	setSelectionMode(int selectionMode) { getSelectionModel().setSelectionMode(selectionMode); }
	
	/**
	 * Changes the selection to be between index0 and index1 inclusive.
	 * @see javax.swing.ListSelectionModel#setSelectionInterval
	 */
	public void
	setSelectionInterval(int index0, int index1) {
		getSelectionModel().setSelectionInterval(index0, index1);
	}
	
	/**
	 * Gets the first selected index or -1 if the selection is empty.
	 * @return The first selected index or -1 if the selection is empty.
	 * @see javax.swing.ListSelectionModel#getMinSelectionIndex
	 */
	public int
	getMinSelectionIndex() { return getSelectionModel().getMinSelectionIndex(); }
	
	/**
	 * Gets the last selected index or -1 if the selection is empty.
	 * @return The last selected index or -1 if the selection is empty.
	 * @see javax.swing.ListSelectionModel#getMaxSelectionIndex
	 */
	public int
	getMaxSelectionIndex() { return getSelectionModel().getMaxSelectionIndex(); }
	
	/**
	 * Determines whether the selection is empty.
	 * @return <code>true</code> if nothing is selected.
	 * @see javax.swing.ListSelectionModel#isSelectionEmpty
	 */
	public boolean
	isSelectionEmpty() { return getSelectionModel().isSelectionEmpty(); }
	
	/**
	 * Determines whether the specified index is selected.
	 * @return <code>true</code> if the specified index is selected,
	 * <code>false</code> otherwise.
	 * @see javax.swing.ListSelectionModel#isSelectedIndex
	 */
	public boolean
	isSelectedIndex(int index) { return getSelectionModel().isSelectedIndex(index); }
	
	/**
	 * Selects the component at the specified index.
	 * @param index The position of the component to be selected.
	 */
	public void
	setSelectedIndex(int index) {
		if(index < 0 || index > getModel().getSize()) return;
		getSelectionModel().setSelectionInterval(index, index);
	}
	
	/**
	 * Getss an array of all of the selected indices in increasing order.
	 * @return An array of all of the selected indices in increasing order.
	 */
	public int[]
	getSelectedIndices() {
		ListSelectionModel m = getSelectionModel();
		int i = m.getMinSelectionIndex();
		int j = m.getMaxSelectionIndex();
		
		if(i < 0 || j < 0) return new int[0];
		
		Vector<Integer> v = new Vector<Integer>((j - i) + 1);
		for(int a = i; a <= j; a++) if(m.isSelectedIndex(a)) v.add(a);
		
		int[] res = new int[v.size()];
		for(int a = 0; a < v.size(); a++) res[a] = v.get(a);
		
		return res;
	}
	
	/**
	 * Selects the components with the specified indices.
	 * @param indices An array of the indices of the components to be selected.
	 */
	public void
	setSelectedIndices(int[] indices) {
		ListSelectionModel m = getSelectionModel();
		m.clearSelection();
		
		for(int i : indices)
			if(i < getModel().getSize()) m.addSelectionInterval(i, i);
	}
	
	/**
	 * Gets all selected components.
	 * The returned components are sorted in increasing index order.
	 * @return An array of all selected components.
	 */
	public Component[]
	getSelectedComponents() {
		ListSelectionModel m = getSelectionModel();
		int i = m.getMinSelectionIndex();
		int j = m.getMaxSelectionIndex();
		
		if(i < 0 || j < 0) return new Component[0];
		
		Vector<Component> v = new Vector<Component>((j - i) + 1);
		
		for(int a = i; a <= j; a++)
			if(m.isSelectedIndex(a)) v.add(getModel().get(a));
		
		return v.toArray(new Component[v.size()]);
	}
	
	/**
	 * Selects the specified component.
	 * @param c The component to be selected.
	 * @param shouldScroll Determines whether the list should scroll to display
	 * the selected component; <code>true</code> if the list should scroll to display
	 * the selected component, <code>false</code> otherwise.
	 */
	public void
	setSelectedComponent(Component c, boolean shouldScroll) {
		ComponentListModel clm = getModel();
		
		if(c == null) {
			clearSelection();
			return;
		}
		
		for(int i = 0; i < clm.getSize(); i++) {
			if(c.equals(clm.get(i))) {
				setSelectedIndex(i);
				if(shouldScroll) ensureIndexIsVisible(i);
				return;
			}
		}
		
		clearSelection();
	}
	
	/** Selects all components in the list. */
	public void
	selectAll() {
		if(getSelectionModel().getSelectionMode() == ListSelectionModel.SINGLE_SELECTION)
			return;
		
		if(getModel().getSize() == 0) return;
		getSelectionModel().setSelectionInterval(0, getModel().getSize() - 1);
	}
	
	/**
	 * Clears the selection.
	 * @see javax.swing.ListSelectionModel#clearSelection
	 */
	public void
	clearSelection() { getSelectionModel().clearSelection(); }
	
	/**
	 * Scrolls the viewport to make the specified component visible.
	 * @param index The index of the component to make visible.
	 */
	public void
	ensureIndexIsVisible(int index) { getUI().ensureIndexIsVisible(index); }
	
	/**
	 * Determines whether the component list should be automatically updated
	 * when component is added/removed. The default value is <code>true</code>.
	 * @see #updateList()
	 */
	public boolean
	getAutoUpdate() { return autoUpdate; }
	
	/**
	 * Determines whether the component list should be automatically updated
	 * when component is added/removed.
	 * @see #updateList()
	 */
	public void
	setAutoUpdate(boolean b) { autoUpdate = b; }
	
	/**
	 * Updates the component list UI.
	 * @see #setAutoUpdate(boolean)
	 */
	public void
	updateList() { getUI().updateList(); }
	
	/**
	 * Notifies all listeners that have registered interest for notification on this event type.
	 */
	public void
	fireSelectionProbablyChanged(int index) {
		if(index >= getModel().getSize()) return;
		
		Component c = getModel().get(index);
		PropertyChangeListener[] listeners = 
			c.getPropertyChangeListeners("selectionProbablyChanged");
				
		PropertyChangeEvent e = new PropertyChangeEvent (
			this,
			"selectionProbablyChanged",
			null,
			getSelectionModel().isSelectedIndex(index) ? "true" : "false"
		);	
		
		for(PropertyChangeListener l : listeners) l.propertyChange(e);
	}
}
