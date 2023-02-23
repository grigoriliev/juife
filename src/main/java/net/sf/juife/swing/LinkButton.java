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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JButton;

import static java.awt.event.HierarchyEvent.SHOWING_CHANGED;


/**
 * This class represents a button that acts as a hyperlink.
 * @author Grigor Iliev
 */
public class LinkButton extends JButton {
	/** The bold style constant. This can be combined with the other style constants. */
	public final static int BOLD = 1;
	/** The italic style constant. This can be combined with the other style constants. */
	public final static int ITALIC = 2;
	/** The underline style constant. This can be combined with the other style constants. */
	public final static int UNDERLINE = 4;
	
	/** The default color for visited links. */
	public final static Color COLOR_VISITED = new Color(0x800088);
	
	private String linkText;
	
	private boolean visited = false;
	
	private Color unvisitedColor = new Color(0x0000ff);
	private Color visitedColor = null;
	private Color hoverColor;
	private Color disabledColor = Color.GRAY;
	
	private Font hoverFont = null;
	
	private int hoverFontStyle = UNDERLINE;
	private int visitedFontStyle = 0;
	private int unvisitedFontStyle = 0;
	private int disabledFontStyle = 0;
	
	private final Handler handler = new Handler();
	
	private boolean mouseOver = false;
	
	/** Creates a new instance of <code>LinkButton</code>. */
	public
	LinkButton() { this(""); }
	
	/**
	 * Creates a new instance of <code>LinkButton</code> where
	 * properties are taken from the Action supplied.
	 * @param a The <code>Action</code> used to specify the new button.
	 */
	public
	LinkButton(Action a) {
		super(a);
		
		initLinkButton();
	}
	
	/**
	 * Creates a new instance of <code>LinkButton</code> with the specified text.
	 * @param text The text of the button.
	 */
	public
	LinkButton(String text) {
		super(text);
		
		initLinkButton();
	}
	
	/** The initial initialization of the link button. */
	private void
	initLinkButton() {
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setForeground(getUnvisitedColor());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setHorizontalAlignment(LEFT);
		setMargin(new java.awt.Insets(0, 0, 0, 0));
		setMaximumSize(getPreferredSize());
		
		addMouseListener(getHandler());
		addActionListener(getHandler());
		addHierarchyListener(getHandler());
		
		setFont(getFont().deriveFont(Font.PLAIN));
	}
	
	/**
	 * Sets the button's text.
	 * @param text The string used to set the text.
	 */
	public void
	setText(String text) {
		linkText = text;
		
		StringBuffer sb = new StringBuffer("<html>");
		StringBuffer sb2 = new StringBuffer();
		
		int fs;
		
		if(!isEnabled()) {
			fs = getDisabledFontStyle();
		} else if(isMouseOver()) {
			fs = getHoverFontStyle();
		} else {
			if(this.isVisited()) fs = getVisitedFontStyle();
			else fs = getUnvisitedFontStyle();
		}
		
		if((fs & UNDERLINE) == UNDERLINE) {
			sb.append("<u>");
			sb2.insert(0, "</u>");
		}
		
		if((fs & BOLD) == BOLD) {
			sb.append("<b>");
			sb2.insert(0, "</b>");
		}
		
		if((fs & ITALIC) == ITALIC) {
			sb.append("<i>");
			sb2.insert(0, "</i>");
		}
		
		sb.append(text).append(sb2);
		
		super.setText(sb.toString());
	}
	
	/**
	 * Gets the font color that is used when the mouse is over the link button.
	 * @return The font color that is used when the mouse is over the link button.
	 */
	public Color
	getHoverColor() { return hoverColor; }
	
	/**
	 * Sets the font color to be used when the mouse is over the button.
	 * Set to <code>null</code> if you don't want to use hover color.
	 * @param c The font color to be used when the mouse is over the button.
	 */
	public void
	setHoverColor(Color c) {
		hoverColor = c;
		updateFontColor();
	}
	
	/**
	 * Gets the font that is used when the mouse is over the link button.
	 * @return The font that is used when the mouse is over the link button.
	 */
	public Font
	getHoverFont() { return hoverFont; }
	
	/**
	 * Sets the font to be used when the mouse is over the button.
	 * Set to <code>null</code> if you don't want to use hover font.
	 * @param hoverFont The font to be used when the mouse is over the button.
	 */
	public void
	setHoverFont(Font hoverFont) {
		this.hoverFont = hoverFont;
		if(isMouseOver() && hoverFont!= null && isEnabled()) setFont(hoverFont);
	}
	
	/**
	 * Gets the font style to be used when the mouse is over the button.
	 * @return A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 */
	public int
	getHoverFontStyle() { return hoverFontStyle; }
	
	/**
	 * Sets the font style to be used when the mouse is over the button.
	 * @param style A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 */
	public void
	setHoverFontStyle(int style) {
		hoverFontStyle = style;
		setText(linkText);
	}
	
	/**
	 * Determines the visited state of this link button.
	 * @return <code>true</code> if this button is clicked at least once,
	 * <code>false</code> otherwise.
	 */
	public boolean
	isVisited() { return visited; }
	
	/**
	 * Sets the visited state of this link button.
	 * @param visited If <code>true</code> the link button is marked as visited.
	 */
	public void
	setVisited(boolean visited) {
		this.visited = visited;
		updateFontColor();
	}
	
	/**
	 * Gets the font color that is used for already visited links.
	 * Visited link means a link button that is clicked at least once.
	 * @return The font color that is used for already visited links.
	 * @see #isVisited
	 * @see #setVisited
	 */
	public Color
	getVisitedColor() { return visitedColor; }
	
	/**
	 * Sets the font color to be used for already visited links.
	 * Visited link means a link button that is clicked at least once.
	 * Set to <code>null</code> if you don't want to use visited color.
	 * @param c The font color to be used for already visited links.
	 * @see #isVisited
	 * @see #setVisited
	 */
	public void
	setVisitedColor(Color c) {
		visitedColor = c;
		updateFontColor();
	}
	
	/**
	 * Gets the font style to be used for a visited links.
	 * @return A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 */
	public int
	getVisitedFontStyle() { return visitedFontStyle; }
	
	/**
	 * Sets the font style to be used for a visited links.
	 * @param style A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 */
	public void
	setVisitedFontStyle(int style) {
		visitedFontStyle = style;
		setText(linkText);
	}
	
	/**
	 * Gets the font color that is used for unvisited links.
	 * Unvisited link means a link button that isn't clicked even once.
	 * @return The font color that is used for unvisited links.
	 * @see #isVisited
	 * @see #setVisited
	 */
	public Color
	getUnvisitedColor() { return unvisitedColor; }
	
	/**
	 * Sets the font color to be used for unvisited links.
	 * Unvisited link means a link button that isn't clicked even once.
	 * @param c The font color to be used for unvisited links.
	 * @throws IllegalArgumentException if <code>c</code> is <code>null</code>.
	 * @see #isVisited
	 * @see #setVisited
	 */
	public void
	setUnvisitedColor(Color c) {
		if(c == null) throw new IllegalArgumentException("c must be non-null");
		unvisitedColor = c;
		updateFontColor();
	}
	
	/**
	 * Gets the font style to be used for a unvisited links.
	 * @return A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 */
	public int
	getUnvisitedFontStyle() { return unvisitedFontStyle; }
	
	/**
	 * Sets the font style to be used for a unvisited links.
	 * @param style A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 */
	public void
	setUnvisitedFontStyle(int style) {
		unvisitedFontStyle = style;
		setText(linkText);
	}
	
	/**
	 * Gets the font color that is used when the link button is disabled.
	 * @return The font color that is used when the link button is disabled.
	 * @see #setEnabled
	 */
	public Color
	getDisabledColor() { return disabledColor; }
	
	/**
	 * Sets the font color to be used when the link button is disabled.
	 * @param c The font color to be used when the link button is disabled.
	 * @throws IllegalArgumentException if <code>c</code> is <code>null</code>.
	 * @see #setEnabled
	 */
	public void
	setDisabledColor(Color c) {
		if(c == null) throw new IllegalArgumentException("c must be non-null");
		disabledColor = c;
		updateFontColor();
	}
	
	/**
	 * Gets the font style to be used when the link button is disabled.
	 * @return A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 * @see #setEnabled
	 */
	public int
	getDisabledFontStyle() { return disabledFontStyle; }
	
	/**
	 * Sets the font style to be used when the link button is disabled.
	 * @param style A combination of <code>BOLD</code>,
	 * <code>ITALIC</code> and <code>UNDERLINE</code> flags.
	 * @see #setEnabled
	 */
	public void
	setDisabledFontStyle(int style) {
		disabledFontStyle = style;
		setText(linkText);
	}
	
	/**
	 * Enables/disables the link button.
	 * @param b <code>true</code> to enable the button, <code>false</code> otherwise.
	 */
	public void
	setEnabled(boolean b) {
		super.setEnabled(b);
		
		updateFontColor();
		setText(linkText);
	}
	
	/**
	 * Determines whehter the mouse cursor is over the link button.
	 * @return <code>true</code> if the mouse cursor is over the
	 * link button, <code>false</code> otherwise.
	 */
	private boolean
	isMouseOver() { return mouseOver; }
	
	private Handler
	getHandler() { return handler; }
	
	private class Handler extends MouseAdapter implements ActionListener, HierarchyListener {
		private Font font = null;
		
		/** Invoked when the mouse enters a component. */
		public void
		mouseEntered(MouseEvent e) {
			mouseOver = true;
			
			if(getHoverFont() != null && isEnabled()) {
				font = getFont();
				setFont(getHoverFont());
			}
			
			updateFontColor();
			setText(linkText);
		}
		
		/** Invoked when the mouse exits a component. */
		public void
		mouseExited(MouseEvent e) {
			mouseOver = false;
			
			if(font != null) {
				setFont(font);
				font = null;
			}
			
			updateFontColor();
			setText(linkText);
		}
		
		/** Invoked when the link button is pressed. */
		public void
		actionPerformed(ActionEvent e) { setVisited(true); }
	
		/** Called when the hierarchy has been changed. */
		public void
		hierarchyChanged(HierarchyEvent e) {
			if((e.getChangeFlags() & SHOWING_CHANGED) == SHOWING_CHANGED) {
				if(getMousePosition() == null) mouseExited(null);
				else mouseEntered(null);
			}
		}
	}
	
	/** Updates the font color of this link button. */
	private void
	updateFontColor() {
		if(!isEnabled()) {
			setForeground(getDisabledColor());
		} else if(isMouseOver() && getHoverColor() != null) {
			setForeground(getHoverColor());
		} else if(isVisited() && getVisitedColor() != null) {
			setForeground(getVisitedColor());
		} else {
			setForeground(getUnvisitedColor());
		}
	}
}
