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

import java.awt.Window;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;


/**
 * <code>TitleBar</code> is a generic lightweight container with the only
 * difference that when performing a mouse dragging on it the parent window
 * is moving just like if the title bar is dragged.
 * @author Grigor Iliev
 */
public class TitleBar extends JPanel {
	
	/**
	 * Creates a new <code>TitleBar</code> with a double buffer and a flow layout.
	 */
	public
	TitleBar() { this(new java.awt.FlowLayout()); }
	
	/**
	 * Creates a new <code>TitleBar</code> with a double buffer and
	 * with the specified layout manager.
	 * @param layout the <code>LayoutManager</code> to use.
	 */
	public
	TitleBar(java.awt.LayoutManager layout) {
		super(layout);
		
		MouseHandler mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
	}
	
	
	private class MouseHandler extends MouseAdapter implements MouseMotionListener {
		private int x;
		private int y;
		
		public void
		mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}
		
		public void
		mouseDragged(MouseEvent e) {
			Window window = JuifeUtils.getWindow(TitleBar.this);
			if(window == null) return;
			
			int x2 = window.getLocation().x + (e.getX() - x);
			int y2 = window.getLocation().y + (e.getY() - y);
			
			if(window != null) window.setLocation(x2, y2);
		}
		
		public void
		mouseMoved(MouseEvent e) { }
	}
}
