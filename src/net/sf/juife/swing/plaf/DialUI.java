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

package net.sf.juife.swing.plaf;

import java.awt.Point;

import javax.swing.plaf.ComponentUI;


/**
 * Pluggable look and feel interface for <code>Dial</code>.
 * @author Grigor Iliev
 */
public abstract class DialUI extends ComponentUI {
	/**
	 * Gets the value that the dial knob will have if
	 * dragging to point <code>p</code> is made in radial mode.
	 * @param p The point for which the respective dial's value should be obtained.
	 * @return The value that the dial knob will have if
	 * dragging to point <code>p</code> is made in radial mode.
	 * @throws IllegalArgumentException if <code>p</code> is <code>null</code>.
	 */
	public abstract int getValueByPoint(Point p);
}
