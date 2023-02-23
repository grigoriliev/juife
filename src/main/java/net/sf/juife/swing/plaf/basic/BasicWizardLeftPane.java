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

import java.awt.*;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import static net.sf.juife.JuifeI18n.i18n;


/**
 * Basic L&F implementation of the wizard's left pane.
 * @author Grigor Iliev
 */
public class BasicWizardLeftPane extends JPanel {
	private final JLabel lSteps = new JLabel(i18n.getLabel("lSteps"));
	private final JPanel stepsPane = new JPanel();
	
	private final JScrollPane spSteps;
	
	private Color fgColor = lSteps.getForeground();
	private final Vector<JComponent> comps = new Vector<JComponent>();
	
	
	/** Creates new instance of <code>BasicWizardLeftPane</code>. */
	BasicWizardLeftPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		lSteps.setAlignmentX(LEFT_ALIGNMENT);
		add(lSteps);
		
		JSeparator sep = new JSeparator();
		sep.setAlignmentX(LEFT_ALIGNMENT);
		sep.setMaximumSize (
			new Dimension(sep.getMaximumSize().width, sep.getPreferredSize().height)
		);
		add(sep);
		
		add(Box.createRigidArea(new Dimension(0, 12)));
		
		initStepsPane();
		spSteps = new JScrollPane(stepsPane);
		spSteps.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spSteps.setBorder(BorderFactory.createEmptyBorder());
		spSteps.setOpaque(false);
		spSteps.getViewport().setOpaque(false);
		spSteps.setAlignmentX(LEFT_ALIGNMENT);
		add(spSteps);
		
		add(Box.createGlue());
		
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(12,12, 11, 11));
	}
	
	private void
	initStepsPane() {
		stepsPane.setLayout(new BoxLayout(stepsPane, BoxLayout.Y_AXIS));
		
		stepsPane.setOpaque(false);
		stepsPane.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	/**
	 * Sets the step list in the wizard's left pane.
	 * @param steps The steps to be shown in the wizard's left pane.
	 * @param currentStep Specifies the current step in the list.
	 */
	protected void
	setSteps(String[] steps, String currentStep) {
		stepsPane.removeAll();
		comps.removeAllElements();
		
		for(int i = 0; i < steps.length; i++) {
			boolean current = (steps[i] == currentStep);
			JPanel p = createStepPane(i + 1, steps[i], current);
			stepsPane.add(p);
			stepsPane.add(Box.createRigidArea(new Dimension(0, 5)));
			
			if(current) stepsPane.scrollRectToVisible(p.getBounds());
		}
		
		stepsPane.add(Box.createGlue());
	}
	
	private JPanel
	createStepPane(int index, String title, boolean current) {
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setAlignmentX(LEFT_ALIGNMENT);
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		JLabel lNum = new JLabel(index + ".");
		lNum.setAlignmentY(TOP_ALIGNMENT);
		lNum.setForeground(getForegroundColor());
		p.add(lNum);
		comps.add(lNum);
		
		p.add(Box.createRigidArea(new Dimension(6, 0)));
		
		JTextArea txtDesc = new JTextArea(title);
		txtDesc.setAlignmentY(TOP_ALIGNMENT);
		txtDesc.setMaximumSize(new Dimension(30000, txtDesc.getPreferredSize().width));
		txtDesc.setLineWrap(true);
		txtDesc.setWrapStyleWord(true);
		txtDesc.setEnabled(false);
		txtDesc.setOpaque(false);
		txtDesc.setBorder(BorderFactory.createEmptyBorder());
		txtDesc.setForeground(getForegroundColor());
		txtDesc.setDisabledTextColor(getForegroundColor());
		
		if(current) {
			lNum.setFont(lNum.getFont().deriveFont(Font.BOLD));
			txtDesc.setFont(txtDesc.getFont().deriveFont(Font.BOLD));
			//getPane().setBackground(new Color(0xccccff));
			//getPane().setOpaque(true);
		} else {
			lNum.setFont(lNum.getFont().deriveFont(Font.PLAIN));
			txtDesc.setFont(txtDesc.getFont().deriveFont(Font.PLAIN));
			//getPane().setOpaque(false);
		}
		
		p.add(txtDesc);
		comps.add(txtDesc);
		
		return p;
	}
	
	public void
	setForegroundColor(Color c) {
		fgColor = c;
		updateFg();
	}
	
	public Color
	getForegroundColor() { return fgColor; }
	
	private void
	updateFg() {
		lSteps.setForeground(getForegroundColor());
		
		for(JComponent c : comps) {
			c.setForeground(getForegroundColor());
			if(c instanceof JTextArea) {
				((JTextArea)c).setDisabledTextColor(getForegroundColor());
			}
		}
	}
}
