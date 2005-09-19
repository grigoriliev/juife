/*
 *   juife - Java User Interface Framework Extensions
 *
 *   Copyright (C) 2005 Grigor Kirilov Iliev
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

package net.sf.juife.plaf;

import javax.swing.plaf.PanelUI;

/**
 * Pluggable look and feel interface for <code>Wizard</code>.
 * @author Grigor Iliev
 */
public abstract class WizardUI extends PanelUI {
	/**
	 * Determines whether the wizard can be cancelled.
	 * @return <code>true</code> if the wizard can be cancelled, <code>false</code> otherwise.
	 */
	public abstract boolean mayCancelWizard();
}
