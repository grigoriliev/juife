/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2005-2010 Grigor Iliev <grigor@grigoriliev.com>
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
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.UIManager;
import javax.swing.plaf.MenuItemUI;
import javax.swing.plaf.PopupMenuUI;

/**
 *
 * @author Grigor Iliev
 */
public class MultiColumnMenu extends javax.swing.JMenu {
	protected JPopupMenu popupMenu = null;

	public
	MultiColumnMenu(String s) { super(s); }

	protected JPopupMenu
	createPopupMenu() { return new PopupMenu(); }

	protected void
	ensurePopupMenuCreated() {
		if(popupMenu !=  null) return;

		popupMenu = createPopupMenu();
		popupMenu.setInvoker(this);
		popupListener = createWinListener(popupMenu);
	}

	public JMenuItem
	add(JMenuItem menuItem) { return getPopupMenu().add(menuItem); }

	public void
	insert(String s, int pos)
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	@Override
	public JMenuItem
	insert(JMenuItem mi, int pos)
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	@Override
	public JMenuItem
	insert(javax.swing.Action a, int pos)
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	@Override
	public void
	remove(JMenuItem item)
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	@Override
	public void
	setMenuLocation(int x, int y)
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	public int
	getMenuComponentCount()
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	@Override
	public void
	applyComponentOrientation(ComponentOrientation o)
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	@Override
	public void
	setComponentOrientation(ComponentOrientation o)
	{ throw new UnsupportedOperationException("Not yet implemented"); }

	@Override
	public void
	removeAll() { if (getPopupMenu() != null) getPopupMenu().removeAll(); }

	@Override
	public MenuElement[]
	getSubElements() {
		MenuElement result[] = new MenuElement[1];
		result[0] = getPopupMenu();
		return result;
	}

	@Override
	public JPopupMenu
	getPopupMenu() {
		ensurePopupMenuCreated();
		return popupMenu;
	}

	@Override
	public boolean
	isPopupMenuVisible() { return getPopupMenu().isVisible(); }

	public void
	updateUI() {
		setUI((MenuItemUI)UIManager.getUI(this));
		if(getPopupMenu() != null) {
			getPopupMenu().setUI((PopupMenuUI)UIManager.getUI(getPopupMenu()));
		}
	}

	public static class PopupMenu extends JPopupMenu {
		private int maxItemsPerColumn = -1;

		private final Vector<JMenuItem> menuItems = new Vector<JMenuItem>();

		public
		PopupMenu() {
			GridBagLayout gridbag = new GridBagLayout();
			setLayout(gridbag);
		}

		@Override
		public JMenuItem
		add(JMenuItem menuItem) {
			if(maxItemsPerColumn == -1) {
				int scrWidth = Toolkit.getDefaultToolkit().getScreenSize().height;
				maxItemsPerColumn = scrWidth / menuItem.getPreferredSize().height;
			}
			menuItems.add(menuItem);
			super.removeAll();

			int itemCount = menuItems.size();
			int columnCount = (itemCount / maxItemsPerColumn) + 1;
			int itemsPerColumn = itemCount / columnCount;
			if(itemCount % columnCount > 0) itemsPerColumn++;

			for(int i = 0; i < itemCount; i++) {
				add(menuItems.get(i), i, itemsPerColumn);
			}

			return menuItem;
		}

		@Override
		public void
		insert(Component component, int index)
		{ throw new UnsupportedOperationException("Not yet implemented"); }

		protected JComponent
		createColumnSeparator() { return null; }

		private void
		add(JMenuItem menuItem, int itemCount, int itemsPerColumn) {
			int columnCount = (itemCount / itemsPerColumn) + 1;

			GridBagConstraints c = new GridBagConstraints();

			if(itemCount > 0 && itemCount % itemsPerColumn == 0) {
				JComponent sp = createColumnSeparator();

				if(sp != null) {
					c.gridx = columnCount * 2 - 1;
					c.gridy = 0;
					c.gridheight = itemsPerColumn;
					c.weighty = 1.0;
					c.fill = GridBagConstraints.VERTICAL;
					((GridBagLayout)getLayout()).setConstraints(sp, c);
					super.add(sp);
				}
			}

			c.gridx = columnCount * 2;
			c.gridy = itemCount % itemsPerColumn;
			c.gridheight = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			((GridBagLayout)getLayout()).setConstraints(menuItem, c);
			super.add(menuItem);
		}

		@Override
		public void
		removeAll() {
			menuItems.removeAllElements();
			super.removeAll();
		}
	}
}
