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

package com.grigoriliev.jsampler.juife;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * This class provides methods for managing the locale-specific
 * data of a particular UI application or library.
 * @author Grigor Iliev
 */
public class I18n {
	private Locale currentLocale = null;
	
	private ResourceBundle buttonLabels;	// buttons labels
	private ResourceBundle errors;		// errors
	private ResourceBundle labels;		// labels
	private ResourceBundle logMessages;	// log messages
	private ResourceBundle menuLabels;	// menu labels
	private ResourceBundle messages;	// messages
	
	private String buttonsBundle;
	private String errorsBundle;
	private String labelsBundle;
	private String logsBundle;
	private String menusBundle;
	private String messagesBundle;
	
	
	/** Creates a new instance of <code>I18n</code>. */
	public
	I18n() { }
	
	/**
	 * Sets the current locale using the specified language and country.
	 * @param language lowercase two-letter ISO-639 code.
	 * @param country uppercase two-letter ISO-3166 code.
	 */
	public void
	setCurrentLocale(String language, String country) {
		currentLocale = new Locale(language, country);
		
		if(buttonsBundle == null) buttonLabels = null;
		else buttonLabels = ResourceBundle.getBundle(buttonsBundle, getCurrentLocale());
		
		if(labelsBundle == null) labels = null;
		else labels = ResourceBundle.getBundle(labelsBundle, getCurrentLocale());
		
		if(menusBundle == null) menuLabels = null;
		else menuLabels = ResourceBundle.getBundle(menusBundle, getCurrentLocale());
		
		if(errorsBundle == null) errors = null;
		else errors = ResourceBundle.getBundle(errorsBundle, getCurrentLocale());
		
		if(messagesBundle == null) messages = null;
		else messages = ResourceBundle.getBundle(messagesBundle, getCurrentLocale());
		
		if(logsBundle == null) logMessages = null;
		else logMessages = ResourceBundle.getBundle(logsBundle, getCurrentLocale());
	}
	
	/**
	 * Gets the current locale.
	 * @return The current locale.
	 */
	public Locale
	getCurrentLocale() {
		if(currentLocale == null) setCurrentLocale("en", "US");
		return currentLocale;
	}
	
	/**
	 * Sets the resource bundle that will provide the button labels.
	 * @param baseName The base name of the resource bundle that will
	 * provide the button labels (a fully qualified class name).
	 */
	public void
	setButtonsBundle(String baseName) {
		buttonsBundle = baseName;
		
		if(buttonsBundle == null) buttonLabels = null;
		else buttonLabels = ResourceBundle.getBundle(buttonsBundle, getCurrentLocale());
	}
	
	/**
	 * Sets the resource bundle that will provide the common labels.
	 * @param baseName The base name of the resource bundle that will
	 * provide the common labels (a fully qualified class name).
	 */
	public void
	setLabelsBundle(String baseName) {
		labelsBundle = baseName;
		
		if(labelsBundle == null) labels = null;
		else labels = ResourceBundle.getBundle(labelsBundle, getCurrentLocale());
	}
	
	/**
	 * Sets the resource bundle that will provide the menu labels.
	 * @param baseName The base name of the resource bundle that will
	 * provide the menu labels (a fully qualified class name).
	 */
	public void
	setMenusBundle(String baseName) {
		menusBundle = baseName;
		
		if(menusBundle == null) menuLabels = null;
		else menuLabels = ResourceBundle.getBundle(menusBundle, getCurrentLocale());
	}
	
	/**
	 * Sets the resource bundle that will provide the error messages.
	 * @param baseName The base name of the resource bundle that will
	 * provide the error messages (a fully qualified class name).
	 */
	public void
	setErrorsBundle(String baseName) {
		errorsBundle = baseName;
		
		if(errorsBundle == null) errors = null;
		else errors = ResourceBundle.getBundle(errorsBundle, getCurrentLocale());
	}
	
	/**
	 * Sets the resource bundle that will provide the common messages.
	 * @param baseName The base name of the resource bundle that will
	 * provide the common messages (a fully qualified class name).
	 */
	public void
	setMessagesBundle(String baseName) {
		messagesBundle = baseName;
		
		if(messagesBundle == null) messages = null;
		else messages = ResourceBundle.getBundle(messagesBundle, getCurrentLocale());
	}
	
	/**
	 * Sets the resource bundle that will provide the log messages.
	 * @param baseName The base name of the resource bundle that will
	 * provide the log messages (a fully qualified class name).
	 */
	public void
	setLogsBundle(String baseName) {
		logsBundle = baseName;
		
		if(logsBundle == null) logMessages = null;
		else logMessages = ResourceBundle.getBundle(logsBundle, getCurrentLocale());
	}
	
	/**
	 * Gets a string for the given key from the specified resource bundle.
	 * @param key The key for the desired string.
	 * @param rb The resource bundle from which a string should be obtained.
	 * @return The string for the given key.
	 */
	private String
	getString(String key, ResourceBundle rb) {
		return rb.getString(key);
	}
	
	/**
	 * Gets a string for the given pattern and arguments from the specified resource bundle.
	 * @param pattern The pattern for the desired string.
	 * @param rb The resource bundle from which a string should be obtained.
	 * @param arguments The arguments to be used to format the string.
	 * @return The string for the given pattern and arguments.
	 */
	private String
	getString(String pattern, ResourceBundle rb, Object[] arguments) {
		MessageFormat mf = new MessageFormat("");
		mf.setLocale(getCurrentLocale());
		mf.applyPattern(getString(pattern, rb));
		return mf.format(arguments);
	}

	/**
	 * Gets a button label for the given key.
	 * @param key The key for the desired button label.
	 * @return The button label for the given key.
	 */
	public String
	getButtonLabel(String key) {
		return getString(key, buttonLabels);
	}
	
	/**
	 * Formats a button label using the specified pattern and arguments.
	 * @param pattern The pattern for the desired button label.
	 * @param arguments The arguments to be used to format the button label.
	 * @return The button label for the given pattern and arguments.
	 */
	public String
	getButtonLabel(String pattern, Object... arguments) {
		return getString(pattern, buttonLabels, arguments);
	}
	
	/**
	 * Gets an error message for the given key.
	 * @param key The key for the desired error message.
	 * @return The error message for the given key.
	 */
	public String
	getError(String key) {
		return getString(key, errors);
	}
	
	/**
	 * Formats an error message using the specified pattern and arguments.
	 * @param pattern The pattern for the desired error message.
	 * @param arguments The arguments to be used to format the error message.
	 * @return The error message for the given pattern and arguments.
	 */
	public String
	getError(String pattern, Object... arguments) {
		return getString(pattern, errors, arguments);
	}

	/**
	 * Gets a label for the given key.
	 * @param key The key for the desired label.
	 * @return The label for the given key.
	 */
	public String
	getLabel(String key) {
		return getString(key, labels);
	}
	
	/**
	 * Formats a label using the specified pattern and arguments.
	 * @param pattern The pattern for the desired label.
	 * @param arguments The arguments to be used to format the label.
	 * @return The label for the given pattern and arguments.
	 */
	public String
	getLabel(String pattern, Object... arguments) {
		return getString(pattern, labels, arguments);
	}
	
	/**
	 * Gets a log message for the given key.
	 * @param key The key for the desired log message.
	 * @return The log message for the given key.
	 */
	public String
	getLogMessage(String key) {
		return getString(key, logMessages);
	}
	
	/**
	 * Formats a log message using the specified pattern and arguments.
	 * @param pattern The pattern for the desired log message.
	 * @param arguments The arguments to be used to format the log message.
	 * @return The log message for the given pattern and arguments.
	 */
	public String
	getLogMessage(String pattern, Object... arguments) {
		return getString(pattern, logMessages, arguments);
	}
	
	/**
	 * Gets a menu label for the given key.
	 * @param key The key for the desired menu label.
	 * @return The menu label for the given key.
	 */
	public String
	getMenuLabel(String key) {
		return getString(key, menuLabels);
	}
	
	/**
	 * Formats a menu label using the specified pattern and arguments.
	 * @param pattern The pattern for the desired menu label.
	 * @param arguments The arguments to be used to format the menu label.
	 * @return The menu label for the given pattern and arguments.
	 */
	public String
	getMenuLabel(String pattern, Object... arguments) {
		return getString(pattern, menuLabels, arguments);
	}
	
	/**
	 * Gets a message for the given key.
	 * @param key The key for the desired message.
	 * @return The message for the given key.
	 */
	public String
	getMessage(String key) {
		return getString(key, messages);
	}
	
	/**
	 * Formats a message using the specified pattern and arguments.
	 * @param pattern The pattern for the desired message.
	 * @param arguments The arguments to be used to format the message.
	 * @return The message for the given pattern and arguments.
	 */
	public String
	getMessage(String pattern, Object... arguments) {
		return getString(pattern, messages, arguments);
	}
}
