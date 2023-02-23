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

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * Panel which uses an image for background.
 * @author Grigor Iliev
 */
public class PixmapPane extends JPanel {
	private ImageIcon pixmap;
	
	/**
	 * Creates a new double buffered <code>PixmapPane</code> with
	 * flow layout and with the specified pixmap to be used as background.
	 * @param pixmap The pixmap to be used for background.
	 */
	public
	PixmapPane(ImageIcon pixmap) { this(pixmap, new java.awt.FlowLayout()); }
	
	/**
	 * Creates a new double buffered <code>PixmapPane</code> with
	 * the specified layout manager and pixmap to be used as background.
	 * @param pixmap The pixmap to be used for background.
	 * @param layout the <code>LayoutManager</code> to use.
	 */
	public
	PixmapPane(ImageIcon pixmap, java.awt.LayoutManager layout) {
		super(layout);
		
		setOpaque(false);
		this.pixmap = pixmap;
	}
	
	protected void
	paintComponent(Graphics g) {
		super.paintComponent(g);
		pixmap.paintIcon(this, g, 0, 0);
	}
	
	public Dimension
	getMinimumSize() { return getPreferredSize(); }
	
	public Dimension
	getMaximumSize() { return getPreferredSize(); }
	
	public Dimension
	getPreferredSize() {
		return new Dimension(pixmap.getIconWidth(), pixmap.getIconHeight());
	}
	
	/**
	 * Gets the pixmap that is used for background.
	 * @return The pixmap that is used for background.
	 */
	public ImageIcon
	getPixmap() { return pixmap; }
	
	/**
	 * Sets the pixmap to be used for background.
	 * @param pixmap Specifies the pixmap to be used for background.
	 */
	public void
	setPixmap(ImageIcon pixmap) {
		this.pixmap = pixmap;
		revalidate();
		repaint();
	}
}
