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

package net.sf.juife.wizard;

import java.util.Vector;


/**
 * Default implementation of the <code>WizardModel</code> interface.
 * @author Grigor Iliev
 */
public class DefaultWizardModel extends AbstractWizardModel {
	private final Vector<WizardPage> pages = new Vector<WizardPage>();
	private int pos = -1;
	private int last = -1;
	
	private Vector<String> steps = null;
	private final Vector<String> stepsMap = new Vector<String>();
	
	
	/** Creates a new instance of <code>DefaultWizardModel</code> */
	public
	DefaultWizardModel() {
	}
	
	/**
	 * Creates a new instance of <code>DefaultWizardModel</code> using the specified pages.
	 * @param pages A <code>WizardPage</code> array providing
	 * the pages this wizard will use arranged in order of appearance.
	 */
	public
	DefaultWizardModel(WizardPage[] pages) { setPages(pages); }
	
	/**
	 * Adds the specified wizard page to the end of the list.
	 * @param page The non <code>null</code> wizard page to be added.
	 * @throws IllegalArgumentException If the <code>page</code> is <code>null</code>.
	 */
	public void
	addPage(WizardPage page) {
		if(page == null) throw new IllegalArgumentException("page must be non-null");
		pages.add(page);
	}
	
	/**
	 * Sets the pages this wizard will use in the specified order.
	 * Note that this method removes all previously added pages.
	 * @param pages A <code>WizardPage</code> array providing
	 * the pages this wizard will use arranged in order of appearance.
	 * @throws IllegalArgumentException If <code>pages</code> contains
	 * <code>null</code> element.
	 */
	public void
	setPages(WizardPage[] pages) {
		this.pages.removeAllElements();
		if(pages == null) return;
		
		for(WizardPage p : pages) addPage(p);
	}
	
	/**
	 * Sets the last page of the wizard to be the last page of the model.
	 * @throws IllegalArgumentException If the model is empty.
	 */
	public void
	setLast() {
		if(pages.size() == 0) throw new IllegalArgumentException (
			"The wizard model is empty"
		);
		
		last = pages.size() - 1;
	}
	
	/**
	 * Sets the last page of the wizard.
	 * The last page in the wizard is not necessarily the last page in the model.
	 * It is just the page which will be displayed when the user clicks the 'Last' button.
	 * <p>Note that the specified page must be added to the
	 * model before the invocation of this method.</p>
	 * @param page Specifies the last page of the wizard.
	 * @throws IllegalArgumentException If the model does not contain the specified page.
	 */
	public void
	setLast(WizardPage page) {
		last = pages.indexOf(page);
		if(last == -1) throw new IllegalArgumentException (
			"The wizard model does not contain the specified page"
		);
	}
	
	/**
	 * Determines whether there is a next page in this wizard.
	 * @return <code>true</code> if there is a next page in this wizard,
	 * <code>false</code> otherwise.
	 * @see #next
	 */
	public boolean
	hasNext() { return pos + 1 < pages.size(); }
	
	/**
	 * Moves to the next page in the wizard.
	 * @return The next page in the wizard.
	 * @see #hasNext
	 */
	public WizardPage
	next() {
		WizardPage wp = pages.get(++pos);
		fireActionPerformed();
		return wp;
	}
	
	/**
	 * Determines whether there is a previous page in this wizard.
	 * @return <code>true</code> if there is a previous page in this wizard,
	 * <code>false</code> otherwise.
	 * @see #previous
	 */
	public boolean
	hasPrevious() { return pos > 0; }
	
	/**
	 * Moves to the previous page in the wizard.
	 * @return The previous page in the wizard.
	 * @see #hasPrevious
	 */
	public WizardPage
	previous() {
		WizardPage wp = pages.get(--pos);
		fireActionPerformed();
		return wp;
	}
	
	
	/**
	 * Determines whether the last page is specified.
	 * If the last page is specified the 'Last' button in the wizard will be enabled.
	 * @return <code>true</code> if last page is specified,
	 * <code>false</code> otherwise.
	 * @see #last
	 */
	public boolean
	hasLast() { return last != -1; }
	
	/**
	 * Moves to the last page in the wizard.
	 * Last page is the page which will be displayed when the user clicks the 'Last' button.
	 * Note that the last page in the wizard is not necessarily the last page in the model.
	 * @return The page specified as last in the wizard.
	 * @see #hasLast
	 */
	public WizardPage
	last() {
		WizardPage wp = pages.get(pos = last);
		fireActionPerformed();
		return wp;
	}
	
	/**
	 * Gets the current page.
	 * @return The current page or <code>null</code> if there isn't current page yet.
	 */
	public WizardPage
	getCurrentPage() { return (pos == -1) ? null : pages.get(pos); }
	
	/**
	 * Gets a <code>String</code> array providing the current list of steps.
	 * @return A <code>String</code> array providing the current list of steps or
	 * <code>null</code> if the list of steps is not available.
	 */
	public String[]
	getSteps() {
		return steps == null ? null : steps.toArray(new String[0]);
	}
	
	/**
	 * Gets the current step in the wizard.
	 * @return The current step in the wizard or
	 * <code>null</code> if there is no current step.
	 */
	public String
	getCurrentStep() { return pos == -1 ? null : stepsMap.get(pos); }
	
	/**
	 * Adds a step that corresponds to one wizard page.
	 * @param step The step title.
	 */
	public void
	addStep(String step) { addStep(step, 1); }
	
	/**
	 * Adds a step to this model.
	 * @param step The step title.
	 * @param pageCount The number of pages this step corresponds to.
	 */
	public void
	addStep(String step, int pageCount) {
		if(steps == null) steps = new Vector<String>();
		
		steps.add(step);
		for(int i = 0; i < pageCount; i++) stepsMap.add(step);
	}
}
