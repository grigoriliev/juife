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

package net.sf.juife.plaf.basic;

import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.plaf.ComponentUI;

import net.sf.juife.ComponentList;
import net.sf.juife.ComponentListModel;

import net.sf.juife.plaf.ComponentListUI;


/**
 * Basic L&F implementation of <code>ComponentListUI</code>.
 * @author Grigor Iliev
 */
public class BasicComponentListUI extends ComponentListUI {
	private final static String propertyPrefix = "ComponentList" + ".";
	
	private ComponentList componentList = null;
	
	private JPanel listPane;
	
	
	private
	BasicComponentListUI() { }
	
	/**
	 * Gets the property prefix.
	 * @return The property prefix.
	 */
	protected String
	getPropertyPrefix() { return propertyPrefix; }
	
	/**
	 * Creates a new instance of <code>BasicComponentListUI</code>.
	 * @return A new instance of <code>BasicComponentListUI</code>.
	 */
	public static ComponentUI
	createUI(JComponent c) { return new BasicComponentListUI(); }
	
	/**
	 * Configures the specified component appropriate for the look and feel.
	 * This method is invoked when the ComponentUI instance is being
	 * installed as the UI delegate on the specified component.
	 * @param c The component where this UI delegate is being installed.
	 */
	public void
	installUI(JComponent c) {
		componentList = (ComponentList) c;
		
		installDefaults();
		installListeners();
	}
	
	/** Installs the UI defaults. */
	protected void
	installDefaults() {
		listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));
		componentList.add(listPane);
		
		loadActionMap();
		loadInputMap();
	}
	
	/** Installs the event listeners for the UI. */
	protected void
	installListeners() {
		componentList.addPropertyChangeListener(getHandler());
		componentList.addListSelectionListener(getHandler());
		
		listPane.addMouseListener(getHandler());
	}
	
	/**
	 * Reverses configuration which was done on the specified component
	 * during <code>installUI</code>. This method is invoked when this
	 * <code>BasicComponentListUI</code> instance is being removed as
	 * the UI delegate for the specified component.
	 * @param c The component from which this UI delegate is being removed.
	 */
	public void
	uninstallUI(JComponent c) {
		uninstallListeners();
		uninstallDefaults();
	}
	
	/** Uninstalls the UI defaults. */
	protected void
	uninstallDefaults() {
		componentList.remove(listPane);
		componentList = null;
		listPane = null;
	}
	
	/** Uninstalls the event listeners for the UI. */
	protected void
	uninstallListeners() {
		componentList.removePropertyChangeListener(getHandler());
		componentList.getModel().removeListDataListener(getHandler());
		componentList.removeListSelectionListener(getHandler());
		
		listPane.removeMouseListener(getHandler());
	}
	
	/**
	 * Scrolls the viewport to make the specified component visible.
	 * @param index The index of the component to make visible.
	 */
	public void
	ensureIndexIsVisible(int index) {
		if(index < 0 || index >= componentList.getModel().getSize()) return;
		Component c = componentList.getModel().get(index);
		if(c == null) return;
		
		listPane.scrollRectToVisible(c.getBounds());
	}
	
	private void
	loadActionMap() {
		ActionMap map = listPane.getActionMap();
		
		map.put(Actions.SELECT_PREV_COMPONENT, new Actions(Actions.SELECT_PREV_COMPONENT));
		map.put(Actions.SELECT_NEXT_COMPONENT, new Actions(Actions.SELECT_NEXT_COMPONENT));
		
		map.put (
			Actions.SELECT_PREV_COMPONENT_EXTEND,
			new Actions(Actions.SELECT_PREV_COMPONENT_EXTEND)
		);
		
		map.put (
			Actions.SELECT_NEXT_COMPONENT_EXTEND,
			new Actions(Actions.SELECT_NEXT_COMPONENT_EXTEND)
		);
	}
	
	private void
	loadInputMap() {
		InputMap map = listPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		
		map.put (
			KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
			Actions.SELECT_PREV_COMPONENT
		);
		
		map.put (
			KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
			Actions.SELECT_NEXT_COMPONENT
		);
		
		map.put (
			KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.SHIFT_MASK),
			Actions.SELECT_PREV_COMPONENT_EXTEND
		);
		
		map.put (
			KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.SHIFT_MASK),
			Actions.SELECT_NEXT_COMPONENT_EXTEND
		);
	}
	
	private class Actions extends AbstractAction {
		private final static String SELECT_PREV_COMPONENT = "SelectPreviousComponent";
		private final static String SELECT_NEXT_COMPONENT = "SelectNextComponent";
		private final static String SELECT_PREV_COMPONENT_EXTEND =
			"ExtendSelectionToPreviousComponent";
		private final static String SELECT_NEXT_COMPONENT_EXTEND =
			"ExtendSelectionToNextComponent";
		
		Actions(String name) { super(name); }
		
		public void
		actionPerformed(ActionEvent e) {
			ComponentListModel dm = componentList.getModel();
			ListSelectionModel sm = componentList.getSelectionModel();
			int ai = sm.getAnchorSelectionIndex();
			
			Object name = getValue(Action.NAME);
			
			if(name == SELECT_PREV_COMPONENT) {
				if(sm.isSelectionEmpty()) return;
				if(ai < 1) return;
				sm.setSelectionInterval(ai - 1, ai - 1);
			} else if(name == SELECT_NEXT_COMPONENT) {
				//if(sm.isSelectionEmpty()) return;
				if(ai >= dm.getSize() - 1) return;
				sm.setSelectionInterval(ai + 1, ai + 1);
			} else if(name == SELECT_PREV_COMPONENT_EXTEND) {
				if(sm.isSelectionEmpty()) return;
				if(ai < 1) return;
				
				if(sm.isSelectedIndex(ai - 1)) {
					sm.removeSelectionInterval(ai, ai);
					sm.setAnchorSelectionIndex(ai - 1);
				} else sm.addSelectionInterval(ai - 1, ai - 1);
			} else if(name == SELECT_NEXT_COMPONENT_EXTEND) {
				if(sm.isSelectionEmpty()) return;
				if(ai >= dm.getSize() - 1) return;
				
				if(sm.isSelectedIndex(ai + 1)) {
					sm.removeSelectionInterval(ai, ai);
					sm.setAnchorSelectionIndex(ai + 1);
				} else sm.addSelectionInterval(ai + 1, ai + 1);
			}
		}
	}
	
	/** Updates the component list UI. */
	public void
	updateList() {
		for(Component c : listPane.getComponents()) c.removeMouseListener(getHandler());
		
		listPane.removeAll();
		
		for(int i = 0; i < componentList.getModel().getSize(); i++) {
			Component c = componentList.getModel().get(i);
			listPane.add(c, i);
			c.addMouseListener(getHandler());
		}
		
		listPane.add(Box.createGlue());
		
		if(!listPane.hasFocus() && listPane.isRequestFocusEnabled())
			listPane.requestFocus();
		
		listPane.revalidate();
		listPane.repaint();
	}
	
	private final Handler handler = new Handler();
	
	private Handler
	getHandler() { return handler; }
	
	private class Handler implements ListDataListener,
			ListSelectionListener, PropertyChangeListener, MouseListener {
		
		// Implementation of ListDataListener interface
		public void
		intervalAdded(ListDataEvent e) {
			ListSelectionModel m = componentList.getSelectionModel();
			if(m == null) return;
			
			int min = Math.min(e.getIndex0(), e.getIndex1());
			int max = Math.max(e.getIndex0(), e.getIndex1());
			m.insertIndexInterval(min, max - min + 1, true);
			
			if(componentList.getAutoUpdate()) updateList();
		}
		
		public void
		intervalRemoved(ListDataEvent e) {
			ListSelectionModel m = componentList.getSelectionModel();
			if(m != null) m.removeIndexInterval(e.getIndex0(), e.getIndex1());
			
			if(componentList.getAutoUpdate()) updateList();
		}
		
		public void
		contentsChanged(ListDataEvent e) {
			if(componentList.getAutoUpdate()) updateList();
		}
		///////
		
		
		// Implementation of ListSelectionListener interface
		public void
		valueChanged(ListSelectionEvent e) {
			for(int i = e.getFirstIndex(); i <= e.getLastIndex(); i++)
				componentList.fireSelectionProbablyChanged(i);
		}
		
		// Implementation of PropertyChangeListener interface
		public void
		propertyChange(PropertyChangeEvent e) {
			String name = e.getPropertyName();
			
			if(e.getPropertyName() == "model") {
				ComponentListModel old1 = (ComponentListModel)e.getOldValue();
				ComponentListModel new1 = (ComponentListModel)e.getNewValue();
				
				if(old1 != null) old1.removeListDataListener(getHandler());
				if(new1 != null) new1.addListDataListener(getHandler());
			}
		}
		
		// Implementation of MouseListener interface
		public void
		mouseClicked(MouseEvent e) { }
		
		public void
		mouseEntered(MouseEvent e) { }
		
		public void
		mouseExited(MouseEvent e) { }
		
		public void
		mousePressed(MouseEvent e) {
			ListSelectionModel sm = componentList.getSelectionModel();
		
			Component c;
			if(e.getSource() == listPane) {
				c = listPane.getComponentAt(e.getX(), e.getY());
			} else {
				c = (Component)e.getSource();
			}
			
			if(c == null) {
				if(!listPane.hasFocus() && listPane.isRequestFocusEnabled())
					listPane.requestFocus();
				
				if( (e.isControlDown() || e.isShiftDown()) &&
				    componentList.getSelectionMode() != sm.SINGLE_SELECTION );
				else componentList.clearSelection();
				
				return;
			} else if(!c.hasFocus()) c.requestFocus();
			
			int idx = -1;
			
			for(int i = 0; i < componentList.getModel().getSize(); i++) {
				if(componentList.getModel().get(i) == c) {
					idx = i;
					break;
				}
			}
			
			if(idx == -1) {
				if( (e.isControlDown() || e.isShiftDown()) &&
				    componentList.getSelectionMode() != sm.SINGLE_SELECTION );
				else componentList.clearSelection();
				
				return;
			}
			
			int ai = sm.getAnchorSelectionIndex();
			
			if(!e.isControlDown()) {
				if(!e.isShiftDown()) {
					componentList.setSelectedComponent(c, false);
					return;
				}
				
				if(ai != -1) sm.setSelectionInterval(ai, idx);
				return;
			}
			
			if(e.isShiftDown()) sm.addSelectionInterval(ai, idx);
			else {
				if(sm.isSelectedIndex(idx)) sm.removeSelectionInterval(idx, idx);
				else sm.addSelectionInterval(idx, idx);
			}	
		}
		
		public void
		mouseReleased(MouseEvent e) { }
		///////
	}
}
