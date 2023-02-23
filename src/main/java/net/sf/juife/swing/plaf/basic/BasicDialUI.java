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

package net.sf.juife.swing.plaf.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

import javax.swing.plaf.ComponentUI;

import net.sf.juife.swing.Dial;

import net.sf.juife.swing.plaf.DialUI;

import static java.lang.Math.*;


/**
 * Basic L&F implementation of <code>DialUI</code>.
 * @author Grigor Iliev
 */
public class BasicDialUI extends DialUI {
	private Dial dial;
	private TrackListener trackListener;
	
	private
	BasicDialUI(Dial dial) { }
	
	/**
	 * Creates a new instance of <code>BasicDialUI</code>.
	 * @return A new instance of <code>BasicDialUI</code>.
	 */
	public static ComponentUI
	createUI(JComponent c) { return new BasicDialUI((Dial)c); }

	/**
	 * Configures the specified component appropriate for the look and feel.
	 * This method is invoked when the ComponentUI instance is being
	 * installed as the UI delegate on the specified component.
	 * @param c The component where this UI delegate is being installed.
	 */
	public void
	installUI(JComponent c) {
		dial = (Dial) c;
		trackListener = new TrackListener();
		installListeners(dial);
		installKeyboradActions(dial);
	}
	
	private void
	installListeners(Dial d) {
		d.addMouseListener(trackListener);
		d.addMouseMotionListener(trackListener);
		d.addChangeListener(getHandler());
	}
	
	private void
	installKeyboradActions(Dial d) {
		d.getInputMap(JComponent.WHEN_FOCUSED).put (
			KeyStroke.getKeyStroke("LEFT"),
			Actions.DECREMENT
		);
		
		d.getActionMap().put(Actions.DECREMENT, new Actions(Actions.DECREMENT));
		
		
		d.getInputMap(JComponent.WHEN_FOCUSED).put (
			KeyStroke.getKeyStroke("DOWN"),
			Actions.DECREMENT_X4
		);
		
		d.getActionMap().put(Actions.DECREMENT_X4, new Actions(Actions.DECREMENT_X4));
		
		
		d.getInputMap(JComponent.WHEN_FOCUSED).put (
			KeyStroke.getKeyStroke("RIGHT"),
			Actions.INCREMENT
		);
		
		d.getActionMap().put(Actions.INCREMENT, new Actions(Actions.INCREMENT));
		
		
		d.getInputMap(JComponent.WHEN_FOCUSED).put (
			KeyStroke.getKeyStroke("UP"),
			Actions.INCREMENT_X4
		);
		
		d.getActionMap().put(Actions.INCREMENT_X4, new Actions(Actions.INCREMENT_X4));
		
		
		d.getInputMap(JComponent.WHEN_FOCUSED).put (
			KeyStroke.getKeyStroke("HOME"),
			Actions.SET_MINIMUM
		);
		
		d.getActionMap().put(Actions.SET_MINIMUM, new Actions(Actions.SET_MINIMUM));
		
		
		d.getInputMap(JComponent.WHEN_FOCUSED).put (
			KeyStroke.getKeyStroke("END"),
			Actions.SET_MAXIMUM
		);
		
		d.getActionMap().put(Actions.SET_MAXIMUM, new Actions(Actions.SET_MAXIMUM));
		
		
	}
	
	/**
	 * Reverses configuration which was done on the specified component
	 * during <code>installUI</code>. This method is invoked when this
	 * <code>BasicDialUI</code> instance is being removed as
	 * the UI delegate for the specified component.
	 * @param c The component from which this UI delegate is being removed.
	 */
	public void
	uninstallUI(JComponent c) {
		uninstallListeners(dial);
		uninstallKeyboradActions(dial);
		trackListener = null;
	}
	
	private void
	uninstallListeners(Dial d) {
		d.removeMouseListener(trackListener);
		d.removeMouseMotionListener(trackListener);
		d.removeChangeListener(getHandler());
	}
	
	private void
	uninstallKeyboradActions(Dial d) {
		d.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke("LEFT"));
		d.getActionMap().remove(Actions.DECREMENT);
		
		d.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke("DOWN"));
		d.getActionMap().remove(Actions.DECREMENT_X4);
		
		d.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke("RIGHT"));
		d.getActionMap().remove(Actions.INCREMENT);
		
		d.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke("UP"));
		d.getActionMap().remove(Actions.INCREMENT_X4);
		
		d.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke("HOME"));
		d.getActionMap().remove(Actions.SET_MINIMUM);
		
		d.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke("END"));
		d.getActionMap().remove(Actions.SET_MAXIMUM);
	}
	
	/**
	 * Returns the specified component's minimum size appropriate for the look and feel.
	 * @return The specified component's minimum size appropriate for the look and feel.
	 */
	public Dimension
	getMinimumSize(JComponent c) { return getPreferredSize(c); }
	
	/**
	 * Returns the specified component's preferred size appropriate for the look and feel.
	 * @return The specified component's preferred size appropriate for the look and feel.
	 */
	public Dimension
	getPreferredSize(JComponent c) {
		if(dial.getDialPixmap() != null) return new Dimension (
			dial.getDialPixmap().getIconHeight(),
			dial.getDialPixmap().getIconHeight()
		);
		else return new Dimension(32, 32);
	}
	
	/**
	 * Returns the specified component's maximum size appropriate for the look and feel.
	 * @return The specified component's maximum size appropriate for the look and feel.
	 */
	public Dimension
	getMaximumSize(JComponent c) { return getPreferredSize(c); }
	
	/**
	 * Paints the specified component appropriate for the look and feel.
	 * @param g the <code>Graphics</code> context in which to paint.
	 * @param c the component being painted.
	 */
	public void
	paint(Graphics g, JComponent c) {
		super.paint(g, c);
		
		if(dial.getDialPixmap() != null) {
			paintPixmapDial(g, c);
			return;
		}
		
		double k, a;
		k = dial.getValue() - dial.getMinimum();
		k /= dial.getMaximum() - dial.getMinimum();
		a = k*(dial.getMaximumAngle() - dial.getMinimumAngle()) + dial.getMinimumAngle();
		a = toRadians(a);
		
		double x1 = (c.getSize().getWidth() / 2);
		double y1 = (c.getSize().getHeight() / 2);
		double r = min(x1, y1) - 6;
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint (
			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
		);
		
		Color color1 = new Color(0xdcdcdc);
		Color color2 = Color.GRAY;
		Color color3 = Color.WHITE;
		
		GradientPaint gp = new GradientPaint (
			(float)(x1 - r), (float)(y1 - r), color1, (float)x1, (float)y1, color2
		);
		g2.setPaint(gp);
		
		Ellipse2D e2 = new Ellipse2D.Double(x1 - r, y1 - r, 2 * r, 2 * r);
		g2.fill(e2);
		
		gp = new GradientPaint (
			(float)(x1 + r), (float)(y1 + r), color1, (float)x1, (float)y1, color3
		);
		
		g2.setPaint(gp);
		double r2 = r - r/4;
		e2 = new Ellipse2D.Double(x1 - r2, y1 - r2, 2 * r2, 2 * r2);
		g2.fill(e2);
		
		double thumbStart = r - (r - r2) - r/2.2;
		double thumbEnd = r - (r - r2) - r/7;
		Line2D l2 = new Line2D.Double (
			x1 - thumbStart * sin(a),
			y1 + thumbStart * cos(a),
			x1 - thumbEnd * sin(a),
			y1 + thumbEnd * cos(a)
		);
		
		g2.setStroke(new java.awt.BasicStroke(2.0f));
		g2.setPaint(color2);
		//g2.setClip(e2);
		g2.draw(l2);
	}
	
	/**
	 * Paints a dial with pixmap is set.
	 * @param g the <code>Graphics</code> context in which to paint.
	 * @param c the component being painted.
	 */
	protected void
	paintPixmapDial(Graphics g, JComponent c) {
		ImageIcon dpm;
		boolean pressed = trackListener.isButtonDown();
		boolean rollover = trackListener.isMouseOver() || trackListener.isButtonDown();
		
		if(!dial.isEnabled()) {
			if(dial.getDisabledDialPixmap() != null) dpm = dial.getDisabledDialPixmap();
			else dpm = dial.getDialPixmap();
		} else if(pressed && dial.getPressedDialPixmap() != null) {
			dpm = dial.getPressedDialPixmap();
		} else if(rollover && dial.getRolloverDialPixmap() != null) {
			dpm = dial.getRolloverDialPixmap();
		} else {
			dpm = dial.getDialPixmap();
		}
		
		int h = dpm.getIconHeight();
		int w = dpm.getIconWidth();
		int n = w / h;
		double k = dial.getValue() - dial.getMinimum();
		k /= (dial.getMaximum() - dial.getMinimum());
		int i = (int)((n - 1) * k);
		
		g.drawImage(dpm.getImage(), 0, 0, h, h, i * h, 0, i*h + h, h, c);
	}
	
	/**
	 * Processes the mouse draging to calculate and set the new dial's value.
	 * Override this method to change the mouse dragging behavior.
	 * @param oldMousePosition The previous mouse position.
	 * @param newMousePosition The new mouse position.
	 */
	protected void
	processMouseDragging(Point oldMousePosition, Point newMousePosition) {
		int oldX = oldMousePosition.x;
		int oldY = oldMousePosition.y;
		int newX = newMousePosition.x;
		int newY = newMousePosition.y;
		
		switch(dial.getMouseHandlerMode()) {
		case LEFT_TO_RIGHT:
			if(newX != oldX) dial.setValue(dial.getValue() + (newX > oldX ? 1 : -1));
			break;
		case RIGHT_TO_LEFT:
			if(newX != oldX) dial.setValue(dial.getValue() + (newX > oldX ? -1 : 1));
			break;
		case DOWN_TO_UP:
			if(newY != oldY) dial.setValue(dial.getValue() + (newY < oldY ? 1 : -1));
			break;
		case UP_TO_DOWN:
			if(newY != oldY) dial.setValue(dial.getValue() + (newY < oldY ? -1 : 1));
			break;
		case LEFT_TO_RIGHT_AND_DOWN_TO_UP:
			if(newX != oldX) dial.setValue(dial.getValue() + (newX > oldX ? 1 : -1));
			if(newY != oldY) dial.setValue(dial.getValue() + (newY < oldY ? 1 : -1));
			break;
		case LEFT_TO_RIGHT_AND_UP_TO_DOWN:
			if(newX != oldX) dial.setValue(dial.getValue() + (newX > oldX ? 1 : -1));
			if(newY != oldY) dial.setValue(dial.getValue() + (newY < oldY ? -1 : 1));
			break;
		case RIGHT_TO_LEFT_AND_DOWN_TO_UP:
			if(newX != oldX) dial.setValue(dial.getValue() + (newX > oldX ? -1 : 1));
			if(newY != oldY) dial.setValue(dial.getValue() + (newY < oldY ? 1 : -1));
			break;
		case RIGHT_TO_LEFT_AND_UP_TO_DOWN:
			if(newX != oldX) dial.setValue(dial.getValue() + (newX > oldX ? -1 : 1));
			if(newY != oldY) dial.setValue(dial.getValue() + (newY < oldY ? -1 : 1));
			break;
		case RADIAL:
			dial.setValue(getValueByPoint(newMousePosition));
			break;
		}
	}
	
	/**
	 * Gets the value that the dial knob will have if
	 * dragging to point <code>p</code> is made in radial mode.
	 * @param p The point for which the respective dial's value should be obtained.
	 * @return The value that the dial knob will have if
	 * dragging to point <code>p</code> is made in radial mode.
	 * @throws IllegalArgumentException if <code>p</code> is <code>null</code>.
	 */
	public int
	getValueByPoint(Point p) {
		if(p == null) throw new IllegalArgumentException("p should be non-null!");
		
		double x = p.x - (dial.getSize().height / 2);
		double y = (dial.getSize().height / 2) - p.y;
		double angle = toDegrees(atan2(x, y)) + 180;
		
		int min = dial.getMinimum();
		int max = dial.getMaximum();
		
		int range = dial.getMaximumAngle() - dial.getMinimumAngle();
		angle -= dial.getMinimumAngle();
		if(angle < 0) angle = 0;
		else if(angle > range) angle = range;
		
		return (int)( (max - min) * (angle / range) + min );
	}
	
	
	private static class Actions extends AbstractAction {
		private static final String DECREMENT = "decrementDialValue";
		private static final String DECREMENT_X4 = "decrementDialValueX4";
		private static final String INCREMENT = "incrementDialValue";
		private static final String INCREMENT_X4 = "incrementDialValueX4";
		private static final String SET_MINIMUM = "setMinimum";
		private static final String SET_MAXIMUM = "setMaximum";
		
		Actions(String name) {
			super(name);
		}
		
		public void
		actionPerformed(ActionEvent e) {
			String key = getValue(Action.NAME).toString();
			Dial dial = (Dial)e.getSource();
			
			int v;
			if(key == DECREMENT) {
				v = dial.getValue();
				if(v > dial.getMinimum()) dial.setValue(v - 1);
			} else if(key == DECREMENT_X4) {
				v = dial.getValue() - 4;
				dial.setValue(v < dial.getMinimum() ? dial.getMinimum() : v);
			} else if(key == INCREMENT) {
				v = dial.getValue();
				if(v < dial.getMaximum()) dial.setValue(v + 1);
			} else if(key == INCREMENT_X4) {
				v = dial.getValue() + 4;
				dial.setValue(v > dial.getMaximum() ? dial.getMaximum() : v);
			} else if(key == SET_MINIMUM) {
				dial.setValue(dial.getMinimum());
			} else if(key == SET_MAXIMUM) {
				dial.setValue(dial.getMaximum());
			}
		}
	}
	
	/** Track mouse movements. */
	private class TrackListener extends MouseInputAdapter {
		private transient int currentMouseX = 0, currentMouseY = 0;
		
		private boolean mouseOver = false;
		private boolean buttonDown = false;
		
		public void
		mouseDragged(MouseEvent e) {
			if((e.getModifiersEx() & e.BUTTON1_DOWN_MASK) != e.BUTTON1_DOWN_MASK) {
				return;
			}
			if(!dial.isEnabled()) return;
			
			Point oldPoint = new Point(currentMouseX, currentMouseY);
			currentMouseX = e.getX();
			currentMouseY = e.getY();
			
			Point newPoint = new Point(currentMouseX, currentMouseY);
			
			processMouseDragging(oldPoint, newPoint);
		}
		
		public void
		mouseEntered(MouseEvent e) {
			mouseOver = true;
			dial.repaint();
		}
		
		public void
		mouseExited(MouseEvent e) {
			mouseOver = false;
			dial.repaint();
		}
		
		public void
		mousePressed(MouseEvent e) {
			if(!dial.hasFocus() && dial.isRequestFocusEnabled()) dial.requestFocus();
			
			if(e.getButton() != MouseEvent.BUTTON1) return;
			
			buttonDown = true;
			dial.repaint();
			
			dial.setValueIsAdjusting(true);
		}
		
		public void
		mouseReleased(MouseEvent e) {
			if(e.getButton() != MouseEvent.BUTTON1) return;
			
			buttonDown = false;
			dial.repaint();
			
			dial.setValueIsAdjusting(false);
		}
		
		public boolean
		isMouseOver() { return mouseOver; }
		
		public boolean
		isButtonDown() { return buttonDown; }
	}
	
	private final Handler handler = new Handler();
	
	private Handler
	getHandler() { return handler; }
	
	private class Handler implements ChangeListener {
		public void
		stateChanged(ChangeEvent e) { dial.repaint(); }
	}
}
