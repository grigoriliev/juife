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

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;

import static java.lang.Math.max;


/**
 * A collection of utility methods.
 * @author Grigor Iliev
 */
public class JuifeUtils {
	private
	JuifeUtils() { }
	
	/**
	 * Calculates the location that can be used to center
	 * <code>component</code> related to <code>parent</code>.
	 * @return The location of the top-left corner that can be used to center
	 * <code>component</code> related to <code>parent</code>.
	 * @see java.awt.Component#setLocation(Point p)
	 */
	public static Point
	centerLocation(Component component, Component parent) {
		int x = parent.getLocation().x;
		int y = parent.getLocation().y;
		int w = parent.getBounds().width;
		int h = parent.getBounds().height;
		Rectangle r = component.getBounds();
		w -= r.width; 
		h -= r.height;
		w /= 2;
		h /= 2;
		x += w;
		y += h;
		if(x < 0) x = 0;
		if(y < 0) y = 0;
		return new Point(x, y);
	}
	
	/**
	 * Gets the union size of the specified components.
	 * The union size is calculated by
	 * {@link #getUnionSize(Dimension d1, Dimension d2)}
	 * method using the components' preferred size.
	 * @return The union size of the specified components.
	 */
	public static Dimension
	getUnionSize(Component c1, Component c2) {
		return getUnionSize(c1.getPreferredSize(), c2.getPreferredSize());
	}
	
	
	/**
	 * Gets the union size of the specified component and dimension
	 * using the component's preferred size.
	 * @return The union size of the specified component and dimension.
	 */
	public static Dimension
	getUnionSize(Component c1, Dimension d2) {
		return getUnionSize(c1.getPreferredSize(), d2);
	}
	
	/**
	 * Gets the union size of the specified dimensions.
	 * @return The union size of the specified dimensions.
	 */
	public static Dimension
	getUnionSize(Dimension d1, Dimension d2) {
		return new Dimension(max(d1.width, d2.width), max(d1.height, d2.height));
	}
	
	/**
	 * Gets the toplevel window of the specified component.
	 * @param c The component whose toplevel window should be obtained.
	 * @return The toplevel window of the specified component or
	 * <code>null</code>.
	 */
	public static Window
	getWindow(Component c) {
		if(c == null) return null;
		if(c instanceof Frame || c instanceof Dialog) return (Window) c;
		return getWindow(c.getParent());
	}
}
