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

package net.sf.juife;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The <code>JuifeI18n</code> class manages the locale-specific data of this library.
 *
 * @author  Grigor Iliev
 */
public class JuifeI18n extends I18n {
	/** Provides the locale-specific data of this library. */
	public static JuifeI18n i18n = new JuifeI18n();
	
	private
	JuifeI18n() {
		setButtonsBundle("net.sf.juife.langprops.ButtonsLabelsBundle");
		setErrorsBundle("net.sf.juife.langprops.ErrorsBundle");
		setLabelsBundle("net.sf.juife.langprops.LabelsBundle");
		setLogsBundle("net.sf.juife.langprops.LogsBundle");
		setMenusBundle("net.sf.juife.langprops.MenuLabelsBundle");
		setMessagesBundle("net.sf.juife.langprops.MessagesBundle");
	}
}
