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

import java.net.URL;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.LayoutManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.border.EtchedBorder;

import javax.swing.plaf.ComponentUI;

import net.sf.juife.swing.NavigationHistoryModel;
import net.sf.juife.swing.NavigationPage;
import net.sf.juife.swing.NavigationPane;

import net.sf.juife.swing.plaf.NavigationPaneUI;


/**
 * Basic L&F implementation of <code>NavigationPaneUI</code>.
 * @author Grigor Iliev
 */
public class BasicNavigationPaneUI extends NavigationPaneUI {
	private final static String propertyPrefix = "NavigationPane" + ".";
	
	private static Icon iconBack = null;
	private static Icon iconForward = null;
	private static Icon iconDown = null;
	
	private final JLabel lTitle = new JLabel();
	private JPanel tiltePane;
	private JButton btnBack;
	private JButton btnForward;
	private JButton btnMenu = new JButton();
	private JPopupMenu npMenu = new JPopupMenu();
	
	private LayoutManager oldLayoutManager;
	
	private NavigationPane navigationPane = null;
	private NavigationPage currentPage = null;
	
	private final Handler handler = new Handler();
	
	
	private
	BasicNavigationPaneUI() { }
	
	/**
	 * Gets the property prefix.
	 * @return The property prefix.
	 */
	protected String
	getPropertyPrefix() { return propertyPrefix; }
	
	/**
	 * Creates a new instance of <code>BasicNavigationPaneUI</code>.
	 * @return A new instance of <code>BasicNavigationPaneUI</code>.
	 */
	public static ComponentUI
	createUI(JComponent c) { return new BasicNavigationPaneUI(); }
	
	/**
	 * Configures the specified component appropriate for the look and feel.
	 * This method is invoked when the ComponentUI instance is being
	 * installed as the UI delegate on the specified component.
	 * @param c The component where this UI delegate is being installed.
	 */
	public void
	installUI(JComponent c) {
		navigationPane = (NavigationPane) c;
		
		installDefaults();
		installListeners();
	}
	
	/** Installs the UI defaults. */
	protected void
	installDefaults() {
		String s;
		
		if(getBackIcon() == null) {
			s = "net/sf/juife/icons/navigation/Back16.gif";
			URL url = ClassLoader.getSystemClassLoader().getResource(s);
			setBackIcon(new ImageIcon(url));
		}
		
		if(getForwardIcon() == null) {
			s = "net/sf/juife/icons/navigation/Forward16.gif";
			URL url = ClassLoader.getSystemClassLoader().getResource(s);
			setForwardIcon(new ImageIcon(url));
		}
		
		if(getDownIcon() == null) {
			s = "net/sf/juife/icons/navigation/Down16.gif";
			URL url = ClassLoader.getSystemClassLoader().getResource(s);
			setDownIcon(new ImageIcon(url));
		}
		
		oldLayoutManager = navigationPane.getLayout();
		navigationPane.setLayout(new BorderLayout());
		
		btnBack = new JButton(getBackIcon());
		
		btnForward = new JButton(getForwardIcon());
		
		btnMenu = new JButton(getDownIcon());
		
		npMenu = new JPopupMenu();
		initMenu();
		
		tiltePane = new JPanel();
		tiltePane.setLayout(new BorderLayout());
		
		Color bgc = new Color(0xCCCCCC);
		navigationPane.setTitleBackground(bgc);
		setTitleBackground(bgc);
		
		initTitlePane();
		
		navigationPane.add(tiltePane, BorderLayout.NORTH);
		
		lTitle.setOpaque(false);
	}
	
	/** Installs the event listeners for the UI. */
	protected void
	installListeners() {
		btnBack.addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				if(!navigationPane.getModel().hasBack()) {
					String s = "BasicNavigationPaneUI: The begining of " +
						"the history list reached unexpectedly"; 
					Logger.getLogger("net.sf.juife").info(s);
					
					return;
				}
				
				if((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
					navigationPane.getModel().goFirst();
				} else {
					navigationPane.getModel().goBack();
				}
			}
		});
		
		btnForward.addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				if(!navigationPane.getModel().hasForward()) {
					String s = "BasicNavigationPaneUI: The end of " +
						"the history list reached unexpectedly"; 
					Logger.getLogger("net.sf.juife").info(s); 
				}
				
				if((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
					navigationPane.getModel().goLast();
				} else {
					navigationPane.getModel().goForward();
				}
			}
		});
		
		navigationPane.addPropertyChangeListener(getHandler());
		//navigationPane.getModel().addActionListener(getHandler());
	}
	
	private void
	initTitlePane() {
		btnMenu.addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				int x = (int)btnMenu.getMinimumSize().getWidth();
				x -= (int)npMenu.getPreferredSize().getWidth();
				int y = (int)btnMenu.getMinimumSize().getHeight() + 1;
				npMenu.show(btnMenu, x, y);
			}
		});
		
		initButton(btnMenu);
		initButton(btnBack);
		initButton(btnForward);
		
		btnBack.setEnabled(false);
		btnForward.setEnabled(false);
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(btnBack, BorderLayout.WEST);
		p.add(btnForward, BorderLayout.EAST);
		p.setOpaque(false);
		
		tiltePane.add(p, BorderLayout.WEST);
		
		lTitle.setHorizontalAlignment(JLabel.CENTER);
		tiltePane.add(lTitle, BorderLayout.CENTER);
		
		btnMenu.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
		tiltePane.add(btnMenu, BorderLayout.EAST);
		tiltePane.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
	}
	
	private void
	initMenu() {
		npMenu.removeAll();
		if(navigationPane.getPages() == null) return;
		for(NavigationPage page : navigationPane.getPages()) initMenuItem(page);
	}
	
	private void
	initMenuItem(final NavigationPage page) {
		JMenuItem mi = new JMenuItem(page.getTitle());
		npMenu.add(mi);
		mi.addActionListener(new ActionListener() {
			public void
			actionPerformed(ActionEvent e) {
				navigationPane.getModel().addPage(page);
			}
		});
	}
	
	private void
	initButton(final JButton button) {
		button.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		
		button.addMouseListener(new MouseAdapter() {
			public void
			mouseEntered(MouseEvent e) {
				if(button.isEnabled()) button.setBorderPainted(true);
			}
			
			public void
			mouseExited(MouseEvent e) { button.setBorderPainted(false); }
		});
	}
	
	private void
	setTitleBackground(Color c) {
		tiltePane.setBackground(c);
		btnBack.setBackground(c);
		btnForward.setBackground(c);
		btnMenu.setBackground(c);
	}
	
	/**
	 * Determines whether the titlebar should be visible.
	 */
	public void
	setTitlebarVisiblie(boolean b) { tiltePane.setVisible(b); }
	
	/**
	 * Reverses configuration which was done on the specified component
	 * during <code>installUI</code>. This method is invoked when this
	 * <code>BasicNavigationPaneUI</code> instance is being removed as
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
		navigationPane.remove(tiltePane);
		navigationPane.setLayout(oldLayoutManager);
		oldLayoutManager = null;
		
		navigationPane = null;
		tiltePane = null;
		btnMenu = null;
		btnBack = null;
		btnForward = null;
		npMenu = null;
	}
	
	/** Uninstalls the event listeners for the UI. */
	protected void
	uninstallListeners() {
		navigationPane.removePropertyChangeListener(getHandler());
		navigationPane.getModel().removeActionListener(getHandler());
	}
	
	private Handler
	getHandler() { return handler; }
	
	/**
	 * Gets the icon for the 'Back' button in the NavigationPane.
	 * @return The icon for the 'Back' button in the NavigationPane.
	 */
	protected Icon
	getBackIcon() { return iconBack; }
	
	/**
	 * Sets the icon for the 'Back' button in the NavigationPane.
	 * @param icon The new icon for the 'Back' button in the NavigationPane.
	 */
	protected void
	setBackIcon(Icon icon) { iconBack = icon; }
	
	/**
	 * Gets the icon for the 'Forward' button in the NavigationPane.
	 * @return The icon for the 'Forward' button in the NavigationPane.
	 */
	protected Icon
	getForwardIcon() { return iconForward; }
	
	/**
	 * Sets the icon for the 'Forward' button in the NavigationPane.
	 * @param icon The new icon for the 'Forward' button in the NavigationPane.
	 */
	protected void
	setForwardIcon(Icon icon) { iconForward = icon; }
	
	/**
	 * Gets the icon for the 'Down' button in the NavigationPane.
	 * @return The icon for the 'Down' button in the NavigationPane.
	 */
	protected Icon
	getDownIcon() { return iconDown; }
	
	/**
	 * Sets the icon for the 'Down' button in the NavigationPane.
	 * @param icon The new icon for the 'Down' button in the NavigationPane.
	 */
	protected void
	setDownIcon(Icon icon) { iconDown = icon; }
	
	private class Handler implements ActionListener, PropertyChangeListener {
		public void
		actionPerformed(ActionEvent e) {
			NavigationPage p = navigationPane.getCurrentPage();
			if(currentPage != null) { navigationPane.remove(currentPage); }
			currentPage = p;
			if(currentPage == null) return;
			
			navigationPane.add(currentPage, BorderLayout.CENTER);
			
			if(!navigationPane.getModel().hasBack()) btnBack.setBorderPainted(false);
			btnBack.setEnabled(navigationPane.getModel().hasBack());
			
			if(!navigationPane.getModel().hasForward())
				btnForward.setBorderPainted(false);
			btnForward.setEnabled(navigationPane.getModel().hasForward());
			
			lTitle.setText(currentPage.getTitle());
			
			navigationPane.revalidate();
			navigationPane.repaint();
		}
		
		public void
		propertyChange(PropertyChangeEvent e) {
			String pn = e.getPropertyName();
			if(pn == "model") {
				NavigationHistoryModel oldModel =
					(NavigationHistoryModel)e.getOldValue();
				
				NavigationHistoryModel newModel =
					(NavigationHistoryModel)e.getNewValue();
				
				if(oldModel != null) oldModel.removeActionListener(getHandler());
				if(newModel != null) newModel.addActionListener(getHandler());
			} else if(pn == "pages") {
				initMenu();
			} else if(pn == "titleBackground") {
				setTitleBackground((Color)e.getNewValue());
			}
		}
	}
}
